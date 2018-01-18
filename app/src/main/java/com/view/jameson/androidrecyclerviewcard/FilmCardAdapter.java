package com.view.jameson.androidrecyclerviewcard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.view.jameson.library.CardAdapterHelper;
import com.view.jameson.library.CardScaleHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by w8782 on 18-1-16.
 */

public class FilmCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final RecyclerView mView;
    private List<ImageSource> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    private Context context;
    CardScaleHelper mCardScaleHelper;
    public FilmCardAdapter(Context context, RecyclerView view, CardScaleHelper cardScaleHelper) {
        this.context = context;
        this.mView = view;
        mCardScaleHelper = cardScaleHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item1, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolderImage(itemView);
    }

    public void setList(List<ImageSource> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());

            ViewHolderImage holderimg = (ViewHolderImage) holder;

            mCardAdapterHelper.onCreateHalfHolder(mView, holderimg.itemView);

            holderimg.itemView.requestLayout();
            GlideApp.with(context).load(mList.get(position).getUri()).into(holderimg.mImageView);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolderImage extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public ViewHolderImage(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            //mSubView = (SubsamplingScaleImageView) itemView.findViewById(R.id.subimageView);
        }

    }

}