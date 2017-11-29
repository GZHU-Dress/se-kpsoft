package encryption;


import java.util.Objects;

/**
 * 此接口接管加密解密的一个编解码器
 *
 * 对key的要求：
 * 实现的类里要有一个key对象，并对其封装。
 * key的类型可以由创建时候定义，由实现类中完成通过key对明文密文的编解码
 * 访问控制要求：key可以被set一次，且不能被访问。
 *
 * 对encrypt的要求：
 * 实现类里有一个对象来询问该编解码器是否是加密的，
 * 这个变量在setkey被执行的之后需要被设置。
 * 访问控制要求：该变量不能被设置，只能被访问
 *
 *
 * Created by zyvis on 2017/11/29.
 */
public interface EncryptionEncoder<T> {
    /**
     * 编解码将会以该key为准
     * 该方法只能被执行一次
     *
     * @param key   key
     * @return      设置的结果
     */
    boolean setKey(T key);

    /**
     * 该方法询问这个编解码器是不是加密的
     *
     * @return  返回编解码器加密模式
     */
    boolean isEncrypt();

    /**
     * 该方法用于生产可以被此编解码器使用的key
     * 参数里的Objects对象可以由开发者设置
     *
     * @param objects   开发者设置参数
     * @return          返回可以被本编解码器使用的key
     */
    T generateKey(Objects... objects);

    /**
     * 解码器
     * 将密文进行解码，根据已有的钥匙或无需钥匙的自身算法
     *
     * @return  明文
     */
    String encode(String ComplexText);

    /**
     * 编码器
     * 将明文进行加密，根据已有的钥匙或无需钥匙的自身算法
     *
     * @return  密文
     */
    String codec(String originText);

}
