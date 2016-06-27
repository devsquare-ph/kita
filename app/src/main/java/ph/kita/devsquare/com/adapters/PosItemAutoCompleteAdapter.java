package ph.kita.devsquare.com.adapters;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;
import ph.kita.devsquare.com.utils.Utility;

/**
 * Created by abnonymous on 6/22/16.
 */

public class PosItemAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private static final String TAG = PosItemAutoCompleteAdapter.class.getSimpleName();
    private Context mContext;
    private List<Item> items;
    private List<Item> suggestionItems = new ArrayList<>();

    public PosItemAutoCompleteAdapter(Context context, List<Item> items)
    {
        mContext = context;
        this.items = items;
        this.suggestionItems.addAll(items);
    }

    @Override
    public int getCount() {
        return this.suggestionItems.size();
    }

    @Override
    public Item getItem(int i) {
        return this.suggestionItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        }else{
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_pos_search_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.name.setText(this.getItem(position).getName());
        holder.stock.setText("" + this.getItem(position).getStock());
        holder.price.setText("" + this.getItem(position).getPrice());
        Utility.setImage(this.getItem(position).getImageURL(), holder.img, mContext);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.stock) TextView stock;
        @BindView(R.id.price) TextView price;
        @BindView(R.id.img) ImageView img;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            public CharSequence convertResultToString(Object resultValue) {
//                return super.convertResultToString(resultValue);
                return "";
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d(TAG, "performFiltering");
                Log.d(TAG, "isConstraint null: " + (constraint == null));
                suggestionItems.clear();

                if (constraint != null && items != null && suggestionItems != null) {
                    for(Item i : items){
                        if(i.getName().contains(constraint))
                            suggestionItems.add(i);
                    }

                }

                FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
                results.values = suggestionItems;
                results.count = suggestionItems.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Log.d(TAG, "publishResults");
                if (results != null && results.count >= 0) {
                    Log.d(TAG, "notifyDataSetChanged");
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
}
