package protocol;

public class KPprotocolEncoder{

    private EncoderImpl encoder;
    private ProtocolStruct  protocolStruct;

    public final static String splitSignal=";";
    public final static String encoderVersion ="0.1";

    public String encode(String header_msgType, String device_type, int device_timeStamp, int device_timeOffset, String[] instruction,String[] data) throws EncoderException{

        return this.encode(new Instruct(header_msgType,device_type,device_timeStamp,device_timeOffset,instruction,data));
    }
    public String encode(String header_msgType, String device_type, String[] instruction,String[] data) throws EncoderException{

        return this.encode(new Instruct(header_msgType,device_type,instruction,data));
    }

    public String encode(Instruct instruct) throws EncoderException {
        String instructs="";
        for (String i : instruct.getInstruction()) {
               instructs+=i;
               instructs+=splitSignal;
        }
        return encoder.encode(new ProtocolStruct(
                encoderVersion +splitSignal+instruct.getHeader_msgType(),
                instruct.getDevice_type()+splitSignal+(instruct.getDevice_timeStamp()==0?"":instruct.getDevice_timeStamp())
                        +splitSignal+(instruct.getDevice_timeOffset()==0?"":instruct.getDevice_timeOffset()),
                        instructs,
                        instruct.getData()
                ));
    }

    public Instruct decode(ProtocolStruct protocolStruct)throws EncoderException{
        String[] head=protocolStruct.getHeader().split(splitSignal);
        if(head.length!=2)
            throw new EncoderException("the header line is not in law",protocolStruct);
        if(!head[0].equals(encoderVersion)) {
            double protocolversion;
            try {
                 protocolversion = Double.valueOf(head[0]);
            }catch (NumberFormatException nume){
                throw new EncoderException("protocol version is not in law",protocolStruct);
            }
            if(Double.valueOf(encoderVersion)<protocolversion)
                throw new EncoderException("protocol version is not adaptive: encoder version : " + encoderVersion + " protocol struct version : " +protocolversion, protocolStruct);
        }
        String[] instruct=protocolStruct.getInstruction().split(splitSignal);
        String[] data=protocolStruct.getData();
        String[] device=protocolStruct.getDevice().split(splitSignal);
        switch (device.length){
            case 1:return new Instruct(head[1], device[0],instruct,data);
            case 2:return new Instruct(head[1], device[0],Integer.valueOf(device[1]),0,instruct,data);
            case 3:return new Instruct(head[1], device[0],Integer.valueOf(device[1]),Integer.valueOf(device[2]),instruct,data);
            default: throw new EncoderException("device line is not in law",protocolStruct);
        }
    }





}
