package com.dev.chris.cryptonite;


import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

/**
 * Christiaan Wewer
 * 11943858
 * Fragment for specific graphical crypto coin close prices on daily base.
 */

public class SpecificCoinGraphFragment extends Fragment {

    GraphView graph;
    String urlString;
    Timer timer;
    int timerPeriodInt = 100000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get needed info
        timer = new Timer();
        View rootView = inflater.inflate(R.layout.fragment_specific_coin_info_graph, container, false);
        graph = rootView.findViewById(R.id.graph);
        String coinPartOfUrl = getActivity().getIntent().getStringExtra("coinSymbolString");
        urlString = "https://min-api.cryptocompare.com/data/histoday?fsym=" + coinPartOfUrl +
                "&tsym=USD&limit=7";
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // start auto refresher up to date data
        try {
            autoRefresher();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void autoRefresher() throws InterruptedException {
        final Handler handler = new Handler();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> networkRequestCoinGraphInfo());
            }
        };
        timer.schedule(doAsynchronousTask, 0, timerPeriodInt);
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

    @Override
    public void onStop() {
        timer.cancel();
        super.onStop();
    }

    private void networkRequestCoinGraphInfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        client.get(urlString, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                makeLineGraphSeries(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }
        });
    }

    public void makeLineGraphSeries(JSONObject response) {

        // make from json data linegraph data and pass it to setGraph method
        try {
            JSONArray priceJsonArray = response.getJSONArray("Data");
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{});

            for (int i = 0, n = priceJsonArray.length(); i < n; i++) {
                Calendar calendar = Calendar.getInstance();
                long xPointUnixTimeStamp = Integer.parseInt(priceJsonArray.getJSONObject(i).getString("time"));
                float yPoint = Float.parseFloat(priceJsonArray.getJSONObject(i).getString("close"));
                calendar.setTimeInMillis(xPointUnixTimeStamp * 1000);
                DataPoint newDataPoint = new DataPoint(calendar.getTime(), yPoint);
                series.appendData(newDataPoint,true,30);
            }

            setGraph(series);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGraph(LineGraphSeries<DataPoint> series) {

        // set graph data
        graph.addSeries(series);
        GridLabelRenderer graphGridLabelRenderer = graph.getGridLabelRenderer();
        graphGridLabelRenderer.setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graphGridLabelRenderer.setHorizontalAxisTitle("days");
        graphGridLabelRenderer.setVerticalAxisTitle("price in USD");
        String coinName = getActivity().getIntent().getStringExtra("coinName");
        graph.setTitle(coinName + " close price last 7 days");
    }
}

