import java.io.*;
import java.net.Socket;

/**
 * 此类专门用来测试强哥
 * Created by zyvis on 2017/11/21.
 */
public class main {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("119.23.31.19", 11000);
            //Socket s = new Socket("127.0.0.1", 11000);
            PrintStream pr = new PrintStream(s.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("./test"))));
            BufferedReader buf =  new BufferedReader(new InputStreamReader(s.getInputStream()));
            while (true){
                    String tmp=reader.readLine();
                    if(tmp==null){ System.out.println("send done");break;}
                    System.out.println("send: " + tmp);
                    pr.println(tmp);
            }
            while(true){
                System.out.println("waiting for line");
                String echo = buf.readLine();
                if(echo==null){
                    System.out.println("receive null");break;}
                System.out.println(echo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
