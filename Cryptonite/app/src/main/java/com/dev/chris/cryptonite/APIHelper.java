package com.dev.chris.cryptonite;

import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by chris on 29-1-2018.
 */

public class APIHelper {

    public ArrayList<CryptoCoinData2> setRestOfCoinData(JSONObject httpRequestResponse, ArrayList<CryptoCoinData2> cryptoArrayList2, int saveStartPlaceInt) throws JSONException {
//        ArrayList<CryptoCoinData2> cryptoArrayList2 = new ArrayList<>();

        Log.d("httpRequestResponse in apihelper setRestOfCoinData", httpRequestResponse.toString());

        JSONObject jsonObjectForKey = httpRequestResponse.getJSONObject("RAW");

        for (Iterator key = jsonObjectForKey.keys(); key.hasNext();) {

            String keyNext = key.next().toString();
            Log.d("keyNext", keyNext);
            JSONObject oneRawCoinJsonObject = (JSONObject) jsonObjectForKey.get(keyNext);
            JSONObject oneDisplayCoinJsonObject = (JSONObject) httpRequestResponse.getJSONObject("DISPLAY").get(keyNext);
//            Log.d("iteration", Integer.toString(saveStartPlaceInt) + ", " +cryptoArrayList2.get(saveStartPlaceInt).getSymbol() + ", " + oneCoinJsonWithInfo.getJSONObject("USD").getString("FROMSYMBOL"));


            String testSymbolStringUpdateAPI = oneRawCoinJsonObject.getJSONObject("USD").getString("FROMSYMBOL");
            String testSymbolStringListAPI = cryptoArrayList2.get(saveStartPlaceInt).getSymbol();

            while (!(testSymbolStringListAPI.equals(testSymbolStringUpdateAPI))) {

                Log.d("check1", testSymbolStringListAPI + "  " + testSymbolStringUpdateAPI);
                saveStartPlaceInt++;

                if (saveStartPlaceInt > cryptoArrayList2.size() -1) {

                    return cryptoArrayList2;
                }

                testSymbolStringUpdateAPI = oneRawCoinJsonObject.getJSONObject("USD").getString("FROMSYMBOL");
                testSymbolStringListAPI = cryptoArrayList2.get(saveStartPlaceInt).getSymbol();

                Log.d("check2", testSymbolStringListAPI + "  " + testSymbolStringUpdateAPI);

            }

            String priceString = oneDisplayCoinJsonObject.getJSONObject("USD").getString("PRICE");
            String changeString = oneDisplayCoinJsonObject.getJSONObject("USD").getString("CHANGEPCTDAY");

            cryptoArrayList2.get(saveStartPlaceInt).setPriceUsd(priceString);
            cryptoArrayList2.get(saveStartPlaceInt).setChangeDay(changeString);
            saveStartPlaceInt++;
        }

        return cryptoArrayList2;

    }

}
