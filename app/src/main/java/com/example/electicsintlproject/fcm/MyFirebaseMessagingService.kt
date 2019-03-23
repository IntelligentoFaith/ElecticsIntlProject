package com.example.electicsintlproject.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.example.electicsintlproject.R
import com.example.electicsintlproject.acitvities.AccountActivity
import com.example.electicsintlproject.utils.Constant
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    internal var intent: Intent? = null
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

       val data = remoteMessage!!.getData()
        val title = data.get("title")
        val body = data.get("body")

        Constant.logMessage("!!!!!!!!!!!MyFirebaseMessagingService-->data.get!!!!!!!!!!!!!!!","title= $title  \nbody = $body ")
        if (null != data && 0 < data.size)
        {
            if (data.containsKey("title" ) && data.containsKey("body"))
            {
                sendNotification(remoteMessage)
                //data.get("title"), data.get("body"))
            }
        }

        Constant.logMessage("FROM!##", "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.data.size > 0) {
            Constant.logMessage("PAYLOAD!##", "Datapayload: " + remoteMessage.getData());
        }
        else{
            Constant.logMessage("BODY!##",  "NO DATA")
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Constant.logMessage("BODY!##",  "Message Notification Body: " + remoteMessage.notification!!.body)
        }else{
            Constant.logMessage("BODY!##",  "NO PAYLOAD")
        }





    }


    private fun sendNotification(remoteMessage: RemoteMessage?) {
        intent = Intent(this, AccountActivity::class.java)
        intent!!.putExtra("title",remoteMessage!!.data?.get("title"))
        intent!!.putExtra("body", remoteMessage.data?.get("body"))
        val inboxStyle = NotificationCompat.InboxStyle()
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder = NotificationCompat.Builder(this)
        notificationBuilder.setSmallIcon(R.drawable.notification_icon_background)
        notificationBuilder.setSound(uri)
       // notificationBuilder.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        notificationBuilder.setStyle(inboxStyle)
        notificationBuilder.setContentText(remoteMessage.data.get("body"))
        notificationBuilder.setContentTitle(remoteMessage.data.get("title"))
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())

    }

}