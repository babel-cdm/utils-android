package library.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Others {

    public static String generateUUID() {
        return java.util.UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

    /**
     * Is sim card ready.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isSimCardReady(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY);
    }

    /**
     * Gets devices id.
     *
     * @param context the context
     * @return the devices id
     */
    public static String getDevicesId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * Is nif nie.
     *
     * @param nif the nif
     * @return the boolean
     */
    public static boolean isNifNie(String nif) {

        //si es NIE, eliminar la x,y,z inicial para tratarlo como nif
        if (nif.toUpperCase().startsWith("X")) {
            nif = "0" + nif.substring(1);
        } else if (nif.toUpperCase().startsWith("Y")) {
            nif = "1" + nif.substring(1);
        } else if (nif.toUpperCase().startsWith("Z")) {
            nif = "2" + nif.substring(1);
        }

        Pattern nifPattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher m = nifPattern.matcher(nif);
        if (m.matches()) {
            String letra = m.group(2);
            //Extraer letra del NIF
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int dni = Integer.parseInt(m.group(1));
            dni = dni % 23;
            String reference = letras.substring(dni, dni + 1);

            if (reference.equalsIgnoreCase(letra)) {
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }

    /**
     * Check password (UpperCase, LowerCase, Numbers, and size 8-40).
     *
     * @param password the password
     * @return the boolean
     */
    public static boolean checkPassword(String password) {
        String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,40})";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches() && password.matches("[a-zA-Z0-9.? ]*");
    }

    /**
     * Check email.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean checkEmail(String email) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static double randDouble(double min, double max) {

        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();

        return randomValue;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static int enterPart(double value) {
        return (int) value;
    }
}
