package com.dev.chris.cryptonite;


import android.os.Bundle;
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
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SpecificCoinListFragment extends Fragment {

    View rootView;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_specific_coin_list, container, false);

        String coinPartOfUrl = getActivity().getIntent().getStringExtra("coinSymbolString");

        Log.d("coinPartOfUrl", coinPartOfUrl);
        url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=" + coinPartOfUrl + "&tsyms=USD";

        RequestParams specificParams = new RequestParams();
        specificParams.put("limit", 10);
        networkRequest(specificParams, coinPartOfUrl);

        return rootView;
    }

    private void networkRequest(RequestParams tries, String coinSymbol) {
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

                SpecificCryptoCoinData specificCoin = SpecificCryptoCoinData.fromJson(response, coinSymbol);

                setFragmentUI(specificCoin);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }

        });

    }

    public void setFragmentUI(SpecificCryptoCoinData specificCoin) {

//        View rootView = SpecificCoinListFragment.this.getView();
//        View rootView = this.rootView;
        Button graphButton = rootView.findViewById(R.id.goToGraphButton);

        graphButton.setOnClickListener(v -> {
            // Code here executes on main thread after user presses button
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            SpecificCoinGraphFragment specificCoinGraphFragment = new SpecificCoinGraphFragment();
            ft.replace(R.id.specific_crypto_fragment_container, specificCoinGraphFragment);
            ft.commit();
        });

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
