package ph.kita.devsquare.com.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ph.kita.devsquare.com.KitaApplication;
import ph.kita.devsquare.com.dummy.ItemDummyDb;
import ph.kita.devsquare.com.fragment.PosFragment;
import ph.kita.devsquare.com.kita.MainActivity;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;

/**
 * Created by abnonymous on 6/22/16.
 */

public class InventoryAdapter extends BaseAdapter{

    private static final int MAX_RESULTS = 10;
    private static final String TAG = InventoryAdapter.class.getSimpleName();
    private Context mContext;
    private List<Item> items;
    private List<Item> itemFilters = new ArrayList<>();

    public InventoryAdapter(Context context)
    {
        final KitaApplication kitaApplication = ((KitaApplication) ((MainActivity) context).getApplication());
        mContext = context;
        this.items = kitaApplication.getItemDummyDb().getAll();
        this.itemFilters.addAll(items);
    }

    public InventoryAdapter(Context context, List<Item> items)
    {
        mContext = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.itemFilters.size();
    }

    @Override
    public Item getItem(int i) {
        return this.itemFilters.get(i);
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
            convertView = inflater.inflate(R.layout.item_inventory, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.name.setText(this.getItem(position).getName());
        holder.stock.setText("" + this.getItem(position).getStock());
        holder.price.setText("" + this.getItem(position).getPrice());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.stock) TextView stock;
        @BindView(R.id.price) TextView price;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        itemFilters.clear();
        if (charText.length() == 0) {
            itemFilters.addAll(items);
        } else {
            for (Item i : items) {
                if (i.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemFilters.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }

}
