package sample.mail.ru.httpsample;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements RequestListener {

    private AsyncTask<?, ?, ?> mRequestTask;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        mRecyclerView.setHasFixedSize(true);
        app = (MyApp) getApplicationContext();

        if (savedInstanceState == null) {
            mRequestTask = new DownloadJSONAsync(MainActivity.this).execute(DownloadJSONAsync.JSON_URL);
        } else {
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new MyAdapter(app.imgs, this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRequestTask != null) {
            mRequestTask.cancel(true);
        }
    }

    @Override
    public void onRequestResult(List<ImgObj> result) {
        app.imgs = result;
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(app.imgs, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new DownoladTask().execute(app.imgs);
    }

    @Override
    public void onRequestError(int errorStringID) {
        Toast toast = Toast.makeText(this, errorStringID, Toast.LENGTH_SHORT);
        toast.show();
    }

    public class DownoladTask extends AsyncTask<List<ImgObj>, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(List<ImgObj>... params) {
            Iterator<ImgObj> it  = params[0].iterator();
            int i = 0;
            while (it.hasNext()){
                try {
                    URL newUrl = new URL(it.next().getImgUrl());
                    Bitmap mIconVal = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
                    app.imgs.get(i).setImgBitmap(mIconVal);
                    mAdapter.notifyItemChanged(i);
                    i++;
                } catch (Exception e) {}
            }
            return null;
        }
    }
}


