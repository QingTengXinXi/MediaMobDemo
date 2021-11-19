package com.media.mob.demo

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.media.mob.demo.application.DemoApplication

val density: Float by lazy {
    DemoApplication.application.resources.displayMetrics.density
}

fun Float.transformDiPixels(): Float {
    return (this * density + 0.5F)
}

fun Int.transformPixels(): Float {
    return (this / density + 0.5f)
}

fun EditText.afterTextChanged(afterChangedCallback: (String) -> Unit) {

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterChangedCallback.invoke(editable?.toString() ?: "")
        }

        override fun beforeTextChanged(sequence: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(sequence: CharSequence, start: Int, before: Int, count: Int) {}
    }

    this.addTextChangedListener(textWatcher)

    this.tag = textWatcher
}