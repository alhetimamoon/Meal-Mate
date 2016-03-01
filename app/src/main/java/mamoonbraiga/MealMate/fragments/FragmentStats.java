package mamoonbraiga.MealMate.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import mamoonbraiga.poodle_v3.R;


/**
 * Created by MamoonBraiga on 2015-10-25.
 */
public class FragmentStats extends Fragment {

    private PieChart pieChart;
    FrameLayout chartContainer;
    private float[] yData = {25, 40, 35};
    private String[] xData = {"Fat", "Protein", "Carbs"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View view = inflater.inflate(R.layout.fragment_stats, container, false);
        chartContainer = (FrameLayout) view.findViewById(R.id.chartContainer);
        pieChart = new PieChart(getContext());

        //add pie chart to the view
        chartContainer.addView(pieChart);


        //configure the pie chart

        pieChart.setUsePercentValues(false);
        pieChart.setBackgroundColor(Color.WHITE);
        pieChart.setDescriptionColor(Color.WHITE);
        pieChart.setCenterText("DAILY MACRONUTRIENT");
        pieChart.setCenterTextColor(getResources().getColor(R.color.ColorPrimary));
        pieChart.setDrawCenterText(true);
        pieChart.setCenterTextSize(20);
        pieChart.setDescription("");



        //enable hole and configure

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(50);
        pieChart.setTransparentCircleRadius(55);


        //enable rotation
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(false);

        //set a chart value selected listener
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {

                //display message when value selected
                if (entry == null)
                    return;
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //add data
        addData();

        //customize legends
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);


        return view;
    }

    private void addData() {

        ArrayList<Entry> yValues1 = new ArrayList<>();

        for (int i=0; i<yData.length; i++){

            yValues1.add(new Entry(yData[i], i));
        }

        ArrayList<String> xValues = new ArrayList<>();

        for (int i=0; i<xData.length; i++){

            xValues.add(xData[i]);
        }

        //create pie chart set

        PieDataSet dataSet = new PieDataSet(yValues1, "Calories Breakdown");
        dataSet.setSliceSpace(1);

        dataSet.setSelectionShift(5);
        dataSet.setValueTextColor(Color.WHITE);

        //add colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(c);
        }

        for (int c: ColorTemplate.JOYFUL_COLORS){
            colors.add(c);
        }

        for (int c: ColorTemplate.COLORFUL_COLORS){
            colors.add(c);
        }

        for (int c: ColorTemplate.LIBERTY_COLORS){
            colors.add(c);
        }

        for (int c: ColorTemplate.PASTEL_COLORS){
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        //instantiate pie data object

        PieData pieData = new PieData(xValues, dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.GRAY);


        pieChart.setData(pieData);

        //undo highlights
        pieChart.highlightValues(null);

        //update pie chart
        pieChart.invalidate();

    }
}
