package ph.kita.devsquare.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.kita.devsquare.com.adapters.PosReceiptAdapter;
import ph.kita.devsquare.com.dialog.ConfirmationDialog;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;

/**
 * Created by jericcabana on 19/06/2016.
 */
public class PosReceiptFragment extends Fragment {

    private static final String TAG = PosReceiptFragment.class.getSimpleName();

    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.totalPrice)
    TextView totalPrice;

    private OnFragmentReceiptListener onFragmentReceiptListener;
    private List<Item> items;


    public interface OnFragmentReceiptListener {

        public void onSave(List<Item> item);

    }

    static final String ITEMS = "ITEMS";

    public static Fragment newInstance(ArrayList<Item> items) {

        PosReceiptFragment posReceptFragment = new PosReceiptFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList(ITEMS, items);
        posReceptFragment.setArguments(b);
        return posReceptFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        if (context instanceof OnFragmentReceiptListener) {
            Log.d(TAG, "OnFragmentPOSListener");
            onFragmentReceiptListener = (OnFragmentReceiptListener) context;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pos_receipt, container, false);
        Log.d(TAG, "onCreateView");
        ButterKnife.bind(this, view);

        items = getArguments().getParcelableArrayList(ITEMS);
        Log.d(TAG, "items size: " + items.size());

        list.setAdapter(new PosReceiptAdapter(getActivity(), items));

        //set totalprice
        float totalPrices = 0.0f;

        for (Item itm : items) {
            //increment total price
            totalPrices += (itm.getPrice() * itm.getQualitytNWeight());
        }

        totalPrice.setText("" + totalPrices);

        return view;
    }

    @OnClick(R.id.ok)
    public void ok() {

        ConfirmationDialog confirmationDialog = ConfirmationDialog.newInstance("Are you sure to save?");
        confirmationDialog.setOnListener(new ConfirmationDialog.OnDialogConfirmationListener() {
            @Override
            public void onOK() {
                onFragmentReceiptListener.onSave(items);
            }
        });
        confirmationDialog.show(getActivity().getSupportFragmentManager(), ConfirmationDialog.class.getSimpleName());

    }

}
