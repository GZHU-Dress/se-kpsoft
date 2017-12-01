import java.io.*;
import java.net.*;
import java.security.*;
import sun.misc.*;
import java.util.Queue;
import java.util.LinkedList;
public class ClientLink extends Thread{
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
	private ClientLink(){
		q=new LinkedList<String>();
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
		q=new LinkedList<String>();
		socket=s;
	}
	/**
	*开始读取数据，并把信息存到消息队列里
	*/
	public void onread()throws IOException{
		InputStream is=socket.getInputStream();
		int length=-1;
		byte[] by=new byte[10005];
		while(true){
			length=is.read(by);
			System.out.println(length);
			Framedata fd=(new Alzdata()).analizedata(by);
			fd.output();
			q.offer(fd.getpayloaddata());
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
		return q.element()==null;
	}
	/**
	*发送信息给客户端,message为服务端封装好的字节信息
	*/
	public void send(byte[] message) throws IOException{
		(socket.getOutputStream()).write(message);				
	}
	/**
	*开启线程后会接受websocket握手信息，然后再从信息中分离出key，再对key进行sha-1 hash后再base64加密,最后把信息返回给客户端检验
	*/
	public void run(){
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
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
	}
}

