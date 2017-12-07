package server;

public class Alzdata{
	/**
	*服务器封装数据,返回封装后的数据
	*/
	public static byte[] packdata(String message){
	    byte[] content=null;
	    byte[] temp=message.getBytes();
	    if(message.length()<126){
	        content=new byte[message.length()+2];
	        content[0]=(byte)(0x81);
	        content[1]=(byte)message.length();
	        System.arraycopy(temp,0,content,2,message.length());
	    }
	    else if(message.length()<0xFFFF){
	        content=new byte[message.length()+4];
	        content[0]=(byte)(0x81);
	        content[1]=(byte)126;
	        content[3]=(byte)(message.length()&0xFF);
	        content[2]=(byte)(message.length()>>8&0xFF);
	        System.arraycopy(temp,0,content,4,message.length());
	    }
	    else{
	        //暂时不做处理	
	    }
	    return content;
	}
	private void solve(byte[] by,Framedata fd,int st,int length){
		fd.setpllength(length);
		byte[] data;
		data=new byte[length];
		byte[] maskdata=new byte[4];
		if(fd.getmask()){
			System.arraycopy(by,st,maskdata,0,4);
			System.arraycopy(by,st+4,data,0,length);
		}
		else
			System.arraycopy(by,st,data,0,length);
		for(int i=0;i<length;i++){
			data[i]=(byte)(data[i]^maskdata[i%4]);
		}
		fd.setpayloaddata(new String(data));
	}
	/**
	*by是数据包，此方法为解析数据帧的作用
	*/
	public Framedata analizedata(byte[] by){
		Framedata fd=new Framedata();	
		fd.setfin((by[0]&0x80)==0x80);
		fd.setframetype(by[0]&0x0F);
		fd.setmask((by[1]&0x80)==0x80);
		int length=by[1]&0x7f;
		if(length<=125)
			solve(by,fd,2,length);
		else if(length==126){
			length=by[2]<<8|by[3];
			solve(by,fd,4,length);
		}
		else{
			length=0;
			for(int i=2;i<10;i++)
				length=length<<8|by[i];
			solve(by,fd,10,length);
		}
		return fd;
	}
}

