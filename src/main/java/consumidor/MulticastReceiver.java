package consumidor;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Used to receive packets sent to a multicast IP.
 */
public class MulticastReceiver extends Thread{

    private byte[] buf = new byte[256];

    public void run() {
        try{
            System.out.println("Started listening");
            MulticastSocket socket = new MulticastSocket(4446);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                System.out.println("Received the following message:" + received);

                //TODO: Answer the issuer with my IP address

                if ("end".equals(received)) {
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
