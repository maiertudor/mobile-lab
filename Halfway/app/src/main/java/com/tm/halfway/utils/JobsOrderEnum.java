package com.tm.halfway.utils;

/**
 * Created by vladnegrea on 13/01/2018.
 */

public enum JobsOrderEnum {
    CREATED,UPDATED,ENDING,COST_ASC,COST_DESC;

    public static JobsOrderEnum get(int index) {
        return values()[index];
    }
}