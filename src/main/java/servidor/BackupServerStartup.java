package servidor;

public class BackupServerStartup {

    public static void main (String[] args){

        Integer port = 6666;
        MulticastListener ml = new MulticastListener(port);

    }
}
