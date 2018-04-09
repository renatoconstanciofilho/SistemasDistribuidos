//    https://stackoverflow.com/questions/6999306/java-quickly-check-for-network-connection

import cliente.MulticastPublisher;
import consumidor.MulticastReceiver;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
       // NetworkStatusThread netStatus = new NetworkStatusThread();
       // netStatus.addNetworkListener(this);
       // Thread t = new Thread(netStatus);
       // t.start();

        new MulticastReceiver().run();

        while(true){
            MulticastPublisher publisher = new MulticastPublisher();
            publisher.multicast("Estou enviando mensagem");
            Thread.sleep(100);
        }

    }


}
