package com.nanodegree.newyorktravel.async;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkAsync extends AsyncTask<String, Void, Boolean> {

    public interface NetworkAsyncListener {
        void showProgressDialog(boolean show);
        void isInternetOk(boolean isOk);
    }

    private NetworkAsyncListener listener;

    public NetworkAsync(NetworkAsyncListener networkListener) {
        this.listener = networkListener;
    }

    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.showProgressDialog(true);
        }

        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... arg0) {
        try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (listener != null) {
            listener.showProgressDialog(false);
            listener.isInternetOk(result);
        }
    }
}