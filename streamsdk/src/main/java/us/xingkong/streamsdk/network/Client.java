package us.xingkong.streamsdk.network;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.model.GetAppResult;
import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.model.StatusResult;

import static android.content.ContentValues.TAG;

/**
 * Created by 饶翰新 on 2017/12/20.
 * <p>
 * 本类模拟浏览器环境实现API对接
 */

public class Client {

    private Context context;
    /**
     * 默认API的URL接口
     */
    private static final String API_HOST_DEFAULT = "http://live.xingkong.us/?s=/index/user/";

    /**
     * 接口方法数组
     */
    private static final String API_PART[] = new String[]{"apps", "app", "login", "signin", "createapp", "appupdate", "user"};

    /**
     * 内部Handler
     */
    private Handler handler;

    /**
     * 内部缓存线程池
     */
    private ThreadPoolExecutor pool;

    /**
     * 默认构造函数
     */
    public Client(Context context) {
        this.context = context;
        this.handler = new Handler();
        this.pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();//初始化缓存线程池
        this.pool.setCorePoolSize(10);//设置核心池大小
        this.pool.setMaximumPoolSize(20);//设置最大池大小

    }

    /**
     * 登陆方法
     *
     * @param username 用户名
     * @param password 密码
     * @param listener 回调监听器
     */
    public void login(String username, String password, final ResultListener<Result> listener) {

        HttpRequest request = new HttpRequest(API_HOST_DEFAULT + API_PART[2], context);//构造请求对象
        request.post("username", username);//请求对象设置POST数据
        request.post("password", password);//请求对象设置POST数据

        //构造Http执行器
        HttpRunnable hr = new HttpRunnable(request) {
            @Override
            public void onDone(final String result, final Exception e) {
                Log.d(TAG, "onDone: this.getRequest().getCookie():" + this.getRequest().getCookie());

                //本onDone方法环境尚处于非UI线程环境中,使用handler在UI线程触发listener的事件。
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //本run方法已经处于UI线程，故可以触发onDone方法后，onDone方法的UI操作不会受影响
                        if (e != null) {
                            listener.onDone(null, e);
                        } else {
                            try {
                                listener.onDone(new Result(result), null);
                            } catch (Exception e1) {
                                listener.onDone(null, e1);
                            }
                        }

                    }

                });
            }
        };

        pool.execute(hr);//将Http执行器递给线程池
    }

    /**
     * 注册方法
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @param listener 回调监听器
     */
    public void signin(String username, String password, String nickname, final ResultListener<Result> listener) {
        HttpRequest request = new HttpRequest(API_HOST_DEFAULT + API_PART[3], context);//构造请求对象
        request.post("username", username);//请求对象设置POST数据
        request.post("password", password);//请求对象设置POST数据
        request.post("nickname", nickname);//请求对象设置POST数据

        //构造Http执行器
        HttpRunnable hr = new HttpRunnable(request) {
            @Override
            public void onDone(final String result, final Exception e) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (e != null) {
                            listener.onDone(null, e);
                        } else {
                            try {
                                listener.onDone(new Result(result), null);
                            } catch (Exception e1) {
                                listener.onDone(null, e1);
                            }
                        }
                    }
                });

            }
        };

        pool.execute(hr);
    }

    /**
     * 检查登陆状态
     *
     * @param listener
     */
    public void checkLoginStatus(final ResultListener<StatusResult> listener) {
        HttpRequest request = new HttpRequest(API_HOST_DEFAULT + API_PART[6], context);
        HttpRunnable hr = new HttpRunnable(request) {
            @Override
            public void onDone(final String result, final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (e != null) {
                            listener.onDone(null, e);
                        } else {
                            try {
                                listener.onDone(new StatusResult(result), null);
                            } catch (Exception e1) {
                                listener.onDone(null, e1);
                            }
                        }
                    }
                });
            }
        };
        pool.execute(hr);
    }

    /**
     * 获取某个用户信息(未测试)
     *
     * @param username
     * @param listener
     */
    public void getUser(String username, final ResultListener<StatusResult> listener) {
        HttpRequest request = new HttpRequest(API_HOST_DEFAULT + API_PART[6], context);
        request.post("username", username);//请求对象设置POST数据
        HttpRunnable hr = new HttpRunnable(request) {
            @Override
            public void onDone(final String result, final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (e != null) {
                            listener.onDone(null, e);
                        } else {
                            try {
                                listener.onDone(new StatusResult(result), null);
                            } catch (Exception e1) {
                                listener.onDone(null, e1);
                            }
                        }
                    }
                });
            }
        };
        pool.execute(hr);
    }

    /**
     * 获取所有直播信息
     *
     * @param listener 回调监听器
     */
    public void getApps(final ResultListener<AppsResult> listener) {
        HttpRequest request = new HttpRequest(API_HOST_DEFAULT + API_PART[0], context);
        HttpRunnable hr = new HttpRunnable(request) {
            @Override
            public void onDone(final String result, final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (e != null) {
                            listener.onDone(null, e);
                        } else {
                            try {
                                listener.onDone(new AppsResult(result), null);
                            } catch (Exception e1) {
                                listener.onDone(null, e1);
                            }
                        }

                    }
                });
            }
        };

        pool.execute(hr);
    }

    /**
     * 获取当前登陆用户创建的所有直播信息
     *
     * @param listener
     */
    public void getUserApps(final ResultListener<AppsResult> listener) {
        HttpRequest request = new HttpRequest(API_HOST_DEFAULT + API_PART[0] + "&q=1", context);
        HttpRunnable hr = new HttpRunnable(request) {
            @Override
            public void onDone(final String result, final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (e != null) {
                            listener.onDone(null, e);
                        } else {
                            try {
                                listener.onDone(new AppsResult(result), null);
                            } catch (Exception e1) {
                                listener.onDone(null, e1);
                            }
                        }

                    }
                });
            }
        };

        pool.execute(hr);
    }

    /**
     * 获取单个直播信息(未测试)
     *
     * @param appname  直播唯一标识名
     * @param listener 回调监听器
     */
    public void getApp(String appname, final ResultListener<GetAppResult> listener) {
        HttpRequest request = new HttpRequest(API_HOST_DEFAULT + API_PART[1], context);
        request.post("app", appname);
        HttpRunnable hr = new HttpRunnable(request) {
            @Override
            public void onDone(final String result, final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (e != null) {
                            listener.onDone(null, e);
                        } else {
                            try {
                                listener.onDone(new GetAppResult(result), null);
                            } catch (Exception e1) {
                                listener.onDone(null, e1);
                            }
                        }

                    }
                });
            }
        };
        pool.execute(hr);
    }

    /**
     * 创建直播的方法
     *
     * @param appname
     * @param apptitle
     * @param maintext
     * @param listener
     */
    public void createApp(String appname, String apptitle, String maintext, final ResultListener<Result> listener) {
        HttpRequest request = new HttpRequest(API_HOST_DEFAULT + API_PART[4], context);
        request.post("appname", appname);//请求对象设置POST数据
        request.post("apptitle", apptitle);//请求对象设置POST数据
        request.post("maintext", maintext);//请求对象设置POST数据
        HttpRunnable hr = new HttpRunnable(request) {
            @Override
            public void onDone(final String result, final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (e != null) {
                            listener.onDone(null, e);
                        } else {
                            try {
                                listener.onDone(new StatusResult(result), null);
                            } catch (Exception e1) {
                                listener.onDone(null, e1);
                            }
                        }
                    }
                });
            }
        };
        pool.execute(hr);
    }

    /**
     * 更新直播的方法
     *
     * @param app
     * @param apptitle
     * @param maintext
     * @param listener
     */
    public void updateApp(String app, String apptitle, String maintext, final ResultListener<Result> listener) {
        HttpRequest request = new HttpRequest(API_HOST_DEFAULT + API_PART[5], context);
        request.post("app", app);//请求对象设置POST数据
        if (!TextUtils.isEmpty(apptitle))
            request.post("title", apptitle);//请求对象设置POST数据
        if (!TextUtils.isEmpty(maintext))
            request.post("maintext", maintext);//请求对象设置POST数据
        HttpRunnable hr = new HttpRunnable(request) {
            @Override
            public void onDone(final String result, final Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (e != null) {
                            listener.onDone(null, e);
                        } else {
                            try {
                                listener.onDone(new StatusResult(result), null);
                            } catch (Exception e1) {
                                listener.onDone(null, e1);
                            }
                        }
                    }
                });
            }
        };
        pool.execute(hr);
    }

}
