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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ph.kita.devsquare.com.adapters.PosAdapter;
import ph.kita.devsquare.com.adapters.PosItemAutoCompleteAdapter;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;
import ph.kita.devsquare.com.utils.Utility;

/**
 * Created by jericcabana on 19/06/2016.
 */
public class PosFragment extends Fragment implements AdapterView.OnItemClickListener{

    private static final String TAG = PosFragment.class.getSimpleName();
    public static List<Item> dumyPOSItems = new ArrayList<Item>(Arrays.asList(new Item(0,"vaseline", 1, "shampoo", 2, 1, null,null),
            new Item(0,"safeguard", 1, "soap", 2, 1, null,null),
            new Item(0,"silka", 1, "soap", 2, 1, null,null)));

    @BindView(R.id.itemAutoComplete)
    AutoCompleteTextView itemAutoComplete;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.totalPrice)
    TextView totalPrice;

    private List<Item> itemCarts = new ArrayList<>();

    public void addPosCart(Item item){

        PosAdapter posAdapter = (PosAdapter) list.getAdapter();

        //check duplicate data in itemCarts
        int i;
        for(i = 0; i < itemCarts.size(); i++){
            //check duplicate item
            if(itemCarts.get(i).getName().equalsIgnoreCase(item.getName())){
                itemCarts.set(i, item);
                break;
            }
        }

        Log.d(TAG, "index: " + i);
        Log.d(TAG, "items Size: " + itemCarts.size());
        //if index equal to itemcarts size the data has no duplicate
        if(itemCarts.size() == i) {
            itemCarts.add(item);
        }

        //set totalprice
        float totalPrices = 0.0f;

        for(Item itm : itemCarts){
            //increment total price
            totalPrices += (itm.getPrice() * itm.getQualitytNWeight());
        }

        posAdapter.addItems(itemCarts);

        final String sTotalPrice = Float.toString(totalPrices);
        totalPrice.post(new Runnable() {
            @Override
            public void run() {
                totalPrice.setText(sTotalPrice);
            }
        });
        Log.d(TAG, "totalPrices: " + sTotalPrice);
        Log.d(TAG, "count2: " + list.getAdapter().getCount());
    }

    private OnFragmentPOSListener onFragmentPOSListener;

    public interface OnFragmentPOSListener {

        public void onCart(Item item);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
        if(context instanceof  Activity && context instanceof OnFragmentPOSListener){
            Log.d(TAG,"OnFragmentPOSListener");
            onFragmentPOSListener = (OnFragmentPOSListener) context;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pos, container, false);
        Log.d(TAG,"onCreateView");
        ButterKnife.bind(this, view);

        PosItemAutoCompleteAdapter posAdapter = new PosItemAutoCompleteAdapter(getActivity(), dumyPOSItems);
        itemAutoComplete.setAdapter(posAdapter);
        itemAutoComplete.setOnItemClickListener(this);


        list.setAdapter(new PosAdapter(getActivity(), itemCarts));
        list.setEmptyView(view.findViewById(R.id.empty));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Item item = (Item) adapterView.getItemAtPosition(position);
                Log.d(TAG,"ItemSelected name: " + item.getName());
                onFragmentPOSListener.onCart(item);
            }
        });

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Utility.hideSoftKey(getActivity(), itemAutoComplete);

        Item item = (Item) adapterView.getItemAtPosition(position);
        Log.d(TAG,"ItemSelected name: " + item.getName());
        onFragmentPOSListener.onCart(item);

    }
}
