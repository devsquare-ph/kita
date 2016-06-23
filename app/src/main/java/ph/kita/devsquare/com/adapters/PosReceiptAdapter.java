package ph.kita.devsquare.com.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

public class PosReceiptAdapter extends BaseAdapter{

    private static final int MAX_RESULTS = 10;
    private static final String TAG = PosReceiptAdapter.class.getSimpleName();
    private Context mContext;
//    private List<Item> items = new ArrayList<Item>();
    private List<Item> items;

    public PosReceiptAdapter(Context context, List<Item> items)
    {
        mContext = context;
        this.items = items;
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
            convertView = inflater.inflate(R.layout.item_pos_receipt, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.name.setText(this.getItem(position).getName());
        holder.qtNwt.setText("" + this.getItem(position).getQualitytNWeight());
        holder.price.setText("" + this.getItem(position).getPrice());
        holder.totalPrice.setText("" + (this.getItem(position).getPrice() * this.getItem(position).getQualitytNWeight()));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.qtNwt) TextView qtNwt;
        @BindView(R.id.price) TextView price;
        @BindView(R.id.totalPrice) TextView totalPrice;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
