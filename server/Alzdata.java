public class Alzdata{
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

