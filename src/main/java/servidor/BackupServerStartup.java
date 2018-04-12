package servidor;

import java.util.concurrent.ThreadLocalRandom;

public class BackupServerStartup {

    public static void main (String[] args){
        System.out.println("[INTERNAL] Iniciando Servidor");

        int tcpPort = ThreadLocalRandom.current().nextInt(6667, 65535 + 1);
        TCPServer tcpServer = new TCPServer(tcpPort);
        Thread tcpThread = new Thread(tcpServer);
        tcpThread.start();

        System.out.println("[INTERNAL] Thread TCPServer iniciado na porta: " +tcpPort);

        Multicast mc = new Multicast(tcpPort);
        Thread mcThread = new Thread(mc);
        mcThread.start();

        System.out.println("[INTERNAL] Thread Multicast iniciada na porta: 6666");
    }
}
