package library.utils.security.encryptation;

public class EncryptationManager {

    private static Params mParams;

    public static final class Params {
        String AESKey;

        public String getAESKey() {
            return AESKey;
        }

        public Params setAESKey(String AESKey) {
            this.AESKey = AESKey;
            return this;
        }
    }

    public static Params config() {
        mParams = new Params();
        return mParams;
    }

    public static Encrypt getEncrypt() {

        EncryptationComponent component = DaggerEncryptationComponent.builder()
                .encryptationModule(new EncryptationModule()).build();

        Encrypt encrypt = component.provideEncrypt();
        if (mParams != null) {
            encrypt.setAESKey(mParams.getAESKey());
        }
        return encrypt;
    }
}
