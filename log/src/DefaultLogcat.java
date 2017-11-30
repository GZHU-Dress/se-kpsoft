import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyvis on 2017/5/28.
 */
public class DefaultLogcat extends Logcat {



    /**
     * 用于每一个Log进行临时信息存储，可以通过子类print覆写进行重引导
     * 在此作为一个筛选的记录仪，默认使用ArrayList
     * 静态存在，即Logcat类子对象共享一个LogList
     */
    private static List<SingleLog> logList;

    /**
     * 类对于Loglist进行封装，所有子类共享一个
     * 取出的对象不能修改
     * @return logList
     */
    public List<SingleLog> getLogList() {
        return logList;
    }
    /**
     * levelFilter中指定不打印、或tagFilter指定不打印
     * 则直接返回
     * 否则直接调用print函数，推到输出流
     *
     * @param singleLog
     */
    @Override
    public void filter(SingleLog singleLog) {
        collect(singleLog);
        if ((singleLog.level.level
                <levelFilter.level)
                || (tagFilter.containsKey(singleLog.tag)
                && !tagFilter.get(singleLog.tag)))
            return;
        print(singleLog);
    }

    /**
     * 可以重新定义输出流，会先调用父类函数完成初始化
     * 后完成对DefaultLog的LogList的初始化
     *
     * @param outputStream
     */
    public DefaultLogcat(OutputStream outputStream) {
        super(outputStream);
        logList = new ArrayList<>();
    }

    /**
     * 根据父类的指引，在{@link #filter(SingleLog)}开始先调用
     * 将log推入LogList
     * 通过list收集Log
     *
     * @param singleLog 一个日志
     */
    @Override
    public void collect(SingleLog singleLog) {
        logList.add(singleLog);
    }

    /**
     * 向指定的输出流输出Log内容
     *
     * @param singleLog 一个日志
     */
    @Override
    public void print(SingleLog singleLog) {
        ((PrintStream) outputStream).println(singleLog.toString());
    }

    /**
     * 默认配置系统的输出流
     */
    public DefaultLogcat() {
        this(System.out);
    }

    /**
     * 每次调用该方法会从头到尾遍历一次整个logList
     * 查找所有带有抛出异常的logList
     * 此方法请勿循环调用
     *
     * @return 返回所有带有抛出异常的logList迭代器
     */
    @Override
    public Iterator<SingleLog> getAllThrowable() {
        LinkedList<SingleLog> tmp = new LinkedList<>();
        int size = logList.size();
        for (int i = 0; i < size; i++) {
            if (logList.get(i).hasThrowable())
                tmp.add(logList.get(i));
        }
        Log.d("getAllThrowable, " + tmp.toArray().length);
        return tmp.iterator();
    }
}
