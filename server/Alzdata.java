public class Alzdata{
	private void solve(byte[] by,Framedata fd,int st,int length){
		fd.setpllength(length);
		byte[] data;
		data=new byte[length];
		if(fd.getmask()){
			int maskdata=0;
			for(int i=st;i<st+4;i++)
				maskdata=maskdata<<8|by[i];
			fd.setmaskdata(maskdata);
			System.arraycopy(by,st+4,data,0,length);
		}
		else
			System.arraycopy(by,st,data,0,length);
		fd.setpayloaddata(new String(data));
	}
	public Framedata analizedata(byte[] by){//by数据包　bylength数据包长度 解析数据帧功能
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

