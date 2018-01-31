package com.dev.chris.cryptonite;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

/**
 * Christiaan Wewer
 * 11943858
 * Fragment for specific textual crypto coin information.
 */

public class SpecificCoinInfoFragment extends Fragment {

    View rootView;
    String url;
    Timer timer;

    Button graphButton;
    TextView marketTextView;
    TextView priceTextView;
    TextView lastUpdateTextView;
    TextView lastVolumeTextView;
    TextView lastVolumeToTextView;
    TextView lastTradeIdTextView;
    TextView volume24HourTextView;
    TextView volume24HourToTextView;
    TextView open24HourTextView;
    TextView high24HourTextView;
    TextView low24HourTextView;
    TextView change24HourTextView;
    TextView changePct24HourTextView;
    TextView changeDayTextView;
    TextView changePctDayTextView;
    TextView supplyTextView;
    TextView mktCapTextView;
    TextView totalVolume24HourTextView;
    TextView totalVolume24HourToTextView;
    TextView coinNameTextView;

    String firstPartOfUrl = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=";
    String lastPartOfUrl = "&tsyms=USD";
    int refreshInterval = 30000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_specific_coin_list, container, false);

        graphButton = rootView.findViewById(R.id.goToGraphButton);
        marketTextView = rootView.findViewById(R.id.marketFromApiTextView);
        priceTextView = rootView.findViewById(R.id.priceFromAPITextView);
        lastUpdateTextView = rootView.findViewById(R.id.lastUpdateFromAPITextView);
        lastVolumeTextView = rootView.findViewById(R.id.lastVolumeFromAPITextView);
        lastVolumeToTextView = rootView.findViewById(R.id.lastVolumeToFromAPITextView);
        lastTradeIdTextView	= rootView.findViewById(R.id.lastTradedIdFromAPITextView);
        volume24HourTextView = rootView.findViewById(R.id.volume24HourFromAPITextView);
        volume24HourToTextView = rootView.findViewById(R.id.volume24HourToFromAPITextView);
        open24HourTextView = rootView.findViewById(R.id.open24HourFromAPITextView);
        high24HourTextView = rootView.findViewById(R.id.high24HourFromAPITextView);
        low24HourTextView = rootView.findViewById(R.id.low24HourFromAPITextView);
        change24HourTextView = rootView.findViewById(R.id.change24HourFromAPITextView);
        changePct24HourTextView = rootView.findViewById(R.id.changePct24HourFromAPITextView);
        changeDayTextView = rootView.findViewById(R.id.changeDayFromAPITextView);
        changePctDayTextView = rootView.findViewById(R.id.changePctDayFromAPITextView);
        supplyTextView = rootView.findViewById(R.id.supplyFromAPITextView);
        mktCapTextView = rootView.findViewById(R.id.mktCapFromAPITextView);
        totalVolume24HourTextView = rootView.findViewById(R.id.totalVolume24HFromAPITextView);
        totalVolume24HourToTextView = rootView.findViewById(R.id.totalVolume24HToFromAPITextView);
        coinNameTextView = rootView.findViewById(R.id.coinNameTextView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // get and set specific coinInfo
        timer = new Timer();
        String coinPartOfUrl = getActivity().getIntent().getStringExtra("coinSymbolString");
        String coinName = getActivity().getIntent().getStringExtra("coinName");
        coinNameTextView.setText(coinName);
        url = firstPartOfUrl + coinPartOfUrl + lastPartOfUrl;
        graphButton.setOnClickListener(new graphButtonOnClickListener());

        // start auto refresher
        try {
            autoRefresher(coinPartOfUrl);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class graphButtonOnClickListener implements View.OnClickListener  {
        @Override
        public void onClick(View v) {

            // load new fragment for graph
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            SpecificCoinGraphFragment specificCoinGraphFragment = new SpecificCoinGraphFragment();
            ft.replace(R.id.specific_crypto_fragment_container, specificCoinGraphFragment);
            ft.commit();
        }
    }

    private void autoRefresher(String coinSymbol) throws InterruptedException {
        final Handler handler = new Handler();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> networkRequestSpecificCoinInfo(coinSymbol));
            }
        };
        timer.schedule(doAsynchronousTask, 0, refreshInterval);
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

    private void networkRequestSpecificCoinInfo(String coinSymbol) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // load specific coin data into the model and show it
                SpecificCryptoCoinDataModel specificCoin = SpecificCryptoCoinDataModel.fromJson(response, coinSymbol);
                setFragmentUI(specificCoin);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }
        });
    }

    public void setFragmentUI(SpecificCryptoCoinDataModel specificCoin) {

        TextView marketTextView = rootView.findViewById(R.id.marketFromApiTextView);
        TextView priceTextView = rootView.findViewById(R.id.priceFromAPITextView);
        TextView lastUpdateTextView = rootView.findViewById(R.id.lastUpdateFromAPITextView);
        TextView lastVolumeTextView = rootView.findViewById(R.id.lastVolumeFromAPITextView);
        TextView lastVolumeToTextView = rootView.findViewById(R.id.lastVolumeToFromAPITextView);
        TextView lastTradeIdTextView = rootView.findViewById(R.id.lastTradedIdFromAPITextView);
        TextView volume24HourTextView = rootView.findViewById(R.id.volume24HourFromAPITextView);
        TextView volume24HourToTextView = rootView.findViewById(R.id.volume24HourToFromAPITextView);
        TextView open24HourTextView = rootView.findViewById(R.id.open24HourFromAPITextView);
        TextView high24HourTextView = rootView.findViewById(R.id.high24HourFromAPITextView);
        TextView low24HourTextView = rootView.findViewById(R.id.low24HourFromAPITextView);
        TextView change24HourTextView = rootView.findViewById(R.id.change24HourFromAPITextView);
        TextView changePct24HourTextView = rootView.findViewById(R.id.changePct24HourFromAPITextView);
        TextView changeDayTextView = rootView.findViewById(R.id.changeDayFromAPITextView);
        TextView changePctDayTextView = rootView.findViewById(R.id.changePctDayFromAPITextView);
        TextView supplyTextView = rootView.findViewById(R.id.supplyFromAPITextView);
        TextView mktCapTextView = rootView.findViewById(R.id.mktCapFromAPITextView);
        TextView totalVolume24HourTextView = rootView.findViewById(R.id.totalVolume24HFromAPITextView);
        TextView totalVolume24HourToTextView = rootView.findViewById(R.id.totalVolume24HToFromAPITextView);

        marketTextView.setText(specificCoin.getMarket());
        priceTextView.setText(specificCoin.getPrice());
        lastUpdateTextView.setText(specificCoin.getLastUpdate());
        lastVolumeTextView.setText(specificCoin.getLastVolume());
        lastVolumeToTextView.setText(specificCoin.getLastVolumeTo());
        lastTradeIdTextView.setText(specificCoin.getLastTradeId());
        volume24HourTextView.setText(specificCoin.getVolume24Hour());
        volume24HourToTextView.setText(specificCoin.getVolume24HourTo());
        open24HourTextView.setText(specificCoin.getOpen24Hour());
        high24HourTextView.setText(specificCoin.getHigh24Hour());
        low24HourTextView.setText(specificCoin.getLow24Hour());
        change24HourTextView.setText(specificCoin.getChange24Hour());
        changePct24HourTextView.setText(specificCoin.getChangePct24Hour());
        changeDayTextView.setText(specificCoin.getChangeDay());
        changePctDayTextView.setText(specificCoin.getChangePctDay());
        supplyTextView.setText(specificCoin.getSupply());
        mktCapTextView.setText(specificCoin.getMktCap());
        totalVolume24HourTextView.setText(specificCoin.getTotalVolume24Hour());
        totalVolume24HourToTextView.setText(specificCoin.getTotalVolume24Hour24HourTo());

    }
}
