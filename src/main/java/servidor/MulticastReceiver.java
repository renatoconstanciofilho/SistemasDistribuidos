package servidor;

//http://www.baeldung.com/java-broadcast-multicast

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[1024];

    public void run() {
        try {
            socket = new MulticastSocket(6666);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                if (received.substring(0,4).equals("[CLT]")) {
                    int port = Integer.parseInt(received.substring(6,(received.length())-1));
                    System.out.println("[INTERNAL] Servidor recebeu solicitação de BACKUP do cliente ip: " + packet.getAddress().getHostAddress() + " na porta: " + port );
                    MulticastPublisher mp = new MulticastPublisher();
                    mp.multicast("[BKPSRV] Aguardando envio de arquivo na porta informada.");
                    TCPServer tcpServer =  new TCPServer(port);
                }
            }
//            socket.leaveGroup(group);
//            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
