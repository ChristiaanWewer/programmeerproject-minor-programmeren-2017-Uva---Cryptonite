package com.dev.chris.cryptonite;


import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class SpecificCoinGraphFragment extends Fragment {

    GraphView graph;

    String url;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_specific_coin_info_graph, container, false);
        String coinPartOfUrl = getActivity().getIntent().getStringExtra("coinSymbolString");

        url = "https://min-api.cryptocompare.com/data/histoday?fsym=" + coinPartOfUrl + "&tsym=USD&limit=5&aggregate=1";

        graph = (GraphView) rootView.findViewById(R.id.graph);



        RequestParams params = new RequestParams();
        params.put("10", 10);
        networkRequest(params);

        return rootView;
    }

    private void networkRequest(RequestParams tries) {
        Log.d("coins", "networkJob() called");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        Log.d("networkRequest starts", "yes it starts");
        client.get(url, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // from response to datapoint arrays
//                Log.d("priceJsonArray", response.toString());
                try {
                    JSONArray priceJsonArray = response.getJSONArray("Data");


                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{});

                    for (int i = 0, n = priceJsonArray.length(); i < n; i++) {
                        priceJsonArray.getJSONObject(i).getString("time");

                        Calendar calendar = Calendar.getInstance();


                        long xPointUnixTimeStamp = Integer.parseInt(priceJsonArray.getJSONObject(i).getString("time"));
                        float yPoint = Float.parseFloat(priceJsonArray.getJSONObject(i).getString("close"));

                        calendar.setTimeInMillis(xPointUnixTimeStamp * 1000);

//                        calendar.time


                        DataPoint newDataPoint = new DataPoint(calendar.getTime(), yPoint);

                        series.appendData(newDataPoint,true,9999);

                        Log.d("closestuff?", priceJsonArray.getJSONObject(i).getString("close"));
                    }

                    graph.addSeries(series);
                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));


                }
                catch (Exception e) {
                    Log.d("exception", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }

        });

    }

}

