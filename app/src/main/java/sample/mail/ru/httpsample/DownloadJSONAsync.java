package sample.mail.ru.httpsample;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DownloadJSONAsync extends AsyncTask<String, Integer, List<ImgObj>> {

    public static final String JSON_URL = "http://188.166.49.215/tech/imglist.json";
    private WeakReference<RequestListener> mListener;
    protected int mErrorStringID;

    public DownloadJSONAsync(RequestListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    protected List<ImgObj> doInBackground(String... params) {
        if (params != null && params.length > 0) {
            HttpRequest request = new HttpRequest(params[0]);
            int status = request.makeRequest();

            if (status == HttpRequest.REQUEST_OK) {
                JSONTokener jtk = new JSONTokener(request.getContent());
                try {
                    JSONArray jsonArray = (JSONArray)jtk.nextValue();
                    List<ImgObj> urlArr = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        ImgObj mObj = new ImgObj();
                        mObj.setImgUrl(jsonArray.getString(i));
                        urlArr.add(mObj);
                    }
                    return urlArr;
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                    mErrorStringID = R.string.incorrect_json;
                }
            }
            else {
                mErrorStringID = request.getErrorStringId();
            }
        }
        else {
            mErrorStringID = R.string.too_few_params;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<ImgObj> s) {
        if (!isCancelled()) {
            RequestListener l = mListener.get();
            if (l != null) {
                if (s != null) {
                    l.onRequestResult(s);
                }
                else {
                    l.onRequestError(mErrorStringID);
                }
            }
        }
    }
}
