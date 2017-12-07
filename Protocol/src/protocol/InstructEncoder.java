package protocol;

/**
 *  该接口定义了从文本到指令体的总过程
 *  在实现接口的时候，需要底层实现对协议体接口的封装
 *
 * 文本到协议体是底层转换。通过网络获取的文本在这里转换为协议体
 * 再由协议体转换为指令体（其实是同一个东西的不同表现，
 * 协议体注重结构与封装完整性，指令体注重内容与解封装、可执行性）
 *
 * 详见指令体{@link Instruct}与协议体解码器{@link Encoder}
 *
 * Created by visn on 17-12-7.
 */
public interface InstructEncoder {

    /**
     * 编码器
     * 将指令体转换为String用于传输
     *
     * @param instruct
     * @return
     * @throws EncoderException
     */
    String encode(Instruct instruct) throws EncoderException;

    /**
     * 解码器
     * 将获取到的文本解码并返回协议体对象
     *
     * @param msg                 可以转化为协议体的文本
     * @return                    返回转化完的协议体
     * @throws EncoderException   当无法解码的时候抛出异常
     */
    Instruct decode(String msg)throws EncoderException;
}
