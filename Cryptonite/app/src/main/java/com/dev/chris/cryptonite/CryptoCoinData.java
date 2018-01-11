package com.dev.chris.cryptonite;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by chris on 4-1-2018.
 */

public class CryptoCoinData {

    private String coinName;
    private int rank;
    private double priceUsd;
    private int dayVolumeUsd;
    private int marketCapUsd;
    private float change24h;
    private String coinId;

    public static CryptoCoinData fromJson(JSONArray jsonArray, int coinJsonObjectIndex) {

        CryptoCoinData coinData = new CryptoCoinData();
        try {
            coinData.coinName = jsonArray.getJSONObject(coinJsonObjectIndex).getString("name");
            coinData.rank = jsonArray.getJSONObject(coinJsonObjectIndex).getInt("rank");
            coinData.priceUsd = jsonArray.getJSONObject(coinJsonObjectIndex).getDouble("price_usd");;
            coinData.dayVolumeUsd = jsonArray.getJSONObject(coinJsonObjectIndex).getInt("24h_volume_usd");
            coinData.marketCapUsd = jsonArray.getJSONObject(coinJsonObjectIndex).getInt("market_cap_usd");
            coinData.change24h = jsonArray.getJSONObject(coinJsonObjectIndex).getInt("percent_change_24h");
            coinData.coinId = jsonArray.getJSONObject(coinJsonObjectIndex).getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return coinData;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public int getDayVolumeUsd() {
        return dayVolumeUsd;
    }

    public float getChange24h() {
        return change24h;
    }

    public void setDayVolumeUsd(int dayVolumeUsd) {
        this.dayVolumeUsd = dayVolumeUsd;
    }

    public int getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(int marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

}
