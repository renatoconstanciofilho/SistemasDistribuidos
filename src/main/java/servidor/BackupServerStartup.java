package servidor;

public class BackupServerStartup {

    public static void main (String[] args){

        MulticastReceiver mr = new MulticastReceiver();
        mr.run();
    }
}
