package com.dev.chris.cryptonite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Christiaan Wewer
 * 11943858
 * Get list with all coin info or search reqeust with coin info
 */

public class CoinOverviewListFragment extends ListFragment implements ResponseHandler {

    CryptoAdapter cryptoAdapter;
    ArrayList<CryptoCoinDataModel> searchCryptoCoinList;

    Boolean search;
    String query;
    static ArrayList<CryptoCoinDataModel> cryptoCoinArrayList = new ArrayList<>();

    NetworkAndMergeInfoClass networkAndMergeInfoClass = new NetworkAndMergeInfoClass();

    private FavoriteCoinDatabase favoriteCoinDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get bundle and see if there is a search request or not
        networkAndMergeInfoClass.delegate = this;
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        search = bundle.getBoolean("search");

        if (!search) {
            networkAndMergeInfoClass.networkRequest(cryptoCoinArrayList);
        }
        else {
            query = bundle.getString("query");
            setCryptoAdapter(true);
        }

        return inflater.inflate(R.layout.fragment_crypto, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshMenu:

                if (!search) {
                    cryptoCoinArrayList = new ArrayList<>();
                    networkAndMergeInfoClass.networkRequest(cryptoCoinArrayList);
                }
                else if (searchCryptoCoinList != null) {
                    networkAndMergeInfoClass.
                            generateLongestPossibleUrlAlgorithm(searchCryptoCoinList, 0);
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());
        getListView().setOnItemLongClickListener(new onListItemLongClick());

    }

    public class onListItemLongClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            ArrayList<CryptoCoinDataModel> favoriteCryptoCoinList;

            // add coin to favorites
            if (search) {
                favoriteCryptoCoinList = searchCryptoCoinList;
            }
            else {
                favoriteCryptoCoinList = cryptoCoinArrayList;
            }

            int coinRank = favoriteCryptoCoinList.get(position).getRank();
            String coinSymbol = favoriteCryptoCoinList.get(position).getSymbol();
            String coinName = favoriteCryptoCoinList.get(position).getCoinName();
            if (favoriteCoinDatabase.addCoinRankSymbolName(coinRank, coinSymbol, coinName)) {
                Toast.makeText(getActivity(), "Coin added to favorites", Toast.LENGTH_LONG)
                        .show();
            }
            return true;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchBarMenuItem = menu.findItem(R.id.appBarSearch);

        if (search) {
            searchBarMenuItem.setVisible(false);
        }
        else {
            searchBarMenuItem.setVisible(true);
        }
    }

    @Override
    public void NetworkHandler(ArrayList<CryptoCoinDataModel> networkCoinArrayList) {
        cryptoCoinArrayList = networkCoinArrayList;
        setCryptoAdapter(false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ArrayList<CryptoCoinDataModel> ArrayListToExtraData;

        // go to specific coin info
        if (search) {
            ArrayListToExtraData = searchCryptoCoinList;
        }
        else {
            ArrayListToExtraData = cryptoCoinArrayList;
        }

        if (ArrayListToExtraData.get(position).getPriceUsd() != null) {
            String symbolString = ArrayListToExtraData.get(position).getSymbol();
            String nameString = ArrayListToExtraData.get(position).getCoinName();
            Intent intent = new Intent(getActivity(), SpecificCoinInfoActivity.class);
            intent.putExtra("coinSymbolString", symbolString);
            intent.putExtra("coinName", nameString);
            startActivity(intent);
        }
    }

    public void setCryptoAdapter(boolean searchBoolean) {
        if (searchBoolean) {
            searchCryptoCoinList = new ArrayList<>();

            for (int i = 0; i < cryptoCoinArrayList.size(); i++) {
                CryptoCoinDataModel cryptoCoinData = cryptoCoinArrayList.get(i);
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
            cryptoAdapter = new CryptoAdapter(getActivity(), cryptoCoinArrayList);
        }

        CoinOverviewListFragment.this.setListAdapter(cryptoAdapter);
    }
}