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
import java.util.Comparator;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class CryptoFragment extends ListFragment {

    CryptoAdapter cryptoAdapter;
    String url = "https://api.coinmarketcap.com/v1/ticker/?limit=100";
    String urlCryptoCompare = "https://min-api.cryptocompare.com/data/all/coinlist";
    int maxApiUrlLenInt = 280;

    ArrayList<CryptoCoinData2> searchCryptoCoinList;


    ArrayList<CryptoCoinData> cryptoArrayList = new ArrayList<>();
    Boolean search;
    String query;
    Timer timer;
    static ArrayList<CryptoCoinData2> cryptoArrayList2 = new ArrayList<>();

    //    ListView cryptoList;
    private FavoriteCoinDatabase favoriteCoinDatabase;

    APIHelper apiHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        Log.d("open fragment", "yes!");

        timer = new Timer();
        apiHelper = new APIHelper();
        View rootView = inflater.inflate(R.layout.fragment_crypto, container, false);

        Bundle bundle = this.getArguments();
        search = bundle.getBoolean("search");
        if (!search) {
            RequestParams params = new RequestParams();
            params.put("limit", 100);
            networkRequest(params);
        }
        else {

            query = bundle.getString("query");
            setCryptoAdapter(true);
        }
        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        getListView().setOnItemLongClickListener((av, v, position, id) -> {
            ArrayList<CryptoCoinData2> favoriteCryptoCoinList;

            if (search) {

                 favoriteCryptoCoinList = searchCryptoCoinList;
            }

            else {
                favoriteCryptoCoinList = cryptoArrayList2;
            }

            int coinRank = favoriteCryptoCoinList.get(position).getRank();
            String coinSymbol = favoriteCryptoCoinList.get(position).getSymbol();
            String coinName = favoriteCryptoCoinList.get(position).getCoinName();
            favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());

            Log.d("rankSymbolName", Integer.toString(coinRank) + "  " + coinSymbol + "  " + coinName);
            favoriteCoinDatabase.addCoinRankSymbolName(coinRank, coinSymbol, coinName);
            return true;
        });
    }

    private void networkRequest(RequestParams tries) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        client.get(url, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("success: ", response.toString());



                cryptoArrayList2 = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    CryptoCoinData2 aCoin = CryptoCoinData2.fromJson2(response, i);
                    cryptoArrayList2.add(aCoin);
                }
                concatinatePriceUrl(0);
                setCryptoAdapter(false);

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
        if (symbolString != null) {
            Intent intent = new Intent(getActivity(), SpecificCoinInfoActivity.class);
            intent.putExtra("coinSymbolString", symbolString);
            startActivity(intent);
        }
    }



    private void networkRequest2(RequestParams tries) {
        Log.d("coins2", "networkJob() called");
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
//                cryptoArrayList2 = new ArrayList<>();
//
//                JSONObject dataJsonObject;
//                try {
//                    dataJsonObject = response.getJSONObject("Data");
//
////                    String otherSpecificCoinDataUrlString = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=";
//
//                    for (Iterator key = dataJsonObject.keys(); key.hasNext();) {
//
//                        JSONObject oneCoinJsonWithInfo = (JSONObject) dataJsonObject.get(key.next().toString());
//
//                        CryptoCoinData2 aCoin2 = CryptoCoinData2.fromJson2(oneCoinJsonWithInfo);
//
//                        cryptoArrayList2.add(aCoin2);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

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

        if (!(endPlaceInt < cryptoArrayList2.size())) {

            return;
        }
        concatinatePriceUrl(endPlaceInt);
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

                    cryptoArrayList2 = apiHelper.setRestOfCoinData(response, cryptoArrayList2, saveStartPlaceInt);
                    setCryptoAdapter(false);
//                    setRestOfCoinData(response.getJSONObject("RAW"), saveStartPlaceInt);
//
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


    public void setCryptoAdapter(boolean searchBoolean) {

        if (searchBoolean) {

            searchCryptoCoinList = new ArrayList<>();


            for (int i = 0; i < cryptoArrayList2.size(); i++) {
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

            cryptoAdapter = new CryptoAdapter(getActivity(), cryptoArrayList2);
        }
        CryptoFragment.this.setListAdapter(cryptoAdapter);
    }
}
