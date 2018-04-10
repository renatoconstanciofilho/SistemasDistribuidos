package cliente;

import consumidor.MulticastReceiver;

/**
 * Initializes the server side.
 */
public class ClientEntryPoint {
    public static void main(String... args){
//        new MulticastReceiver().run();
        Integer port = 6666;
        MulticastListener ml = new MulticastListener(port);
    }
}
