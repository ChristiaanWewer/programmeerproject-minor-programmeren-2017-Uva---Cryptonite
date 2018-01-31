package com.dev.chris.cryptonite;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Christiaan Wewer
 * 11943858
 * Model to save all specific crypto coin data in.
 */

class SpecificCryptoCoinDataModel {

    private String market;
    private String price;
    private String lastUpdate;
    private String lastVolume;
    private String lastVolumeTo;
    private String lastTradeId;
    private String volume24Hour;
    private String volume24HourTo;
    private String open24Hour;
    private String high24Hour;
    private String low24Hour;
    private String change24Hour;
    private String changePct24Hour;
    private String changeDay;
    private String changePctDay;
    private String supply;
    private String mktCap;
    private String totalVolume24Hour;
    private String totalVolume24Hour24HourTo;

    private static String displayString = "DISPLAY";
    private static String fiatCurrencyString = "USD";

    static SpecificCryptoCoinDataModel fromJson(JSONObject jsonObject, String coinSymbol) {

        SpecificCryptoCoinDataModel specificCoinData = new SpecificCryptoCoinDataModel();

        try {

            specificCoinData.market = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("MARKET");
            specificCoinData.price = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("PRICE");
            specificCoinData.lastUpdate = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("LASTUPDATE");
            specificCoinData.lastVolume = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("LASTVOLUME");
            specificCoinData.lastVolumeTo = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("LASTVOLUMETO");
            specificCoinData.lastTradeId = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("LASTTRADEID");
            specificCoinData.volume24Hour = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("VOLUME24HOUR");
            specificCoinData.volume24HourTo = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("VOLUME24HOURTO");
            specificCoinData.open24Hour = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("OPEN24HOUR");
            specificCoinData.high24Hour = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("HIGH24HOUR");
            specificCoinData.low24Hour = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("LOW24HOUR");
            specificCoinData.change24Hour = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("CHANGE24HOUR");
            specificCoinData.changePct24Hour = "% " + jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("CHANGEPCT24HOUR");
            specificCoinData.changeDay = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("CHANGEDAY");
            specificCoinData.changePctDay = "% " + jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("CHANGEPCTDAY");
            specificCoinData.supply = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("SUPPLY");
            specificCoinData.mktCap = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("MKTCAP");
            specificCoinData.totalVolume24Hour = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("TOTALVOLUME24H");
            specificCoinData.totalVolume24Hour24HourTo = jsonObject.getJSONObject(displayString)
                    .getJSONObject(coinSymbol).getJSONObject(fiatCurrencyString)
                    .getString("TOTALVOLUME24HTO");
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return specificCoinData;
    }

    String getMarket() {
        return market;
    }

    String getPrice() {
        return price;
    }

    String getLastUpdate() {
        return lastUpdate;
    }

    String getLastVolume() {
        return lastVolume;
    }

    String getLastVolumeTo() {
        return lastVolumeTo;
    }

    String getLastTradeId() {
        return lastTradeId;
    }

    String getVolume24Hour() {
        return volume24Hour;
    }

    String getVolume24HourTo() {
        return volume24HourTo;
    }

    String getOpen24Hour() {
        return open24Hour;
    }

    String getHigh24Hour() {
        return high24Hour;
    }

    String getLow24Hour() {
        return low24Hour;
    }

    String getChange24Hour() {
        return change24Hour;
    }

    String getChangePct24Hour() {
        return changePct24Hour;
    }

    String getChangeDay() {
        return changeDay;
    }

    String getChangePctDay() {
        return changePctDay;
    }

    String getSupply() {
        return supply;
    }

    String getMktCap() {
        return mktCap;
    }

    String getTotalVolume24Hour() {
        return totalVolume24Hour;
    }

    String getTotalVolume24Hour24HourTo() {
        return totalVolume24Hour24HourTo;
    }
}
