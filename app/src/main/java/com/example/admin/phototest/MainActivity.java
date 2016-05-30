package com.example.admin.phototest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.List;

public class MainActivity extends Activity {

    private Button btn;
    AlbumHelper albumHelper;
    List<ImageBucket> imageBuckets;
    private GridView gv;
    public static Bitmap bimap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= (Button) findViewById(R.id.photo);
        albumHelper=AlbumHelper.getHelper();
        albumHelper.init(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBuckets = albumHelper.getImagesBucketList(false);
                bimap = BitmapFactory.decodeResource(
                        getResources(),
                        R.mipmap.ic_launcher);
                for (int i = 0; i < imageBuckets.size(); i++) {
                    Log.e("Tag", "bucketName:" + imageBuckets.get(i).bucketName + " size:"
                            + imageBuckets.get(i).imageList.size());
                    List<ImageItem> imageList = imageBuckets.get(i).imageList;
                    for (int j = 0; j < imageList.size(); j++) {
                        Log.e("Tag", "imageId:" + imageList.get(j).imageId + imageList.get(j).imagePath + imageList.get(j).thumbnailPath);
                    }
                }
                gv.setAdapter(new MyAdapter());
            }
        });
        gv= (GridView) findViewById(R.id.gridview);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,ItemActrivity.class);
                intent.putExtra("imagelist", (Serializable) imageBuckets.get(position).imageList);
                startActivity(intent);
            }
        });

    }
    class MyAdapter extends BaseAdapter{
       /* BitmapCache cache;
        BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap,
                                  Object... params) {
                if (imageView != null && bitmap != null) {
                    String url = (String) params[0];
                    if (url != null && url.equals((String) imageView.getTag())) {
                        ((ImageView) imageView).setImageBitmap(bitmap);
                    } else {
                        Log.e("tag", "callback, bmp not match");
                    }
                } else {
                    Log.e("tag", "callback, bmp null");
                }
            }
        };
        public MyAdapter(){
            cache=new BitmapCache();
        }*/


        @Override
        public int getCount() {
            return imageBuckets.size();
        }

        @Override
        public Object getItem(int position) {
            return imageBuckets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=View.inflate(MainActivity.this,R.layout.item,null);
            ImageView iv= (ImageView) convertView.findViewById(R.id.iv);
            TextView tv= (TextView) convertView.findViewById(R.id.name);


            String thumbPath = imageBuckets.get(position).imageList.get(0).thumbnailPath;
            String sourcePath = imageBuckets.get(position).imageList.get(0).imagePath;
            //iv.setTag(sourcePath);
            //cache.displayBmp(iv, thumbPath, sourcePath, callback);
            Log.e("Ag","thumbPath:" +thumbPath+" sourcePath:"+sourcePath);
            Glide.with(MainActivity.this).load(new File(sourcePath)).into(iv);
           /* String url=imageBuckets.get(position).imageList.get(0).thumbnailPath;
            Bitmap bitmap=BitmapFactory.decodeFile(url);
            iv.setImageBitmap(bitmap);*/
            tv.setText(imageBuckets.get(position).bucketName + " 总：" + imageBuckets.get(position).imageList.size());
            return convertView;
        }
    }

}
