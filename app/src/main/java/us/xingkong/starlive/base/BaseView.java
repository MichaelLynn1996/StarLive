package us.xingkong.starlive.base;

/**
 * Created by SeaLynn0 on 2018/3/26.
 */

public interface BaseView<P> {
    void setPresenter(P presenter);

    void showToast(CharSequence msg);

    void showToast(int msgId);

    void showLoadingDialog(CharSequence msg);

    void hideLoadingDialog();
}
