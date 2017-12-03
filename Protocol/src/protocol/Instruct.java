package protocol;

/**
 * 该类为解封装协议后获得到的可读取信息
 * 包含了信息类型、指明设备、时间、一系列的指令和数据
 *
 */
public class Instruct {
    /**
     * 头部行中的信息类型
     */
    private String header_msgType;
    /**
     * 被指明的设备类型
     */
    private String device_type;
    /**
     * 附加项，是设备的时间戳
     * 默认为0，即不参与
     */
    private int device_timeStamp=0;
    /**
     * 附加项，是设备的时间戳偏移
     * 默认为0，即不参与
     */
    private int device_timeOffset=0;
    /**
     * 一系列的指令
     */
    private String[] instruction;
    /**
     * 一系列的数据
     */
    private String[] data;


    /**
     *  此构造器为全构造器
     *
     * @param header_msgType
     * @param device_type
     * @param device_timeStamp  0即为空
     * @param device_timeOffset 0即为空
     * @param instruction
     * @param data
     */
    public Instruct(String header_msgType, String device_type, int device_timeStamp, int device_timeOffset, String[] instruction, String[] data) {
        this(header_msgType, device_type, instruction, data);
        this.device_timeStamp =device_timeStamp;
        this.device_timeOffset =device_timeOffset;
    }


    /**
     *  此构造器为移除了时间和偏移的构造器
     *
     * @param header_msgType
     * @param device_type
     * @param instruction
     * @param data
     */
    public Instruct(String header_msgType, String device_type, String[] instruction, String[] data) {
        this.header_msgType =(header_msgType==null?"":header_msgType);
        this.device_type =(device_type==null?"":device_type);
        for (int i = 0; i < instruction.length; i++) {
            if(instruction[i]==null)instruction[i]="";
        }
        for (int i = 0; i < data.length; i++) {
            if(data[i]==null)data[i]="";
        }
        this.instruction =instruction;
        this.data =data;
    }

    public String getHeader_msgType() {
        return header_msgType;
    }

    public String getDevice_type() {
        return device_type;
    }

    public int getDevice_timeStamp() {
        return device_timeStamp;
    }

    public int getDevice_timeOffset() {
        return device_timeOffset;
    }

    public String[] getInstruction() {
        return instruction;
    }

    public String[] getData() {
        return data;
    }
}
