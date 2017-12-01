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
	*服务器封装数据,返回封装后的数据
	*/
	private byte[] packdata(String message){
		byte[] content=null;
		byte[] temp=message.getBytes();
		if(message.length()<126){
			content=new byte[message.length()+2];
			content[0]=(byte)(0x81);
			content[1]=(byte)message.length();
			System.arraycopy(temp,0,content,2,message.length());
		}
		else if(message.length()<0xFFFF){
			content=new byte[message.length()+4];
			content[0]=(byte)(0x81);
			content[1]=(byte)126;
			content[3]=(byte)(message.length()&0xFF);
			content[2]=(byte)(message.length()>>8&0xFF);
			System.arraycopy(temp,0,content,4,message.length());
		}
		else{
			//暂时不做处理
		}
		return content;
	}
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
	public void send(String message){
		byte[] ms=packdata(message);
		cl.send(ms);	
	}
	/**
	*开启接收客户端数据
	*/
	/*public void onread()throws IOException{
		cl.onread();
	}*/
}

