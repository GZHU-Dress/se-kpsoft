package server;

import java.net.ServerSocket;

/**
 * 此类为WebSocket模块
 * 内部包含了对socket的获取，ws协议的连接
 *
 * Created by zyvis on 2017/11/30.
 */
public interface WebSocketMod extends Runnable{
    /**
     * 这个方法里指定一个用于监听的端口，由该模块实现
     * 当创建失败的时候抛出WebSocketException异常
     *
     * 初步版本：每一个WebSocket模块只包含对一个端口的监听
     *
     * @param port                  要监听的端口
     * @throws WebsocketException   无法正确建立监听端口等异常抛出
     */
    void setup(int port) throws WebsocketException;

    /**
     * 该方法尝试断开并关闭socket连接
     *
     * @throws WebsocketException   无法正常关闭时抛出异常
     */
    void close() throws WebsocketException;

    /**
     * 询问是否有下一条指令的存在
     * 这个方法类似bufferedReader中的类似名方法
     * 通常用在while()之中，轮询是否有新消息的接入
     *
     * @return  是否有下一条指令的存在
     */
    boolean hasNextLine();

    /**
     * 通常结合{@link WebSocketMod#hasNextLine()}一起使用，
     * 在轮询出现结果的时候调用该方法获取新的消息
     *
     * @return                      新的信息
     * @throws WebsocketException   当无法被读取或其他异常发生时抛出
     */
    String readNextLine()throws WebsocketException;

    /**
     * 调用该方法进行信息传送
     * 该模块中存在指令缓冲区，可以调用是否直接传输出去
     *
     * @param msg                   传送的信息
     * @param sendNow               标识其是否立即传输
     * @throws WebsocketException   当发送信息发生问题时候抛出异常
     */
    void send(String msg,boolean sendNow) throws WebsocketException;
}
