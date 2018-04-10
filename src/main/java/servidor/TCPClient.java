package servidor;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;

public class TCPClient {

    public TCPClient(int port){
        try {
            createTCPClient(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTCPClient(int port) throws IOException {
        Socket socket = new Socket(InetAddress.getByName("localhost"), port);
        byte[] contents = new byte[10000];

        //Initialize the FileOutputStream to the output file's full path.
        String path = "C:/BKPSRV/bkp" + LocalDateTime.now() + ".txt";
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        InputStream is = socket.getInputStream();

        //No of bytes read in one read() call
        int bytesRead = 0;

        while((bytesRead=is.read(contents))!=-1)
            bos.write(contents, 0, bytesRead);

        bos.flush();
        socket.close();

        System.out.println("[INTERNAL] Arquivo salvo com sucesso em: " + path);
    }
}
