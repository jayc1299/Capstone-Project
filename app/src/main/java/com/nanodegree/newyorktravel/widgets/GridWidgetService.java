package com.nanodegree.newyorktravel.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavsRemoteViewsFactory(this.getApplicationContext());
    }
}