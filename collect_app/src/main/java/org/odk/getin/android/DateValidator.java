package org.odk.getin.android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateValidator {
    public boolean isThisDateWithinRange(String dateToValidate, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateToValidate);

            Calendar currentDateAfterTime = Calendar.getInstance();
            currentDateAfterTime.set(Calendar.HOUR_OF_DAY, 10);

            Calendar currentDateBeforeTime = Calendar.getInstance();
            currentDateBeforeTime.set(Calendar.HOUR_OF_DAY, 11);

            return date.before(currentDateBeforeTime.getTime())
                    && date.after(currentDateAfterTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
