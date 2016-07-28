package com.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.entities.ActionType;
import com.messenger.entities.Message;
import com.messenger.entities.Sender;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    private static final int SERVER_PORT = 6666;
    private static final String ADDRESS = "127.0.0.1";

    ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] ar) {

        Client client = new Client();
        client.run();
    }

    private void run() {
        try(Scanner keyboard = new Scanner(System.in)) {
            InetAddress ipAddress = InetAddress.getByName(ADDRESS);

            try (Socket socket = new Socket(ipAddress, SERVER_PORT);
                 DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {

                new Thread(new SocketReader(in)).start();

                Sender sender = new Sender()
                        .setNickName("user" + Math.round(Math.random() * 100))
                        .setUuid(UUID.randomUUID().toString());

                while (true) {
                    String line = keyboard.nextLine();
                    if ("quit".equals(line)) {
                        writeAction(ActionType.EXIT, null, out);
                        break;
                    }

                    Message message = new Message()
                            .setSender(sender)
                            .setContent(line);
                    ActionType actionType = ActionType.SEND;
                    writeAction(actionType, message, out);
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    private void writeAction( ActionType actionType, Object command, DataOutputStream out ) throws IOException {
        byte[] actionBytes = objectMapper.writeValueAsBytes(command);
        int size = actionBytes.length;

        out.writeByte(actionType.getCode());
        out.writeInt(size);
        out.write(actionBytes);
        out.flush();
    }
}
