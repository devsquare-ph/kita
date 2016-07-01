package ph.kita.devsquare.com.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.kita.devsquare.com.kita.R;
import ph.kita.devsquare.com.objects.Item;

/**
 * Created by jericcabana on 19/06/2016.
 */
public class DemandGraphFragment extends Fragment {

    private static final String TAG = DemandGraphFragment.class.getSimpleName();

    @BindView(R.id.next)
    Button next;
    @BindView(R.id.previous)
    Button previous;

    private PieChart mChart;

    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    private int lastIndex = 0;
    private int maxItem = 2;
    private int counts = 0;

    public static Fragment newInstance(){
        return new DemandGraphFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demand_graph, container, false);
        Log.d(TAG,"onCreateView");
        ButterKnife.bind(this, view);

        initialGraph(view);

        lastIndex = 0;
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//        mChart.setOnChartValueSelectedListener(this);

        if(PosFragment.dumyPOSItems.size() > maxItem)
            next.setEnabled(true);

        setData(4, 100);

        initialProductList();


        return view;
    }

    @OnClick(R.id.previous)
    public void previous(Button view){
        Toast.makeText(getActivity(), "previous: " + lastIndex, Toast.LENGTH_SHORT).show();

        if(lastIndex > (maxItem - 1))
            lastIndex-= (counts + maxItem);
            setData(4, 100);

        //disable previous button
        if(lastIndex == maxItem)
            view.setEnabled(false);


        //enable next button
        next.setEnabled(true);
    }

    @OnClick(R.id.next)
    public void next(Button view){
        Toast.makeText(getActivity(), "next: " + lastIndex, Toast.LENGTH_SHORT).show();

        if(lastIndex < PosFragment.dumyPOSItems.size())
            setData(4, 100);

        if((PosFragment.dumyPOSItems.size() - lastIndex) < maxItem)
            view.setEnabled(false);

//        if(lastIndex < (PosFragment.dumyPOSItems.size() - 1))
//        else {
//            view.setEnabled(false);
//        }
        //enable previous button
        previous.setEnabled(true);
    }

    private void initialGraph(View view) {
        mChart = (PieChart) view.findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
    }

    private void initialProductList() {
        mChart.animateY(1400, Easing.EasingOption.EaseInOutBack);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        counts = 0;
        for (; lastIndex < PosFragment.dumyPOSItems.size(); lastIndex++) {

            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, counts));
            xVals.add(PosFragment.dumyPOSItems.get(lastIndex).getName());
            counts++;
            //break max item display
            if(counts > (maxItem - 1)) {
                lastIndex++;
                break;
            }
        }



//        for (int i = 0; i < count + 1; i++)
//            xVals.add(mParties[i % mParties.length]);

//        for (; lastIndex < PosFragment.dumyPOSItems.size(); lastIndex++) {
            //break last index of dumy items
//            if(lastIndex == PosFragment.dumyPOSItems.size())
//                break;
//        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Product List");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Products Demand Chart");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

}
