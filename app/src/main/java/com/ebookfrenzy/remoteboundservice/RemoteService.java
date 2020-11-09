package com.ebookfrenzy.remoteboundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class RemoteService extends Service
{
    final Messenger myMessenger = new Messenger(new IncomingHandler());

    public RemoteService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return myMessenger.getBinder();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            Bundle data = msg.getData();
            String dataString = data.getString("MyString");
            Toast.makeText(getApplicationContext(), dataString, Toast.LENGTH_LONG).show();
        }
    }
}
