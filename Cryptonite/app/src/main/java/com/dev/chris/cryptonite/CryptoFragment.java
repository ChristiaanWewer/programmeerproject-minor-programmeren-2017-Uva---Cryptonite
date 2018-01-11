package com.dev.chris.cryptonite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import com.loopj.android.http.*;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class CryptoFragment extends ListFragment {

    CryptoAdapter cryptoAdapter;
    String url = "https://api.coinmarketcap.com/v1/ticker/?limit=10";
    ArrayList<CryptoCoinData> cryptoArrayList = new ArrayList<>();
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

        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) {

                String coinName = cryptoArrayList.get(position).getCoinName();
                String coinId = cryptoArrayList.get(position).getCoinId();
                Log.d("COINID AND ITEM", coinId + coinName);
                favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());

                favoriteCoinDatabase.addCoinNameIdItem(coinId, coinName);

                return true;
            }
        });

    }

    private void networkRequest(RequestParams tries) {
        Log.d("coins", "networkJob() called");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);
        client.get(url, tries, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("success: ", response.toString());
                cryptoArrayList = new ArrayList<>();

                for (int i = 0, n = 5; i < n; i++) {
                    CryptoCoinData aCoin = CryptoCoinData.fromJson(response, i);
                    cryptoArrayList.add(aCoin);
                }
                Log.d("moresucces", cryptoArrayList.get(0).getCoinName());

                cryptoAdapter = new CryptoAdapter(getActivity(), cryptoArrayList);
//                cryptoList.setAdapter(cryptoAdapter);
                CryptoFragment.this.setListAdapter(cryptoAdapter);
                Log.d("cryptoadapter", cryptoAdapter.toString());
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

    }





}
