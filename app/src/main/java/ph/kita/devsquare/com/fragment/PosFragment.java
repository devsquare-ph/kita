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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ph.kita.devsquare.com.adapters.PosItemAutoCompleteAdapter;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;

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

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Item item = (Item) adapterView.getItemAtPosition(position);
        Log.d(TAG,"ItemSelected name: " + item.getName());
        onFragmentPOSListener.onCart(item);

    }
}
