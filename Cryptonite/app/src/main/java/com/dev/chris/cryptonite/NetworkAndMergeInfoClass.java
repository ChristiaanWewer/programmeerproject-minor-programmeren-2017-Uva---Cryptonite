package com.dev.chris.cryptonite;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

/**
 * Christiaan Wewer
 * 11943858
 * Network and coin info merger class for CoinOverviewListFragment and FavoriteCoinListFragment
 */

class NetworkAndMergeInfoClass {

    ResponseHandler delegate;

    private int maxApiUrlLenInt = 280;
    private String url = "https://api.coinmarketcap.com/v1/ticker/?limit=100";
    private String displayString = "DISPLAY";
    private String fiatCurrencyString = "USD";

    void networkRequest(ArrayList<CryptoCoinDataModel> cryptoCoinArrayList) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                // make ArrayList with basic info coins (rank, symbol, name)
                for (int i = 0; i < response.length(); i++) {
                    CryptoCoinDataModel aCoin = CryptoCoinDataModel.fromJson(response, i);
                    cryptoCoinArrayList.add(aCoin);
                }

                generateLongestPossibleUrlAlgorithm(cryptoCoinArrayList, 0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("error: ", throwable.toString());
            }
        });
    }

    void generateLongestPossibleUrlAlgorithm(ArrayList<CryptoCoinDataModel> cryptoArrayList, int startPlaceInt) {

        int lastPlaceInt = startPlaceInt;
        String concatinatedUrlString = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=";
        int urlLenInt = 0;

        // generate URL from basic coin ArrayList
        while (urlLenInt < maxApiUrlLenInt && (lastPlaceInt < cryptoArrayList.size())) {
            String symbolString = cryptoArrayList.get(lastPlaceInt).getSymbol();
            concatinatedUrlString += "," + symbolString;
            lastPlaceInt++;
            urlLenInt = concatinatedUrlString.length();
        }

        // start network request with created URL, quit if all coins are done and start over again
        networkRequestSpecificInfo(cryptoArrayList, startPlaceInt, concatinatedUrlString);

        if (!(lastPlaceInt < cryptoArrayList.size())) {
            return;
        }

        generateLongestPossibleUrlAlgorithm(cryptoArrayList, lastPlaceInt);
    }

    private void networkRequestSpecificInfo(ArrayList<CryptoCoinDataModel> cryptoCoinArrayList, int saveStartPlaceInt, String urlForCrypto) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        urlForCrypto += "&tsyms=USD";
        client.get(urlForCrypto, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("Start", "started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    setSpecificCoinDataAlgorithm(cryptoCoinArrayList, response, saveStartPlaceInt);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("error: ", throwable.toString());
            }
        });
    }

    private void setSpecificCoinDataAlgorithm(ArrayList<CryptoCoinDataModel> cryptoCoinArrayList, JSONObject httpRequestResponse, int saveStartPlaceInt) throws JSONException {

        JSONObject jsonObjectForKey = httpRequestResponse.getJSONObject("RAW");

        // get info for listview from JSON
        for (Iterator key = jsonObjectForKey.keys(); key.hasNext();) {
            String keyNext = key.next().toString();
            JSONObject oneRawCoinJsonObject = (JSONObject) jsonObjectForKey.get(keyNext);
            JSONObject oneDisplayCoinJsonObject = (JSONObject) httpRequestResponse.getJSONObject(displayString).get(keyNext);
            String testSymbolStringUpdateAPI = oneRawCoinJsonObject.getJSONObject(fiatCurrencyString).getString("FROMSYMBOL");
            String testSymbolStringListAPI = cryptoCoinArrayList.get(saveStartPlaceInt).getSymbol();

            // check if coins match and if not, try next one
            while (!(testSymbolStringListAPI.equals(testSymbolStringUpdateAPI))) {

                saveStartPlaceInt++;

                if (saveStartPlaceInt > cryptoCoinArrayList.size() -1) {

                    delegate.NetworkHandler(cryptoCoinArrayList);
                }

                testSymbolStringUpdateAPI = oneRawCoinJsonObject.getJSONObject(fiatCurrencyString).getString("FROMSYMBOL");
                testSymbolStringListAPI = cryptoCoinArrayList.get(saveStartPlaceInt).getSymbol();
            }

            // set further coin info
            String priceString = oneDisplayCoinJsonObject.getJSONObject(fiatCurrencyString).getString("PRICE");
            String changeString = oneDisplayCoinJsonObject.getJSONObject(fiatCurrencyString).getString("CHANGEPCTDAY");
            cryptoCoinArrayList.get(saveStartPlaceInt).setPriceUsd(priceString);
            cryptoCoinArrayList.get(saveStartPlaceInt).setChangeDay(changeString);
            saveStartPlaceInt++;
        }

        delegate.NetworkHandler(cryptoCoinArrayList);
    }

}
