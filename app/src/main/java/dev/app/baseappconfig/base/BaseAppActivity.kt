package dev.app.baseappconfig.base

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Vibrator
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import android.text.method.TextKeyListener.clear
import android.content.Intent
import android.os.Bundle
import java.io.Serializable
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import dev.app.baseappconfig.R




open class BaseAppActivity : AppCompatActivity() {

    open fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun makeLog(key: String, value: String) {
        Log.w(key, value)
    }

    open fun capitalize(s: String): String {
        if (s.isEmpty())
            return s
        val sb = StringBuilder(s)
        sb.setCharAt(0, Character.toUpperCase(sb[0]))
        return sb.toString()
    }

    open fun hideSoftKey(mCon: Context) {
        val activity = mCon as AppCompatActivity
        if (activity.currentFocus != null) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    open fun isNetworkAvailable(mCon: Context): Boolean {
        val cm = mCon.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null
    }

    open fun vibrate(mCon: Context) {
        val v = mCon.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Vibrate for 500 milliseconds
        v.vibrate(40)
    }

    private lateinit var  bundle: Bundle

    open fun startActivityputExtra(mCon: Context, cls: Class<*>, key: String, o: Any) {
        try {
            val intent = Intent(mCon, cls)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                    Intent.FLAG_ACTIVITY_NEW_TASK


            bundle.clear()
            bundle.putSerializable(key, o as Serializable)
            intent.putExtras(bundle)
            mCon.startActivity(intent)
        } catch (e: Exception) {
            showToast(mCon, e.message.toString())
        }

    }

    open fun showSmallNotification(
        mCon: Context, mBuilder: NotificationCompat.Builder,
        icon: Int, title: String,
        message: String,
        resultPendingIntent: PendingIntent,
        alarmSound: Uri, autoCancel: Boolean,
        notification_id: Int
    ) {

        val inboxStyle = NotificationCompat.InboxStyle()

        inboxStyle.addLine(message)

        val notification: Notification
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(resultPendingIntent)
            .setSound(alarmSound)
            .setStyle(inboxStyle)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setLargeIcon(BitmapFactory.decodeResource(mCon.resources, icon))
            .setContentText(message)
            .setAutoCancel(autoCancel)
            .build()
        if (!autoCancel) {
            notification.flags = Notification.FLAG_ONGOING_EVENT
        }


        val notificationManager = mCon.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notification_id, notification)
    }

    open interface AppOkInter {
        open  fun onOk()
    }


    open fun attentionDialog(mCon: Context, appOkInter: AppOkInter, msg: String) {
        val activity = mCon as AppCompatActivity
        activity.runOnUiThread {
            val alertDialogBuilder = AlertDialog.Builder(mCon)
            alertDialogBuilder.setIcon(ContextCompat.getDrawable(mCon, R.mipmap.ic_launcher))
            alertDialogBuilder.setTitle(mCon.getResources().getString(R.string.app_name))
            alertDialogBuilder.setMessage(msg)
            alertDialogBuilder.setPositiveButton("ok",
                DialogInterface.OnClickListener { arg0, arg1 -> appOkInter.onOk() })

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
            alertDialog.setCancelable(false)
        }
    }



}
