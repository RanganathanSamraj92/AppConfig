package dev.app.baseappconfig.base

import android.util.Log

object AppUtils{

    open fun makeLog(msg: String) {
        Log.w("base", msg)
    }
}