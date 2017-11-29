import java.io.*;
import java.net.*;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.IOException;
import java.io.File;
public class Clientchat extends Thread{
	private Socket socket;
	public Clientchat(Socket s){
		socket=s;
	}
	public Socket getsocket(){
		return socket;
	}
	public void output(String s){
		try{
			socket.getOutputStream().write(s.getBytes());
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public void run(){
		File file=new File("data.log");
		try{
			if(!file.exists())
				file.createNewFile();
			try{
				PrintStream ps=new PrintStream(new FileOutputStream(file));
				System.setOut(ps);
				try{
					BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String line=null;
					while(true){
						line=br.readLine();
						if(line.equals("end"))
							break;
						System.out.println(line);
					}
					br.close();
					ps.close();
				}
				catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
			catch(FileNotFoundException e){}
		}
		catch(Exception e){}
	}
}

