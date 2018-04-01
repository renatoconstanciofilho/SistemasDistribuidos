import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class NetworkStatusThread implements Runnable {

    List listenerList = new Vector();

    @SuppressWarnings("unchecked")
    public synchronized void addNetworkListener(NetworkListener nl) {
        listenerList.add(nl);
    }

    public synchronized void removeNetworkListener(NetworkListener nl) {
        listenerList.remove(nl);
    }


    private synchronized void sendNetworkStatus(String status) {
        // send it to subscribers
        @SuppressWarnings("rawtypes")
        ListIterator iterator = listenerList.listIterator();
        while (iterator.hasNext()) {
            NetworkListener rl = (NetworkListener) iterator.next();
            rl.sendNetworkStatus(status);
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Getting resource status");
            try {
                Thread.sleep(2000);
                System.out.println("Sending resource status to registered listeners");
                this.sendNetworkStatus("OK");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
