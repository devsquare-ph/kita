package ph.kita.devsquare.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

import butterknife.ButterKnife;
import ph.kita.devsquare.com.custom.MyYAxisValueFormatter;
import ph.kita.devsquare.com.kita.R;

/**
 * Created by jericcabana on 19/06/2016.
 */
public class StatisticFragment extends Fragment {

    private static final String TAG = StatisticFragment.class.getSimpleName();

    public static Fragment newInstance(){
        return new StatisticFragment();
    }

    public static TabLayout tabLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        Log.d(TAG,"onCreateView");
        ButterKnife.bind(this, view);

        tabLayout = (TabLayout) view.findViewById(R.id.toolbar);

//        ViewGroup tab = (ViewGroup) view.findViewById(R.id.tab);
//        tab.addView(LayoutInflater.from(getActivity()).inflate(R.layout.demo_distribute_evenly, tab, false));

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //animated tabs
        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);

        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
        pages.add(FragmentPagerItem.of("Sales", SalesGraphFragment.class));
        pages.add(FragmentPagerItem.of("Demand", DemandGraphFragment.class));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

        return view;
    }

}
