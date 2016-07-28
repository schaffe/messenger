package com.messenger.entities;

/**
 * Created by dzidzoiev on 7/27/16.
 */
public class Sender {
    private String uuid;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public Sender setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public Sender setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}
