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

    Boolean search;
    String query;

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
//        networkRequest2(params);
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

                    cryptoAdapter = new CryptoAdapter(getActivity(), searchCryptoCoinList);

                }
                else {


//                Log.d("moresucces", cryptoArrayList.get(0).getCoinName());

                    cryptoAdapter = new CryptoAdapter(getActivity(), cryptoArrayList);
                }
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

        int saveStartPlaceInt = startPlaceInt;
        String concatinatedUrlString = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=";

        concatinatedUrlString += "&tsyms=USD"; // in networkrequest3 doen

        int urlLenInt = 0;

        String symbolString = cryptoArrayList2.get(startPlaceInt).getSymbol();
        concatinatedUrlString += symbolString;

        if (cryptoArrayList2.get(startPlaceInt).equals(cryptoArrayList2.get(lengthCryptoArrayList))) {
            // request
            testNetworkConnect(concatinatedUrlString);

            RequestParams params = new RequestParams();
            params.put("limit", 10);
            networkRequest3(params, saveStartPlaceInt, concatinatedUrlString);

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


//                Log.d("CHECK1", "CHECK THIS");

                RequestParams params = new RequestParams();
                params.put("limit", 10);
                networkRequest3(params, saveStartPlaceInt, concatinatedUrlString);
                return;
            }
        }

        if (!cryptoArrayList2.get(startPlaceInt).equals(cryptoArrayList2.get(lengthCryptoArrayList))) {
            //request
            testNetworkConnect(concatinatedUrlString);

            RequestParams params = new RequestParams();
            params.put("limit", 10);
            networkRequest3(params, saveStartPlaceInt, concatinatedUrlString);

            startPlaceInt++;
            concatinatePriceUrl(startPlaceInt, lengthCryptoArrayList);
        }
    }


    public void testNetworkConnect(String firstUrlPart) {
        firstUrlPart += "&tsyms=USD";
        Log.d("ifthiswasanetworkconnect", firstUrlPart);
    }


    private void networkRequest3(RequestParams tries, int saveStartPlaceInt, String urlForCrypto) {
        Log.d("coins", "networkJob() called");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);

        urlForCrypto = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=42,365,404,611,808,888,1337,2015,BTC,LTC,DASH,XMR,NXT,ETC,DOGE,ZEC,BTS,XRP,BTCD,PPC,CRAIG,XBS,XPY,PRC,YBC,DANK,GIVE,KOBO,DT,CETI,SUP,XPD,GEO,CHASH,SPR,NXTI,WOLF,XDP,AC,ACOIN,AERO,ALF,AGS,AMC,ALN,APEX,ARCH,ARG,ARI,AUR,AXR,BCX&tsyms=USD";

        client.get(urlForCrypto, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("success: ", response.toString());

                try {
                    setRestOfCoinData(response.getJSONObject("DISPLAY"), saveStartPlaceInt);

//                    Log.d("response", response.getJSONObject("DISPLAY").getJSONObject("USD").toString());
                } catch (JSONException e) {
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

            Log.d("iteration", oneCoinJsonWithInfo.getJSONObject("USD").toString());

            String priceString = oneCoinJsonWithInfo.getJSONObject("USD").getString("PRICE");
            String changeString = oneCoinJsonWithInfo.getJSONObject("USD").getString("CHANGEPCTDAY");

            cryptoArrayList2.get(saveStartPlaceInt - 1).setPriceUsd(priceString);
            cryptoArrayList2.get(saveStartPlaceInt - 1).setChangeDay(changeString);

            saveStartPlaceInt++;
        }
    }
}
