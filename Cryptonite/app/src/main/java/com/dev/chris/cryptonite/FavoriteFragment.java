package com.dev.chris.cryptonite;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.json.JSONArray;
import com.loopj.android.http.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;


public class FavoriteFragment extends ListFragment {

    CryptoAdapter cryptoAdapter;
    String url = "https://api.coinmarketcap.com/v1/ticker/";
    ArrayList<CryptoCoinData> favoriteCryptoCoins;
    FavoriteCoinDatabase favoriteCoinDatabase;

    View rootView;

    Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        favoriteCryptoCoins = new ArrayList<>();

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


                cryptoAdapter = new CryptoAdapter(getActivity(), favoriteCryptoCoins);
                FavoriteFragment.this.setListAdapter(cryptoAdapter);
                return true;
            }
        });

    }


    private void networkRequest(RequestParams tries, final String idString) {
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

                CryptoCoinData aCoin = CryptoCoinData.fromJson(response, 0);
                aCoin.setCoinId(idString);
                favoriteCryptoCoins.add(aCoin);

                cryptoAdapter = new CryptoAdapter(getActivity(), favoriteCryptoCoins);
                FavoriteFragment.this.setListAdapter(cryptoAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error: ", throwable.toString());
            }

        });
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

                        favoriteCryptoCoins = new ArrayList<>();

                        Cursor idCursor = favoriteCoinDatabase.selectId();

                        while (idCursor.moveToNext()) {
                            String idString = idCursor.getString(idCursor.getColumnIndex(FavoriteCoinDatabase.COL1));

                            RequestParams params = new RequestParams();
                            params.put("limit", 10);
                            networkRequest(params, idString);
                            Log.d("idString", idString);
                        }
                    }
                });
            }
        };

        timer.schedule(doAsynchronousTask, 0, 2000);
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
