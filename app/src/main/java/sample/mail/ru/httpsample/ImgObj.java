package sample.mail.ru.httpsample;

import android.graphics.Bitmap;

/**
 * Created by epesnya on 23.03.17.
 */

public class ImgObj {
    private String imgUrl = null;
    private Bitmap imgBitmap = null;

    public void setImgUrl(String s) {
        imgUrl = s;
    }

    public void setImgBitmap(Bitmap b) {
        imgBitmap = b;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }
}
