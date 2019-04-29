/*
 * Copyright (c) 2018 UTStarcom, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.rest;

public class IfIndexGenerator {

    static final int FALSE = 0;
    static final int TRUE = 1;
    public static final int VIRTUAL_TRUNK_INTF = 540020224;

    public static int cardType(int ifIdx) {
        return (ifIdx & IfIndexInfo.CtrlType_Mask) >> 29;
    }

    // 如果是100G的Trunk上的3个通道，不能用此法生成
    public static int formIfIndex(IfIndexInfo info) throws RuntimeException {
        int ifIdx = 0;
        ifIdx = info.neNumber;
        ifIdx = (ifIdx << 2) + info.controlType;
        ifIdx = (ifIdx << 6) + info.slotIdx;
        ifIdx = (ifIdx << 1) + info.isVlan;
        ifIdx = (ifIdx << 1) + info.isAggregate;

        if (info.isExternal == TRUE)
            ifIdx = (ifIdx << 1) + 0;
        else
            ifIdx = (ifIdx << 1) + 1;

        ifIdx = (ifIdx << 1) + info.isL3;
        ifIdx = (ifIdx << 7) + info.portIdx;

        if ((info.isVlan == FALSE && info.isAggregate == FALSE && info.isExternal == TRUE)
        /*
         * || (info.isVlan == TRUE && info.isAggregate == FALSE && info.isExternal == FALSE)
         */) {
            ifIdx = handleExternalServiceType(ifIdx, 5, info.serviceType);
            ifIdx = (ifIdx << 7);
            // if (info.isVlan == TRUE) {
            // ifIdx += info.vlanPort;
            // }
            if (info.serviceType == ServiceType.PW) {
                ifIdx += (info.serviceId & 0x3f80) << 5 + (info.serviceId & 0x7f);
            }
        } else if (info.isVlan == FALSE && info.isAggregate == FALSE && info.isExternal == FALSE) {
            ifIdx = handleInternalServiceType(ifIdx, 4, info.serviceType);
            ifIdx = (ifIdx << 8);

            if (info.serviceType == ServiceType.ETHAC) {
                ifIdx += ((info.serviceId & 0x1fc0) << 6) + (info.serviceId & 0x3f);
            } else if (info.serviceType == ServiceType.E1AC) {
                ifIdx += ((info.serviceId & 0x1fc0) << 6) + (info.serviceId & 0x3f) + (1 << 6);
            } else if (info.serviceType == ServiceType.T1AC) {
                ifIdx += ((info.serviceId & 0x1fc0) << 6) + (info.serviceId & 0x3f) + (2 << 6);
            } else if (info.serviceType == ServiceType.SDHAC) {
                ifIdx += ((info.serviceId & 0x1fc0) << 6) + (info.serviceId & 0x3f) + (3 << 6);
            } else if (info.serviceType == ServiceType.ATMAC) {
                ifIdx += ((info.serviceId & 0x1fc0) << 6) + (info.serviceId & 0x3f);
            } else if (info.serviceType == ServiceType.PW) {
                ifIdx += ((info.serviceId & 0x3f80) << 5) + (info.serviceId & 0x7f);
            }
        }
        // else if (info.isVlan == TRUE && info.isAggregate == FALSE)
        else if (info.isVlan == TRUE) {
            ifIdx = (ifIdx << 12) + info.vlanPort;
        } else if (info.isAggregate == TRUE) {
            switch (info.serviceType) {
                case ServiceType.VirtualTrunkAc:
                    ifIdx = (ifIdx << 4) + 14;
                    break;
                default:
                    ifIdx = (ifIdx << 4);
            }
            ifIdx = ifIdx << 8;
        }

        // 是vlanIntf或subIntf， 高3bit用于表示service type
        if (info.isVlan == TRUE) {
            switch (info.serviceType) {
                case ServiceType.Ethernet:
                    ifIdx = (ifIdx & ~(IfIndexInfo.VlanSvcType_Mask)) + (1 << 29);
                    break;
                case ServiceType.POS:
                    ifIdx = (ifIdx & ~(IfIndexInfo.VlanSvcType_Mask)) + (2 << 29);
                    break;
            }
        }
        return ifIdx;
    }

    // 如果是普通trunk, FID格式是\\e=1\\t=64;
    // 如果是100G通道上的trunk, FID格式是\\e=1\\t=64\\p=8\\ch=2
    // 其中64是trunk唯一的ID， 8是100G通道1上的Trunk ID， 2是表示通道2
    public static int formTrunkIfIndex(String trunkFid) throws RuntimeException {
        IfIndexInfo info = IfIndexInfo.createTrunkIfInfo(trunkFid);

        int ch = FidUtils.getFidIndex(trunkFid, "ch");
        if (ch == -1)
            return formIfIndex(info);
        return formIfIndex(info) + info.tId;
    }

    public static int formVirtualPort(int slot) {
        IfIndexInfo info = IfIndexInfo.createVirtualIfinfo(slot);
        return formIfIndex(info);
    }

    public static String generateTrunkIfIndex(String trunkFid) {

        int neIdx = FidUtils.getFidIndex(trunkFid, "e");
        if (neIdx != -1)
            return generateTrunkIfIndex2(trunkFid);

        int ifIdx = formTrunkIfIndex(trunkFid);
        return "\\\\\\interface=" + ifIdx;
    }

    public static String generateTrunkIfIndex2(String trunkFid) {
        int ifIdx = formTrunkIfIndex(trunkFid);
        return FidUtils.getNeFid(trunkFid) + "\\\\interface=" + ifIdx;
    }

    public static int getPortIdx(int ifIdx) {
        return ((ifIdx & IfIndexInfo.Port_Mask) >> 12) & 0x7F;
    }

    /**
     * to int
     */
    public static int getServiceType(int ifIdx) {
        int portType = (ifIdx & IfIndexInfo.PortType_Mask) >> 20;
        if (portType == 0 || portType == 5) {
            int serviceType = (ifIdx & IfIndexInfo.ExtSvcType_Mask) >> 7;
            return serviceType;
        } else if (portType == 1) {
            int serviceType = (ifIdx & IfIndexInfo.IntSvcType_Mask) >> 8;
            switch (serviceType) {
                case 0:
                    serviceType = ServiceType.Other;
                    break;
                case 1:
                    serviceType = ServiceType.GFP;
                    break;
                case 2:
                    serviceType = ServiceType.PW;
                    break;
                case 3:
                    serviceType = ServiceType.MPLSTunnel;
                    break;
                case 4:
                    serviceType = ServiceType.VlanTrunk_Tunnel;
                    break;
                case 5:
                    serviceType = ServiceType.PBB_ISID;
                    break;
                case 6:
                    serviceType = ServiceType.PBB_BVLAN;
                    break;
                case 7:
                    serviceType = ServiceType.PBT_ISID;
                    break;
                case 8:
                    serviceType = ServiceType.PBT_BVLAN;
                    break;
                case 9:
                    serviceType = ServiceType.Ethernet;
                    break;
                case 10:
                    serviceType = ServiceType.LoopBack;
                    break;
                case 11:
                    serviceType = ServiceType.Console;
                    break;
                case 12:
                    serviceType = ServiceType.AC1;
                    break;
                case 13:
                    serviceType = ServiceType.AC2;
                    break;
            }
            return serviceType;
        } else {
            if ((portType & 2) > 0) {
                int serviceType = (ifIdx & IfIndexInfo.IntSvcType_Mask) >> 8;
                if (serviceType == 14)
                    return ServiceType.VirtualTrunkAc;
            }
        }
        return -1;
    }

    public static int getSlotIdx(int ifIdx) {
        if (isAggregation(ifIdx))
            return 0;
        return ((ifIdx & IfIndexInfo.SlotIndex_Mask) >> 23);
    }

    public static int getSubSlotIdx(int ifIdx) {
        if (isSubCardMode(ifIdx))
            return ifIdx & IfIndexInfo.SubCardSlot_Mask;
        return -1;
    }

    public static int getTrunkChanIdx(int ifIdx) {
        if (!isAggregation(ifIdx))
            return 0;
        return ((ifIdx & IfIndexInfo.SlotIndex_Mask) >> 23);
    }

    // 如果是普通trunk, FID格式是\\e=1\\t=64;
    // 如果是100G trunk, FID格式是\\e=1\\t=64\\p=8\\ch=2 其中64是trun唯一ID，
    // 8是100G通道1上的Trunk ID， 2是表示通道2
    public static String getTrunkFId(int ifIdx) {
        if (!isAggregation(ifIdx))
            return null;
        int port = getPortIdx(ifIdx) + 1;
        int tIdx = (ifIdx & IfIndexInfo.TrunkPort100G_Mask) + 1;
        int chanIdx = ((ifIdx & IfIndexInfo.SlotIndex_Mask) >> 23);
        if (chanIdx == 0)
            return "\\t=" + port;
        return "\\t=" + port + "\\p=" + tIdx + "\\ch=" + chanIdx;
    }

    public static String getTrunkName(int ifIdx) {
        if (!isAggregation(ifIdx))
            return null;
        int port = getPortIdx(ifIdx) + 1;
        int tIdx = (ifIdx & IfIndexInfo.TrunkPort100G_Mask) + 1;
        int chanIdx = ((ifIdx & IfIndexInfo.SlotIndex_Mask) >> 23);
        return chanIdx == 0 ? String.valueOf(port) : (tIdx + "." + chanIdx);
    }

    public static int handleExternalServiceType(int ifIdx, int offset, int serviceType) {
        switch (serviceType) {
            case ServiceType.Other:
                ifIdx = (ifIdx << offset) + 0;
                break;
            case ServiceType.ATM:
                ifIdx = (ifIdx << offset) + 1;
                break;
            case ServiceType.ATMVPC:
                ifIdx = (ifIdx << offset) + 2;
                break;
            case ServiceType.ATMVCC:
                ifIdx = (ifIdx << offset) + 3;
                break;
            case ServiceType.SDH_SONET:
                ifIdx = (ifIdx << offset) + 4;
                break;
            case ServiceType.SONETPath:
                ifIdx = (ifIdx << offset) + 5;
                break;
            case ServiceType.SONETVT:
                ifIdx = (ifIdx << offset) + 6;
                break;
            case ServiceType.PPP:
                ifIdx = (ifIdx << offset) + 7;
                break;
            case ServiceType.E1:
                ifIdx = (ifIdx << offset) + 8;
                break;
            case ServiceType.T1_DS1:
                ifIdx = (ifIdx << offset) + 9;
                break;
            case ServiceType.DS0:
                ifIdx = (ifIdx << offset) + 10;
                break;
            case ServiceType.DS0Bundle:
                ifIdx = (ifIdx << offset) + 11;
                break;
            case ServiceType.POS:
                ifIdx = (ifIdx << offset) + 12;
                break;
            case ServiceType.SyncEthernet:
                ifIdx = (ifIdx << offset) + 13;
                break;
            case ServiceType.FiberChannel:
                ifIdx = (ifIdx << offset) + 14;
                break;
            case ServiceType.RPR:
                ifIdx = (ifIdx << offset) + 15;
                break;
            case ServiceType.PW:
                ifIdx = (ifIdx << offset) + 16;
                break;
            case ServiceType.MPLSTunnel:
                ifIdx = (ifIdx << offset) + 17;
                break;
            case ServiceType.TELink:
                ifIdx = (ifIdx << offset) + 18;
                break;
            case ServiceType.VlanTrunk_Tunnel:
                ifIdx = (ifIdx << offset) + 19;
                break;
            case ServiceType.PBB_ISID:
                ifIdx = (ifIdx << offset) + 20;
                break;
            case ServiceType.PBB_BVLAN:
                ifIdx = (ifIdx << offset) + 21;
                break;
            case ServiceType.PBT_ISID:
                ifIdx = (ifIdx << offset) + 22;
                break;
            case ServiceType.PBT_BVLAN:
                ifIdx = (ifIdx << offset) + 23;
                break;
            case ServiceType.Ethernet:
                ifIdx = (ifIdx << offset) + 24;
                break;
            case ServiceType.Eth_L3:
                ifIdx = (ifIdx << offset) + 35;
                break;
            default:
                throw new RuntimeException("Illegal service type: " + serviceType);
        }
        return ifIdx;
    }
    public static String toIfIndex(String fid) {
        IfIndexInfo info = new IfIndexInfo();
        info.setPhyFid(fid);
        info.isVlan = FALSE;
        info.isExternal = TRUE;
        info.isAggregate = FALSE;
        info.isL3 = TRUE;
        info.serviceType = ServiceType.Ethernet;
        int index = formIfIndex(info);
        String ifIdx = "\\\\\\interface=" + index;
        return ifIdx;
    }
    public static int handleInternalServiceType(int ifIdx, int offset, int serviceType) {
        switch (serviceType) {
            case ServiceType.Other:
                ifIdx = (ifIdx << offset) + 0;
                break;
            case ServiceType.GFP:
                ifIdx = (ifIdx << offset) + 1;
                break;
            case ServiceType.PW:
                ifIdx = (ifIdx << offset) + 2;
                break;
            case ServiceType.MPLSTunnel:
                ifIdx = (ifIdx << offset) + 3;
                break;
            case ServiceType.VlanTrunk_Tunnel:
                ifIdx = (ifIdx << offset) + 4;
                break;
            case ServiceType.PBB_ISID:
                ifIdx = (ifIdx << offset) + 5;
                break;
            case ServiceType.PBB_BVLAN:
                ifIdx = (ifIdx << offset) + 6;
                break;
            case ServiceType.PBT_ISID:
                ifIdx = (ifIdx << offset) + 7;
                break;
            case ServiceType.PBT_BVLAN:
                ifIdx = (ifIdx << offset) + 8;
                break;
            case ServiceType.Ethernet:
                ifIdx = (ifIdx << offset) + 9;
                break;
            case ServiceType.LoopBack:
                ifIdx = (ifIdx << offset) + 10;
                break;

            case ServiceType.Console:
                ifIdx = (ifIdx << offset) + 11;
                break;
            case ServiceType.ETHAC:
            case ServiceType.E1AC:
            case ServiceType.T1AC:
            case ServiceType.SDHAC:
                ifIdx = (ifIdx << offset) + 12;
                break;
            case ServiceType.ATMAC:
                ifIdx = (ifIdx << offset) + 13;
                break;
            default:
                throw new RuntimeException("Illegal service type: " + serviceType);
        }
        return ifIdx;
    }

    public static boolean isAggregation(int ifIdx) {
        int portType = (ifIdx & IfIndexInfo.PortType_Mask) >> 20;
        return (portType & 2) > 0;
    }

    public static boolean isL3(int ifIdx) {
        int portIdx = (ifIdx & IfIndexInfo.Port_Mask) >> 12;
        return (portIdx & 0x80) > 0;
    }

    public static boolean isL3Interface(String fid) {
        int v = FidUtils.getFidIndex(fid, "interface");
        return IfIndexGenerator.isL3(v);
    }

    public static boolean isSubCardMode(int ifIdx) {
        int portType = (ifIdx & IfIndexInfo.PortType_Mask) >> 20;
        if ((portType & 1) > 0 && (portType & 2) == 0 && (portType & 4) > 0) {
            return true;
        }
        return false;
    }


    // for SR
    public static String parseToIfIndex(String ifIndex) {
        try {
            int neIdx = FidUtils.getFidIndex(ifIndex, "e");
            if (neIdx != -1)
                return parseToIfIndex2(ifIndex);

            int ifIdx = FidUtils.getFidIndex(ifIndex, "interface");
            if (ifIdx == -1)
                return ifIndex;

            if (isAggregation(ifIdx)) {
                return "\\" + getTrunkFId(ifIdx);
            } else if (IfIndexGenerator.getServiceType(ifIdx) == ServiceType.ATMVPC) {
                int sIdx = getSlotIdx(ifIdx);
                int iIdx = getPortIdx(ifIdx);
                return new StringBuilder().append("\\s=").append(sIdx + 1).append("\\c=1\\j=").append(iIdx + 1)
                    .append("\\i=1").toString();
            }
            return parseToIfIndexInfo(neIdx, ifIdx).formPhyFid();
        } catch (Exception e) {
            return ifIndex;
        }
    }

    public static String parseToIfIndex2(String ifIndex) {
        try {
            int neIdx = FidUtils.getFidIndex(ifIndex, "e");
            int ifIdx = FidUtils.getFidIndex(ifIndex, "interface");
            if (neIdx == -1 || ifIdx == -1)
                return ifIndex;

            if (isAggregation(ifIdx)) {
                return "\\e=" + neIdx + getTrunkFId(ifIdx);
            } else if (IfIndexGenerator.getServiceType(ifIdx) == ServiceType.ATMVPC) {
                int sIdx = getSlotIdx(ifIdx);
                int iIdx = getPortIdx(ifIdx);
                return new StringBuilder("\\e=").append(neIdx).append("\\s=").append(sIdx + 1).append("\\c=1\\j=")
                    .append(iIdx + 1).append("\\i=1").toString();
            }
            return parseToIfIndexInfo(neIdx, ifIdx).formPhyFid2();
        } catch (Exception e) {
            return ifIndex;
        }
    }

    public static IfIndexInfo parseToIfIndexInfo(int neIdx, int ifIdx) {
        IfIndexInfo ifInfo = new IfIndexInfo();
        ifInfo.neIdx = neIdx;
        if ((ifIdx & IfIndexInfo.NeNO_Mask) == TRUE) {
            ifInfo.neNumber = IfIndexInfo.TWO_NE;
        } else {
            ifInfo.neNumber = IfIndexInfo.ONE_NE;
        }
        // Control...
        int controlType = ifIdx & IfIndexInfo.CtrlType_Mask >> 29;
        ifInfo.controlType = controlType;
        // SlotIndex...
        ifInfo.slotIdx = (ifIdx & IfIndexInfo.SlotIndex_Mask) >> 23;

        // PortType...
        int portType = (ifIdx & IfIndexInfo.PortType_Mask) >> 20;

        // TODO BIT1
        if ((portType & 1) > 0) {
            ifInfo.isExternal = FALSE;
        } else {
            ifInfo.isExternal = TRUE;
        }

        // TODO: BIT2
        if ((portType & 2) > 0) {
            ifInfo.isAggregate = TRUE;
        } else {
            ifInfo.isAggregate = FALSE;
        }

        // TODO: BIT3
        if ((portType & 4) > 0) {
            ifInfo.isVlan = TRUE;
            if (ifInfo.isExternal == TRUE) {
                ifInfo.isVlanIntf = TRUE;
                ifInfo.isSubIntf = FALSE;
            } else {
                ifInfo.isVlanIntf = FALSE;
                ifInfo.isSubIntf = TRUE;
            }

        } else {
            ifInfo.isVlan = FALSE;
            ifInfo.isVlanIntf = FALSE;
            ifInfo.isSubIntf = FALSE;
        }

        // Port...
        int portIdx = (ifIdx & IfIndexInfo.Port_Mask) >> 12;
        // TODO: BIT7 (1000000)
        if ((portIdx & 0x80) > 0) {
            ifInfo.isL3 = TRUE;
        } else {
            ifInfo.isL3 = FALSE;
        }

        ifInfo.portIdx = portIdx & 0x7F;

        // ServiceType
        if ((ifInfo.isAggregate == FALSE && ifInfo.isVlan == FALSE && ifInfo.isExternal == TRUE)
        /*
         * || (ifInfo.isAggregate == FALSE && ifInfo.isVlan == TRUE && ifInfo.isExternal == FALSE)
         */) {
            int serviceType = (ifIdx & IfIndexInfo.ExtSvcType_Mask) >> 7;
            ifInfo.serviceType = serviceType;
            // if (ifInfo.isVlan == TRUE) // 子卡模式 ifInfo.isAggregate == FALSE &&
            // // ifInfo.isVlan == TRUE &&
            // // ifInfo.isExternal == FALSE
            // ifInfo.vlanPort = ifIdx & IfIndexInfo.SubCardSlot_Mask;

            // // 解析PW,使用Port 7bit + 余下的7bit, 没使用，不处理
            if (ifInfo.serviceType == ServiceType.PW) {
                ifInfo.serviceId = (ifInfo.portIdx << 7) | (ifIdx & IfIndexInfo.ExtSvcMargin_Mask);
            }
        } else if ((ifInfo.isVlan == FALSE) && (ifInfo.isAggregate == FALSE) && (ifInfo.isExternal == FALSE)) {
            int serviceType = (ifIdx & IfIndexInfo.IntSvcType_Mask) >> 8;
            switch (serviceType) {
                case 0:
                    ifInfo.serviceType = ServiceType.Other;
                    break;
                case 1:
                    ifInfo.serviceType = ServiceType.GFP;
                    break;
                case 2:
                    ifInfo.serviceType = ServiceType.PW;
                    break;
                case 3:
                    ifInfo.serviceType = ServiceType.MPLSTunnel;
                    break;
                case 4:
                    ifInfo.serviceType = ServiceType.VlanTrunk_Tunnel;
                    break;
                case 5:
                    ifInfo.serviceType = ServiceType.PBB_ISID;
                    break;
                case 6:
                    ifInfo.serviceType = ServiceType.PBB_BVLAN;
                    break;
                case 7:
                    ifInfo.serviceType = ServiceType.PBT_ISID;
                    break;
                case 8:
                    ifInfo.serviceType = ServiceType.PBT_BVLAN;
                    break;
                case 9:
                    ifInfo.serviceType = ServiceType.Ethernet;
                    break;
                case 10:
                    ifInfo.serviceType = ServiceType.LoopBack;
                    break;
                case 11:
                    ifInfo.serviceType = ServiceType.Console;
                    break;
                case 12:
                    ifInfo.serviceType = ServiceType.AC1;
                    break;
                case 13:
                    ifInfo.serviceType = ServiceType.AC2;
                    break;
            // default: throws RuntimeException
            }

            // 解析PW,使用Port 7bit + 余下的7bit, 没使用，不处理
            if (ifInfo.serviceType == ServiceType.PW) {
                ifInfo.serviceId = (ifInfo.portIdx << 7) | (ifIdx & IfIndexInfo.ExtSvcMargin_Mask);
            } else if (ifInfo.serviceType == ServiceType.AC1) {
                int t = (ifIdx & IfIndexInfo.IntSvcMargin_Mask) >> 6;
                switch (t) {
                    case 0:
                        ifInfo.serviceType = ServiceType.ETHAC;
                        break;
                    case 1:
                        ifInfo.serviceType = ServiceType.E1AC;
                        break;
                    case 2:
                        ifInfo.serviceType = ServiceType.T1AC;
                        break;
                    case 3:
                        ifInfo.serviceType = ServiceType.SDHAC;
                        break;
                }
                ifInfo.serviceId = (ifInfo.portIdx << 6) | (ifIdx & IfIndexInfo.IntSvcMargin_Mask);
            } else if (ifInfo.serviceType == ServiceType.AC2) {
                ifInfo.serviceType = ServiceType.ATMAC;
                ifInfo.serviceId = (ifInfo.portIdx << 6) | (ifIdx & IfIndexInfo.IntSvcMargin_Mask);
            }
            // ifInfo.serviceType = serviceType;
        }
        // else if ((ifInfo.isVlan == TRUE ) && (ifInfo.isAggregate == FALSE)){
        else if (ifInfo.isVlan == TRUE) {
            ifInfo.vlanPort = ifIdx & IfIndexInfo.VLANPort_Mask;

            int vlanSvcType = (ifIdx & IfIndexInfo.VlanSvcType_Mask) >> 29;
            switch (vlanSvcType) {
                case 1:
                    ifInfo.serviceType = ServiceType.Ethernet;
                    break;
                case 2:
                    ifInfo.serviceType = ServiceType.POS;
                    break;
                default:
                    ifInfo.serviceType = ServiceType.Other;
                    break;
            }
        }
        // else if(ifInfo.isAggregate == 1 && ifInfo.isL3 == 0 &&
        // ifInfo.isExternal == 0)
        // {
        // ifInfo.serviceType = ServiceType.VirtualTrunkAc;
        // }
        else if (ifInfo.isAggregate == TRUE) // 同时 ifInfo.isL3 == FALSE &&
        // ifInfo.isExternal == FALSE
        {
            int t = (ifIdx & IfIndexInfo.IntSvcType_Mask) >> 8;
            ifInfo.serviceType = t == 14 ? ServiceType.VirtualTrunkAc : ServiceType.Other;
        }

        if (ifInfo.serviceType == ServiceType.LoopBack) {
            ifInfo.isLoopBack = TRUE;
        } else if (ifInfo.serviceType == ServiceType.Console) {
            ifInfo.isConsole = TRUE;
        }
        return ifInfo;
    }

    public static IfIndexInfo parseToIfIndexInfo(String ifIndex) {
        if (null == ifIndex)
            return null;
        try {
            int neIdx = FidUtils.getFidIndex(ifIndex, "e");
            int ifIdx = FidUtils.getFidIndex(ifIndex, "interface");
            return parseToIfIndexInfo(neIdx, ifIdx);
        } catch (Exception e) {
            return null;
        }
    }

    public static int toL2(int ifIdx) {
        return ifIdx & 0xFFF7FFFF;
    }

    public static String toL2Interface(String fid) {
        int v = FidUtils.getFidIndex(fid, "interface");
        int v1 = IfIndexGenerator.toL2(v);
        return fid.replace(String.valueOf(v), String.valueOf(v1));
    }

    public static int toL3(int ifIdx) {
        return ifIdx | 0x00080000;
    }

    public static String toL3Interface(String fid) {
        int v = FidUtils.getFidIndex(fid, "interface");
        int v1 = IfIndexGenerator.toL3(v);
        return fid.replace(String.valueOf(v), String.valueOf(v1));
    }

    public static int getEthAcId(int rawId) {
        return getAcId(rawId, ServiceType.Ethernet);
    }

    public static int getEthRawAcId(int acId) {
        return getRawAcId(acId, ServiceType.Ethernet);
    }

    public static int getVpwsVpnId(int acId) {
        int rawId = getEthRawAcId(acId);
        return 512 * 1024 + rawId;
    }

    public static int getAcId(int rawId, int type) {
        if (rawId == -1)
            return -1;
        int id = IfIndexGenerator.handleExternalServiceType(0, 0, type);
        return (id << 24) + rawId;
    }

    public static int getRawAcId(int acId, int type) {
        int id = IfIndexGenerator.handleExternalServiceType(0, 0, type);
        return acId - (id << 24);
    }

    public static class ServiceType {
        public final static int Other = 0;
        public final static int ATM = 1;
        public final static int ATMVPC = 2;
        public final static int ATMVCC = 3;
        public final static int SDH_SONET = 4;
        public final static int SONETPath = 5;
        public final static int SONETVT = 6;
        public final static int PPP = 7;
        public final static int E1 = 8;
        public final static int T1_DS1 = 9;
        public final static int DS0 = 10;
        public final static int DS0Bundle = 11;
        public final static int POS = 12;
        public final static int SyncEthernet = 13;
        public final static int FiberChannel = 14;
        public final static int RPR = 15;
        public final static int PW = 16;
        public final static int MPLSTunnel = 17;
        public final static int TELink = 18;
        public final static int VlanTrunk_Tunnel = 19;
        public final static int PBB_ISID = 20;
        public final static int PBB_BVLAN = 21;
        public final static int PBT_ISID = 22;
        public final static int PBT_BVLAN = 23;
        public final static int Ethernet = 24;
        public final static int GFP = 25;
        public final static int LoopBack = 26;
        public final static int Console = 27;
        public final static int CX = 28;

        public final static int AC1 = 28;
        public final static int AC2 = 29;
        public final static int ETHAC = 30;
        public final static int E1AC = 31;
        public final static int T1AC = 32;
        public final static int SDHAC = 33;
        public final static int ATMAC = 34;

        public final static int Eth_L3 = 35; // 非L3时，SDH_COPY = 35，表示MSP自增业务
        public final static int VirtualTrunkAc = 39;
    }

}
