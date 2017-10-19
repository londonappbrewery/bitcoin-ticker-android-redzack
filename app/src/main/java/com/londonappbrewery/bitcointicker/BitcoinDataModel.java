package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 19-Oct-17.
 */

public class BitcoinDataModel {
    //TODO:create member variable
    private String mPrice;



    //TODO:create BitcoinDataModel from a json
    public static BitcoinDataModel fromJson(JSONObject jsonObject) throws JSONException {

        BitcoinDataModel bitcoinDataModel = new BitcoinDataModel();
        bitcoinDataModel.mPrice = jsonObject.getString("last");

        return bitcoinDataModel;
    }

    public String getPrice() {
        return mPrice;
    }
}
