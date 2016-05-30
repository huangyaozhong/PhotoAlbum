package com.example.admin.phototest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

/**
 * User: HYZ(1101233283@qq.com)
 * Date: 2016-04-28
 * Time: 18:22
 */
public class ItemActrivity extends Activity {

    private GridView gridView;
    private List<ImageItem> imageItems;
    private RelativeLayout rl;
    private ImageView iv_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridview);
        rl= (RelativeLayout) findViewById(R.id.rl);
        iv_rl= (ImageView) findViewById(R.id.rl_iv);
        imageItems = (List<ImageItem>) getIntent().getSerializableExtra("imagelist");
        gridView.setAdapter(new MyAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rl.setVisibility(View.VISIBLE);
               /* String thumbPath = imageItems.get(position).thumbnailPath;
                String sourcePath = imageItems.get(position).imagePath;
                iv_rl.setTag(sourcePath);
                cache.displayBmp(iv_rl, thumbPath, sourcePath, callback);*/
                Bitmap bitmap = BitmapFactory.decodeFile(imageItems.get(position).imagePath);
                Log.e("bitmap", bitmap + "");
                iv_rl.setImageBitmap(bitmap);
            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
            }
        });
    }

    class MyAdapter extends BaseAdapter {


        public MyAdapter() {
        }


        @Override
        public int getCount() {
            return imageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return imageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(ItemActrivity.this, R.layout.item, null);
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
            TextView tv = (TextView) convertView.findViewById(R.id.name);


            String thumbPath = imageItems.get(position).thumbnailPath;
            String sourcePath = imageItems.get(position).imagePath;

            Glide.with(ItemActrivity.this).load(new File(sourcePath)).into(iv);

           /* String url=imageBuckets.get(position).imageList.get(0).thumbnailPath;
            Bitmap bitmap=BitmapFactory.decodeFile(url);
            iv.setImageBitmap(bitmap);*/
            tv.setText(imageItems.get(position).imageId);
            return convertView;
        }
    }
}
