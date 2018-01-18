package com.view.jameson.androidrecyclerviewcard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.view.jameson.library.CardAdapterHelper;
import com.view.jameson.library.CardScaleHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jameson on 8/30/16.
 */
class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final RecyclerView mView;
    private List<ImageSource> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    private Context context;
    CardScaleHelper mCardScaleHelper;
    public CardAdapter(Context context, RecyclerView view, CardScaleHelper cardScaleHelper) {
        this.context = context;
        this.mView = view;
        mCardScaleHelper = cardScaleHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
                mCardAdapterHelper.onCreateViewHolder(parent, itemView);
                return new ViewHolder(itemView);
            case 1:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item1, parent, false);
                mCardAdapterHelper.onCreateViewHolder(parent, itemView);
                return new ViewHolderImage(itemView);

        }
        return null;
    }

    public void setList(List<ImageSource> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
       // mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        switch (getItemViewType(position)) {
            case 0:
                ViewHolder holdersub = (ViewHolder) holder;
                mCardAdapterHelper.onCreateViewHolder(mView, holdersub.itemView);
                holdersub.itemView.requestLayout();
                holdersub.mSubImageView.setImage(mList.get(position));
                GlideApp.with(context).asBitmap().load(mList.get(position).getUri()).centerInside().into(new CustomTarget(holdersub.mSubImageView));
                if (mList.get(position) == null) {

                }
                holdersub.mSubImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                break;
            case 1:
                ViewHolderImage holderimg = (ViewHolderImage) holder;
                mCardAdapterHelper.onCreateViewHolder(mView, holderimg.itemView);

                holderimg.itemView.requestLayout();
                GlideApp.with(context).load(mList.get(position).getUri()).into(holderimg.mImageView);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final SubsamplingScaleImageView mSubImageView;
        public ViewHolder(final View itemView) {
            super(itemView);
            mSubImageView = (SubsamplingScaleImageView) itemView.findViewById(R.id.imageView);
            //mSubView = (SubsamplingScaleImageView) itemView.findViewById(R.id.subimageView);
        }

    }

    public class ViewHolderImage extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public ViewHolderImage(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            //mSubView = (SubsamplingScaleImageView) itemView.findViewById(R.id.subimageView);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).supportScale()) {
            return 0;
        } else {
            return 1;
        }
    }
}
