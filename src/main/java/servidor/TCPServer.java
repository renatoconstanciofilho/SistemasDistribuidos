package servidor;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;

public class TCPServer {

    public TCPServer(int port) {
        createTCPServer(port);
    }


    private void createTCPServer(Integer port) {

        // Instancia o ServerSocket ouvindo a porta 12345
        try {
            ServerSocket tcpServer = new ServerSocket(port);
            System.out.println("BackupServer ouvindo a porta: " + port);

            while (true) {
                // o método accept() bloqueia a execução até que
                // o servidor receba um pedido de conexão
                Socket client = tcpServer.accept();

                System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress());
                ObjectOutputStream receivedFile = new ObjectOutputStream(client.getOutputStream());

                receivedFile.flush();
                receivedFile.writeObject(new Date(System.currentTimeMillis()));

                receivedFile.close();
                client.close();
                System.out.println("Backup realizado.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
