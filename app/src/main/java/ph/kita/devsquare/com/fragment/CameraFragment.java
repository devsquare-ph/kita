package ph.kita.devsquare.com.fragment;

import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.utils.CameraPreview;
import ph.kita.devsquare.com.utils.CameraUtility;

/**
 * Created by jericcabana on 01/07/2016.
 */
public class CameraFragment extends Fragment {

    private String TAG = getClass().getSimpleName();

    private CameraUtility cameraUtility;
    private CameraPreview cameraPreview;
    private Camera camera;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        ButterKnife.bind(this, view);

        cameraUtility = new CameraUtility(getActivity());
        // Create an instance of Camera
        camera = cameraUtility.getCameraInstance();
        Log.d(TAG, "CAMERA " + camera);
        // Create our Preview view and set it as the content of our activity.
        cameraPreview = new CameraPreview(getActivity(), camera);
        FrameLayout preview = (FrameLayout) view.findViewById(R.id.cameraPreview);
        preview.addView(cameraPreview);

        return view;
    }

    @OnClick(R.id.btnCapture)
    public void captureImage(){
        Log.d(TAG, "CAPTURE IMAGE");
        camera.takePicture(shutterCallback, null, pictureCallback);
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File imageFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            if (imageFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(imageFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }

        }
    };

    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            audioManager.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
        }
    };

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.getExternalStorageState()), "Kita");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //Release the camera if the app doesn't use it
        camera.release();
    }

}
