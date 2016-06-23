package ph.kita.devsquare.com.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by abnonymous on 6/23/16.
 */
public class Utility {

    public static void hideSoftKey(Activity context, View view){

        if(view == null && context == null)
            return;

        //hide softkey
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
