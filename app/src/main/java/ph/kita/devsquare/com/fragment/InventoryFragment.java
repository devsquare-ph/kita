package ph.kita.devsquare.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.kita.devsquare.com.adapters.InventoryAdapter;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;

/**
 * Created by jericcabana on 19/06/2016.
 */
public class InventoryFragment extends Fragment {

    private static final String TAG = InventoryFragment.class.getSimpleName();

    @BindView(R.id.itemfilter)
    EditText itemFilter;
    @BindView(R.id.totalItem)
    TextView totalItem;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.addItem)
    Button addItem;

    private InventoryAdapter inventoryAdapter;

    private OnFragmentInventoryListener onFragmentInventoryListener;

    public static Fragment newInstance(){
        return new InventoryFragment();
    }

    public interface OnFragmentInventoryListener {
        public void onAddItem();
        public void onUpdateItem(Item item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
        if(context instanceof OnFragmentInventoryListener){
            Log.d(TAG,"OnFragmentPOSListener");
            onFragmentInventoryListener = (OnFragmentInventoryListener) context;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        Log.d(TAG,"onCreateView");
        ButterKnife.bind(this, view);

        //sort item
        Collections.sort(PosFragment.dumyPOSItems, new Comparator<Item>() {
            @Override public int compare(Item lhs, Item rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });

        inventoryAdapter = new InventoryAdapter(getActivity());
        list.setAdapter(inventoryAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item item = (Item) parent.getItemAtPosition(position);
                Log.d(TAG, "ITEM " + item.getName());
                onFragmentInventoryListener.onUpdateItem(item);
            }
        });

        totalItem.setText("" + PosFragment.dumyPOSItems.size());

        // Capture Text in EditText
        itemFilter.addTextChangedListener(new TextWatcher() {

            @Override public void afterTextChanged(Editable arg0) {
                String text = itemFilter.getText().toString().toLowerCase(Locale.getDefault());
                Log.d(TAG, "Text Change: " + text);
                inventoryAdapter.filter(text);
            }

            @Override public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

        return view;
    }

    @OnClick(R.id.addItem)
    public void addItem(){
        onFragmentInventoryListener.onAddItem();
    }

}
