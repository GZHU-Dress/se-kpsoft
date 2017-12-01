import protocol.EncoderException;
import protocol.AbstractEncoder;
import protocol.KPProtocolStruct;

/**
 * Created by zyvis on 2017/11/30.
 */
public class Main {
    public static void main(String[] args) throws EncoderException {
        AbstractEncoder<KPProtocolStruct> abstractEncoder =new AbstractEncoder<>();
        abstractEncoder.encode("<kp><h>0.1 ACT</h><v>BLT</v><i>ison=isOn();canconnect=canConnect();</i><d></d></kp>");
    }
}
