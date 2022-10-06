package com.trifonovkv.production

import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import java.text.DecimalFormat
import java.util.*

fun TextView.setRubles(kopecks: Int, isHaveKopecks: Boolean, isRubleSign: Boolean) {
    val string = if (isHaveKopecks) {
        String.format(Locale.ROOT, "%.2f", (kopecks.toFloat() / 100))
    } else {
        (kopecks / 100).toString()
    }

    val formattedNumber = DecimalFormat("###,###").format(string.toDouble()).replace(',', ' ')

    this.text = if (isRubleSign) {
        "$formattedNumber${resources.getString(R.string.ruble_sign)}"
    } else {
        formattedNumber
    }
}

fun EditText.setRubles(kopecks: Int, isHaveKopecks: Boolean, isRubleSign: Boolean) {
    val string = if (isHaveKopecks) {
        String.format(Locale.ROOT, "%.2f", kopecks.toFloat() / 100)
    } else {
        (kopecks / 100).toString()
    }
    this.setText(
        if (isRubleSign) {
            "$string${R.string.ruble_sign}"
        } else {
            string
        }
    )
}

fun Editable?.toFloat(): Float {
    val string = this?.toString() ?: ""
    return string.toFloatOrNull() ?: 0f
}

fun Editable?.getKopecksFromRubles(): Int {
    return (this.toFloat() * 100).toInt()
}