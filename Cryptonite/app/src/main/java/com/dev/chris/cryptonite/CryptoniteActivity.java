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

public class CryptoniteActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptonite);

        Bundle bundle = new Bundle();
        bundle.putBoolean("search", false);

        Log.d("oncreate", "it works");
        FragmentManager fm = getSupportFragmentManager();
        CryptoFragment cryptoFragment = new CryptoFragment();
        Log.d("fragment", cryptoFragment.toString());
        cryptoFragment.setArguments(bundle);
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.crypto_fragment_container, cryptoFragment);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.appBarSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("search", true);
                bundle.putString("query", query);
                CryptoFragment cryptoFragment = new CryptoFragment();
                cryptoFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.crypto_fragment_container, cryptoFragment,"search")
                        .addToBackStack(null)
                        .commit();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       Log.d("works", "works");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.addToBackStack(null);

        switch (item.getItemId()) {
           case R.id.goToFavoriteMenu:

               FavoriteFragment favoriteFragment = new FavoriteFragment();
               ft.replace(R.id.crypto_fragment_container, favoriteFragment);
               ft.commit();

               break;

        }

       return super.onOptionsItemSelected(item);
    }
}
