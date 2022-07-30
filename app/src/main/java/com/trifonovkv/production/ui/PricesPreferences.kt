package com.trifonovkv.production.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

data class Prices(
    val shift: Int,
    val adry: Int,
    val afresh: Int,
    val afrost: Int,
    val afruit: Int,
    val alco: Int,
    val amez: Int,
    val holod3: Int,
)

class PricesPreferences(context: Context) {

    private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor: SharedPreferences.Editor = sharedPrefs.edit()


    fun getPrices(): Prices {
        return Prices(
            sharedPrefs.getInt("shift_price", 90000),
            sharedPrefs.getInt("adry_price", 130),
            sharedPrefs.getInt("afresh_price", 190),
            sharedPrefs.getInt("afrost_price", 254),
            sharedPrefs.getInt("afruit_price", 254),
            sharedPrefs.getInt("alco_price", 150),
            sharedPrefs.getInt("amez_price", 113),
            sharedPrefs.getInt("holod3_price", 130),
        )
    }

    fun putShiftPrice(shiftPrice: Int) {
        editor.putInt("shift_price", shiftPrice).apply()
    }

    fun putAdryPrice(adryPrice: Int) {
        editor.putInt("adry_price", adryPrice).apply()
    }

    fun putAfreshPrice(afreshPrice: Int) {
        editor.putInt("afresh_price", afreshPrice).apply()
    }

    fun putAfrostPrice(afrostPrice: Int) {
        editor.putInt("afrost_price", afrostPrice).apply()
    }

    fun putAfruitPrice(afruitPrice: Int) {
        editor.putInt("afruit_price", afruitPrice).apply()
    }

    fun putAlcoPrice(alcoPrice: Int) {
        editor.putInt("alco_price", alcoPrice).apply()
    }

    fun putAmezPrice(amezPrice: Int) {
        editor.putInt("amez_price", amezPrice).apply()
    }

    fun putHolod3Price(holod3Price: Int) {
        editor.putInt("holod3_price", holod3Price).apply()
    }
}