package com.lx.newscoorer.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.newscoorer.adapter.HomeAdapter;
import com.lx.newscoorer.adapter.HomeBannerAdapter;
import com.lx.newscoorer.R;
import com.lx.newscoorer.bean.BaseBean;
import com.lx.newscoorer.bean.CategoryBean;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.e("liuxing", "创建--HomeFragment");
        initView();
        initHomeData();     //网络请求数据
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
    private void initHomeData() {
        OkHttpUtils
                .get()
                .url("http://123.207.32.32:8001/api/category")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int ids) {
//                        Log.e("liuxing", "首页数据：" + response);
                        /**
                         * 这里我写法不一样，是因为每个接口都有自己对应的数据结构，灵活去变化，解析就行
                         * 解析数据分两种，一种用原生的JSONObject去解析取值；  一种是gson框架解析
                         * 这里我用了两种，先用的原生的，取出category数据，然后用gson框架去解析
                         *
                         */
                        JSONObject  jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String category = jsonObject.getString("category");
                            List<CategoryBean> data = new Gson().fromJson(category, new TypeToken<List<CategoryBean>>() {}.getType());
                            if (data!=null&&data.size()>0){
                                //判断数据成功并且非空的时候，去加载显示数据
                                homeAdapter.setNewData(data);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

}
