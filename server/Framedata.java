public class Framedata{
	private boolean fin;//表示是否为最后一帧
	private int frametype;//帧的数据类型
	private boolean mask;//是否经过掩码处理
	private int pllength;//数据长度
	private String payloaddata;//应用层数据
	public Framedata(){
		fin=false;
		frametype=pllength=0;
		mask=false;
		payloaddata="";
	}
	public void setfin(boolean f){
		fin=f;
	}
	public void setframetype(int ft){
		frametype=ft;
	}
	public void setmask(boolean m){
		mask=m;
	}
	public void setpllength(int pllen){
		pllength=pllen;
	}
	public void setpayloaddata(String pl){
		payloaddata=pl;
	}
	public boolean getfin(){
		return fin;
	}
	public int getframetype(){
		return frametype;
	}
	public boolean getmask(){
		return mask;
	}
	public int getpllength(){
		return pllength;
	}
	public String getpayloaddata(){
		return payloaddata;
	}
	public void output(){
		System.out.println("fin="+fin);
		System.out.println("frametype="+frametype);
		System.out.println("mask="+mask);
		System.out.println("pllength="+pllength);
		System.out.println("payloaddata="+payloaddata);
	}
}

