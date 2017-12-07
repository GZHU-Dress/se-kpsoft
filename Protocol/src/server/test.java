package server;

import log.Core;
import log.InstallException;
import log.Log;

//import log.Core;
public class test {
	public static void main(String[] args) throws InstallException {
        Core.install();

        WebSocketMod webSocketMod=new Server();

        try {
            webSocketMod.setup(10088);
            Log.d("webSocketMod install");
        } catch (WebsocketException e) {
            Log.e(e.getMessage(),e);
        }
    }
}
