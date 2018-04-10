package cliente;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Initializes the server side.
 */
public class ClientEntryPoint {
    public static void main(String... args) throws IOException {
//      https://stackoverflow.com/questions/3768258/running-loop-for-5-minutes
        final long NANOSEC_PER_SEC = 1000l*1000*1000;

//       https://stackoverflow.com/questions/858980/file-to-byte-in-java
        Path path = Paths.get("C:/CLTBKP/file.txt");
        byte[] data = Files.readAllBytes(path);

        long startTime = System.nanoTime();

        while ((System.nanoTime()-startTime)< 5*60*NANOSEC_PER_SEC){
//          https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
            int port = ThreadLocalRandom.current().nextInt(6667, 65535 + 1);
            MulticastReceiver mr = new MulticastReceiver();
            mr.run(data,port);
            MulticastPublisher mp = new MulticastPublisher();
            try {
                mp.multicast("[CLT]"+port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
