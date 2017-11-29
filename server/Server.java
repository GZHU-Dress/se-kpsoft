import java.io.*;
import java.net.*;
public class Server{
	public static int PORT=11000;
	public Server(){
		try{
			ServerSocket server=new ServerSocket(PORT);
			System.out.println("server start");
			while(true){
				Socket socket=server.accept();
				System.out.println("link start");
				new ClientLink(socket).start();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}

