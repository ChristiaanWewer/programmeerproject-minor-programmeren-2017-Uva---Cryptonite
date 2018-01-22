package com.dev.chris.cryptonite;

import android.content.Intent;
import android.os.Bundle;
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

import cz.msebera.android.httpclient.Header;

public class CryptoFragment extends ListFragment {

    CryptoAdapter cryptoAdapter;
    String url = "https://api.coinmarketcap.com/v1/ticker/?limit=10";
    String urlCryptoCompare = "https://min-api.cryptocompare.com/data/all/coinlist";
    int maxApiUrlLenInt = 280;

    ArrayList<CryptoCoinData> cryptoArrayList = new ArrayList<>();
    ArrayList<CryptoCoinData2> cryptoArrayList2 = new ArrayList<>();


    //    ListView cryptoList;
    private FavoriteCoinDatabase favoriteCoinDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        Log.d("open fragment", "yes!");

        View rootView = inflater.inflate(R.layout.fragment_crypto, container, false);;

        RequestParams params = new RequestParams();
        params.put("limit", 10);
        networkRequest(params);
        networkRequest2(params);
        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setOnItemLongClickListener((av, v, position, id) -> {
            String coinName = cryptoArrayList.get(position).getCoinName();
            String coinId = cryptoArrayList.get(position).getCoinId();
            Log.d("COINID AND ITEM", coinId + coinName);
            favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());
            favoriteCoinDatabase.addCoinNameIdItem(coinId, coinName);
            return true;
        });

    }

    private void networkRequest(RequestParams tries) {
        Log.d("coins", "networkJob() called");
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

                for (int i = 0, n = 10; i < n; i++) {
                    CryptoCoinData aCoin = CryptoCoinData.fromJson(response, i);
                    cryptoArrayList.add(aCoin);
                }

//                Log.d("moresucces", cryptoArrayList.get(0).getCoinName());

                cryptoAdapter = new CryptoAdapter(getActivity(), cryptoArrayList);

                CryptoFragment.this.setListAdapter(cryptoAdapter);
//                Log.d("cryptoadapter", cryptoAdapter.toString());
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

        String symbolString = cryptoArrayList.get(position).getSymbol();
        Intent intent = new Intent(getActivity(), SpecificCoinInfoActivity.class);
        intent.putExtra("coinSymbolString", symbolString);
        startActivity(intent);
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

                concatinatePriceUrl(0, cryptoArrayList2.size() - 1);
//            Log.d("check coinname", cryptoArrayList2.get(0).getCoinName());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }
        });
    }


    private void concatinatePriceUrl(int startPlaceInt, int lengthCryptoArrayList) {

        String concatinatedUrlString = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=";

//        concatinatedUrlString += "&tsyms=USD"; // in networkrequest3 doen

        int urlLenInt = 0;

        String symbolString = cryptoArrayList2.get(startPlaceInt).getSymbol();
        concatinatedUrlString += symbolString;

        if (cryptoArrayList2.get(startPlaceInt).equals(cryptoArrayList2.get(lengthCryptoArrayList))) {
            // request
            testNetworkConnect(concatinatedUrlString);
            return;
        }

        while (urlLenInt < maxApiUrlLenInt) {

            startPlaceInt++;

            urlLenInt = concatinatedUrlString.length();

            symbolString = cryptoArrayList2.get(startPlaceInt).getSymbol();

            concatinatedUrlString += "," + symbolString;

            if (cryptoArrayList2.get(startPlaceInt).equals(cryptoArrayList2.get(lengthCryptoArrayList))) {
                // request
                testNetworkConnect(concatinatedUrlString);
                return;
            }
        }


        if (!cryptoArrayList2.get(startPlaceInt).equals(cryptoArrayList2.get(lengthCryptoArrayList))) {
            //request
            testNetworkConnect(concatinatedUrlString);

            startPlaceInt++;
            concatinatePriceUrl(startPlaceInt, lengthCryptoArrayList);
        }
    }


    public void testNetworkConnect(String firstUrlPart) {
        firstUrlPart += "&tsyms=USD";
        Log.d("ifthiswasanetworkconnect", firstUrlPart);
    }

}
