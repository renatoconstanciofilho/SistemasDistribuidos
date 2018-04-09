package cliente;

import java.io.IOException;
import java.net.*;


public class MulticastPublisher {

    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;

    /**
     * Publishes a given message to the specified multicast IP
     * @param multicastMessage the message to be sent
     */

    public void multicast(String multicastMessage) throws IOException {
        socket = new DatagramSocket();
        group = InetAddress.getByName("230.0.0.0");
        buf = multicastMessage.getBytes();

        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
        socket.close();
        System.out.printf("Sent %s%s", multicastMessage, "\n");
    }
}
