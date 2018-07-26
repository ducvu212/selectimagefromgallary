package com.example.ducvu212.selectimagefromgallary;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<Image> mPaths;

    public ImageAdapter(ArrayList<Image> paths) {
        mPaths = paths;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        new ImageLoadAsynctask(imageViewHolder.mImageView).execute(mPaths.get(i).getPath());
    }

    @Override
    public int getItemCount() {
        return mPaths.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }

    public interface IListImage {
        Image getItem(int position);
        int getCount();
    }

    public class ImageLoadAsynctask extends AsyncTask<String, Void, String> {

        private WeakReference<ImageView> mReference;
        private final int mHeightImage = 1333;
        private final int mWidthImage = 1000;

        public ImageLoadAsynctask(ImageView imageView) {
            mReference = new WeakReference<>(imageView);
        }

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            if (mReference != null && string != null) {
                final ImageView imageView = mReference.get();
                if (imageView != null) {
                    Picasso.get()
                            .load(Uri.parse(string))
                            .resize(mWidthImage, mHeightImage)
                            .into(imageView);
                }
            }
        }
    }
}
