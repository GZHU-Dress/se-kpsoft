import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Logcat抽象类，此类对象将被Log所规定
 * 可以通过覆写实体类进行Log输出的实现
 * Created by zyvis on 2017/5/28.
 */
public abstract class Logcat {
    /**
     * 该类指定一个输出流，用于Log的输出
     */
    public OutputStream outputStream;

    /**
     * 指定当前Logcat的输出等级，如Error则为全部输出
     * None则全不输出
     */
    protected Log.Level levelFilter;
    /**
     * 每一个Logcat拥有自己的Tag筛选器，包括一个Tag-开关键值对
     * 用于控制每一个携带Tag的Log是否打印。
     */
    protected LinkedHashMap<String, Boolean> tagFilter;

    /**
     * 此方法用于将所有经过该Logcat的日志对象进行一次访问
     * 可以在{@link #filter(SingleLog)}之中进行调用
     * 可以新建一个列表对这些日志进行搜集，暂存在对象之中
     *
     * @param singleLog 一个日志
     */
    public abstract void collect(SingleLog singleLog);

    /**
     * Log之中直接调用这个功能对日志对象进行处理
     * 本身应当包含经过等级筛选器、Tag筛选器的检验
     * 可以实现{@link #collect(SingleLog)}方法对日志进行收集
     * 使用{@link #print(SingleLog)}对日志进行打印（向输出流输出）
     *
     * @param singleLog 一个日志
     */
    public abstract void filter(SingleLog singleLog);

    /**
     * 此方法可以在{@link #filter(SingleLog)}之中进行调用
     * 对数据进行输出打印，往指定的输出流打印
     *
     * @param singleLog 一个日志
     */
    public abstract void print(SingleLog singleLog);

    /**
     * 构造函数的时候初始化等级筛选器和Tag筛选器
     */
    private Logcat() {
        levelFilter= Log.Level.Verbose;
        tagFilter = new LinkedHashMap<String, Boolean>();
    }

    /**
     * 可以在默认Logcat构造时重新定义输出流，
     * 同时会调用{@link Logcat#Logcat()}进行表格初始化
     *
     * @param outputStream
     */
    public Logcat(OutputStream outputStream) {
        this();
        this.outputStream = outputStream;
    }

    /**
     * 设置筛选级别，只输出该级别与以上的log
     * 如 只输出warning以上的log，则Debug级别不会输出
     *
     * @param levelFilter
     */
    public void setLevelFilter(Log.Level levelFilter) {
        this.levelFilter = levelFilter;
    }

    /**
     * 设置输出级别为全部输出，包括verbose级别以上
     */
    public void logAll() {
        this.levelFilter = Log.Level.Verbose;
    }

    /**
     * 不输出任何的log
     */
    public void logNone() {
        this.levelFilter = Log.Level.None;
    }

    /**
     * 此方法用于返回所有带Throwable的条目
     *
     * @return 带Throwable的条目的迭代器
     */
    public abstract Iterator getAllThrowable();

    /**
     * 此方法用于对此Logcat对象进行Tag筛选规则的添加
     * 添加进去后{@link #filter(SingleLog)}方法应当完成
     * 对此表格中所指定信息的筛选，默认开关为Falseu
     * 即调用该方法，则屏蔽所指定的Tag的日志
     * 关于Tag的获取，详见{@link Log#getNowTag()}可以获取当前的Tag
     *
     * @param tag 指定要进行筛选的Tag
     */
    public void pass(String tag) {
        tagFilter.put(tag, false);
    }

    @Override
    public String toString() {
        return this.getClass().getName()+" [ level : "+levelFilter+" , outputStream : "+ (outputStream==null?"null":outputStream.getClass().getName())+" ] "+super.toString();
    }
}
