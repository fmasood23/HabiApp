package edu.sjsu.android.habiteamproject;

import android.app.Activity;
import android.database.Cursor;

public class UserManager {

    public String getUsername(Activity activity) {
        try (Cursor c = activity.getContentResolver().
                query(HabiProvider.CONTENT_URI_CURRENT, null, null, null, null)) {
            if (c.moveToFirst()) {
                String result = "";
                do {
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        result = result.concat
                                (c.getString(i));
                    }
                } while (c.moveToNext());
                return result;
            } else {
                return null;
            }
        }
    }
}
