package com.dev.chris.cryptonite;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

/**
 * Christiaan Wewer
 * 11943858
 * Main activity for CoinOverviewListFragment and FavoriteCoinListFragment
 */

public class CryptoniteActivity extends AppCompatActivity {

    SearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptonite);

        // load CoinOverviewistFragment
        Bundle bundle = new Bundle();
        bundle.putBoolean("search", false);
        Log.d("oncreate", "it works");
        FragmentManager fm = getSupportFragmentManager();
        CoinOverviewListFragment coinOverviewListFragment = new CoinOverviewListFragment();
        Log.d("fragment", coinOverviewListFragment.toString());
        coinOverviewListFragment.setArguments(bundle);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.crypto_fragment_container, coinOverviewListFragment);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);

        // get search view
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.appBarSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new onSearchListener());
        return true;
    }

    public class onSearchListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {

            // load CoinOverviewListFragment as search
            Log.d("komikhierweldetweedekeerIN", "JAAAAAAAAAAAAAAAAAAA");
            Bundle bundle = new Bundle();
            bundle.putBoolean("search", true);
            bundle.putString("query", query);
            CoinOverviewListFragment searchFragment = new CoinOverviewListFragment();
            searchFragment.setArguments(bundle);

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            }

            searchView.setQuery("", false);
            searchView.onActionViewCollapsed();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.crypto_fragment_container, searchFragment,"search")
                    .addToBackStack(null)
                    .commit();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goToFavoriteMenu:
                // load favorite fragment

                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                FavoriteCoinListFragment favoriteCoinListFragment = new FavoriteCoinListFragment();
                ft.replace(R.id.crypto_fragment_container, favoriteCoinListFragment);
                ft.commit();
               break;
        }

       return super.onOptionsItemSelected(item);
    }
}
