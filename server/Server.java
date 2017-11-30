import java.io.*;
import java.net.*;
public class Server{
	/**
	*端口号为PORT
	*/
	private int PORT;
	/**
	*启动服务器即创建Server对象，参数p为创建时候要监听的端口　
	*/
	public Server(int p){
		PORT=p;
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

