import log.Core;
import log.InstallException;
import log.Log;
import log.PrintLogcat;
import protocol.*;
import timer.TimerTag;

/**
 * Created by zyvis on 2017/11/30.
 */
public class Main {
    public static void main(String[] args) throws EncoderException, InstallException, InterruptedException {
        Core.install();
            Log.d(TimerTag.START,false);
            Thread.sleep(2000);
            Log.d("helloworld");
            Log.d(TimerTag.END,false);


//
//        Core.setPrintLog(PrintLogcat.defaultFilepath);
//        Core.getDefaultLogcat().setLevelFilter(Log.Level.Info);
//
//        KPprotocolEncoder kPprotocolEncoder=new KPprotocolEncoder();

//        ProtocolStruct[] tester=new ProtocolStruct[]{
//                new ProtocolStruct("0.1 ACK","BLT","",new String[]{"ison=\"true\"","canconnect=\"\""}),
//                new ProtocolStruct("0.1 ACK","BLT","ison=isOn",new String[]{}),
//                new ProtocolStruct("0.1 ACK<h></h>","test","fahfuias<p></p>",new String[]{}),
//                new ProtocolStruct("0.1 ACK<p>","test","fahfuias<kp></kp>",new String[]{}),
//                new ProtocolStruct("0.1 ACK","test","fahfuias",new String[]{"hellop",null}),
//                new ProtocolStruct("0.1 ACK","BLT","ison=isOn",null),
//        };

//        Instruct[] tester=new Instruct[]{
//            new Instruct("ACT","BLT",new String[]{"ison=isOn()","hello"},new String[]{"data<p></p>;'jigoapsm,.;" +
//                    "cx`.'lags;'mnbv gasd.1","data2"})
//        };
//        String[] testString =new String[tester.length];
//
//
//        for(int i = 0; i < tester.length; i++) {
//            Log.i(tester[i].toString());
//            testString[i]= kPprotocolEncoder.encode(tester[i]);
//            Log.i(kPprotocolEncoder.decode(testString[i]).toString());
//
//        }

    }
}
