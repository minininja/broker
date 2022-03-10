package org.dorkmaster.comms.util;

public class Param {
    protected String value = null;

    public Param(String paramName) {
        value = System.getProperty(paramName);
        if (null == value) {
            value = System.getenv(paramName);
        }
    }

    public String asString(String defValue) {
        return null != value ? value : defValue;
    }

    public int asInt(int defValue) {
        return Integer.valueOf(asString(Integer.toString(defValue)));
    }
}
