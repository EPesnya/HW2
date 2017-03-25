package sample.mail.ru.httpsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * Created by epesnya on 23.03.17.
 */

public class MyAdapter extends
        RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (ImageView) v.findViewById(R.id.image);
        }
    }

    private List<ImgObj> mDataset;
    private Context mContext;

    public MyAdapter(List<ImgObj> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_main, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setImageResource(R.mipmap.ic_launcher);
        if (mDataset.get(position).getImgBitmap() != null) {
            holder.mTextView.setImageBitmap(mDataset.get(position).getImgBitmap());
        } else {
            new ImgDownloadTask().execute(position);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ImgDownloadTask extends AsyncTask<Integer, Integer, Integer> {

        protected String mErrorStringID = null;

        @Override
        protected Integer doInBackground(Integer... params) {
            int position = params[MyApp.ZERO];
            try {
                URL newUrl = new URL(MyApp.imgs.get(position).getImgUrl());
                Bitmap mIconVal = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
                MyApp.imgs.get(position).setImgBitmap(mIconVal);
                publishProgress(position);
            } catch (Exception e) {
                mErrorStringID = e.toString();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (!MainActivity.paused) {
                notifyItemChanged(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (!MainActivity.paused) {
                if (mErrorStringID != null) {
                    Toast toast = Toast.makeText(mContext, mErrorStringID, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }
}