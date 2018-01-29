package com.dev.chris.cryptonite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
public class FavoriteFragment extends ListFragment {

    CryptoAdapter favoriteAdapter;
    String url = "https://api.coinmarketcap.com/v1/ticker/";
    ArrayList<CryptoCoinData2> favoriteCryptoCoins;
    int maxApiUrlLenInt = 290;
    FavoriteCoinDatabase favoriteCoinDatabase;

    View rootView;

    Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        favoriteCryptoCoins = new ArrayList<>();
        setHasOptionsMenu(true);

        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());

        timer = new Timer();

        try {
            autoRefresher();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here

        MenuItem goToFavMenuItem = menu.findItem(R.id.goToFavoriteMenu);
        goToFavMenuItem.setVisible(false);

        MenuItem searchBarMenuItem = menu.findItem(R.id.appBarSearch);
        searchBarMenuItem.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) {

                String coinName = favoriteCryptoCoins.get(position).getCoinName();
                favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());

                favoriteCoinDatabase.removeCoin(coinName);

                for (int i = 0, n = favoriteCryptoCoins.size(); i < n; i++) {
                    if (favoriteCryptoCoins.get(i).getCoinName().equals(coinName)){
                        favoriteCryptoCoins.remove(i);
                    }
                }

//                cryptoAdapter = new CryptoAdapter(getActivity(), favoriteCryptoCoins);
//                FavoriteFragment.this.setListAdapter(cryptoAdapter);
                return true;
            }
        });
    }

    private void networkRequest(RequestParams tries, final String idString, int arrayListLocInt) {
        Log.d("coins", "networkJob() called");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        String coinUrl = url;
        coinUrl += idString;

        Log.d("url", "url");
        client.get(coinUrl, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("success: ", response.toString());

//                CryptoCoinData2 aCoin = CryptoCoinData2.fromJson(response, 0);

//                aCoin.setCoinId(idString);
//                aCoin.setCoinFavIndex(arrayListLocInt);
//                favoriteCryptoCoins.add(aCoin);

//                Collections.sort(favoriteCryptoCoins, Comparator.comparing(CryptoCoinData::getCoinFavIndex));
                favoriteCryptoCoins.sort(Comparator.comparing(CryptoCoinData2::getCoinFavIndex));


//                cryptoAdapter = new CryptoAdapter(getActivity(), favoriteCryptoCoins);
//                FavoriteFragment.this.setListAdapter(cryptoAdapter);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }

        });
    }


    private void autoRefresher() throws InterruptedException {


        favoriteCryptoCoins = new ArrayList<>();
        Cursor favCursor = favoriteCoinDatabase.selectRankSymbolName();

        while (favCursor.moveToNext()) {
            String symbolString = favCursor.getString(favCursor.getColumnIndex(FavoriteCoinDatabase.COL2));
            int rankInt = favCursor.getInt(favCursor.getColumnIndex(FavoriteCoinDatabase.COL1));
            String nameString = favCursor.getString(favCursor.getColumnIndex(FavoriteCoinDatabase.COL3));
            CryptoCoinData2 aFavCoin = new CryptoCoinData2();
            aFavCoin.setRank(rankInt);
            aFavCoin.setSymbol(symbolString);
            aFavCoin.setCoinName(nameString);
            favoriteCryptoCoins.add(aFavCoin);
        }


        concatinatePriceUrl(0);

//        final Handler handler = new Handler();
////        Timer timer = new Timer();
//
//        TimerTask doAsynchronousTask = new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        favoriteCryptoCoins = new ArrayList<>();
//                        Cursor idCursor = favoriteCoinDatabase.selectId();
//
//                        int arrayListLocInt = 0;
//                        while (idCursor.moveToNext()) {
//                            String idString = idCursor.getString(idCursor.getColumnIndex(FavoriteCoinDatabase.COL1));
//
//                            RequestParams params = new RequestParams();
//                            params.put("limit", 10);
//                            networkRequest(params, idString, arrayListLocInt);
//                            Log.d("idString", idString);
//                            arrayListLocInt++;
//                        }
//                    }
//                });
//            }
//        };
//
//        timer.schedule(doAsynchronousTask, 0, 2000);
    }


    private void concatinatePriceUrl(int startPlaceInt) {


        int endPlaceInt = startPlaceInt;

        String concatinatedUrlString = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=";

        int urlLenInt = 0;

        while (urlLenInt < maxApiUrlLenInt && (endPlaceInt < favoriteCryptoCoins.size())) {
//            Log.d("endPlaceInt, cryptoArrayList2.size()", Integer.toString(endPlaceInt) + "     " + Integer.toString(cryptoArrayList2.size()));
            String symbolString = favoriteCryptoCoins.get(endPlaceInt).getSymbol();
            concatinatedUrlString += "," + symbolString;
            endPlaceInt++;
            urlLenInt = concatinatedUrlString.length();
        }

        RequestParams params = new RequestParams();
        params.put("limit", 10);
        networkRequest3(params, startPlaceInt, concatinatedUrlString);

//        testNetworkConnect(concatinatedUrlString, startPlaceInt);


        if (!(endPlaceInt < favoriteCryptoCoins.size())) {

            return;
        }
//        RequestParams params = new RequestParams();
//        params.put("limit", 10);
//        networkRequest3(params, startPlaceInt, concatinatedUrlString);

        concatinatePriceUrl(endPlaceInt);
    }


    private void networkRequest3(RequestParams tries, int saveStartPlaceInt, String urlForCrypto) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);

        urlForCrypto += "&tsyms=USD"; // in networkrequest3 doen
        Log.d("coins", urlForCrypto);

        client.get(urlForCrypto, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("new one", response.toString());

                try {

                    Log.d("fav network3 response", response.toString());
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
            String testSymbolStringListAPI = favoriteCryptoCoins.get(saveStartPlaceInt).getSymbol();

            while (!(testSymbolStringListAPI.equals(testSymbolStringUpdateAPI))) {

                Log.d("check1", testSymbolStringListAPI + "  " + testSymbolStringUpdateAPI);
                saveStartPlaceInt++;

                if (saveStartPlaceInt > favoriteCryptoCoins.size() -1) {

                    setCryptoAdapter();
                    return;
                }


                testSymbolStringUpdateAPI = oneCoinJsonWithInfo.getJSONObject("USD").getString("FROMSYMBOL");
                testSymbolStringListAPI = favoriteCryptoCoins.get(saveStartPlaceInt).getSymbol();

                Log.d("check2", testSymbolStringListAPI + "  " + testSymbolStringUpdateAPI);



            }

            String priceString = oneCoinJsonWithInfo.getJSONObject("USD").getString("PRICE");
            String changeString = oneCoinJsonWithInfo.getJSONObject("USD").getString("CHANGEPCTDAY");

            favoriteCryptoCoins.get(saveStartPlaceInt).setPriceUsd(priceString);
            favoriteCryptoCoins.get(saveStartPlaceInt).setChangeDay(changeString);

            saveStartPlaceInt++;
        }

        setCryptoAdapter();

    }
    public void setCryptoAdapter() {



//                Log.d("moresucces", cryptoArrayList.get(0).getCoinName());

        favoriteAdapter = new CryptoAdapter(getActivity(), favoriteCryptoCoins);

        FavoriteFragment.this.setListAdapter(favoriteAdapter);
//                Log.d("cryptoadapter", cryptoAdapter.toString());





//        cryptoAdapter = new CryptoAdapter(getActivity(), cryptoArrayList2);
//        CryptoFragment.this.setListAdapter(cryptoAdapter);
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
}
