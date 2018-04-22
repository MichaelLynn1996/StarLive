package us.xingkong.starlive.module.createapp;

import us.xingkong.starlive.base.BasePresenter;
import us.xingkong.starlive.base.BaseView;
import us.xingkong.streamsdk.network.Client;

public interface CreateAppContract {
    interface View extends BaseView<Presenter>{
        void freeze();
        void unfreeze();
    }
    interface Presenter extends BasePresenter{
        void createApp(Client client,String appname,String apptitle,String maintext);
    }
}
