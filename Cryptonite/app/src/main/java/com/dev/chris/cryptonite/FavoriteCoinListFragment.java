package com.dev.chris.cryptonite;

import android.content.Intent;
import android.database.Cursor;
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
 * See favorites from SQL Database
 */

public class FavoriteCoinListFragment extends ListFragment implements ResponseHandler {

    CryptoAdapter favoriteAdapter;
    ArrayList<CryptoCoinDataModel> favoriteCryptoCoins;
    FavoriteCoinDatabase favoriteCoinDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getFavorites();
    }

    public void getFavorites() {

        NetworkAndMergeInfoClass networkAndMergeInfoClass = new NetworkAndMergeInfoClass();
        networkAndMergeInfoClass.delegate = this;

        favoriteCryptoCoins = new ArrayList<>();
        Cursor favCursor = favoriteCoinDatabase.selectRankSymbolName();

        while (favCursor.moveToNext()) {
            String symbolString = favCursor.getString(favCursor.getColumnIndex(FavoriteCoinDatabase.COL2));
            int rankInt = favCursor.getInt(favCursor.getColumnIndex(FavoriteCoinDatabase.COL1));
            String nameString = favCursor.getString(favCursor.getColumnIndex(FavoriteCoinDatabase.COL3));
            CryptoCoinDataModel aFavCoin = new CryptoCoinDataModel();
            aFavCoin.setRank(rankInt);
            aFavCoin.setSymbol(symbolString);
            aFavCoin.setCoinName(nameString);
            favoriteCryptoCoins.add(aFavCoin);
        }

        networkAndMergeInfoClass.generateLongestPossibleUrlAlgorithm(favoriteCryptoCoins,
                0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // disable option menu's
        MenuItem goToFavMenuItem = menu.findItem(R.id.goToFavoriteMenu);
        goToFavMenuItem.setVisible(false);
        MenuItem searchBarMenuItem = menu.findItem(R.id.appBarSearch);
        searchBarMenuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refreshMenu:
                getFavorites();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnItemLongClickListener(new onListItemLongClick());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // load SpecificiCoinInfoActivity
        String symbolString = favoriteCryptoCoins.get(position).getSymbol();
        String nameString = favoriteCryptoCoins.get(position).getCoinName();
        Intent intent = new Intent(getActivity(), SpecificCoinInfoActivity.class);
        intent.putExtra("coinSymbolString", symbolString);
        intent.putExtra("coinName", nameString);
        startActivity(intent);
    }

    public class onListItemLongClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // remove coin from ArrayList with favorite coins and database
            String coinSymbol = favoriteCryptoCoins.get(position).getSymbol();
            favoriteCoinDatabase = FavoriteCoinDatabase.getInstance(getContext());
            favoriteCoinDatabase.removeCoinBySymbol(coinSymbol);
            favoriteCryptoCoins.remove(position);
            Toast.makeText(getActivity(), "Coin removed from favorites", Toast.LENGTH_LONG).show();
            favoriteAdapter = new CryptoAdapter(getActivity(), favoriteCryptoCoins);
            FavoriteCoinListFragment.this.setListAdapter(favoriteAdapter);
            return false;
        }
    }

    @Override
    public void NetworkHandler(ArrayList<CryptoCoinDataModel> receiveFavoriteCryptoCoins) {
        favoriteCryptoCoins = receiveFavoriteCryptoCoins;
        setCryptoAdapter();
    }

    public void setCryptoAdapter() {
        favoriteAdapter = new CryptoAdapter(getActivity(), favoriteCryptoCoins);
        FavoriteCoinListFragment.this.setListAdapter(favoriteAdapter);
    }
}
