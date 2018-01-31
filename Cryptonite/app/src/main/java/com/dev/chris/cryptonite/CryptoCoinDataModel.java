package com.dev.chris.cryptonite;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Christiaan Wewer
 * 11943858
 * Model to save all crypto coin data in used for lists.
 */

class CryptoCoinDataModel {

    private String coinName;
    private int rank;
    private String priceUsd;
    private String symbol;
    private String changeDay;

    static CryptoCoinDataModel fromJson(JSONArray jsonArray, int coinJsonObjectIndexInt) {
        CryptoCoinDataModel coinData2 = new CryptoCoinDataModel();

        try {
            coinData2.rank = jsonArray.getJSONObject(coinJsonObjectIndexInt).getInt("rank");
            coinData2.symbol = jsonArray.getJSONObject(coinJsonObjectIndexInt).getString("symbol");
            coinData2.coinName = jsonArray.getJSONObject(coinJsonObjectIndexInt).getString("name");
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return coinData2;
    }

    String getCoinName() {
        return coinName;
    }

    void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    int getRank() {
        return rank;
    }

    void setRank(int rank) {
        this.rank = rank;
    }

    String getPriceUsd() {
        return priceUsd;
    }

    void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    String getSymbol() {
        return symbol;
    }

    void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    String getChangeDay() {
        return changeDay;
    }

    void setChangeDay(String changeDay) {
        this.changeDay = changeDay;
    }

}
