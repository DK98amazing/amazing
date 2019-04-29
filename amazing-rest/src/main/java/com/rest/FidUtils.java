/*
 * Copyright (c) 2018 UTStarcom, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.rest;

public class FidUtils {
    public static final String TYPE_EQUIPMENT = "e";
    public static final String TYPE_CARD = "c";
    public static final String TYPE_JACK = "j";
    public static final String TYPE_SLOT = "s";
    public static final String TYPE_PORT = "p";
    public static final String TYPE_CHANNEL = "ch";


    // fid sign
    public static final String FID_ROOT = "\\";
    public static final char FID_SEP = '\\';
    public static final char FID_KV_SEP = '=';



    public static String getNeFid(String fid) {
        return getFidByEnd(TYPE_EQUIPMENT, fid);
    }



    public static String getFidByEnd(String type, String fid) {
        if (fid == null)
            return null;
        int index = fid.indexOf("\\" + type + "=");
        if (index == -1)
            return null;
        index = fid.indexOf("\\", index + 1);
        if (index == -1)
            return fid;
        return fid.substring(0, index);
    }

    public static String getFidType(String fid) {
        if (null == fid) {
            return null;
        }
        if (fid.endsWith("\\")) {
            fid = fid.substring(0, fid.length() - 1);
        }

        int idx = fid.lastIndexOf("=");
        int idx2 = fid.lastIndexOf("\\");

        if (idx < idx2 || idx < 0 || idx2 < 0)
            return fid;

        return fid.substring(idx2 + 1, idx);

    }

    public static void main(String[] a) {
        System.out.println(getFidType("\\e=1\\s=1\\c=1"));
    }



    public static int getFidIndex(String fid, String type) {
        if (fid == null || type == null)
            return -1;
        String typeStr = "\\" + type + "=";
        int index = fid.lastIndexOf(typeStr);
        if (index == -1)
            return -1;
        int fidLen = fid.length();
        int vStart = index + typeStr.length();
        int vEnd = vStart;
        for (vEnd = vStart; vEnd < fidLen && Character.isDigit(fid.charAt(vEnd)); vEnd++);
        if (vStart == vEnd)
            return -1;
        return Integer.parseInt(fid.substring(vStart, vEnd));
    }

    public static boolean isPortFid(String fid) {
        return isSpecificTypeFid(fid, TYPE_PORT);
    }

    public static boolean isCardFid(String fid) {
        return isSpecificTypeFid(fid, TYPE_CARD);
    }

    private static boolean isSpecificTypeFid(String fid, String type) {
        if (null == fid) {
            return false;
        }
        String flag = FID_SEP + type + FID_KV_SEP;
        int index = fid.indexOf(flag);
        if (index < 0) {
            return false;
        }
        index += flag.length();
        if (0 <= fid.indexOf(FID_SEP, index)) {
            return false;
        }
        return true;
    }
}
