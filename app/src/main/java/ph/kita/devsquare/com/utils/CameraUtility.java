package ph.kita.devsquare.com.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jericcabana on 29/06/2016.
 */
public class CameraUtility {

    private String TAG = getClass().getSimpleName();


    private Context context;

    public CameraUtility(Context context) {
        this.context = context;
    }

    public static Camera getCameraInstance(){
     Camera camera = null;
        try {
            camera = Camera.open();
            camera.setDisplayOrientation(Constant.CAMERA_ORIENTATION);
            //Camera Settings
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
            Camera.Size size = sizes.get(0);
            parameters.setPreviewSize(size.width, size.height);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            camera.setParameters(parameters);

        }catch (Exception e){
            Log.d("camera", "Exception " + e.getMessage());
        }

        return camera;
    }

    /*Check if the device has a camera*/
    public boolean checkCameraAvailability() {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }


}
