//RENATO CONSTANCIO FILHO

// Fig. 21.6: Client.java
// Set up a Client that will send packets to a
// server and receive packets from a server.

package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Multicast implements Runnable{
    private DatagramPacket sendPacket, receivePacket;
    private MulticastSocket socket;
    private InetAddress grupo;

    int tcpPort;


    public Multicast(int tcpPort){
        super();
        setTcpPort(tcpPort);

        try {
            socket = new MulticastSocket(6666);
            grupo = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(grupo);
        }
        catch( IOException se ) {
            se.printStackTrace();
            System.exit( 1 );
        }

    }

    private void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public int getTcpPort(){
        return this.tcpPort;
    }

    private void waitForPackets()
    {
        while ( true ) {
            try {
                // set up packet
                byte data[] = new byte[ 100 ];
                receivePacket = new DatagramPacket( data, data.length );

                // wait for packet
                socket.receive(receivePacket);
                String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if(!received.substring(0,8).equalsIgnoreCase("[BKPSRV]"))
                    System.out.println("[INTERNAL] Recebida mensagem: " + received + " do ip: " + receivePacket.getAddress().getHostAddress());
                if(received.substring(0,8).equalsIgnoreCase("[BKPCLT]")){
                    System.out.println("[INTERNAL] Servidor recebeu solicitação de BACKUP do cliente ip: " + receivePacket.getAddress().getHostAddress());
                    sendMessage("[BKPSRV] TCPServer utilizando porta: " + tcpPort );
                    System.out.println("[INTERNAL] Enviada informação de porta TCP " + tcpPort);
                }
            }
            catch( IOException exception ) {
                exception.printStackTrace();
            }
        }
    }

    public synchronized void sendMessage(String s )
    {
        try {
            byte data[] = s.getBytes();

            sendPacket = new DatagramPacket( data, data.length,
                    grupo, 6666 );
            socket.send( sendPacket );

        }
        catch ( IOException exception ) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        waitForPackets();
    }
}

/**************************************************************************
 * (C) Copyright 1999 by Deitel & Associates, Inc. and Prentice Hall.     *
 * All Rights Reserved.                                                   *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/

