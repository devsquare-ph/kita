package ph.kita.devsquare.com.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import ph.kita.devsquare.com.kita.R;

/**
 * Created by abnonymous on 6/23/16.
 */
public class Utility {

    private static final String TAG = Utility.class.getSimpleName();

    public static void hideSoftKey(Activity context, View view){

        if(view == null && context == null)
            return;

        //hide softkey
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setImage(String name, ImageView iv) throws FileNotFoundException {

        try {

            Log.d(TAG, "absPath: " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/kita/" + name);
            File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/kita/" + name);
            Log.d(TAG, "isExisted: " + f.exists());
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            iv.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            throw new FileNotFoundException();
        }

    }

    public static void setImage(String name, ImageView iv, Context context){

        //image internal storage path
        File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/kita/" + name);
        Log.d(TAG, "path: " + f.getAbsolutePath());
        Picasso.with(context)
                .load(f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(iv);

    }

    public static <T> void setTopItem(List<T> t, int position){
        t.add(0, t.remove(position));
    }

}
