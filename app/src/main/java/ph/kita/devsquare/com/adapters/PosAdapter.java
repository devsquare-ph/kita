package ph.kita.devsquare.com.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ph.kita.devsquare.com.dialog.ConfirmationDialog;
import ph.kita.devsquare.com.fragment.PosFragment;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;

/**
 * Created by abnonymous on 6/22/16.
 */

public class PosAdapter extends BaseAdapter{

    private static final int MAX_RESULTS = 10;
    private static final String TAG = PosAdapter.class.getSimpleName();
    private Context mContext;
//    private List<Item> items = new ArrayList<Item>();
    private List<Item> items;
    public PosAdapter(Context context)
    {
        mContext = context;
        this.items = new ArrayList<Item>();
//        this.items = PosFragment.dumyPOSItems;
    }

    public PosAdapter(Context context, List<Item> items)
    {
        mContext = context;
        this.items = items;
//        this.items = PosFragment.dumyPOSItems;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Item getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        }else{
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_pos_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.name.setText(this.getItem(position).getName());
        holder.qtNwt.setText("" + this.getItem(position).getQualitytNWeight());
        holder.price.setText("" + this.getItem(position).getPrice());
        holder.totalPrice.setText("" + (this.getItem(position).getPrice() * this.getItem(position).getQualitytNWeight()));
        holder.closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //closed item
                ConfirmationDialog confirmationDialog = ConfirmationDialog.newInstance("Are you sure to remove?");

                confirmationDialog.setOnListener(new ConfirmationDialog.OnDialogConfirmationListener() {
                    @Override
                    public void onOK() {
                        items.remove(position);
                        notifyDataSetChanged();
                    }
                });

                confirmationDialog.show(((FragmentActivity)mContext).getSupportFragmentManager(), ConfirmationDialog.class.getSimpleName());

            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.qtNwt) TextView qtNwt;
        @BindView(R.id.price) TextView price;
        @BindView(R.id.totalPrice) TextView totalPrice;
        @BindView(R.id.closed) ImageView closed;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void addItem(final Item item) {

//        items.add(item);
//        items.addAll(new ArrayList<Item>(Arrays.asList(item)));

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                if(getCount() == 0)
                items = new ArrayList<Item>();

                items.add(item);
                notifyDataSetChanged();

                return null;
            }
        }.execute();


        Log.d(TAG, "Add Item: " + item.getName());
        Log.d(TAG, "Items size: " + items.size());

    }

    public void addItems(final List<Item> items) {

            this.items = items;

        Log.d(TAG, "Items size: " + items.size());

    }

}
