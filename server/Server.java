import java.io.*;
import java.net.*;
public class Server{
	/**
	*端口号为PORT
	*处理连接和读取数据的ClientLink
	*/
	private int PORT;
	private ClientLink cl;
	/**
	*启动服务器即创建Server对象，参数p为创建时候要监听的端口　
	*/
	public Server(int p){
		PORT=p;
		try{
			ServerSocket server=new ServerSocket(PORT,1);
			System.out.println("server start");
			while(true){
				Socket socket=server.accept();
				System.out.println("link start");
				cl=new ClientLink(socket);
				cl.start();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	*获取客户端消息队列里的第一条信息，若不存在返回null
	*/
	public String getmessage(){
		return cl.getmessage();
	}
	/**
	*开启接收客户端数据
	*/
	/*public void onread()throws IOException{
		cl.onread();
	}*/
}

