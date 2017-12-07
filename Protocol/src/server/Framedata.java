package server;

public class Framedata{
	/**
	*是否为最后一帧
	*/
	private boolean fin;
	/**
	*帧的数据类型
	*/
	private int frametype;
	/**
	*是否经过掩码处理
	*/
	private boolean mask;
	/**
	*数据长度
	*/
	private int pllength;
	/**
	*应用层数据
	*/
	private String payloaddata;


	public Framedata(){
		fin=false;
		frametype=pllength=0;
		mask=false;
		payloaddata="";
	}

	public boolean isFin() {
		return fin;
	}

	public void setFin(boolean fin) {
		this.fin = fin;
	}

	public int getFrametype() {
		return frametype;
	}

	public void setFrametype(int frametype) {
		this.frametype = frametype;
	}

	public boolean isMask() {
		return mask;
	}

	public void setMask(boolean mask) {
		this.mask = mask;
	}

	public int getPllength() {
		return pllength;
	}

	public void setPllength(int pllength) {
		this.pllength = pllength;
	}

	public String getPayloaddata() {
		return payloaddata;
	}

	public void setPayloaddata(String payloaddata) {
		this.payloaddata = payloaddata;
	}



}

