package com.media.mob.demo

import com.media.mob.demo.application.DemoApplication

val density: Float by lazy {
    DemoApplication.application.resources.displayMetrics.density
}

fun Float.transformDiPixels(): Float {
    return (this * density + 0.5F)
}