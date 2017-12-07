package server;

import java.io.*;
import java.net.*;
import java.security.*;
import sun.misc.*;
import java.util.Queue;
import java.util.LinkedList;
public class ClientLink{
	/**
	*socket为tcp连接成功的socket
	*/
	private Socket socket;
	/**
	*应用层数据队列
	*/
	private Queue<String> q; 
	/**
	*初始化队列
	*/
	public ClientLink(){
		q=new LinkedList<String>();
		socket=null;
	}
	/**
	*检测key并把key提取后加上规定字符串
	*/
	private String check(String line){
		String x=new String("Sec-WebSocket-Key: ");
		if(line.length()<x.length())
			return null;
		for(int i=0;i<x.length();i++){
			if(x.charAt(i)!=line.charAt(i))
				return null;
		}
		return line.substring(x.length())+"258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
	}
	/**
	*对字符串进行base64加密
	*/
	private String getBase64(byte[] hash){
		return (new BASE64Encoder()).encode(hash);
	}
	/**
	*构造函数初始化socket
	*/
	public ClientLink(Socket s){
		socket=s;
	}
	/**
	*获取socket
	*/
	public Socket getsocket(){
		return socket;
	}
	/**
	*开始读取数据，并把信息存到消息队列里
	*/
	public void onread()throws WebsocketException{
		try{
			InputStream is=socket.getInputStream();
			int length=-1;
			byte[] by=new byte[10005];
			while(true){
				if(socket==null)
					return;
				length=is.read(by);
				System.out.println(length);
				Framedata fd=(new Alzdata()).analizedata(by);
				fd.output();
				if(fd.getframetype()==8)
					break;
				q.offer(fd.getpayloaddata());
			}
			send(by);
			socket.close();
			socket=null;
		}
		catch(IOException e){
			throw new WebsocketException("can not open socket inputstream");
		}
		catch(NullPointerException e1){
			throw new WebsocketException("socket is null");
		}
	}
	/**
	*关闭连接
	*/
	public void onclose() throws WebsocketException{
		try{
			byte[] by=new byte[6];
			by[0]=-120;
			by[1]=-128;
			by[2]=-84;
			by[3]=5;
			by[4]=49;
			by[5]=79;
			send(by);
			socket.close();
			socket=null;
		}
		catch(IOException e){
			throw new WebsocketException("socket close error");
		}	
	}
	/**
	*从消息队列中返回信息
	*/
	public String getmessage(){
		return q.poll();
	}
	/**
	*判断消息队列是否为空
	*/
	public boolean empty(){
		return q.peek()==null;
	}
	/**
	*发送信息给客户端,message为服务端封装好的字节信息
	*/
	public void send(byte[] message) throws WebsocketException{
		try{
			(socket.getOutputStream()).write(message);
		}
		catch(IOException e){
			throw new WebsocketException("can not open socket outputstream");
		}				
		catch(NullPointerException e1){
			throw new WebsocketException("socket is null");
		}
	}
	/**
	*开启线程后会接受websocket握手信息，然后再从信息中分离出key，再对key进行sha-1 hash后再base64加密,最后把信息返回给客户端检验
	*/
	public void run() throws WebsocketException{
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line=null;
			String res=null;
			while(!(line=br.readLine()).equals("")){
				System.out.println(line);
				String key=check(line);
				if(key!=null){
					MessageDigest sha1=MessageDigest.getInstance("sha-1");
					sha1.update(key.getBytes());
					byte[] hash=sha1.digest();
					res=getBase64(hash);
				}
			}
			System.out.println(res);
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("HTTP/1.1 101 Web Socket Protocol Handshake\r\n");
            bw.write("Upgrade: websocket\r\n");
            bw.write("Connection: Upgrade\r\n");
            bw.write("Sec-WebSocket-Accept: "+res+"\r\n\r\n");
			bw.flush();
			onread();
		}
		catch(UnsupportedEncodingException e1){
			throw new WebsocketException("encoding error");
		}
		catch(IOException e2){
			throw new WebsocketException("open socket inputstream error");
		}
		catch(NoSuchAlgorithmException e3){
			throw new WebsocketException("no such algorighm");
		}
	}
}

