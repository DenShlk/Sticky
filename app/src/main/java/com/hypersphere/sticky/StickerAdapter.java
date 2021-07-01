package com.hypersphere.sticky;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerHolder> {

    private final List<Sticker> stickers = new ArrayList<>();

    public void addSticker(@NonNull Sticker sticker) {
        stickers.add(sticker);
        notifyItemInserted(stickers.size() - 1);
    }

    @NonNull
    @Override
    public StickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = viewType == 0 ? R.layout.sticker_element_layout_right : R.layout.sticker_element_layout_left;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new StickerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerHolder holder, int position) {
        holder.bind(stickers.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }

    static class StickerHolder extends RecyclerView.ViewHolder {

        private final TextView emojiText;
        private final ImageView image;
        private final View foreground;
        private final ImageView editIc;

        public StickerHolder(@NonNull View itemView) {
            super(itemView);

            emojiText = itemView.findViewById(R.id.sticker_emoji_text);
            image = itemView.findViewById(R.id.sticker_image);
            foreground = itemView.findViewById(R.id.sticker_foreground);
            editIc = itemView.findViewById(R.id.sticker_edit_ic);
        }

        public Sticker sticker;

        public void bind(@NonNull Sticker sticker) {
            this.sticker = sticker;
            emojiText.setText(sticker.emoji);
            image.setImageBitmap(sticker.image);
        }

        public View getForeground() {
            return foreground;
        }

        public void onSwipe(float t, boolean toRight) {
            t = (float) Math.pow(t, 0.3f);

            editIc.setScaleX(t);
            editIc.setScaleY(t);
            if (toRight) {
                editIc.setImageResource(R.drawable.ic_baseline_edit_24);
            }else {
                editIc.setImageResource(R.drawable.ic_baseline_delete_24);
            }
        }

        public void onSwipeFinished(boolean toRight) {

        }
    }
}
