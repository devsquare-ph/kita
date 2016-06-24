package ph.kita.devsquare.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;

/**
 * Created by jericcabana on 19/06/2016.
 */
public class InventoryItemFragment extends Fragment{

    private static final String TAG = InventoryItemFragment.class.getSimpleName();

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.tag)
    TextView tag;
    @BindView(R.id.stock)
    TextView stock;

    private OnFragmentInventoryItemListener onFragmentInventoryItemListener;

    public interface OnFragmentInventoryItemListener{
        public void onSaveItem(Item item);
    }

    public static Fragment newInstance(){
        return new InventoryItemFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
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

        return view;
    }

    @OnClick(R.id.save)
    public void save(){

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
        onFragmentInventoryItemListener.onSaveItem(new Item(0, name.getText().toString(), prc, tag.getText().toString(), stck, 0, "", new Date(System.currentTimeMillis())));
    }

}
