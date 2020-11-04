package com.example.a11699.androidxallstudy.xmlwriteandread.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a11699.androidxallstudy.R;
import com.example.a11699.androidxallstudy.xmlwriteandread.FaceModel;

import java.util.List;


class EmotionAdapter extends BaseAdapter {

    private Context context;
    private List<FaceModel> emojiList;

    public EmotionAdapter(Context mContext, List<FaceModel> emojiList) {
        this.context = mContext;
        this.emojiList = emojiList;
    }

    @Override
    public int getCount() {
        return emojiList.size() + 1;
    }

    @Override
    public FaceModel getItem(int position) {
        if (position == getCount() - 1) {
            return null;
        }
        return emojiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.emoji_item, null);
        ImageView emojiThumb = (ImageView) convertView.findViewById(R.id.imgEmoji);
        if (position == getCount() - 1) {
            emojiThumb.setImageResource(R.drawable.btn_room_face_del);
        } else {
            int path = emojiList.get(position).getId();
            Glide.with(emojiThumb.getContext())
                    .load(path)
                    .into(emojiThumb);
        }
        return convertView;
    }

}