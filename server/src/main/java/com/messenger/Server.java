package com.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.messenger.entities.Action;
import com.messenger.entities.ActionType;
import com.messenger.entities.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 6666;
    ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] ar) {
        Server server = Guice.createInjector().getInstance(Server.class);
        server.run();
    }

    private void run() {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            Socket socket = ss.accept();
            handleRequest(socket);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    private void handleRequest(Socket socket) throws IOException {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            MAINLOOP: while (true) {
                Action action = readAction(in);

                switch (action.getActionType()) {
                    case SEND:
                        Message message = objectMapper.readValue(action.getPayload(), Message.class);

                        System.out.println("Got: " + message.getContent());
                        out.writeUTF(message.getContent());
                        out.flush();
                        break;
                    case EXIT:
                        break MAINLOOP;
                }
            }
        }
    }

    private Action readAction(DataInputStream is) throws IOException {
        byte[] buffer = new byte[1024 * 8];
        byte type = is.readByte();
        int size = is.readInt();
        byte[] message = new byte[size];

        int total = 0;
        int readCount = 0;
        while ( total < size && (readCount = is.read(buffer) ) > -1) {
            System.arraycopy(buffer, 0, message, total, readCount );
            total += readCount;
        }

        return new Action(ActionType.of(type), message);
    }
}
