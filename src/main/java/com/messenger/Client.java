package com.messenger;

import java.io.*;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 26.07.2016
 * Time: 19:00
 */
public class Client
{
  public static void main( String[] ar )
  {
    int serverPort = 6666;
    String address = "127.0.0.1";

    try
    {
      InetAddress ipAddress = InetAddress.getByName( address );
      try (Socket socket = new Socket( ipAddress, serverPort );
           DataInputStream in = new DataInputStream( socket.getInputStream() );
           DataOutputStream out = new DataOutputStream( socket.getOutputStream() );
           BufferedReader keyboard = new BufferedReader( new InputStreamReader( System.in ) ))
      {
        while( true )
        {
          System.out.print( "> " );
          String line = keyboard.readLine();
          out.writeUTF( line );
          out.flush();
          line = in.readUTF();
          System.out.println( line );
          if( "quit".equals( line ) )
            break;
        }
      }
    }
    catch( Exception x )
    {
      x.printStackTrace();
    }
  }
}
