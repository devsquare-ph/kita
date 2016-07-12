package ph.kita.devsquare.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import ph.kita.devsquare.com.KitaApplication;
import ph.kita.devsquare.com.custom.MyYAxisValueFormatter;
import ph.kita.devsquare.com.dummy.ItemDummyDb;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;

/**
 * Created by jericcabana on 19/06/2016.
 */
public class SalesGraphFragment extends Fragment {

    private static final String TAG = SalesGraphFragment.class.getSimpleName();

    public static Fragment newInstance(){
        return new SalesGraphFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");

    }

    private BarChart mChart;
    private Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales_graph, container, false);
        Log.d(TAG,"onCreateView");
        ButterKnife.bind(this, view);

        mChart = (BarChart) view.findViewById(R.id.chart1);
        // drawn
        // if more than 60 entries are displayed in the chart, no values will be
        mChart.setMaxVisibleValueCount(60);

        initialGraph();
        setData(5, 100);
        setDailySpinner(view);

        //get reference to the spinner from the XML layout
        Spinner spinner = (Spinner) view.findViewById(R.id.product_filter);

        //Array list of animals to display in the spinner
        List<String> list = new ArrayList<String>();
        list.add("All Products");

        final KitaApplication kitaApplication = (KitaApplication) getActivity().getApplication();
        final ItemDummyDb itemDummyDb = kitaApplication.getItemDummyDb();

        for(Item i : itemDummyDb.getAll())
                list.add(i.getName());

        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        spinner.setAdapter(dataAdapter);
        //attach the listener to the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setData(5, 100);
                mChart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void setDailySpinner(View view) {
        //spinner daily
        spinner = (Spinner) view.findViewById(R.id.date_filter);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.list_graph_date, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void initialGraph() {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setForm(Legend.LegendForm.SQUARE);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

//    protected String[] mMonths = new String[] {
//            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
//    };

    protected String[] mMonths = new String[] {
            "Mon", "Tue", "Wed", "Thur", "Fri"
    };

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            //add date column
            xVals.add(mMonths[i % 12]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        //set price per date
        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            //add date bar
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setYVals(yVals1);
            mChart.getData().setXVals(xVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet");
            set1.setBarSpacePercent(35f);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(10f);

            mChart.setData(data);
        }
    }
}
