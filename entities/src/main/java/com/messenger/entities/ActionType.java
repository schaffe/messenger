package com.messenger.entities;

/**
 * Created by dzidzoiev on 7/27/16.
 */
public enum ActionType {
    SEND(1),
    EXIT(-1)
    ;

    private final byte code;

    ActionType(int code) {
        this.code = (byte) code;
    }

    public byte getCode() {
        return code;
    }

    public static ActionType of(byte code) {
        for (ActionType a : ActionType.values()) {
            if (code == a.code)
                return a;
        }
        throw new IllegalArgumentException("ActionType code is not valid");
    }
}
