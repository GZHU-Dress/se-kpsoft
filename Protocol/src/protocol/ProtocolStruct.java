package protocol;

import log.Log;

import java.util.Arrays;

/**
 * 该类里定义了协议体，包含固定的几个大框架的调用
 * 还有具体的内容调用交由实现来的实现
 * Created by zyvis on 2017/11/29.
 */
public class ProtocolStruct {
    private String header;
    private String device;
    private String instruction;
    private String[] data;
    public final static String protocolRound="kp";
    public final static String headerRound="h";
    public final static String deviceRound="v";
    public final static String instructionRound="i";
    public final static String dataRound="d";
    public final static String dataMiniRound="";
    public final static String escapeChar="/o";
    public final static String[] changeChar=new String[]{"<",">","/"};

    public String getHeader() {
        return header;
    }

    public String getDevice() {
        return device;
    }

    public String getInstruction() {
        return instruction;
    }

    public String[] getData() {
        return data;
    }

    public ProtocolStruct(String header, String device, String instruction, String[] data) {
        this.header = header;
        this.device = device;
        this.instruction = instruction;
        this.data = data;
        nullToBlank();
    }

    public static String getMarkupHead(String markup){
        return "<"+markup+">";
    }
    public static String getMarkupTail(String markup){
        return "</"+markup+">";
    }

    /**
     * 生成转义文本，在编码的时候使用
     *
     * 将所有需要转换的字符 changeChar[]
     * 转为转义符（前方加 escapeChar
     */
    public void escapeAll(){
        for (String regex :changeChar) {
            Log.d("regex: "+regex+"; after: "+escapeChar+regex);
            header=header.replaceAll(regex,escapeChar+regex);
            device=device.replaceAll(regex,escapeChar+regex);
            instruction=instruction.replaceAll(regex,escapeChar+regex);
            for(int i=0;i<data.length;i++){
                data[i]=data[i].replaceAll(regex,escapeChar+regex);
            }
        }
    }

    /**
     * 还原转义符，在解码的时候使用
     */
    public void pursueAll(){
        for (int j=changeChar.length-1;j>=0;j--) {
            String regex=changeChar[j];
            for(int i=data.length-1;i>=0;i--){
                data[i]=data[i].replaceAll(escapeChar+regex,regex);
            }
            instruction=instruction.replaceAll(escapeChar+regex,regex);
            device=device.replaceAll(escapeChar+regex,regex);
            header=header.replaceAll(escapeChar+regex,regex);
            Log.d("origin: "+escapeChar+regex+"; regex: "+regex);

        }
    }

    @Override
    public String toString() {
        String tmp="";
        for (String s :
                data) {
            tmp+="[ ";
            tmp += s;
            tmp+=" ]";
        }
        return "ProtocolStruct{" +
                "header=[ " + header + " ]" +
                ", device=[ " + device + " ]" +
                ", instruction=[ " + instruction + " ]" +
                ", data=[ " + tmp +
                " ]"+'}';
    }

    public void nullToBlank(){
        if(header==null)header="";
        if(device==null)device="";
        if(instruction==null)instruction="";
        if(data==null) {
            data = new String[]{};
        }else {
            for (int i = 0; i < data.length; i++) {
                if(data[i]==null)data[i]="";
            }
        }

    }
}
