package com.dev.chris.cryptonite;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chris on 4-1-2018.
 */

public class SpecificCryptoCoinData {

    // example
//    private String coinName;

    // 4real
    private String fromSymbol;
    private String toSymbol;
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



    public static SpecificCryptoCoinData fromJson(JSONObject jsonObject, String coinSymbol) {

        SpecificCryptoCoinData specificCoinData = new SpecificCryptoCoinData();
        try {
            specificCoinData.fromSymbol = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("FROMSYMBOL");
            specificCoinData.toSymbol = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("TOSYMBOL");
            specificCoinData.market = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("MARKET");
            specificCoinData.price = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("PRICE");
            specificCoinData.lastUpdate = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("LASTUPDATE");
            specificCoinData.lastVolume = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("LASTVOLUME");
            specificCoinData.lastVolumeTo = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("LASTVOLUMETO");
            specificCoinData.lastTradeId = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("LASTTRADEID");
            specificCoinData.volume24Hour = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("VOLUME24HOUR");
            specificCoinData.volume24HourTo = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("VOLUME24HOURTO");
            specificCoinData.open24Hour = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("OPEN24HOUR");
            specificCoinData.high24Hour = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("HIGH24HOUR");
            specificCoinData.low24Hour = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("LOW24HOUR");
            specificCoinData.change24Hour = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("CHANGE24HOUR");
            specificCoinData.changePct24Hour = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("CHANGEPCT24HOUR");
            specificCoinData.changeDay = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("CHANGEDAY");
            specificCoinData.changePctDay = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("CHANGEPCTDAY");
            specificCoinData.supply = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("SUPPLY");
            specificCoinData.mktCap = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("MKTCAP");
            specificCoinData.totalVolume24Hour = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("TOTALVOLUME24H");
            specificCoinData.totalVolume24Hour24HourTo = jsonObject.getJSONObject("DISPLAY").getJSONObject(coinSymbol).getJSONObject("USD").getString("TOTALVOLUME24HTO");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return specificCoinData;
    }
    // example
//    public String getCoinName() {
//        return coinName;
//    }
//    public void setCoinName(String coinName) {
//        this.coinName = coinName;
//    }

    // 4real
    public String getFromSymbol() {
        return fromSymbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public String getMarket() {
        return market;
    }

    public String getPrice() {
        return price;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getLastVolume() {
        return lastVolume;
    }

    public String getLastVolumeTo() {
        return lastVolumeTo;
    }

    public  String getLastTradeId() {
        return lastTradeId;
    }

    public String getVolume24Hour() {
        return volume24Hour;
    }

    public String getVolume24HourTo() {
        return volume24HourTo;
    }

    public String getOpen24Hour() {
        return open24Hour;
    }

    public String getHigh24Hour() {
        return high24Hour;
    }

    public String getLow24Hour() {
        return low24Hour;
    }

    public String getChange24Hour() {
        return change24Hour;
    }

    public String getChangePct24Hour() {
        return changePct24Hour;
    }

    public String getChangeDay() {
        return changeDay;
    }

    public String getChangePctDay() {
        return changePctDay;
    }

    public String getSupply() {
        return supply;
    }

    public String getMktCap() {
        return mktCap;
    }

    public String getTotalVolume24Hour() {
        return totalVolume24Hour;
    }

    public String getTotalVolume24Hour24HourTo() {
        return totalVolume24Hour24HourTo;
    }
}
