package org.cardioart.graphit;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.util.ArrayList;

/**
 * Created by jirawat on 10/05/2014.
 */
public class GraphActivity extends ActionBarActivity{
    private static final int HISTORY_SIZE = 2500;
    private static String device_name;
    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private Runnable mTimer2;
    private GraphView graphView;
    private GraphViewSeries series1;
    private GraphViewSeries series2;
    private double serie1LastValue = 0d;
    private double serie2LastValue = 0d;
    private ArrayList<GraphViewData> data1 = new ArrayList<GraphViewData>();
    private ArrayList<GraphViewData> data2 = new ArrayList<GraphViewData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        device_name = getIntent().getStringExtra("device_name");

        if (device_name.isEmpty()) {
            this.finish();
        }
        setTitle(getResources().getString(R.string.app_name) + " " + device_name);

        //generate data
        for (;serie2LastValue < HISTORY_SIZE; serie2LastValue++, serie1LastValue++) {
            data1.add(new GraphViewData(serie1LastValue, 0));
            data2.add(new GraphViewData(serie2LastValue, 0));
        }
        //init graphView for series1
        series1 = new GraphViewSeries(new GraphViewData[] {});
        graphView = new LineGraphView(this, "Serie1");
        graphView.addSeries(series1);
        graphView.setHorizontalLabels(new String[]{});
        graphView.setVerticalLabels(new String[] {"255","127", "0"});
        graphView.setManualYAxis(true);
        graphView.setManualYAxisBounds(255, 0);
        graphView.getGraphViewStyle().setTextSize(getResources().getDimension(R.dimen.small));
        LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
        layout.addView(graphView);

        //init graphView for series2
        series2 = new GraphViewSeries(data2.toArray(new GraphViewData[data2.size()]));
        series2.getStyle().color = Color.CYAN;
        graphView = new LineGraphView(this, "Serie2");
        graphView.addSeries(series2);
        graphView.getGraphViewStyle().setTextSize(getResources().getDimension(R.dimen.small));
        graphView.setVerticalLabels(new String[] {"255","127", "0"});
        graphView.setHorizontalLabels(new String[]{});
        graphView.setManualYAxis(true);
        graphView.setManualYAxisBounds(255, 0);
        layout = (LinearLayout) findViewById(R.id.graph2);
        layout.addView(graphView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i< 100; i++) {
                    serie1LastValue += 1d;
                    data1.remove(0);
                    data1.add(new GraphViewData(serie1LastValue, getRandom(serie1LastValue)));
                }
                series1.resetData(data1.toArray(new GraphViewData[HISTORY_SIZE]));
                mHandler.postDelayed(this, 1);
            }
        };
        mHandler.postDelayed(mTimer1, 100);

        mTimer2 = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i< 100; i++) {
                    serie2LastValue += 1d;
                    data2.remove(0);
                    data2.add(new GraphViewData(serie2LastValue, getSine(serie2LastValue)));
                }
                series2.resetData(data2.toArray(new GraphViewData[HISTORY_SIZE]));
                mHandler.postDelayed(this, 1);
            }
        };
        mHandler.postDelayed(mTimer2, 100);
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mTimer1);
        mHandler.removeCallbacks(mTimer2);
        super.onPause();
    }

    private double getSine(double x) {
        return Math.sin(x/180*Math.PI)*50+127 + Math.random()*100 - 50;
    }
    private double getRandom(double x) {
        return (x % 255);
    }
}
