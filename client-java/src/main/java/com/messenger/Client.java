package com.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.entities.ActionType;
import com.messenger.entities.Message;
import com.messenger.entities.Sender;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

public class Client {
    ObjectMapper objectMapper = new ObjectMapper();
    private static final int SERVER_PORT = 6666;
    private static final String ADDRESS = "127.0.0.1";

    public static void main(String[] ar) {

        Client client = new Client();
        client.run();
    }

    private void run() {
        try {
            InetAddress ipAddress = InetAddress.getByName(ADDRESS);
            try (Socket socket = new Socket(ipAddress, SERVER_PORT);
                 DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {
                Sender sender = new Sender()
                        .setNickName("user" + Math.round(Math.random() * 100))
                        .setUuid(UUID.randomUUID().toString());

                while (true) {
                    System.out.print("> ");
                    String line = keyboard.readLine();
                    if ("quit".equals(line)) {
                        writeAction(ActionType.EXIT, null, out);
                        break;
                    }

                    Message message = new Message()
                            .setSender(sender)
                            .setContent(line);
                    ActionType actionType = ActionType.SEND;
                    writeAction(actionType, message, out);

                    line = in.readUTF();
                    System.out.println(line);
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
