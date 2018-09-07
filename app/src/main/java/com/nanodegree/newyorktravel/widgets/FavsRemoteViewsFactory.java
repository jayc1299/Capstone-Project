package com.nanodegree.newyorktravel.widgets;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.activities.AttractionDetail;
import com.nanodegree.newyorktravel.holders.FavouriteAttraction;
import com.nanodegree.newyorktravel.utils.UserUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;


public class FavsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = FavsRemoteViewsFactory.class.getSimpleName();

    private Context context;
    private ArrayList<FavouriteAttraction> attractions;

    public FavsRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference favsRef = firebaseDatabase.getReference("favouriteAttractions");
        UserUtils userUtils = new UserUtils();

        //We need to query firebase on create, as we need to refresh the view when it's done.
        Query favsQuery = favsRef.orderByChild("userId").equalTo(userUtils.getUserId(context));
        favsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                attractions = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FavouriteAttraction favouriteAttraction = postSnapshot.getValue(FavouriteAttraction.class);
                    if (favouriteAttraction != null) {
                        favouriteAttraction.setId(postSnapshot.getKey());
                        attractions.add(favouriteAttraction);
                    }
                }

                //Now we have to notify the widget that the data has been updated.
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, FavsWidget.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.wdiget_list_view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
            }
        });
    }

    @Override
    public void onDataSetChanged() {}

    @Override
    public void onDestroy() {}

    @Override
    public int getCount() {
        if (attractions != null) {
            return attractions.size();
        }else{
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int i) {
        FavouriteAttraction favouriteAttraction = attractions.get(i);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_attraction_widget);

        try {
            int height = (int) context.getResources().getDimension(R.dimen.small_image_height);
            int width = (int) context.getResources().getDimension(R.dimen.small_image_width);
            Bitmap attractionBitmap = Picasso.get().load(favouriteAttraction.getImageUrl()).resize(width, height).get();
            views.setImageViewBitmap(R.id.item_attraction_img, attractionBitmap);
        } catch (IOException e) {
            Log.e(TAG, "Error during widget getViewAt: ", e);
        }

        views.setTextViewText(R.id.item_attraction_name, favouriteAttraction.getName());
        views.setTextViewText(R.id.item_attraction_desc, favouriteAttraction.getDescription());

        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
        Bundle extras = new Bundle();
        extras.putParcelable(AttractionDetail.TAG_ATTRACTION, favouriteAttraction);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.item_attraction_img, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}