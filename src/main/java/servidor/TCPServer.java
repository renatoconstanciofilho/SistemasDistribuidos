//RENATO CONSTANCIO FILHO

package servidor;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

public class TCPServer implements Runnable{

    private int port;
    public TCPServer(int port){
        super();
        setPort(port);
    }

    public void setPort(int port){
        this.port = port;
    }

    public int getPort(){
        return this.port;
    }


    private void createTCPserver() throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(port);
        byte[] contents = new byte[10000];

        while (true) {
            //Initialize socket
            Socket connectionSocket = welcomeSocket.accept();

            //Initialize the FileOutputStream to the output file's full path.
            FileOutputStream fos = new FileOutputStream("D://bkp" + LocalDateTime.now().toString().replace(':', '_') + ".txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream is = connectionSocket.getInputStream();

            //No of bytes read in one read() call
            int bytesRead = 0;

            while((bytesRead=is.read(contents))!=-1)
                bos.write(contents, 0, bytesRead);

            bos.flush();
            connectionSocket.close();

            System.out.println("[INTERNAL] Arquivo recebido com sucesso!");
        }
    }

    @Override
    public void run() {
        try {
            createTCPserver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
