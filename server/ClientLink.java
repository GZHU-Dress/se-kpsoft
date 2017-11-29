import java.io.*;
import java.net.*;
import java.security.*;
import sun.misc.*;
public class ClientLink extends Thread{
	private Socket socket;
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
	private String getBase64(byte[] hash){
		return (new BASE64Encoder()).encode(hash);
	}
	public ClientLink(Socket s){
		socket=s;
	}
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
			//PrintStream pr=new PrintStream(socket.getOutputStream());
			//pr.println("111111");
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("HTTP/1.1 101 Web Socket Protocol Handshake\r\n");
            bw.write("Upgrade: websocket\r\n");
            bw.write("Connection: Upgrade\r\n");
            bw.write("Sec-WebSocket-Accept: "+res+"\r\n\r\n");
			bw.flush();
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

