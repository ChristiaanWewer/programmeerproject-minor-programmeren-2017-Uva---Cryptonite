package com.dev.chris.cryptonite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class CryptoFragment extends ListFragment {

    CryptoAdapter cryptoAdapter;
    String url = "https://api.coinmarketcap.com/v1/ticker/?limit=10";
    String urlCryptoCompare = "https://min-api.cryptocompare.com/data/all/coinlist";
    int maxApiUrlLenInt = 280;

    ArrayList<CryptoCoinData> cryptoArrayList = new ArrayList<>();
    Boolean search;
    String query;
    Timer timer;
    ArrayList<CryptoCoinData2> cryptoArrayList2 = new ArrayList<>();

    //    ListView cryptoList;
    private FavoriteCoinDatabase favoriteCoinDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        Log.d("open fragment", "yes!");




        timer = new Timer();

        View rootView = inflater.inflate(R.layout.fragment_crypto, container, false);

        RequestParams params = new RequestParams();
        params.put("limit", 10);
        networkRequest(params);

        try {
            autoRefresher();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        networkRequest2(params);
//        networkRequest3(params,0, "w");

        Bundle bundle = this.getArguments();
        search = bundle.getBoolean("search");
        if (search) {
            query = bundle.getString("query");
        }

        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setOnItemLongClickListener((av, v, position, id) -> {

            int coinRank = cryptoArrayList2.get(position).getRank();
            String coinSymbol = cryptoArrayList2.get(position).getSymbol();
            String coinName = cryptoArrayList2.get(position).getCoinName();
            favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());

            Log.d("rankSymbolName", Integer.toString(coinRank) + "  " + coinSymbol + "  " + coinName);
            favoriteCoinDatabase.addCoinRankSymbolName(coinRank, coinSymbol, coinName);
            return true;

        });

    }

    private void networkRequest(RequestParams tries) {
//        Log.d("coins", "networkJob() called");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        client.get(url, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                Log.d("success: ", response.toString());
                cryptoArrayList = new ArrayList<>();

                ArrayList<CryptoCoinData> searchCryptoCoinList;


                for (int i = 0, n = 10; i < n; i++) {
                    CryptoCoinData aCoin = CryptoCoinData.fromJson(response, i);
                    cryptoArrayList.add(aCoin);
                }


                if (search) {
                    searchCryptoCoinList = new ArrayList<>();

                    for (int i = 0; i < cryptoArrayList.size(); i++) {
                        CryptoCoinData cryptoCoinData = cryptoArrayList.get(i);
                        if (cryptoCoinData.getCoinName().matches("(?i:.*" + query + ".*)")) {
                            searchCryptoCoinList.add(cryptoCoinData);
                        }
                        else if (cryptoCoinData.getSymbol().matches("(?i:.*" + query + ".*)")) {
                            searchCryptoCoinList.add(cryptoCoinData);
                        }
                    }

//                    cryptoAdapter = new CryptoAdapter(getActivity(), searchCryptoCoinList);

                }
                else {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String symbolString = cryptoArrayList2.get(position).getSymbol();
        Intent intent = new Intent(getActivity(), SpecificCoinInfoActivity.class);
        intent.putExtra("coinSymbolString", symbolString);
        startActivity(intent);
    }


    private void autoRefresher() throws InterruptedException {

        final Handler handler = new Handler();
//        Timer timer = new Timer();

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        RequestParams params = new RequestParams();
                        params.put("limit", 10);
                        networkRequest(params);
                    }
                });
            }
        };

        timer.schedule(doAsynchronousTask, 0, 10000);
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















    private void networkRequest2(RequestParams tries) {
        Log.d("coins", "networkJob() called");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        client.get(urlCryptoCompare, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("success: ", response.toString());
                cryptoArrayList2 = new ArrayList<>();

                JSONObject dataJsonObject;
                try {
                    dataJsonObject = response.getJSONObject("Data");

//                    String otherSpecificCoinDataUrlString = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=";

                    for (Iterator key = dataJsonObject.keys(); key.hasNext();) {

                        JSONObject oneCoinJsonWithInfo = (JSONObject) dataJsonObject.get(key.next().toString());

                        CryptoCoinData2 aCoin2 = CryptoCoinData2.fromJson2(oneCoinJsonWithInfo);

                        cryptoArrayList2.add(aCoin2);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                concatinatePriceUrl(0, cryptoArrayList2.size() - 1, true, 0);

                concatinatePriceUrl(0);
//            Log.d("check coinname", cryptoArrayList2.get(0).getCoinName());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }
        });
    }


    private void concatinatePriceUrl(int startPlaceInt) {


        int endPlaceInt = startPlaceInt;

        String concatinatedUrlString = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=";

        int urlLenInt = 0;

        while (urlLenInt < maxApiUrlLenInt && (endPlaceInt < cryptoArrayList2.size())) {
//            Log.d("endPlaceInt, cryptoArrayList2.size()", Integer.toString(endPlaceInt) + "     " + Integer.toString(cryptoArrayList2.size()));
            String symbolString = cryptoArrayList2.get(endPlaceInt).getSymbol();
            concatinatedUrlString += "," + symbolString;
            endPlaceInt++;
            urlLenInt = concatinatedUrlString.length();
        }

        RequestParams params = new RequestParams();
        params.put("limit", 10);
        networkRequest3(params, startPlaceInt, concatinatedUrlString);

//        testNetworkConnect(concatinatedUrlString, startPlaceInt);


        if (!(endPlaceInt < cryptoArrayList2.size())) {

            return;
        }
//        RequestParams params = new RequestParams();
//        params.put("limit", 10);
//        networkRequest3(params, startPlaceInt, concatinatedUrlString);

        concatinatePriceUrl(endPlaceInt);
    }


    public void testNetworkConnect(String firstUrlPart, int startPlaceInt) {
        firstUrlPart += "&tsyms=USD";
        Log.d("ifthiswasanetworkconnect", Integer.toString(startPlaceInt) + "   " + firstUrlPart);
    }


    private void networkRequest3(RequestParams tries, int saveStartPlaceInt, String urlForCrypto) {
        Log.d("coins", "networkREQUEST3");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);

        urlForCrypto += "&tsyms=USD"; // in networkrequest3 doen

        client.get(urlForCrypto, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("new one", response.toString());

                try {
                    setRestOfCoinData(response.getJSONObject("RAW"), saveStartPlaceInt);

//                    Log.d("response", response.getJSONObject("DISPLAY").getJSONObject("USD").toString());
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }
        });
    }

    public void setRestOfCoinData(JSONObject httpRequestResponse, int saveStartPlaceInt) throws JSONException {

        for (Iterator key = httpRequestResponse.keys(); key.hasNext();) {
            JSONObject oneCoinJsonWithInfo = (JSONObject) httpRequestResponse.get(key.next().toString());

//            Log.d("iteration", Integer.toString(saveStartPlaceInt) + ", " +cryptoArrayList2.get(saveStartPlaceInt).getSymbol() + ", " + oneCoinJsonWithInfo.getJSONObject("USD").getString("FROMSYMBOL"));


            String testSymbolStringUpdateAPI = oneCoinJsonWithInfo.getJSONObject("USD").getString("FROMSYMBOL");
            String testSymbolStringListAPI = cryptoArrayList2.get(saveStartPlaceInt).getSymbol();

            while (!(testSymbolStringListAPI.equals(testSymbolStringUpdateAPI))) {

                Log.d("check1", testSymbolStringListAPI + "  " + testSymbolStringUpdateAPI);
                saveStartPlaceInt++;

                if (saveStartPlaceInt > cryptoArrayList2.size() -1) {

                    setCryptoAdapter();
                    return;
                }


                testSymbolStringUpdateAPI = oneCoinJsonWithInfo.getJSONObject("USD").getString("FROMSYMBOL");
                testSymbolStringListAPI = cryptoArrayList2.get(saveStartPlaceInt).getSymbol();

                Log.d("check2", testSymbolStringListAPI + "  " + testSymbolStringUpdateAPI);



            }

            String priceString = oneCoinJsonWithInfo.getJSONObject("USD").getString("PRICE");
            String changeString = oneCoinJsonWithInfo.getJSONObject("USD").getString("CHANGEPCTDAY");

            cryptoArrayList2.get(saveStartPlaceInt).setPriceUsd(priceString);
            cryptoArrayList2.get(saveStartPlaceInt).setChangeDay(changeString);
            saveStartPlaceInt++;
        }

        setCryptoAdapter();

    }

    public void setCryptoAdapter() {
        ArrayList<CryptoCoinData2> searchCryptoCoinList;


        if (search) {

            searchCryptoCoinList = new ArrayList<>();


            for (int i = 0; i < cryptoArrayList.size(); i++) {
                CryptoCoinData2 cryptoCoinData = cryptoArrayList2.get(i);
                if (cryptoCoinData.getCoinName().matches("(?i:.*" + query + ".*)")) {
                    searchCryptoCoinList.add(cryptoCoinData);
                }
                else if (cryptoCoinData.getSymbol().matches("(?i:.*" + query + ".*)")) {
                    searchCryptoCoinList.add(cryptoCoinData);
                }
            }

            cryptoAdapter = new CryptoAdapter(getActivity(), searchCryptoCoinList);

        }
        else {


//                Log.d("moresucces", cryptoArrayList.get(0).getCoinName());

            cryptoAdapter = new CryptoAdapter(getActivity(), cryptoArrayList2);
        }
        CryptoFragment.this.setListAdapter(cryptoAdapter);
//                Log.d("cryptoadapter", cryptoAdapter.toString());





//        cryptoAdapter = new CryptoAdapter(getActivity(), cryptoArrayList2);
//        CryptoFragment.this.setListAdapter(cryptoAdapter);
    }
}
