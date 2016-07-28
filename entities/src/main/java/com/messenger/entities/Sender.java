package com.messenger.entities;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sender sender = (Sender) o;
        return Objects.equals(uuid, sender.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
