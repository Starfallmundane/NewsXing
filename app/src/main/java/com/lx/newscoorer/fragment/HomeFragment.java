package com.lx.newscoorer.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.newscoorer.adapter.HomeAdapter;
import com.lx.newscoorer.adapter.HomeBannerAdapter;
import com.lx.newscoorer.R;
import com.lx.newscoorer.bean.BaseBean;
import com.lx.newscoorer.bean.CategoryBean;
import com.lx.newscoorer.ui.MainActivity;
import com.lx.newscoorer.view.CustomLoadMoreView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.Indicator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HomeFragment extends Fragment {

    private View view;
    ArrayList<String> bannerList ;
    private HomeAdapter homeAdapter;
    private SwipeRefreshLayout srl_home;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.e("liuxing", "创建--HomeFragment");
        initView();
        getHomeNetData(true);     //网络请求数据
        return view;
    }

    private void initView() {
        initBannerData();       //轮播图数据

        //轮播图控件
        Banner banner_home = view.findViewById(R.id.banner_home);
        HomeBannerAdapter bannerAdapter = new HomeBannerAdapter(getActivity(), bannerList);
        banner_home.setAdapter(bannerAdapter);
        //设置轮播图底部小圆点
        banner_home.setIndicator(new CircleIndicator(getActivity()));


        //列表控件
        homeAdapter = new HomeAdapter(null);
        RecyclerView rv_home = view.findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_home.setAdapter(homeAdapter);
        homeAdapter.setAnimationEnable(true);

        initRefreshLayout();
        initLoadMore();
    }

    private void initRefreshLayout() {
        //刷新控件
        srl_home = view.findViewById(R.id.srl_home);
        srl_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //网络请求，请求完成，设置setRefreshing 为false
                getHomeNetData(true);
            }
        });
    }

    private void initLoadMore() {
        // 在 Application 中配置全局自定义的 LoadMoreView
//        LoadMoreModuleConfig.setDefLoadMoreView(new CustomLoadMoreView());
        //在当前页面配置全局自定义的 LoadMoreView
//        homeAdapter.getLoadMoreModule().setLoadMoreView(new CustomLoadMoreView());
        homeAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getHomeNetData(false);
            }
        });
        homeAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        homeAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
    }



    //轮播图数据
    private void initBannerData() {
        bannerList = new ArrayList<String>();
        bannerList.add("http://www.g12e.com/upload/html/2007/8/31/liqion947200783110451392826.jpg");
        bannerList.add("http://p4.so.qhmsg.com/t01fba97e00c6a49a89.jpg");
        bannerList.add("http://up.enterdesk.com/edpic_source/df/66/f6/df66f62a97c8e6488ded53ce326b3cb2.jpg");
        bannerList.add("http://pic39.nipic.com/20140308/251960_174116725000_2.jpg");
        bannerList.add("http://p0.so.qhmsg.com/t01fc496abc036fab17.jpg");
    }

    //网络请求
    private void getHomeNetData(boolean isRefresh) {
        OkHttpUtils
                .get()
                .url("http://123.207.32.32:8001/api/category")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();

                        //记的请求结束关闭刷新
                        srl_home.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int ids) {
//                        Log.e("liuxing", "首页数据：" + response);

                        //记的请求结束关闭刷新
                        srl_home.setRefreshing(false);
                        homeAdapter.getLoadMoreModule().setEnableLoadMore(true);

                        /**
                         * 这里我解析数据不一样，是因为每个接口都有自己对应的数据结构，灵活去变化，解析出来就行
                         * 解析数据分两种，一种用原生的JSONObject去解析取值；  一种是gson框架解析
                         * 这里我用了两种，先用的原生的，取出category数据，然后用gson框架去解析
                         *
                         */
                        JSONObject  jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String category = jsonObject.getString("category");
                            List<CategoryBean> newData = new Gson().fromJson(category, new TypeToken<List<CategoryBean>>() {}.getType());
                            Log.e("liuxing","新数据=="+newData.size());
                            if (isRefresh){     //刷新需要先清空数据，重新添加
                                homeAdapter.setList(newData);
                            }else{     //加载更多，直接在原有的数据集合基础上，添加新数据
                                homeAdapter.addData(newData);
                                homeAdapter.getLoadMoreModule().loadMoreComplete();
                                //当后台无数据返回的时候，停止加载更多的时候
//                                mAdapter.getLoadMoreModule().loadMoreEnd();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

}
