package sample.mail.ru.httpsample;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements RequestListener {

    private AsyncTask<?, ?, ?> mRequestTask;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        mRecyclerView.setHasFixedSize(true);

        if (savedInstanceState == null) {
            MyApp.imgs = new ArrayList<>();
            mRequestTask = new DownloadJSONAsync(MainActivity.this).execute(DownloadJSONAsync.JSON_URL);
        } else {
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new MyAdapter(MyApp.imgs, this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRequestTask != null) {
            mRequestTask.cancel(true);
        }
        paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        paused = false;
    }

    @Override
    public void onRequestResult(List<ImgObj> result) {
        MyApp.imgs = result;
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(MyApp.imgs, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRequestError(int errorStringID) {
        Toast toast = Toast.makeText(this, errorStringID, Toast.LENGTH_SHORT);
        toast.show();
    }

}


