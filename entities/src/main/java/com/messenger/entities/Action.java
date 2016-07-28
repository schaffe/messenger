package com.messenger.entities;

public class Action {
    private ActionType actionType;
    private byte[] payload;

    public Action() {
    }

    public Action(ActionType actionType, byte[] payload) {
        this.actionType = actionType;
        this.payload = payload;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Action setActionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public byte[] getPayload() {
        return payload;
    }

    public Action setPayload(byte[] payload) {
        this.payload = payload;
        return this;
    }
}
