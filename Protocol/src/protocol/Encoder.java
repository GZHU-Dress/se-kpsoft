package protocol;

/**
 * 协议编解码器，包含了从文本到协议体的转换
 * Created by zyvis on 2017/11/29.
 */
public interface Encoder<T extends ProtocolStruct> {
     /**
      * 编码器
      * 将协议体转换为String用于传输
      *
      * @param t                   协议体
      * @return                    返回可以传输的String文本
      * @throws EncoderException   当无法编码的时候抛出异常
      */
     String encode(T t) throws EncoderException;

     /**
      * 解码器
      * 将获取到的文本解码并返回协议体对象
      *
      * @param msg                 可以转化为协议体的文本
      * @return                    返回转化完的协议体
      * @throws EncoderException   当无法解码的时候抛出异常
      */
     T decode(String msg) throws EncoderException;

}
