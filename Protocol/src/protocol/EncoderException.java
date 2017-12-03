package protocol;

/**
 * 此类为编码器专属异常类
 * 构造器除了父类的构造器，该异常类新增了对造成异常的文本或协议体的携带
 * 抛出时候指定了错误的地方
 *
 * Created by zyvis on 2017/11/29.
 */
public class EncoderException extends Exception {
    private String errorDecodeNeededString;
    private ProtocolStruct errorEncodeNeededProtocolStruct;

    public EncoderException(String message) {
        super(message);
    }

    /**
     * 抛出异常时候将异常信息
     * 和导致错误异常的待解码文本一起携带抛出
     * 使用get方法获取
     *
     * @param msg                       异常信息
     * @param errorDecodeNeededString   待解码文本
     */
    public EncoderException(String msg,String errorDecodeNeededString){
        this(msg);
        this.errorDecodeNeededString=errorDecodeNeededString;
    }

    /**
     * 抛出异常时候将异常信息
     * 和导致错误异常的待编码协议体一起携带抛出
     * 使用get方法获取
     *
     * @param msg                               异常信息
     * @param errorEncodeNeededProtocolStruct   待编码协议体
     */
    public EncoderException(String msg,ProtocolStruct errorEncodeNeededProtocolStruct) {
        this(msg);
        this.errorEncodeNeededProtocolStruct = errorEncodeNeededProtocolStruct;
    }

    public String getErrorDecodeNeededString() {
        return errorDecodeNeededString;
    }

    public ProtocolStruct getErrorEncodeNeededProtocolStruct() {
        return errorEncodeNeededProtocolStruct;
    }
}
