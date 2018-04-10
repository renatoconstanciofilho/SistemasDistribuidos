package cliente;

import servidor.TCPServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastListener {

    private Integer mcPort;
    private InetAddress group;
    private MulticastSocket socket;

    private Integer selectedPort;

    public MulticastListener(Integer port){
        setMcPort(port);
        setSocket(getMcPort());
        setGroup("230.1.2.3");
        startMulticastListener();
        askServerLocation();
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

    private void askServerLocation() {

        byte data[] = "BackupServer?".getBytes();

        DatagramPacket sendPacket = new DatagramPacket( data, data.length, getGroup(), getMcPort());
        try {
            getSocket().send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startMulticastListener(){
        while ( true ) {
            try {
                // set up packet
                byte data[] = new byte[ 100 ];
                DatagramPacket receivePacket = new DatagramPacket( data, data.length );

                // wait for packet
                getSocket().receive( receivePacket );

                String prefix = receivePacket.getData().toString().substring(0,5);
                Integer tcpPort = Integer.parseInt(receivePacket.getData().toString().substring(5,receivePacket.getData().toString().length()));

                if (prefix.equalsIgnoreCase("Porta:")){
                TCPClient tcpClient = new TCPClient(receivePacket.getAddress().toString(), tcpPort);
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
