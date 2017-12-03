package log;

import timer.TimerTag;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;

/**
 * https://developer.android.com/reference/android/util/Log.html
 * 此类包含了五种类型的日志输出方法，源自android.util.log类的仿生
 * Created by zyvis on 2017/5/28.
 */
public final class Log {

    /**
     * 枚举了五个对象
     * 分别指定五种级别的log
     */
    public enum Level{
        None("none", Color.gray,600),
        Verbose("verbose", Color.white,100),
        Debug("debug", Color.blue,200),
        Info("info", Color.green,300),
        Warn("warn", Color.yellow,400),
        Error("error", Color.red,500);
        public String name;
        public int level;
        public Color color;
        Level(String name, Color color, int level){
            this.name=name;
            this.color=color;
            this.level=level;
        }

    }

    private static class LogInside {
        private final static Log log = new Log();
    }

    /**
     * 隐藏构造器，不允许创建对象
     */
    private Log() {
        logcats = new LinkedList<Logcat>();
    }

    /**
     * @return 询问Log类是否输出
     */
    public boolean isLog() {
        return log;
    }

    /**
     * 指定log的输出与否
     */
    protected boolean log=true;

    /**
     * 用一个容器存放logcat的列表
     */
    private Collection<Logcat> logcats;



    /**
     * 此方法调用线程的堆栈追踪，可以查询到调用这个方法的类和方法
     *
     * @return 返回当前调用这个方法的类和方法
     */
    public static String getNowTag() {
        StackTraceElement tmp = Thread.currentThread().getStackTrace()[2];
        return tmp.getClassName() + ":" + tmp.getMethodName();
    }

    /**
     * 此方法调用线程的堆栈追踪，可以查询到调用这个方法的类和方法
     * 与公开方法不同，此私有方法用于创建SingleLog时候调用
     * 在堆栈追踪上多增了一层嵌套
     *
     * @return 返回当前调用这个方法的类和方法
     */
    private static String getTag() {
        StackTraceElement tmp = Thread.currentThread().getStackTrace()[3];
        return tmp.getClassName() + ":" + tmp.getMethodName();
    }

    private static long timerRecoder;
    private static long timerRecoderNano;

    /**
     * 调用该方法来进行计时
     * 如果是开始，不会产生Log.d标志的内容（为了更准确）
     * 如果是结束，会记录结束时间点，并计算前后时间间隔
     * 注意一定要传第二个参数，不然会被定向到{@link Log#d(Object)}
     *
     * @param timerTag  设置开始或结束标识
     * @param nano      选择是否以纳秒计算
     */
    public static void d(TimerTag timerTag,boolean nano){
        if(nano){
            if (timerTag.equals(TimerTag.START)) {
                timerRecoderNano = System.nanoTime();
                return;
            }else {
                long end = System.nanoTime();
                long duration = end - timerRecoderNano;
                Log.d("timer end : " + (end - Core.coreStartTimeNano) + " , duration :　" + duration + "ns");
                return;
            }
        }else {
            if (timerTag.equals(TimerTag.START)) {
                timerRecoder = System.currentTimeMillis();
                //Log.d("timer start : " + (timerRecoder-Core.coreStartTime));
                return;
            } else {
                long end = System.currentTimeMillis();
                long duration = end - timerRecoder;
                Log.d("timer end : " + (end - Core.coreStartTime) + " , duration :　" + duration + "ms");
                return;
            }
        }
    }


    /**
     *  Log.*(Object)
     *
     * 该系列的接口用于解释所有传入的对象
     * 统一执行toString函数进行解释
     *
     * @param obj   任何需要Log的对象
     */
    public static void v(Object obj){
        Log.v(obj.toString());
    }
    public static void d(Object obj){
        Log.d(obj.toString());
    }
    public static void i(Object obj){
        Log.i(obj.toString());
    }
    public static void w(Object obj){
        Log.w(obj.toString());
    }
    public static void e(Object obj){
        Log.e(obj.toString());
    }

    /**
     *  Log.v()
     *
     *  该系列的接口抛出最简单的运行标志
     *  用于访问检查
     */
    public static void v() {
        LogInside.log.boardcast(new SingleLog(Level.Verbose, getTag(), getTag()+" run ."));
    }

    /**
     *  Log.*(String)
     *
     * 传递信息输出
     *
     * @param msg   要传递的信息
     */
    public static void v(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Verbose, getTag(), msg));
    }
    public static void d(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Debug, getTag(), msg));
    }
    public static void i(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Info, getTag(), msg));
    }
    public static void w(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Warn, getTag(), msg));
    }
    public static void e(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Error, getTag(), msg));
    }

    /**
     *  Log.[d,w,e](String,Throwable)
     *
     * 该方法仅指定给测试类级别的log使用
     *
     * @param msg   传达的信息
     * @param tr    抛出的异常
     */
    public static void d(String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Debug, getTag(), msg, tr));
    }
    public static void w(String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Warn, getTag(), msg, tr));
    }
    public static void e(String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Error, getTag(), msg, tr));
    }

    /**
     *  Log.*(String,String)
     *
     *  伴随Tag的记录，用于区分
     *
     * @param tag   区分标记
     * @param msg   传达的信息
     */
    public static void v(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Verbose, tag, msg));
    }
    public static void d(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Debug, tag, msg));
    }
    public static void i(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Info, tag, msg));
    }
    public static void w(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Warn, tag, msg));
    }
    public static void e(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Error, tag, msg));
    }

    /**
     *  Log.[d,w,e](String,String,Throwable)
     *
     * 该方法仅指定给测试类级别的log使用
     * 允许伴随Tag输出，用于区分
     *
     * @param tag   区分标记
     * @param msg   传达的信息
     * @param tr    抛出的异常
     */
    public static void d(String tag, String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Debug, tag, msg, tr));
    }
    public static void w(String tag, String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Warn, tag, msg, tr));
    }
    public static void e(String tag, String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Error, tag, msg, tr));
    }

    /**
     * 方法进行同步控制
     * 此方法将所有传入的Log扩散，广播到Log在记列表中的所有监听器
     * 可以使用{@link #addLogcat(Logcat)}添加监听器
     *
     * @param singleLog 单条要广播的log
     */
    private synchronized void boardcast(final SingleLog singleLog) {
        if(!log)return;
        for (Logcat logcat:
                LogInside.log.logcats) {
            logcat.filter(singleLog);
        }
    }

    /**
     * 此方法用于往Log中添加Logcat监听器，可扩展。
     *
     * @param logcat Logcat的实体类
     */
    public static void addLogcat(Logcat logcat) {
        System.out.println("check input logcat="+logcat.toString());
        Iterator iterator= LogInside.log.logcats.iterator();
        while(iterator.hasNext()){
            Logcat tmp= (Logcat) iterator.next();
            System.out.println("check once logcat="+tmp.toString());
            if(tmp.equals(logcat)){
                System.out.println("same");
                return;
            }
        }
        LogInside.log.logcats.add(logcat);
        Log.d("add new log.Logcat : "+logcat.toString());
    }


    /**
     * 此方法归档Log支持的原始五个等级和None
     * 返回为对象组
     *
     * @return Loglevel的对象组
     */
    public static Level[] getAllLevel() {
        return new Level[]{
                Level.None,
                Level.Verbose,
                Level.Debug,
                Level.Info,
                Level.Warn,
                Level.Error
        };
    }
    /**
     * 关闭log输出功能
     */
    public static void turnOff(){
        LogInside.log.log=false;
    }

    /**
     * 打开log输出功能
     */
    public static void turnOn(){
        LogInside.log.log=true;
    }


}
