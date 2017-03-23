package sample.mail.ru.httpsample;

import java.util.List;

/**
 * Created by epesnya on 23.03.17.
 */

public interface RequestListener {
    void onRequestResult(List<ImgObj> result);
    void onRequestError(int errorStringID);
}
