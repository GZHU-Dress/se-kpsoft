package server;

import log.Core;
import log.InstallException;
import log.Log;

import java.util.concurrent.FutureTask;


//import log.Core;
public class test {
    public static String packdata(String s){
        String msg="{\"message\":\"";
        return msg+s+"\""+",\"color\":\"#CECECE\",\"bubbleColor\":\"#2E2E2E\",\"fontSize\":\"12\",\"fontType\":\"黑体\"}";
    }
	public static void main(String[] args) throws InstallException {
        Core.install();

        WebSocketMod webSocketMod=new Server();

        try {
            webSocketMod.setup(10011);
            FutureTask futureTask=new FutureTask(webSocketMod);
            new Thread(futureTask).start();
            Log.d("webSocketMod install");
            while(true){
                if(webSocketMod.hasNextLine()){
                    String now=webSocketMod.readNextLine();
                    Log.d(now);
                    webSocketMod.send(packdata("mask=true, pllength"));
                }
            }
        } catch (WebsocketException e) {
            Log.e(e.getMessage(),e);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
