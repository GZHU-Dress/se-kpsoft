package server;

import java.io.*;
import java.net.*;
public class Server implements WebSocketMod{
	/**
	*端口号为PORT
	*/
	private int serverPort;
	/**
	*处理连接和读取数据的ClientLink
	*/
	private ClientLink cl;

	@Override
	public void setup(int port) throws WebsocketException {
		this.serverPort =port;
		cl=new ClientLink();
	}

	@Override
	public boolean hasNextLine() {
		return false;
	}

	/**
	 *获取客户端消息队列里的第一条消息
	 */
	@Override
	public String readNextLine() throws WebsocketException {
		return cl.getmessage();
	}
	/**
	 *将要发送的信息封装，再发送给客户端数据
	 */
	@Override
	public void send(String msg, boolean sendNow) throws WebsocketException {
		byte[] ms=Alzdata.packdata(msg);
		cl.send(ms);
	}

	/**
	 * 隐藏空构造体
	 */
	public Server() {
	}

	/**
	*启动服务器
	*/
	@Override
	public void run(){
		try{
			ServerSocket server=new ServerSocket(serverPort,1);
			System.out.println("server start");
			while(true){
				Socket socket=server.accept();
				cl=new ClientLink(socket);
				cl.run();
			}
		}
		catch(IOException e){
			return;
		}
		catch(WebsocketException e){
			return;
		}
	}

	/**
	*判断消息队列是否为空
	*/
	public boolean empty(){
		return cl.empty();
	}

	public void send(String message) throws WebsocketException{

	}
	/**
	*关闭socket当前socket连接
	*/
	@Override
	public void close() throws WebsocketException{
		cl.onclose();
	}
	/**
	*开启接收客户端数据
	*/
	/*public void onread()throws IOException{
		cl.onread();
	}*/
}

