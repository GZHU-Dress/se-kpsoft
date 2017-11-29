package encryption;

import protocol.EncoderException;

/**
 * Created by zyvis on 2017/11/29.
 */
public abstract class AbstractEncryptionEncoder<T> implements EncryptionEncoder<T> {
    private T key=null;
    private boolean encrypt=false;

    @Override
    public boolean setKey(T key) {
        if (key != null) {
            this.key = key;
            encrypt=true;
            return true;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean isEncrypt() {
        return encrypt;
    }
}
