package com.dev.chris.cryptonite;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Christiaan Wewer
 * 11943858
 * Activity for fragments SpecificCoinInfoFragment and SpecificCoinGraphFragment
 */

public class SpecificCoinInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load SpecificCoinInfoFragment
        setContentView(R.layout.activity_specific_coin_info);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SpecificCoinInfoFragment specificCoinInfoFragment = new SpecificCoinInfoFragment();
        ft.replace(R.id.specific_crypto_fragment_container, specificCoinInfoFragment);
        ft.commit();
    }
}
