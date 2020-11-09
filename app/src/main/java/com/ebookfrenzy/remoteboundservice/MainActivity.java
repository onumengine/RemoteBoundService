package com.ebookfrenzy.remoteboundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    Messenger myService = null;
    boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        triggerRemoteService();
    }

    private ServiceConnection myConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            myService = new Messenger(iBinder);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            myService = null;
            isBound = false;
        }
    };

    private void triggerRemoteService()
    {
        Intent remoteServiceIntent = new Intent(getApplicationContext(), RemoteService.class);
        bindService(remoteServiceIntent, myConnection, Context.BIND_AUTO_CREATE);
    }

    private void sendMessage(View view)
    {
        if (!isBound) return;

        Bundle bundle = new Bundle();
        bundle.putString("MyString", "THIS A MESSAGE RIGHT HERE, MAYNE");

        Message msg = Message.obtain();
        msg.setData(bundle);

        try {
            myService.send(msg);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }
}