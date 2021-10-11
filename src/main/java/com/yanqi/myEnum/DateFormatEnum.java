package com.yanqi.myEnum;

/**
 * @author YQQ
 */

public enum DateFormatEnum {
    Format_Stander("yyyy-MM-dd"),Format_ONE("yyyy-MM/dd"),Format_TWE("yyyyMMdd");
    private String value;

    private DateFormatEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
