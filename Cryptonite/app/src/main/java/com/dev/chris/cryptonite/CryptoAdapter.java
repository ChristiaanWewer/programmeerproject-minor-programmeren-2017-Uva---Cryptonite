package com.dev.chris.cryptonite;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chris on 3-1-2018.
 */

public class CryptoAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<CryptoCoinData> cryptoCoinData;

    public CryptoAdapter(Context context, ArrayList<CryptoCoinData> cryptoCoinData) {
        super(context, R.layout.cryptocoin_row, cryptoCoinData);

        this.cryptoCoinData = cryptoCoinData;
        this.context = context;
        Log.d("check if exists", cryptoCoinData.get(0).getCoinName());

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cryptocoin_row, parent, false);

        TextView numberRowTextView = view.findViewById(R.id.numberRowTextView);
        TextView nameRowTextView = view.findViewById(R.id.nameRowTextView);
        TextView priceRowTextView = view.findViewById(R.id.priceRowTextView);
        TextView priceChangeRowTextView = view.findViewById(R.id.priceChangeRowTextView);

        numberRowTextView.setText(String.valueOf(cryptoCoinData.get(position).getRank()));
        nameRowTextView.setText(cryptoCoinData.get(position).getCoinName());
        priceRowTextView.setText(Double.toString(cryptoCoinData.get(position).getPriceUsd()));
        priceChangeRowTextView.setText(Double.toString(cryptoCoinData.get(position).getChange24h()));

//        Log.d("adapter", "adapter is getting used");

        return view;
    }

}
