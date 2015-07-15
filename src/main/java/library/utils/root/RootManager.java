package library.utils.root;

import java.io.File;

@SuppressWarnings("unused")
public class RootManager {
    private static RootStatus mRootStatus = RootStatus.UNKNOWN;
    private static RootManager  mInstance;

    private boolean isRoot = false;

    private enum RootStatus {

        UNKNOWN("-1"),
        NO("0"),
        YES("1");

        private final String label;

        RootStatus(String label) {
            this.label = label;
        }

        public String getLabel () {
            return this.label;
        }
    }

    private RootManager() {
        isRoot = isDeviceRooted();
    }

    public static RootManager getInstance() {

        if (mInstance == null) {
            mInstance = new RootManager();
        }
        return mInstance;
    }

    public String getRootStatus () {
        if (isDeviceRooted()) {
            mRootStatus = RootStatus.YES;
        } else {
            mRootStatus = RootStatus.NO;
        }

        return mRootStatus.getLabel();
    }

    public boolean isRooted() {
        return isRoot;
    }

    private static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        try {
            File file = new File("/system/app/Superuser.apk");
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean checkRootMethod3() {
        return false;
        //return new ABExecShell().executeCommand(SHELL_CMD.check_su_binary)!=null;
    }
}
