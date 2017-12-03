import log.Core;
import log.InstallException;
import log.Log;
import timer.TimerTag;

public class testMain {
    public static void main(String[] args) throws InstallException {
        Core.install();
        L
        for (int i=0;i<10;i++){
            Log.d(TimerTag.START,true);
            Log.d(100);
            new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.run();
            Log.d(TimerTag.END,true);
        }
    }
}
