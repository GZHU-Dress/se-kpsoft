package server;

import log.Core;
import log.Log;
public class Alzdata{
	/**
	*服务器封装数据,返回封装后的数据
	*/
	public static byte[] packdata(String message){
	    byte[] content=null;
	    byte[] temp=message.getBytes();
	    if(temp.length<126){
	        content=new byte[temp.length+2];
	        content[0]=(byte)(0x81);
	        content[1]=(byte)temp.length;
	        System.arraycopy(temp,0,content,2,temp.length);
	    }
	    else if(message.length()<0xFFFF){
	        content=new byte[temp.length+4];
	        content[0]=(byte)(0x81);
	        content[1]=(byte)126;
	        content[3]=(byte)(temp.length&0xFF);
	        content[2]=(byte)(temp.length>>8&0xFF);
	        System.arraycopy(temp,0,content,4,temp.length);
	    }
	    else{
	        //暂时不做处理	
	    }
	    return content;
	}
	private static void solve(byte[] by,Framedata fd,int st,int length){
		fd.setPllength(length);
		byte[] data;
		data=new byte[length];
		byte[] maskdata=new byte[4];
		if(fd.isMask()){
			System.arraycopy(by,st,maskdata,0,4);
			System.arraycopy(by,st+4,data,0,length);
		}
		else
			System.arraycopy(by,st,data,0,length);
		for(int i=0;i<length;i++){
			data[i]=(byte)(data[i]^maskdata[i%4]);
		}
		fd.setPayloaddata(new String(data));
	}
	/**
	*by是数据包，此方法为解析数据帧的作用
	*/
	public static Framedata analizedata(byte[] by){
		Framedata fd=new Framedata();	
		fd.setFin((by[0]&0x80)==0x80);
		fd.setFrametype(by[0]&0x0F);
		fd.setMask((by[1]&0x80)==0x80);
		int length=by[1]&0x7f;
		if(length<=125)
			solve(by,fd,2,length);
		else if(length==126){
			length=by[2]&0xFF;
			length=length<<8|(by[3]&0xFF);
			solve(by,fd,4,length);
		}
		else{
			length=0;
			for(int i=2;i<10;i++)
				length=length<<8|(by[i]&0xFF);
			solve(by,fd,10,length);
		}
		return fd;
	}
}

