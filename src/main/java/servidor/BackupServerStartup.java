package servidor;

public class BackupServerStartup {

    public static void main (String[] args){
        System.out.println("Iniciando Servidor");
        MulticastReceiver mr = new MulticastReceiver();
        mr.run();
    }
}
