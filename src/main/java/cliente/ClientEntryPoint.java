//RENATO CONSTANCIO FILHO

package cliente;

import java.io.IOException;

/**
 * Initializes the server side.
 */
public class ClientEntryPoint {
    public static void main(String... args) throws IOException {
        System.out.println("[INTERNAL] Iniciando Cliente");
        Multicast mc = new Multicast();
        Thread mcThread = new Thread(mc);
        mcThread.start();
        mc.sendMessage("[BKPCLT] Identificar servidor...");
        System.out.println("[INTERNAL] Enviada mensagem de pesquisa...");
    }
}
