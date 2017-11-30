
/**
 * 一个单条日志的类，包含了一个信息等级、标记和日志信息，
 * 还添加了包含错误的Throwable接口
 * <p>
 * Created by zyvis on 2017/5/28.
 */

public class SingleLog {
    /**
     * Level对象定义了Log最基础的等级
     * 是最基本的区分方法
     */
    protected Log.Level level;
    /**
     * 默认的Tag是指向当前的类和方法
     * 调用者可以自行创建Tag，在Logcat中进行区分
     */
    protected String tag;
    /**
     * Log的具体内容
     */
    protected String msg;
    /**
     * 当其为异常Log的时候，tr非空，
     * 可以调用{@link #hasThrowable()}进行查询
     * 调用{@link #getStackTraceString()}获取异常信息
     */
    protected Throwable tr;

    protected SingleLog(Log.Level level, String tag, String msg, Throwable tr) {
        this.level = level;
        this.tag = tag;
        this.msg = msg;
        this.tr = tr;
    }

    protected SingleLog(Log.Level level, String tag, String msg) {
        this.level = level;
        this.tag = tag;
        this.msg = msg;
        tr = null;
    }

    @Override
    public String toString() {
        return "[ " + level.toString() + " ] : { ( " + tag + " ) : " + msg + (tr == null ? "" : " [ trmsg: " + tr.getMessage() + " ]") + " } ";
    }

    /**
     * 判断这是否是一条异常日志
     *
     * @return 是否包含异常
     */
    public boolean hasThrowable() {
        Log.d("hasThrowable" + tr == null ? "false" : "true");
        return tr == null ? false : true;
    }

    /**
     * 此方法用来指定某一条携带Throwable接口的日志
     * 所包含的异常信息
     *
     * @return 异常信息的栈追踪信息
     */
    public StackTraceElement[] getStackTraceString() {
        if (tr == null) return null;
        return tr.getStackTrace();
    }
}
