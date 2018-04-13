//RENATO CONSTANCIO FILHO

package cliente;

//http://www.codebytes.in/2014/11/file-transfer-using-tcp-java.html

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class TCPClient {

    public TCPClient(){
        super();
    }


    public void createTCPclient(String ip, int port) throws IOException {
        String sentence;
        String modifiedSentence;

        //Initialize socket
        Socket clientSocket = new Socket(ip, port);

        //Specify the file
        File bkpFile = new File("d:\\bkpclient.txt");
        FileInputStream fis = new FileInputStream(bkpFile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        //Get socket's output stream
        OutputStream os = clientSocket.getOutputStream();

        //Read File Contents into contents array
        byte[] contents;
        long fileLength = bkpFile.length();
        long current = 0;
        long start = System.nanoTime();
        while(current!=fileLength){
            int size = 10000;
            if(fileLength - current >= size)
                current += size;
            else{
                size = (int)(fileLength - current);
                current = fileLength;
            }
            contents = new byte[size];
            bis.read(contents, 0, size);
            os.write(contents);
            System.out.println("[INTERNAL] Enviando arquivo... "+(current*100)/fileLength+"% completo.");
        }
        os.flush();
        clientSocket.close();
        System.out.println("[INTERNAL] Backup realizado com sucesso!");
    }


}
