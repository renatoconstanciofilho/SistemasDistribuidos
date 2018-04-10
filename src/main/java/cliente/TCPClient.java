package cliente;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Date;

public class TCPClient {

    public TCPClient(String ip, int port) {
        createTCPClient(ip, port);
    }


    private void createTCPClient(String ip, int port) {
        try {
            Socket cliente = new Socket(ip, port);

//        TODO - Fazer outputstring do arquivo a ser enviado
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

            Date data_atual = (Date) entrada.readObject();

            //Date data_atual = (Date)entrada.readObject();

            JOptionPane.showMessageDialog(null, "Data recebida do servidor: " + data_atual.toString());
            entrada.close();

            System.out.println("Conexão encerrada");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
