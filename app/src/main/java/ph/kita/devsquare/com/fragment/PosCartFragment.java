package ph.kita.devsquare.com.fragment;

import android.app.Activity;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;
import ph.kita.devsquare.com.utils.Utility;

/**
 * Created by jericcabana on 19/06/2016.
 */
public class PosCartFragment extends Fragment{

    private static final String TAG = PosCartFragment.class.getSimpleName();

//    @BindView(R.id.tag)
//    TextView tag;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.stock)
    TextView stock;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.qtNwt)
    EditText qtNwt;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.increment)
    Button increment;
    @BindView(R.id.decrement)
    Button decrement;
    @BindView(R.id.addCart)
    Button addCart;
//    @BindView(R.id.checkOut)
//    Button checkOut;

    static final String ID = "ID";
    static final String NAME = "NAME";
    static final String PRICE = "PRICE";
    static final String IMGURL = "IMGURL";
    static final String QTNWT = "QTNWT";
    static final String ITEM_TAG = "TAG";
    static final String STOCK = "STOCK";

    private Item item;
    private OnFragmentPOSCartListener onFragmentPOSCartListener;
    //remove checkout feature
//    private PosFragment.OnFragmentPOSListener onFragmentPOSListener;

    public interface OnFragmentPOSCartListener{
        public void onAddToCart(Item item);
    }

    public static Fragment newInstance(int id, String name, float price, String imgUrl, float qtNwt, String tag, float stock) throws IOException{
        Log.d(TAG, "name: " + name);
        Log.d(TAG, "tag: " + tag);
        if(name == null && tag == null)
            throw new IOException("Input cannot be null");

        PosCartFragment posCart = new PosCartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        bundle.putString(NAME, name);
        bundle.putFloat(PRICE, price);
        bundle.putString(IMGURL, imgUrl);
        bundle.putFloat(QTNWT, qtNwt);
        bundle.putString(ITEM_TAG, tag);
        posCart.setArguments(bundle);
        bundle.putFloat(STOCK, stock);
        return posCart;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
        if(context instanceof OnFragmentPOSCartListener){
            Log.d(TAG,"onFragmentPOSCartListener");
            onFragmentPOSCartListener = (OnFragmentPOSCartListener) context;
        }

        //remove checkout
//        if(context instanceof  Activity && context instanceof PosFragment.OnFragmentPOSListener){
//            Log.d(TAG,"OnFragmentPOSListener");
//            onFragmentPOSListener = (PosFragment.OnFragmentPOSListener) context;
//        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pos_cart, container, false);

        ButterKnife.bind(this, view);

        if(item == null)
        item = new Item(getArguments().getInt(ID), getArguments().getString(NAME),getArguments().getFloat(PRICE),getArguments().getString(ITEM_TAG), getArguments().getFloat(STOCK), getArguments().getFloat(QTNWT), getArguments().getString(IMGURL),null);

//        tag.setText(item.getTag());
        name.setText(item.getName());
        price.setText("" + item.getPrice());
        qtNwt.setText("" + item.getQualitytNWeight());
        stock.setText("" + item.getStock());
        Utility.setImage(item.getImageURL(), img, getActivity());

        return view;
    }

    @OnClick(R.id.increment)
    public void increment(Button button) {
        try {

            if (this.item.getQualitytNWeight() == this.item.getStock()) {
                Toast.makeText(getActivity(), "Maximum Stocks is " + item.getStock(), Toast.LENGTH_SHORT).show();
                return;
            }

            //increment
            this.qtNwt.setText("" + this.item.incQualitytNWeight());
        } catch (NumberFormatException e) {
            this.qtNwt.setText("0");
        }

    }

    @OnClick(R.id.decrement)
    public void decrement() {
        try{
            //decrement
            if(item.getQualitytNWeight() > 1)
            this.qtNwt.setText("" + this.item.decQualitytNWeight());
        }catch (NumberFormatException e){
            this.qtNwt.setText("0");
        }
    }

    @OnClick(R.id.addCart)
    public void addCart() {
//        Toast.makeText(getActivity(),"addCart",Toast.LENGTH_SHORT).show();

        float price = Float.valueOf(this.price.getText().toString());
        float qnttyNwght = Float.valueOf(this.qtNwt.getText().toString());

        if(price > 0 && qnttyNwght > 0) {
            this.item.setPrice(Float.valueOf(this.price.getText().toString()));
            this.onFragmentPOSCartListener.onAddToCart(item);
        }else{
            Toast.makeText(getActivity(), "Price and Quantity cannot be empty.", Toast.LENGTH_SHORT).show();
        }
    }

    //remove checkout
//    @OnClick(R.id.checkOut)
//    public void checkOut() {
//        this.item.setPrice(Float.valueOf(this.price.getText().toString()));
//
//        float price = Float.valueOf(this.price.getText().toString());
//        float qnttyNwght = Float.valueOf(this.qtNwt.getText().toString());
//
//        if(price > 0 && qnttyNwght > 0) {
//            Toast.makeText(getActivity(), "check out", Toast.LENGTH_SHORT).show();
//            getActivity().getSupportFragmentManager().popBackStack();
//            onFragmentPOSListener.onCheckOut(new ArrayList<Item>(Arrays.asList(item)));
//        }else{
//            Toast.makeText(getActivity(), "Price and Quantity cannot be empty.", Toast.LENGTH_SHORT).show();
//        }
//
//    }

}
