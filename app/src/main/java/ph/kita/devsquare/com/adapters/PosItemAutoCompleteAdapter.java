package ph.kita.devsquare.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;

/**
 * Created by abnonymous on 6/22/16.
 */

public class PosItemAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<Item> items;
    private List<Item> suggestionItems = new ArrayList<>();

    public PosItemAutoCompleteAdapter(Context context, List<Item> items)
    {
        mContext = context;
        this.items = items;
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
                suggestionItems.clear();

                if (constraint != null && items != null) {
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
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
}
