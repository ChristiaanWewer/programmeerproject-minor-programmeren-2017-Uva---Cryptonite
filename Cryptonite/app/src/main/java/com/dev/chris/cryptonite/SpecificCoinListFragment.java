package com.dev.chris.cryptonite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecificCoinListFragment extends Fragment {


   String url;
   SpecificCoinInfoActivity specificCoinInfoActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        String coinPartOfUrl = specificCoinInfoActivity.getSymbolString();
//        String coinPartOfUrl = "BTC";
        String coinPartOfUrl = getActivity().getIntent().getStringExtra("coinSymbolString");

        Log.d("coinPartOfUrl", coinPartOfUrl);
        url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=" + coinPartOfUrl + "&tsyms=USD";



        RequestParams specificParams = new RequestParams();
        specificParams.put("limit", 10);
        networkRequest(specificParams, coinPartOfUrl);


        return inflater.inflate(R.layout.fragment_specific_coin_list, container, false);
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
        View fragmentView = SpecificCoinListFragment.this.getView();

        TextView marketTextView = fragmentView.findViewById(R.id.marketFromApiTextView);
        TextView priceTextView = fragmentView.findViewById(R.id.priceFromAPITextView);
        TextView lastUpdateTextView = fragmentView.findViewById(R.id.lastUpdateFromAPITextView);
        TextView lastVolumeTextView = fragmentView.findViewById(R.id.lastVolumeFromAPITextView);
        TextView lastVolumeToTextView = fragmentView.findViewById(R.id.lastVolumeToFromAPITextView);
        TextView lastTradeIdTextView = fragmentView.findViewById(R.id.lastTradedIdFromAPITextView);
        TextView volume24HourTextView = fragmentView.findViewById(R.id.volume24HourFromAPITextView);
        TextView volume24HourToTextView = fragmentView.findViewById(R.id.volume24HourToFromAPITextView);
        TextView open24HourTextView = fragmentView.findViewById(R.id.open24HourFromAPITextView);
        TextView high24HourTextView = fragmentView.findViewById(R.id.high24HourFromAPITextView);
        TextView low24HourTextView = fragmentView.findViewById(R.id.low24HourFromAPITextView);
        TextView change24HourTextView = fragmentView.findViewById(R.id.change24HourFromAPITextView);
        TextView changePct24HourTextView = fragmentView.findViewById(R.id.changePct24HourFromAPITextView);
        TextView changeDayTextView = fragmentView.findViewById(R.id.changeDayFromAPITextView);
        TextView changePctDayTextView = fragmentView.findViewById(R.id.changePctDayFromAPITextView);
        TextView supplyTextView = fragmentView.findViewById(R.id.supplyFromAPITextView);
        TextView mktCapTextView = fragmentView.findViewById(R.id.mktCapFromAPITextView);
        TextView totalVolume24HourTextView = fragmentView.findViewById(R.id.totalVolume24HFromAPITextView);
        TextView totalVolume24HourToTextView = fragmentView.findViewById(R.id.totalVolume24HToFromAPITextView);

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
