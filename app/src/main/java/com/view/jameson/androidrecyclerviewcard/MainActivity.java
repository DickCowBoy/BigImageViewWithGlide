package com.view.jameson.androidrecyclerviewcard;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.view.jameson.library.CardScaleHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public Uri getContentUri(long id) {
        Uri baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        return baseUri.buildUpon().appendPath(String.valueOf(id)).build();
    }
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewFilm;
    private ImageView mBlurView;
    private List<Integer> mList = new ArrayList<>();
    private CardScaleHelper mFilmCardScaleHelper = null;
    private CardScaleHelper mCardScaleHelper = null;
    private FilmCardAdapter mFilmCardAdapter = null;
    private Runnable mBlurRunnable;
    private int mLastPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        init();
    }
    CardAdapter cardAdapter;
    private void init() {
        for (int i = 0; i < 10; i++) {
            mList.add(R.drawable.pic4);
            mList.add(R.drawable.pic5);
            mList.add(R.drawable.pic6);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerViewFilm = (RecyclerView) findViewById(R.id.recyclerViewfilm);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerViewFilm.setLayoutManager(linearLayoutManager2);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mRecyclerViewFilm.addItemDecoration(new SpaceFilmItemDecoration(10));
        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper();
        mFilmCardScaleHelper = new CardScaleHelper();

        cardAdapter = new CardAdapter(this, mRecyclerView, mCardScaleHelper);
        mFilmCardAdapter = new FilmCardAdapter(this, mRecyclerViewFilm, mFilmCardScaleHelper);


        mRecyclerView.setAdapter(cardAdapter);
        mRecyclerViewFilm.setAdapter(mFilmCardAdapter);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        mCardScaleHelper.attachToRecyclerView(mRecyclerView, -10);
        mFilmCardScaleHelper.attachToRecyclerView(mRecyclerViewFilm, getResources().getDisplayMetrics().widthPixels / 6 - 10);

        initBlurBackground();
    }

    public void click(View view) {
        if (mRecyclerView.getVisibility() == View.GONE) {
            int currentItemPos = mFilmCardScaleHelper.getCurrentItemPos(mRecyclerViewFilm, 0);
            mCardScaleHelper.scrollToPos(currentItemPos);
            Log.e("LJL", "mFilmCardScaleHelper.getCurrentItemPos()"  + currentItemPos);
            ScaleAnimation anim = new ScaleAnimation(1.0F, 1.5f, 1.0F, 1.5F,
                    Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
                    0.5F);
            anim.setDuration(300);
            mRecyclerViewFilm.setAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mRecyclerView.setAlpha(0.0F);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRecyclerView.setAlpha(1.0F);
                    mRecyclerViewFilm.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            anim.startNow();
        }

        if (mRecyclerViewFilm.getVisibility() == View.GONE) {
            mRecyclerViewFilm.setVisibility(View.VISIBLE);
            int currentItemPos = mCardScaleHelper.getCurrentItemPos(mRecyclerView, 10);
            mFilmCardScaleHelper.scrollToPos(currentItemPos);
            Log.e("LJL", "mCardScaleHelper.getCurrentItemPos()" + currentItemPos);
            ScaleAnimation anim = new ScaleAnimation(1.0F, 0.6667f, 1.0F, 0.6667F,
                    Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
                    0.5F);
            mRecyclerView.setAnimation(anim);
            anim.setDuration(300);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mRecyclerView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            anim.startNow();
        }
    }

    private void initBlurBackground() {
//        mBlurView = (ImageView) findViewById(R.id.blurView);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    notifyBackgroundChange();
//                }
//            }
//        });
//
//        notifyBackgroundChange();
    }

    private void notifyBackgroundChange() {
//        if (mLastPos == mCardScaleHelper.getCurrentItemPos()) return;
//        mLastPos = mCardScaleHelper.getCurrentItemPos();
//        final int resId = mList.get(mCardScaleHelper.getCurrentItemPos());
//        mBlurView.removeCallbacks(mBlurRunnable);
//        mBlurRunnable = new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
//                ViewSwitchUtils.startSwitchBackgroundAnim(mBlurView, BlurBitmapUtils.getBlurBitmap(mBlurView.getContext(), bitmap, 15));
//            }
//        };
//        mBlurView.postDelayed(mBlurRunnable, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(){
            @Override
            public void run() {
                Cursor query = null;
                String[] projection = { MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.WIDTH,
                        MediaStore.Images.Media.HEIGHT,
                        MediaStore.Images.Media.ORIENTATION,
                        MediaStore.Images.Media.MIME_TYPE,
                        MediaStore.Images.Media.DATA };
                final List<ImageSource> images = new ArrayList<>();
                try {
                    query = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                            null,
                            null, null);

                    while (query.moveToNext()) {
                        images.add(ImageSource.uri(getContentUri(query.getLong(0)),
                                query.getInt(2),
                                query.getInt(3),
                                query.getInt(4), query.getString(5)));
                    }

                } catch (Exception e) {
                    if (query != null) {
                        query.close();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cardAdapter.setList(images);
                        mFilmCardAdapter.setList(images);
                    }
                });
            }
        }.start();
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        /**
         * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
         * the number of pixels that the item view should be inset by, similar to padding or margin.
         * The default implementation sets the bounds of outRect to 0 and returns.
         * <p>
         * <p>
         * If this ItemDecoration does not affect the positioning of item views, it should set
         * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
         * before returning.
         * <p>
         * <p>
         * If you need to access Adapter for additional data, you can call
         * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
         * View.
         *
         * @param outRect Rect to receive the output.
         * @param view    The child view to decorate
         * @param parent  RecyclerView this ItemDecoration is decorating
         * @param state   The current state of RecyclerView.
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            outRect.left = mSpace;
            outRect.right = mSpace;

        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }

    class SpaceFilmItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        /**
         * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
         * the number of pixels that the item view should be inset by, similar to padding or margin.
         * The default implementation sets the bounds of outRect to 0 and returns.
         * <p>
         * <p>
         * If this ItemDecoration does not affect the positioning of item views, it should set
         * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
         * before returning.
         * <p>
         * <p>
         * If you need to access Adapter for additional data, you can call
         * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
         * View.
         *
         * @param outRect Rect to receive the output.
         * @param view    The child view to decorate
         * @param parent  RecyclerView this ItemDecoration is decorating
         * @param state   The current state of RecyclerView.
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = mRecyclerView.getWidth() / 6;
            } else {
                outRect.left = mSpace;
            }
            if (parent.getChildAdapterPosition(view) != (parent.getAdapter().getItemCount()-1)) {

                outRect.right = mSpace;
            } else {
                outRect.right = mRecyclerView.getWidth() / 6;
            }
//            outRect.bottom = mSpace;
//            if (parent.getChildAdapterPosition(view) == 0) {
//                outRect.top = mSpace;
//            }

        }

        public SpaceFilmItemDecoration(int space) {
            this.mSpace = space;
        }
    }


}
