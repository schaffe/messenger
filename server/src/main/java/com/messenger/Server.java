package com.messenger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 26.07.2016
 * Time: 18:59
 */
public class Server
{
  public static void main( String[] ar )
  {
    int port = 6666;

    try (ServerSocket ss = new ServerSocket( port ))
    {
      Socket socket = ss.accept();
      handleRequest( socket );
    }
    catch( Exception x )
    {
      x.printStackTrace();
    }
  }

  private static void handleRequest( Socket socket ) throws IOException
  {
    try (DataInputStream in = new DataInputStream( socket.getInputStream() );
         DataOutputStream out = new DataOutputStream( socket.getOutputStream() ))
    {
      while( true )
      {
        String line = in.readUTF();
        System.out.println( "Got: " + line );
        out.writeUTF( line );
        out.flush();
        if( "quit".equals( line ) )
          break;
      }
    }
  }
}
