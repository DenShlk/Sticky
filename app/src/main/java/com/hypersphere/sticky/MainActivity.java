package com.hypersphere.sticky;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StickerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler = findViewById(R.id.main_stickers_recycler);
        adapter = new StickerAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        PagerSnapHelper pagerHelper = new PagerSnapHelper();
        //pagerHelper.attachToRecyclerView(recycler);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                return .7f;
            }

            @Override
            public float getSwipeEscapeVelocity(float defaultValue) {
                return defaultValue * 2;
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);

                StickerAdapter.StickerHolder holder = (StickerAdapter.StickerHolder) viewHolder;
                if (holder != null) {
                    Log.d("touch helper", holder.sticker + " " + actionState);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                StickerAdapter.StickerHolder holder = (StickerAdapter.StickerHolder) viewHolder;
                //dX = Math.min(Math.abs(dX), 200) * Math.signum(dX);
                float fullSwipe = recyclerView.getMeasuredWidth() * this.getSwipeThreshold(viewHolder);
                float t = Math.abs(dX / fullSwipe);
                holder.onSwipe(t, dX > 0);
                getDefaultUIUtil().onDraw(c, recyclerView, holder.getForeground(), dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                StickerAdapter.StickerHolder holder = (StickerAdapter.StickerHolder) viewHolder;
                Log.d("touch helper", String.valueOf(holder.sticker));
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                StickerAdapter.StickerHolder holder = (StickerAdapter.StickerHolder) viewHolder;
                Log.d("touch helper", String.valueOf(holder.sticker));
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if(direction == ItemTouchHelper.END || direction == ItemTouchHelper.START) {
                    Log.d("touch helper", String.valueOf(direction));
                    StickerAdapter.StickerHolder holder = (StickerAdapter.StickerHolder) viewHolder;
                    holder.onSwipeFinished(direction == ItemTouchHelper.END);
                    getDefaultUIUtil().clearView(holder.getForeground());
                    holder.onSwipe(0, true);
                }
            }
        });
        touchHelper.attachToRecyclerView(recycler);

        addToRecycler(R.drawable.s1, "😀😁");
        addToRecycler(R.drawable.s2, "🤑");
        addToRecycler(R.drawable.s3, "🐱‍🏍😼");
        addToRecycler(R.drawable.s4, "🐞👨🏾‍🤝‍👨🏻");
        addToRecycler(R.drawable.s5, "👻🐱‍🚀");
        addToRecycler(R.drawable.s6, "🐾🦓");
        addToRecycler(R.drawable.s7, "🦅🦑");
        addToRecycler(R.drawable.s8, "☃🎟🕹🈷");


    }

    private void addToRecycler(int drawableId, String emoji) {
        Glide.with(this)
                .asBitmap()
                .load(drawableId)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        adapter.addSticker(new Sticker(resource, emoji));
                    }
                });
    }
}