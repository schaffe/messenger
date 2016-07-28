package com.messenger;

import com.messenger.entities.Message;
import com.messenger.entities.Sender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatManager {
    private Set<StreamSender> participants;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ChatManager() {
        this.participants = new HashSet<>();
    }

    public synchronized void sendMessage(Message message) {
        Sender sender = message.getSender();
        String nickName = sender.getNickName();
        String utfMessage = nickName + " >" + message.getContent();
        for (StreamSender participant : participants) {
            executorService.submit( () -> {
                try {
                    participant.getStream().writeUTF(utfMessage);
                    participant.getStream().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    public void registerParticipants(StreamSender participant) {
        if (participants.contains(participant))
            return;
        participants.add(participant);
    }


}
