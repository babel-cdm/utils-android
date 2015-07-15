package library.utils.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ExecShell {
    private static final String TAG_CLASS = ExecShell.class.getSimpleName();

    public static enum SHELL_CMD {
        check_su_binary(new String[] { "/system/xbin/which", "su" });

        String[] command;

        SHELL_CMD(String[] command) {
            this.command = command;
        }
    }

    @SuppressWarnings("unused")
    public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {

        String line = null;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess = null;

        try {
            localProcess = Runtime.getRuntime().exec(shellCmd.command);
        } catch (Exception e) {
            return null;
        }

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                localProcess.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(
                localProcess.getInputStream()));

        try {
            while ((line = in.readLine()) != null) {
                fullResponse.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fullResponse;
    }
}
