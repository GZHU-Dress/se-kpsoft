
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 此类用于指定把log打印到本地文件
 * Created by zyvis on 2017/7/17.
 */
public class PrintLogcat extends DefaultLogcat{
    /**
     * 此变量用于引用默认的log地址
     * 可以在添加PrintLogcat的时候使用
     */
    public final static File defaultFilepath = new File(".\\log");

    public boolean isPrintLog() {
        return printLog;
    }

    public void setPrintLog(boolean printLog) {
        this.printLog = printLog;
    }

    /**
     * 此开关用于指定是否讲Log打印到本地文件
     */
    protected boolean printLog;

    /**
     * 初始化函数，创建文件会一路把目录创建过去
     * @param path  指定输出的文件路径
     */
    public PrintLogcat(File path) {
        path.mkdirs();
        File file;
        try {
            file=new File(path+"\\Main_"+ String.valueOf(System.currentTimeMillis()).substring(2)+".log");
            file.createNewFile();
            outputStream=new FileOutputStream(file);
        } catch (IOException e) {
            Log.e("could not create the file output stream to the logfile",e);
        }

    }

    @Override
    public void print(SingleLog singleLog) {
        try {
            outputStream.write((singleLog.toString()+"\n").getBytes());
        } catch (IOException e) {
            Log.e("the log could not be printed to file",e);
        }
    }
}
