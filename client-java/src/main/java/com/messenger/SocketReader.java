package com.messenger;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReader implements Runnable {

    private final DataInputStream in;

    public SocketReader(DataInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        while ( !Thread.currentThread().isInterrupted() ) {
            try {
                String line = in.readUTF();
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
