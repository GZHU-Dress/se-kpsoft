import java.io.*;
import java.net.*;
public class Server extends Thread{
	/**
	*端口号为PORT
	*/
	private int PORT;
	/**
	*处理连接和读取数据的ClientLink
	*/
	private ClientLink cl;
	/**
	*创建监听端口为p的server对象
	*/
	public Server(int p){
		PORT=p;
		cl=new ClientLink();
	}
	/**
	*启动服务器
	*/
	public void run(){
		try{
			ServerSocket server=new ServerSocket(PORT,1);
			System.out.println("server start");
			while(true){
				Socket socket=server.accept();
				cl=new ClientLink(socket);
				cl.run();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	*获取客户端消息队列里的第一条消息
	*/
	public String getmessage(){
		return cl.getmessage();
	}
	/**
	*判断消息队列是否为空
	*/
	public boolean empty(){
		return cl.empty();
	}
	/**
	*将要发送的信息封装，再发送给客户端数据
	*/
	public void send(String message) throws IOException{
		byte[] ms=Alzdata.packdata(message);
		cl.send(ms);	
	}
	/**
	*开启接收客户端数据
	*/
	/*public void onread()throws IOException{
		cl.onread();
	}*/
}

