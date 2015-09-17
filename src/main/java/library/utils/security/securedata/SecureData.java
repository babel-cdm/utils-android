package library.utils.security.securedata;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SecureData {

    /**
     * never ever change this value since it will break backward compatibility in terms of keeping previous data
     */
    private static final String TAG = "SECUREDATA";

    /**
     * never ever change this value since it will break backward compatibility in terms of keeping previous data
     */
    private static final String TAG_CRYPTO = "324909sdfsd98098";

    /**
     * Key to store if the device does not support crypto
     */
    private static final String KEY_NO_CRYPTO = "dfsklj2342nasdfoasdfcrpknasdf";

    private static Encoder encoder;
    private static Storage storage;
    private static Encryption encryption;
    private static LogLevel logLevel;
    private static ExecutorService executorService;

    private static boolean noEncryption;

    private SecureData() {
        // no instance
    }

    /**
     * This will init the secure without password protection.
     *
     * @param context is used to instantiate context based objects. ApplicationContext will be used
     */
    public static void init(Context context) {
        init(context, null, LogLevel.NONE);
    }

    /**
     * This method must be called in order to initiate the secure, all put and get methods should be called after
     * callback methods executed
     *
     * @param context  is used to instantiate context based objects. ApplicationContext will be used
     * @param password is used for key generation
     * @param callback is used for executing the function in another thread and execute either onSuccess or onFail
     *                 methods
     */
    public static void init(Context context, String password, Callback callback) {
        init(context, password, LogLevel.NONE, callback);
    }

    /**
     * This method must be called in order to initiate the secure
     *
     * @param context  is used to instantiate context based objects. ApplicationContext will be used
     * @param password is used for key generation
     */
    public static void init(Context context, String password) {
        init(context, password, LogLevel.NONE);
    }

    /**
     * This method must be called in order to initiate the secure
     * <p/>
     * Some devices don't support the basic crypto, In that case encryption won't be enabled.
     *
     * @param context  is used to instantiate context based objects. ApplicationContext will be used
     * @param password is used for key generation
     * @param logLevel is used for logging
     */
    public static void init(Context context, String password, LogLevel logLevel) {
        Context appContext = context.getApplicationContext();
        SecureData.logLevel = logLevel;
        SecureData.storage = new SharedPreferencesStorage(appContext, TAG);
        SecureData.encoder = new SecureDataEncoder(new GsonParser(new Gson()));

        if (storage.contains(KEY_NO_CRYPTO)) {
            noEncryption = true;
            return;
        }

        Storage cryptoStorage = new SharedPreferencesStorage(appContext, TAG_CRYPTO);
        SecureData.encryption = new AesEncryption(cryptoStorage, password);
        boolean result = SecureData.encryption.init();
        setEncryptionMode(result);
    }

    /**
     * This will allow SecureData to store everything plaintext.
     *
     * @param context  is used to initiate context
     * @param logLevel is used for logging
     */
    public static void initWithoutEncryption(Context context, LogLevel logLevel) {
        Context appContext = context.getApplicationContext();
        SecureData.logLevel = logLevel;
        SecureData.storage = new SharedPreferencesStorage(appContext, TAG);
        SecureData.encoder = new SecureDataEncoder(new GsonParser(new Gson()));
        noEncryption = true;
    }

    private static void setEncryptionMode(boolean isCryptoSupported) {
        if (isCryptoSupported) {
            noEncryption = false;
            return;
        }
        storage.put(KEY_NO_CRYPTO, true);
        noEncryption = true;
    }

    /**
     * This method must be called in order to initiate the secure, all put and get methods should be called after
     * callback methods executed
     *
     * @param context  is used to instantiate context based objects. ApplicationContext will be used
     * @param password is used for key generation
     * @param logLevel is used for logging
     * @param callback is used for executing the function in another thread and execute either onSuccess or onFail
     *                 methods
     */
    public static void init(final Context context, final String password, final LogLevel logLevel,
                            final Callback callback) {
        SecureData.executorService = Executors.newSingleThreadExecutor();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    init(context, password, logLevel);
                    callback.onSuccess();
                } catch (Exception e) {
                    Logger.e("Exception occurred while initialization : ", e);
                    callback.onFail(e);
                }

            }
        };
        executorService.execute(runnable);
        executorService.shutdown();
    }

    /**
     * Saves every type of Objects. List, List<T>, primitives
     *
     * @param key   is used to save the data
     * @param value is the data that is gonna be saved. Value can be object, list type, primitives
     * @return true if put is successful
     */
    public static <T> boolean put(String key, T value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        //if the value is null, simply remove it
        if (value == null) {
            return remove(key);
        }

        String encodedText = encode(value);
        //if any exception occurs during encoding, encodedText will be null and thus operation is unsuccessful
        return encodedText != null && storage.put(key, encodedText);
    }

    /**
     * Saves the list of objects to the storage
     *
     * @param key   is used to save the data
     * @param value is the data that will be saved
     * @return true if put is successful
     */
    public static <T> boolean put(String key, List<T> value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        //if the value is null, simply remove it
        if (value == null) {
            return remove(key);
        }

        String encodedText = encode(value);
        //if any exception occurs during encoding, encodedText will be null and thus operation is unsuccessful
        return encodedText != null && storage.put(key, encodedText);
    }

    /**
     * Encodes the given value as full text (cipher + data info)
     *
     * @param value is the given value to encode
     * @return full text as string
     */
    private static <T> String encode(T value) {
        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        }
        byte[] encodedValue = encoder.encode(value);

        String cipherText;

        if (noEncryption) {
            cipherText = Base64.encodeToString(encodedValue, Base64.DEFAULT);
        } else {
            cipherText = encryption.encrypt(encodedValue);
        }

        if (cipherText == null) {
            return null;
        }
        return DataUtil.addTypeAsObject(cipherText, value.getClass());
    }

    /**
     * Encodes the given list as full text (cipher + data info)
     *
     * @param list is the given list to encode
     * @return full text as string
     */
    private static <T> String encode(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List<T> cannot be null");
        }
        if (list.size() == 0) {
            throw new IllegalStateException("List<T> cannot be empty");
        }
        byte[] encodedValue = encoder.encode(list);

        String cipherText;

        if (noEncryption) {
            cipherText = DataUtil.encodeBase64(encodedValue);
        } else {
            cipherText = encryption.encrypt(encodedValue);
        }

        if (cipherText == null) {
            return null;
        }
        Class clazz = list.get(0).getClass();
        return DataUtil.addTypeAsList(cipherText, clazz);
    }

    /**
     * @param key is used to get the saved data
     * @return the saved object
     */
    public static <T> T get(String key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        String fullText = storage.get(key);
        if (fullText == null) {
            return null;
        }
        DataInfo dataInfo = DataUtil.getDataInfo(fullText);
        byte[] bytes;

        if (noEncryption) {
            bytes = DataUtil.decodeBase64(dataInfo.getCipherText());
        } else {
            bytes = encryption.decrypt(dataInfo.getCipherText());
        }

        if (bytes == null) {
            return null;
        }

        try {
            return encoder.decode(bytes, dataInfo);
        } catch (Exception e) {
            Logger.d(e.getMessage());
        }
        return null;
    }

    /**
     * Gets the saved data, if it is null, default value will be returned
     *
     * @param key          is used to get the saved data
     * @param defaultValue will be return if the response is null
     * @return the saved object
     */
    public static <T> T get(String key, T defaultValue) {
        T t = get(key);
        if (t == null) {
            return defaultValue;
        }
        return t;
    }

    /**
     * Enables chaining of multiple put invocations.
     *
     * @return a simple chaining object
     */
    public static Chain chain() {
        return new Chain();
    }

    /**
     * Enables chaining of multiple put invocations.
     *
     * @param capacity the amount of put invocations you're about to do
     * @return a simple chaining object
     */
    public static Chain chain(int capacity) {
        return new Chain(capacity);
    }

    /**
     * Size of the saved data. Each key will be counted as 1
     *
     * @return the size
     */
    public static int count() {
        return storage.count();
    }

    /**
     * Clears the storage, note that crypto data won't be deleted such as salt key etc.
     * Use resetCrypto in order to clear crypto information
     *
     * @return true if clear is successful
     */
    public static boolean clear() {
        return storage.clear();
    }

    /**
     * Removes the given key/value from the storage
     *
     * @param key is used for removing related data from storage
     * @return true if remove is successful
     */
    public static boolean remove(String key) {
        return storage.remove(key);
    }

    /**
     * Removes values associated with the given keys from the storage
     *
     * @param keys are used for removing related data from storage
     * @return true if all removals are successful
     */
    public static boolean remove(String... keys) {
        return storage.remove(keys);
    }

    /**
     * Checks the given key whether it exists or not
     *
     * @param key is the key to check
     * @return true if it exists in the storage
     */
    public static boolean contains(String key) {
        return storage.contains(key);
    }

    /**
     * Clears all saved data that is used for the crypto
     *
     * @return true if reset is successful
     */
    public static boolean resetCrypto() {
        return encryption == null || encryption.reset();
    }

    public static LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * Provides the ability to chain put invocations:
     * <code>SecureData.chain().put("foo", 0).put("bar", false).commit()</code>
     * <p/>
     * <code>commit()</code> writes the chain values to persistent storage. Omitting it will
     * result in all chained data being lost.
     */
    public static final class Chain {

        private final List<Pair<String, ?>> items;

        public Chain() {
            this(10);
        }

        public Chain(int capacity) {
            items = new ArrayList<>(capacity);
        }

        /**
         * Saves every type of Objects. List, List<T>, primitives
         *
         * @param key   is used to save the data
         * @param value is the data that is gonna be saved. Value can be object, list type, primitives
         */
        public <T> Chain put(String key, T value) {
            if (key == null) {
                throw new NullPointerException("Key cannot be null");
            }
            String encodedText = encode(value);
            if (encodedText == null) {
                Log.d(TAG, "Key : " + key + " is not added, encryption failed");
                return this;
            }
            items.add(new Pair<>(key, encodedText));
            return this;
        }

        /**
         * Saves the list of objects to the storage
         *
         * @param key  is used to save the data
         * @param list is the data that will be saved
         */
        public <T> Chain put(String key, List<T> list) {
            if (key == null) {
                throw new NullPointerException("Key cannot be null");
            }
            String encodedText = encode(list);
            if (encodedText == null) {
                Log.d(TAG, "Key : " + key + " is not added, encryption failed");
                return this;
            }
            items.add(new Pair<>(key, encodedText));
            return this;
        }

        /**
         * Commits the chained values to storage.
         *
         * @return true if successfully saved, false otherwise.
         */
        public boolean commit() {
            return storage.put(items);
        }

    }

    /**
     * Callback interface to make actions on another place and execute code
     * based on a result of action
     * onSuccess function will be called when action is successful
     * onFail function will be called when action fails due to a reason
     */
    public interface Callback {
        void onSuccess();
        void onFail(Exception e);
    }

}
