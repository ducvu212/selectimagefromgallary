package com.example.ducvu212.selectimagefromgallary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ImageAdapter.IListImage {

    private static final int PICK_IMAGE_CODE = 111;
    private static final int SPAN_COUNT = 2;
    private static final String STRING_INDEX = "U:";
    private static final int READ_STORAGE_PERMISSION = 111;
    private RecyclerView mRecyclerViewImage;
    private ArrayList<Image> mImages;
    private final String TITLE_SELECT_IMAGE = "Select Image";
    private final String IMAGE_TYPE = "image/*";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        findViewByIds();
        initComponent();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initComponent() {
        mImages = new ArrayList<>();
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, TITLE_SELECT_IMAGE), PICK_IMAGE_CODE);
        mRecyclerViewImage.setLayoutManager(new GridLayoutManager(getBaseContext(), SPAN_COUNT));
    }

    private void findViewByIds() {
        mRecyclerViewImage = findViewById(R.id.recycle_image);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                String uri = data.getClipData().getItemAt(i).toString();
                mImages.add(new Image(uri.substring(uri.indexOf(STRING_INDEX) + SPAN_COUNT,
                        uri.length() - SPAN_COUNT)));
            }
        }
        ImageAdapter imageAdapter = new ImageAdapter(mImages);
        mRecyclerViewImage.setAdapter(imageAdapter);
    }

    @Override
    public Image getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public int getCount() {
        if (mImages != null) {
            return mImages.size();
        } else {
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    READ_STORAGE_PERMISSION);
        }
    }
}
