package servidor;

import java.io.IOException;
import java.net.*;

public class MulticastListener {

    private Integer mcPort;
    private InetAddress group;
    private MulticastSocket socket;

    private Integer lowerPort = 6667;
    private Integer higherPort = 65535;

    private Integer selectedPort;

    public MulticastListener(Integer port){
        setMcPort(port);
        setSocket(getMcPort());
        setGroup("230.1.2.3");
        startMulticastListener();
    }

    private void setSocket(Integer mcPort) {
        try {
            socket = new MulticastSocket(mcPort);
            socket.joinGroup(getGroup());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MulticastSocket getSocket(){
        return socket;
    }

    private void setGroup(String s) {
        try {
            group = InetAddress.getByName(s);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getGroup(){
        return group;
    }

    private void setMcPort(Integer port) {
        mcPort = port;
    }

    public Integer getMcPort(){
        return mcPort;
    }

    private void sendPort(int selectedPort) {

        byte data[] = ("Porta: " + getSelectedPort()).getBytes();

        DatagramPacket sendPacket = new DatagramPacket( data, data.length, getGroup(), getMcPort() );
        try {
            getSocket().send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int portGenerator(){
        return lowerPort + (int)(Math.random() * ((higherPort - lowerPort) + 1));
    }

    public void startMulticastListener(){
        while ( true ) {
            try {
                // set up packet
                byte data[] = new byte[ 100 ];
                DatagramPacket receivePacket = new DatagramPacket( data, data.length );

                // wait for packet
                getSocket().receive( receivePacket );

                if (receivePacket.getData().toString().equals("ServidorBackup?")){
                    setSelectedPort(portGenerator());
                    sendPort(getSelectedPort());
                    TCPServer tcpServer = new TCPServer(getSelectedPort());
                    System.out.println("Tentativa de localização pelo cliente.\n Porta selecionada para conexão segura: " + getSelectedPort());
                }

            }
            catch( IOException exception ) {
                exception.printStackTrace();
            }
        }
    }

    private int getSelectedPort() {
        return selectedPort;
    }

    private void setSelectedPort(int i) {
        selectedPort = i;
    }

}
