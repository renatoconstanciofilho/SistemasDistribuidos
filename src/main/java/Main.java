//    https://stackoverflow.com/questions/6999306/java-quickly-check-for-network-connection

public class Main {

    public static void main(String[] args) {
        NetworkStatusThread netStatus = new NetworkStatusThread();
        netStatus.addNetworkListener(this);
        Thread t = new Thread(netStatus);
        t.start();
    }


}
