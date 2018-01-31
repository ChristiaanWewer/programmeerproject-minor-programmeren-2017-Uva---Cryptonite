package com.dev.chris.cryptonite;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Christiaan Wewer
 * 11943858
 * Adapter for lists for crypto information.
 */

public class CryptoAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<CryptoCoinDataModel> cryptoCoinData;

    CryptoAdapter(Context context, ArrayList<CryptoCoinDataModel> cryptoCoinData) {
        super(context, R.layout.cryptocoin_row, cryptoCoinData);
        this.cryptoCoinData = cryptoCoinData;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = layoutInflater.inflate(R.layout.cryptocoin_row, parent, false);

        TextView numberRowTextView = rootView.findViewById(R.id.numberRowTextView);
        TextView nameRowTextView = rootView.findViewById(R.id.nameRowTextView);
        TextView priceRowTextView = rootView.findViewById(R.id.priceRowTextView);
        TextView priceChangeRowTextView = rootView.findViewById(R.id.priceChangeRowTextView);

        numberRowTextView.setText(String.valueOf(cryptoCoinData.get(position).getRank()));
        nameRowTextView.setText(cryptoCoinData.get(position).getCoinName());
        priceRowTextView.setText(cryptoCoinData.get(position).getPriceUsd());
        priceChangeRowTextView.setText(cryptoCoinData.get(position).getChangeDay());

        return rootView;
    }

}
