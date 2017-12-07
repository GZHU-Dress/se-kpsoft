package server;

/**
 * 这个接口扩展自{@link WebSocketMod}
 * 新增了一个后台服务的安全机制，要求客户端持续发送心跳包
 * 后台服务可以通过接口里的方法设置与心跳链接相关的参数、模式
 *
 * 接口要求新增三个私有变量：
 *
 * 表示是否启用 心跳链接模式
 * boolean heartbeatMode = 默认值（推荐为false）
 *
 * 表示心跳包的检测时间，单位为毫秒
 * 应当在实现的时候注意最小值，不能低于2秒，最多不能超过3600秒
 * int     heartbeatDurationTime = 默认值（推荐为5000）
 *
 * 表示心跳包的内容，长度在[10,50]之间
 * String  heartbeatString = 默认值（推荐为标识符+符号等低碰撞率的文本）
 *
 * Created by visn on 17-12-7.
 */
public interface HeartbeatWebSocketMod extends WebSocketMod {

    /**
     * 询问是否为心跳包模式
     *
     * @return  心跳包模式
     */
    boolean isHeartbeatMode();

    /**
     * 设置心跳包的模式开启与否
     * 可以是使用该接口但不启动心跳模式（那有啥意义咧？）
     *
     * @param heartbeatMode 设置模式
     */
    void setHeartbeatMode(boolean heartbeatMode);

    /**
     * @return  心跳包的间隔时长
     */
    int getHeartbeatDurationTime();

    /**
     * 设置心跳包的间隔时间
     *
     * @param heartbeatDurationTime [2_000,3_600_000]
     * @throws WebsocketException   超出范围时候抛出异常
     */
    void setHeartbeatDurationTime(int heartbeatDurationTime)throws WebsocketException;

    /**
     * 获取心跳包的内容，由服务器后端调用检查用
     * 前端js代码需要实现对此的应答，可以通过调用后端
     *
     * @return  获取心跳包的内容
     */
    String getHeartbeatString();

    /**
     * 设置心跳包检测的内容
     *
     * @param heartbeatString       [10,50]
     * @throws WebsocketException   超出范围时候抛出异常
     */
    void setHeartbeatString(String heartbeatString)throws WebsocketException;


}
