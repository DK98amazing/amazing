/*
 * Copyright (c) 2018 UTStarcom, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.rest;



public class IfIndexInfo {
    // private static final Logger LOG = LoggerFactory.getLogger(IfIndexInfo.class);

    public static final int FALSE = 0;
    public static final int TRUE = 1;

    // for property neNumber
    public static final int ONE_NE = 0;
    public static final int TWO_NE = 1;
    // for control type
    public static final int CT_ALL_CARD = 0;
    public static final int CT_IF_CARD = 1;
    public static final int CT_PROCESS_CARD = 2;
    public static final int CT_PROTECT_CARD = 3;

    public static final int NeNO_Mask = 0x80000000; // 32bit
    public static final int CtrlType_Mask = 0x60000000; // 30~31bit //
    // 0，接口处理合一CT_ALL_CARD；
    // 1， 接口卡CT_IF_CARD； 2
    // 处理卡CT_PROCESS_CARD；
    // 3，
    // 保护卡CT_PROTECT_CARD;
    public static final int SlotIndex_Mask = 0x1F800000; // 24~29bit
    public static final int PortType_Mask = 0x00700000; // 21~23bit 20bit是isL3
    // 0, external port;
    // 1, internal port;
    // 2,3, trunk port;
    public static final int Port_Mask = 0x000FF000; // 13~19bit
    public static final int ExtSvcType_Mask = 0x00000F80;// 8~12bit
    public static final int IntSvcType_Mask = 0x00000F00;// 9~12bit
    public static final int VLANPort_Mask = 0x00000FFF; // 1~12bit
    public static final int SubCardSlot_Mask = 0x0000007F; // 1~7bit
    public static final int TrunkPort100G_Mask = 0x00000FF; // 1~8bit

    public static final int ExtSvcMargin_Mask = 0x0000007F;
    public static final int IntSvcMargin_Mask = 0x000000FF;
    public static final int IntSvcTypeMargin_Mask = 0x0000003F;
    public static final int VlanSvcType_Mask = 0xe0000000; // isVlan下，高3bit标识serviceType

    public int neIdx = 0;
    public int neNumber = ONE_NE;
    public int controlType = CT_IF_CARD;
    // default is PW.
    public int serviceType = 0;
    public int slotIdx = 0; // 槽位号与端口号都是从0开始
    public int portIdx = 0;
    public int isVlan = FALSE;
    public int isAggregate = FALSE;
    // if isL3 = true, it is a high level ifIndex;
    // if isL3 = false, it is a high level ifIndex;
    public int isL3 = TRUE;
    public int isExternal = TRUE;
    public int vlanPort = 0; // 子卡模式时，表示子卡槽位；非子卡模式，才表示vlanport
    public int isLoopBack = 0;

    public static final int PORT_TYPE_SUBCARD = 5;
    // public int subSlotIdx = 0; //

    public int isConsole = TRUE;
    public long serviceId = 0; // 当业务类型是pw、ac时，使用这个id
    public int isSubIntf = FALSE;
    public int isVlanIntf = FALSE;

    public int tId = -1; // 100G Trunk通道号

    public IfIndexInfo() {
        super();
    }

    public IfIndexInfo(String fid) {
        super();
        setPhyFid(fid);
    }

    public void setPhyFid(String fid) {
        try {
            neIdx = FidUtils.getFidIndex(fid, "e");
            slotIdx = FidUtils.getFidIndex(fid, "s") - 1;
            portIdx = FidUtils.getFidIndex(fid, "j");
            if (portIdx > 0)
                portIdx--;
            else
                portIdx = 0;
        } catch (Exception e) {
            // LOG.error("setPhyFid() : ", e);
        }
    }

    public boolean isSubCardMode() {
        return isVlan == TRUE && isAggregate == FALSE && isExternal == FALSE;
    }

    public String formFid() {
        if (neIdx > 0)
            return formFid2();
        return new StringBuilder("\\").append("\\\\interface=").append(IfIndexGenerator.formIfIndex(this)).toString();
    }

    public String formPhyFid() {
        if (neIdx > 0)
            return formPhyFid2();
        return new StringBuilder().append("\\s=").append(slotIdx + 1).append("\\c=1")
            // .append(isSubCardMode() ? ("\\c=" + (vlanPort + 1)) : "")
            .append("\\j=").append(portIdx + 1).append("\\p=1").toString();
    }

    public String formFid2() {
        return new StringBuilder("\\e=").append(neIdx).append("\\\\interface=")
            .append(IfIndexGenerator.formIfIndex(this)).toString();
    }

    public String formPhyFid2() {
        return new StringBuilder("\\e=").append(neIdx).append("\\s=").append(slotIdx + 1).append("\\c=1")
            // .append(isSubCardMode() ? ("\\c=" + (vlanPort + 1)) : "")
            .append("\\j=").append(portIdx + 1).append("\\p=1").toString();
    }

    public String getEthIfName() {
        StringBuilder b = new StringBuilder();
        if (isAggregate == TRUE) {
            b.append("Trunk").append(this.portIdx + 1);
            if (this.tId != -1)
                b.append(".").append(this.tId + 1).append(".").append(this.slotIdx);
        } else {
            b.append("eth").append(this.slotIdx + 1).append(".").append(this.portIdx + 1);
        }

        if (isVlan == TRUE && isExternal == FALSE) {
            b.append(".").append(vlanPort);
        }
        return b.toString();
    }

    public static IfIndexInfo createVirtualIfinfo(int slot) {
        IfIndexInfo info = new IfIndexInfo();
        info.portIdx = 0;
        info.slotIdx = slot > 0 ? slot - 1 : 0;
        info.serviceType = IfIndexGenerator.ServiceType.VirtualTrunkAc;
        info.isL3 = IfIndexInfo.FALSE;
        info.isAggregate = IfIndexInfo.TRUE;
        info.isExternal = IfIndexInfo.FALSE;
        return info;
    }

    public static IfIndexInfo createTrunkIfInfo(String trunkFid) {
        IfIndexInfo info = new IfIndexInfo();
        info.portIdx = FidUtils.getFidIndex(trunkFid, "t") - 1;
        info.serviceType = IfIndexGenerator.ServiceType.Ethernet;
        info.isL3 = IfIndexInfo.FALSE;
        info.isAggregate = IfIndexInfo.TRUE;

        int ch = FidUtils.getFidIndex(trunkFid, "ch");
        if (ch == -1)
            return info;

        info.slotIdx = ch;
        info.tId = FidUtils.getFidIndex(trunkFid, "p") - 1;
        return info;
    }

    public static IfIndexInfo createPhyIfinfo(String fid) {
        IfIndexInfo info = null;
        boolean isTrunk = FidUtils.getFidIndex(fid, "t") != -1;
        boolean isPort = FidUtils.getFidIndex(fid, "p") != -1;
        if (!isTrunk && !isPort)
            return null;

        if (isTrunk) {
            info = createTrunkIfInfo(fid);
        } else {
            info = new IfIndexInfo();
            info.setPhyFid(fid);
            info.serviceType = 24;
            info.isL3 = FALSE;
            info.isVlan = FALSE;
            info.isAggregate = FALSE;
        }
        return info;
    }

    public static IfIndexInfo createL3PhyIfinfo(String fid) {
        IfIndexInfo info = null;
        boolean isTrunk = FidUtils.getFidIndex(fid, "t") != -1;
        boolean isPort = FidUtils.getFidIndex(fid, "p") != -1;
        if (!isTrunk && !isPort)
            return null;

        if (isTrunk) {
            info = createTrunkIfInfo(fid);
        } else {
            info = new IfIndexInfo();
            info.setPhyFid(fid);
            info.serviceType = 24;
            info.isL3 = TRUE;
            info.isVlan = FALSE;
            info.isAggregate = FALSE;
        }
        return info;
    }

    public static IfIndexInfo createSubIfinfo(String fid, int vlanPort) {
        return createSubIfinfo(fid, vlanPort, false);
    }

    public static IfIndexInfo createSubIfinfo(String fid, int vlanPort, boolean isL3Port) {
        IfIndexInfo info = null;
        boolean isTrunk = FidUtils.getFidIndex(fid, "t") != -1;
        if (isTrunk) {
            info = createTrunkIfInfo(fid);
        } else {
            info = new IfIndexInfo();
            info.setPhyFid(fid);
        }

        info.serviceType = 24;
        info.isL3 = isL3Port ? TRUE : FALSE;
        info.isAggregate = isTrunk ? TRUE : FALSE;
        info.isExternal = FALSE;
        info.isVlan = TRUE;
        info.vlanPort = vlanPort;
        return info;
    }

    public static String createPhyIfindexFromSubIf(String subIfindex) {
        IfIndexInfo info = createPhyIfinfoFromSubIf(subIfindex);
        int ifId = IfIndexGenerator.formIfIndex(info);
        return "\\\\\\interface=" + ifId;
    }

    public static IfIndexInfo createPhyIfinfoFromSubIf(String subIfindex) {
        IfIndexInfo info = null;
        int ifId = FidUtils.getFidIndex(subIfindex, "interface");
        boolean isTrunk = IfIndexGenerator.isAggregation(ifId);
        if (isTrunk) {
            info = createTrunkIfInfo(IfIndexGenerator.getTrunkFId(ifId));
        } else {
            info = createPhyIfinfo(IfIndexGenerator.parseToIfIndex(subIfindex));
        }
        return info;
    }

}
