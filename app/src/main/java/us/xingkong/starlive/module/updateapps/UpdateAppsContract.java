package us.xingkong.starlive.module.updateapps;

import us.xingkong.starlive.base.BasePresenter;
import us.xingkong.starlive.base.BaseView;
import us.xingkong.streamsdk.network.Client;

public interface UpdateAppsContract {
    interface View extends BaseView<Presenter>{
        void freeze();
        void unfreeze();
    }
    interface Presenter extends BasePresenter{
        void updateApp(Client client,String app,String apptitle,String maintext);
    }
}
