package server;

public class WebsocketException extends Exception{
	public WebsocketException(String message){
		super(message);
	}

	public WebsocketException(String message, Throwable cause) {
		super(message, cause);
	}
}
