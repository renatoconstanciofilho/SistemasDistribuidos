package cliente;

//http://www.baeldung.com/java-broadcast-multicast

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.file.Path;

public class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[1024];

    public void run(String path, int port ) {
        try {
            socket = new MulticastSocket(6666);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                if (received.substring(0,7).equals("[BKPSRV]")) {
                    System.out.println("[INTERNAL] Servidor de BACKUP identificado no ip: " + packet.getAddress().getHostAddress());
                    TCPServer tcpServer = new TCPServer(path, packet.getAddress().getHostAddress(), port);
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
