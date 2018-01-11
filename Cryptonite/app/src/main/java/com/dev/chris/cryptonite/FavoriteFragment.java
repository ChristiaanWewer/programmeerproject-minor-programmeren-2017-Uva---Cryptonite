package com.dev.chris.cryptonite;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import com.loopj.android.http.*;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;


public class FavoriteFragment extends ListFragment {

    CryptoAdapter cryptoAdapter;
    String url = "https://api.coinmarketcap.com/v1/ticker/";
    ArrayList<CryptoCoinData> favoriteCryptoCoins;
    FavoriteCoinDatabase favoriteCoinDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());
        Cursor idCursor = favoriteCoinDatabase.selectId();
        while (idCursor.moveToNext()) {
            String idString = idCursor.getString(idCursor.getColumnIndex(FavoriteCoinDatabase.COL1));

            RequestParams params = new RequestParams();
            params.put("limit", 10);
            networkRequest(params, idString);

        }

        return inflater.inflate(R.layout.fragment_favorite, container, false);

    }


    private void networkRequest(RequestParams tries, final String idString) {
        Log.d("coins", "networkJob() called");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        url += idString;
        client.get(url, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("success: ", response.toString());
                favoriteCryptoCoins = new ArrayList<>();

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



}
