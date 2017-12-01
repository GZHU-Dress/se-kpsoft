package log;

import java.io.File;

/**
 * 此类用于在最开始的时候完成所有部件的初始化。
 * Created by zyvis on 2017/11/30.
 */
public class Core {
    /**
     * 该类已初始化的标志
     * 静态对象，只有一个
     */
    private static boolean installed=false;

    public static boolean isInstalled() {
        return installed;
    }

    /**
     * Core包含了一个defaultLogcat对系统输出做最基础的输出
     * 默认指向是System.out
     * 若将该defaultLogcat从Loglist中移去，仍可以在此找到
     * 使用Log的add方法重新添加
     */
    private static DefaultLogcat defaultLogcat= new DefaultLogcat();

    /**
     * 如果你从Log的表中把这个对象删除，可以在再次添加
     * 如果想要关闭输出，通常使用{@link Logcat#logNone()}
     *
     * @return 返回defaultLogcat
     */
    public static DefaultLogcat getDefaultLogcat() {
        return defaultLogcat;
    }

    /**
     * 私有构造函数 禁止创建和反射
     */
    private Core() {
    }

    /**
     * 执行此方法进行初始化
     */
    public static synchronized void install() throws InstallException {
        if (installed)throw new InstallException("log.Core had been installed");
        Log.addLogcat(defaultLogcat);
        Log.d("default logcat has been installed");



        //IOrouter =new IORouter(defaultLogcat.outputStream);

        installed = true;
        Log.d("log.Core has been installed");
    }
    public static void setPrintLog(File filepath)throws InstallException {
        if(!installed)throw new InstallException("Set PrintLog failed, log.Core had not been installed");
        Log.addLogcat(new PrintLogcat(filepath));
        Log.d("print logcat has been installed");
    }
}
