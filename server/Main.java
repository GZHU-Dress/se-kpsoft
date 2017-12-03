//import log.Core;
public class Main{
	public static void main(String[] args){
//		Core.install();
//		Log.d("hrllo");
		Server server=new Server(11000);
		server.run();		
	}
}
