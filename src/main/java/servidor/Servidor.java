package servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.Date;

public class Servidor {

    private DatagramPacket sendPacket, receivePacket;
    private MulticastSocket socket;
    private Integer port = 6666;
    private InetAddress grupo;

    private Integer lowerPort = 6667;
    private Integer higherPort = 65535;

    private Integer selectedPort;

    public Servidor(){
        super();

        try {
            socket = new MulticastSocket(port);
            grupo = InetAddress.getByName("230.1.2.3");
            socket.joinGroup(grupo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForPackets()
    {
        while ( true ) {
            try {
                // set up packet
                byte data[] = new byte[ 100 ];
                receivePacket = new DatagramPacket( data, data.length );

                // wait for packet
                socket.receive( receivePacket );

                if (receivePacket.getData().toString().equals("ServidorBackup?")){
                    selectedPort = portGenerator();
                    sendPort(selectedPort);
                    createTCPServer(selectedPort);
                    System.out.println("Tentativa de localização pelo cliente.\n Porta selecionada para conexão segura: " + selectedPort);
                }

            }
            catch( IOException exception ) {
                exception.printStackTrace();
            }
        }
    }

    private void createTCPServer(Integer selectedPort) {

        // Instancia o ServerSocket ouvindo a porta 12345
        try {
            ServerSocket tcpServer = new ServerSocket(selectedPort);
            System.out.println("Servidor ouvindo a porta: " + selectedPort);

        while(true) {
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
    }
        catch(Exception e) {
        System.out.println("Erro: " + e.getMessage());
    }
    }

    private void sendPort(int selectedPort) {

        byte data[] = Integer.toString(selectedPort).getBytes();

        sendPacket = new DatagramPacket( data, data.length, grupo, 5000 );
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int portGenerator(){
        return lowerPort + (int)(Math.random() * ((higherPort - lowerPort) + 1));
    }
}
