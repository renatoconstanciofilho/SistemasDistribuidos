package consumidor;

import cliente.MulticastPublisher;

import java.io.IOException;

/**
 * Initializes the client side. The class {@code ClientEntryPoint} must be running so that this client can connect.
 */
public class ConsumerEntryPoint {
    public static void main(String... args) throws IOException, InterruptedException {
        while(true){
            new MulticastPublisher().multicast("Message");
            Thread.sleep(1000);
        }
    }
}
