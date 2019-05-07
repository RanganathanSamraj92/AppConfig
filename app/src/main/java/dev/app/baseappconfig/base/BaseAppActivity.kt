package dev.app.baseappconfig.base

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseAppActivity : AppCompatActivity() {

    open fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}
