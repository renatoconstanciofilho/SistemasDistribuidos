package servidor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDateTime;

public class TCPServer {

    public TCPServer(int port) {
        createTCPServer(port);
    }

    private void createTCPServer(Integer port) {

        try {
            ServerSocket tcpServer = new ServerSocket(port);
            System.out.println("[INTERNAL] TCPServer aguardando conexão na porta: " + port);

            while (true) {
                // o método accept() bloqueia a execução até que o servidor receba um pedido de conexão
                Socket client = tcpServer.accept();

                System.out.println("[INTERNAL] Cliente conectado: " + client.getInetAddress().getHostAddress());

                String path = "C:/BKPSRV/bkp" + LocalDateTime.now() + ".txt";
                File bpkFile = new File(path);
                FileOutputStream fos = new FileOutputStream(bpkFile);
                String os = client.getOutputStream().toString();
                byte[] bytesArray = os.getBytes();
                fos.write(bytesArray);
                fos.flush();
                fos.close();
                System.out.println("[INTERNA] Backup realizado em.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void writeToFile(OutputStream receivedFile) {
        File bkpFile
    }
}
