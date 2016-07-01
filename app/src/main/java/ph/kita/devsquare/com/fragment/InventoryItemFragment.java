package ph.kita.devsquare.com.fragment;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;
import ph.kita.devsquare.com.utils.CameraPreview;
import ph.kita.devsquare.com.utils.CameraUtility;
import ph.kita.devsquare.com.utils.Constant;

/**
 * Created by jericcabana on 19/06/2016.
 */
public class InventoryItemFragment extends Fragment{

    private static final String TAG = InventoryItemFragment.class.getSimpleName();
    public final static int ADD = 0;
    public final static int UPDATE = 1;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.tag)
    TextView tag;
    @BindView(R.id.stock)
    TextView stock;
    @BindView(R.id.lnrCamera)
    LinearLayout lnrCamera;

    private OnFragmentInventoryItemListener onFragmentInventoryItemListener;
    private Item item;

    public interface OnFragmentInventoryItemListener{
        public void onSaveItem(Item item, int state);
    }

    public static Fragment newInstance(Item items){
        InventoryItemFragment inventoryItemFragment = new InventoryItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.INVENTORY_ITEM, items);
        inventoryItemFragment.setArguments(bundle);
        return inventoryItemFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        if(context instanceof OnFragmentInventoryItemListener){
            Log.d(TAG,"onFragmentPOSCartListener");
            onFragmentInventoryItemListener = (OnFragmentInventoryItemListener) context;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_item, container, false);

        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        item = bundle.getParcelable(Constant.INVENTORY_ITEM);

        if (item != null){
            Log.d(TAG, "UPDATE");
            name.setText(item.getName());
            price.setText(String.valueOf(item.getPrice()));
            tag.setText(String.valueOf(item.getTag()));
            stock.setText(String.valueOf(item.getStock()));
        }else {
            Log.d(TAG, "ADD");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();



    }

    @OnClick(R.id.save)
    public void save(){

       int state;
        float prc;
        float stck;
        try{
            prc  = Float.parseFloat(price.getText().toString());
        }
        catch(NumberFormatException e){
            prc = 0;
        }

        try{
            stck  = Float.parseFloat(price.getText().toString());
        }
        catch(NumberFormatException e){
            stck = 0;
        }

        if (item != null)
            state = UPDATE;
        else
            state = ADD;

        onFragmentInventoryItemListener.onSaveItem(new Item(0, name.getText().toString(), prc, tag.getText().toString(), stck, 0, "", new Date(System.currentTimeMillis())), state);
    }

    @OnClick(R.id.lnrCamera)
    public void lnrCamera(){

        Log.d(TAG, "CAMERA");

        CameraUtility cameraUtility = new CameraUtility(getActivity());

        if (cameraUtility.checkCameraAvailability()){


            Fragment fragment = new CameraFragment();
            if (fragment != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main, fragment, CameraFragment.class.getSimpleName());
                fragmentTransaction.addToBackStack(InventoryItemFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }

        }else {
            Toast.makeText(getActivity(), "Your device is not supported with a camera device", Toast.LENGTH_LONG).show();
        }


    }

}
