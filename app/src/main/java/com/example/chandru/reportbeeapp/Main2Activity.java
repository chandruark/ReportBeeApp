package com.example.chandru.reportbeeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


    public class Main2Activity extends AppCompatActivity implements OnChartValueSelectedListener {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            PieChart pieChart = (PieChart) findViewById(R.id.piechart);
            pieChart.setUsePercentValues(true);


            Bundle extras=getIntent().getExtras();
            String us=extras.getString("pre");
            String u=extras.getString("abs");
            System.out.println(""+us+""+u);

            int p=Integer.parseInt(us);
            int p1=Integer.parseInt(u);
            Toast.makeText(getBaseContext(), p + "--" + p1, Toast.LENGTH_LONG).show();
            ArrayList<Entry> yvalues = new ArrayList<Entry>();
            yvalues.add(new Entry(p, 0));
            yvalues.add(new Entry(p1, 1));

            PieDataSet dataSet = new PieDataSet(yvalues, "Report Bee Attendance");

            ArrayList<String> xVals = new ArrayList<String>();

            xVals.add("Present");
            xVals.add("Absent");


            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            pieChart.setData(data);
            pieChart.setDescription("Visualization for Present /Absent");

            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(25f);
            pieChart.setHoleRadius(30f);

            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            data.setValueTextSize(13f);
            data.setValueTextColor(Color.DKGRAY);
            pieChart.setOnChartValueSelectedListener(this);

            pieChart.animateXY(1400, 1400);

        }

        @Override
        public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

            if (e == null)
                return;
            Log.i("VAL SELECTED",
                    "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                            + ", DataSet index: " + dataSetIndex);
        }

        @Override
        public void onNothingSelected() {
            Log.i("PieChart", "nothing selected");
        }

    }
