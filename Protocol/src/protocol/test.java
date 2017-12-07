package protocol;

import log.Core;
import log.InstallException;
import log.Log;

/**
 * 该类为测试类，交付给测试员、模块负责人调用
 * Created by visn on 17-12-7.
 */
public class test {
    public static void main(String[] args) throws InstallException, EncoderException {

        Core.install();
        Core.getDefaultLogcat().setLevelFilter(Log.Level.Info);

        KPprotocolEncoder kPprotocolEncoder=new KPprotocolEncoder();

        Instruct[] tester = new Instruct[]{
                new Instruct("ACT", "BLT", new String[]{"ison=isOn()", "hello"}, new String[]{"data<p></p>;'jigoapsm,.;" +
                        "cx`.'lags;'mnbv gasd.1", "data2"})
        };

        String[] testString = new String[tester.length];


        for (int i = 0; i < tester.length; i++) {
            Log.i(tester[i].toString());
            testString[i] = kPprotocolEncoder.encode(tester[i]);
            Log.i(testString[i]);
            Log.i(kPprotocolEncoder.decode(testString[i]).toString());

        }
    }
}
