package protocol;

/**
 * protocol version : 0.1
 * Created by zyvis on 2017/11/30.
 */
public class KPProtocolStruct extends ProtocolStruct {

    public KPProtocolStruct(String header, String device, String instruction, String[] data) {
        super(header, device, instruction, data);
    }
    private String header_version;
    private String header_msgType;
    private String device_type;
    private String device_timeStamp;
    private String device_timeOffset;
    private String[] instruction;
    private String[] data;
}
