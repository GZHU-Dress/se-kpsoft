package server;

import log.Log;

import java.io.*;
import java.net.*;
public class Server implements WebSocketMod{
	/**
	*端口号为serverPort
	*/
	private int serverPort;
	/**
	*处理连接和读取数据的ClientLink
	*/
	private ClientLink clientLink;

	@Override
	public void setup(int port) throws WebsocketException {
		this.serverPort =port;
		clientLink =new ClientLink();
	}

	@Override
	public boolean hasNextLine() {
		return !clientLink.isEmpty();
	}

	/**
	 *获取客户端消息队列里的第一条消息
	 */
	@Override
	public String readNextLine() throws WebsocketException {
		return clientLink.getMessage();
	}
	/**
	 *将要发送的信息封装，再发送给客户端数据
	 */
	@Override
	public void send(String msg) throws WebsocketException {
		byte[] ms=Alzdata.packdata(msg);
		clientLink.send(ms);
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
	public Boolean call()throws WebsocketException{
        ServerSocket server= null;
        try {
            server = new ServerSocket(serverPort,1);
        } catch (IOException e) {
            throw new WebsocketException("server socket build failed : the port is busy");
        }
        try{
            Log.v("server start");
			while(true){
				Socket socket=server.accept();
				clientLink =new ClientLink(socket);
				clientLink.run();
			}
		}
		catch(IOException e){
			throw new WebsocketException("fail in open the accepted socket");
		}catch (WebsocketException e1){
			Log.v(e1);
		}
		return true;
	}

	/**
	*关闭socket当前socket连接
	*/
	@Override
	public void close() throws WebsocketException{
		clientLink.onclose();
	}

}

