package com.dev.chris.cryptonite;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chris on 4-1-2018.
 */

public class CryptoCoinData2 {

    private String coinName;
    private int rank;
    private String priceUsd;
    private int dayVolumeUsd;
    private String symbol;
    private String changeDay;
    private String coinId;

    public static CryptoCoinData2 fromJson2(JSONObject jsonObject) {

        CryptoCoinData2 coinData2 = new CryptoCoinData2();
        try {

            coinData2.coinName = jsonObject.getString("CoinName");

            String symbolString = jsonObject.getString("Symbol");

//            if (symbolString.endsWith("*")) {

//            }
            coinData2.symbol = symbolString.replace("*", "");
            coinData2.rank = Integer.parseInt(jsonObject.getString("SortOrder"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return coinData2;
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

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getChangeDay() {
        return changeDay;
    }

    public void setChangeDay(String changeDay) {
        this.changeDay = changeDay;
    }

}
