package library.utils.TimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by BABEL SI.
 */
public class TimeZoneDates {


    /**
     * Receives a date with peninsular time and returns a date to the time zone of mobile device.
     * @param date
     * @return
     */
    public static String convertToDeviceTimeZone(String date){

//      indicate the date format
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//      indicate the time zone of the date we receive
        simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("Europe/Madrid"));

        String mDate = date;
        Date newDate;

        try {

            newDate = simpleDateFormat.parse(mDate);

        } catch (ParseException e) {
            return "";
        }

//      get the ID of the device time zone
        String deviceTimeZoneID = TimeZone.getDefault().getID();
//      set the time zone of the device
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(deviceTimeZoneID));

        String newTimeZoneDate = simpleDateFormat.format(newDate);

        return newTimeZoneDate;
    }
}
