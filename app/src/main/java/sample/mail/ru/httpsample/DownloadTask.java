package sample.mail.ru.httpsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * Created by epesnya on 24.03.17.
 */

public class DownloadTask extends AsyncTask<List<ImgObj>, Integer, Integer> {

    private WeakReference<RequestListener> mListener;
    protected String mErrorStringID;

    public DownloadTask(RequestListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    protected Integer doInBackground(List<ImgObj>... params) {
        Iterator<ImgObj> it  = params[MyApp.ZERO].iterator();
        int i = MyApp.ZERO;
        while (it.hasNext()){
            try {
                URL newUrl = new URL(it.next().getImgUrl());
                Bitmap mIconVal = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
                MyApp.imgs.get(i).setImgBitmap(mIconVal);
                publishProgress(i);
                i++;
            } catch (Exception e) {
                mErrorStringID = e.toString();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        RequestListener l = mListener.get();
        if (l != null) {
            if (values != null) {
                l.onDownloadResult(values[0]);
            }
            else {
                l.onDownloadError(mErrorStringID);
            }
        }
    }
}

