package cliente;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Initializes the server side.
 */
public class ClientEntryPoint {
    public static void main(String... args) throws IOException {
        System.out.println("Iniciando Cliente");
//      https://stackoverflow.com/questions/3768258/running-loop-for-5-minutes
        final long NANOSEC_PER_SEC = 1000l*1000*1000;

//       https://stackoverflow.com/questions/858980/file-to-byte-in-java
        String path = "C:/CLTBKP/file.txt";

        long startTime = System.nanoTime();

        int port = ThreadLocalRandom.current().nextInt(6667, 65535 + 1);
        MulticastReceiver mr = new MulticastReceiver();
        mr.run(path,port);

        while ((System.nanoTime()-startTime)< 1*60*NANOSEC_PER_SEC){
//          https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
            MulticastPublisher mp = new MulticastPublisher();
            System.out.println("Enviando mensagem de pesquisa do servidor");
            mp.multicast("[CLT]"+port);
        }
    }
}
