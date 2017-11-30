/**
 * Created by zyvis on 2017/11/30.
 */
public class Main {
    public static void main(String[] args) throws InstallException {
        Core.install();
        Core.setPrintLog(PrintLogcat.defaultFilepath);
        Core.getDefaultLogcat().setLevelFilter(Log.Level.Warn);
        Log.d("fgahsuifahsiufo");
    }
}
