package sample.mail.ru.httpsample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

        if (mDataset.get(position).getImgBitmap() != null) {
            holder.mTextView.setImageBitmap(mDataset.get(position).getImgBitmap());
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}