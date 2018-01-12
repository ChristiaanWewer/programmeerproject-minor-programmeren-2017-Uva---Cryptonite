package com.dev.chris.cryptonite;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CryptoniteActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptonite);

        Log.d("oncreate", "it works");
        FragmentManager fm = getSupportFragmentManager();
        CryptoFragment cryptoFragment = new CryptoFragment();
        Log.d("fragment", cryptoFragment.toString());
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.crypto_fragment_container, cryptoFragment);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
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
           case R.id.goToListenerMenu:
               ListenerFragment listenerFragment = new ListenerFragment();
               ft.replace(R.id.crypto_fragment_container, listenerFragment);
               ft.commit();

        }
       return super.onOptionsItemSelected(item);
    }
}
