package com.lx.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.bean.BaseBean;
import com.lx.bean.NewsBean;
import com.lx.newscoorer.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Call;

public class NewsFragment extends Fragment {
    String url = "http://192.168.43.28:8080/zhbj/categories.json";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newscourr, container, false);
        Log.e("liuxing", "创建--NewsFragment");
        return view;
    }

    public void getList() {
        OkHttpUtils.get().url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                Log.e("liuxing", "数据呢?它可能不喜欢我");
                Toast.makeText(getActivity(), "失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response, int id) {
//                Log.e("liuxing","成功"+response);
                parseJson(response);

            }
        });
    }

    //    解析json字符串
    public void parseJson(String jsonString) {
        Gson gson = new Gson();
        /**
         * 1. 要解析的json字符串
         * 2. 安装哪个Javabean解析
         */
//        BaseBean baseBean = gson.fromJson(jsonString, BaseBean.class);
//        if (baseBean.isSuccess()) {
//            BaseBean<List<NewsBean>> data = new Gson().fromJson(jsonString, new TypeToken<BaseBean<List<NewsBean>>>() {}.getType());
//            Log.e("liuxing", data.toString());
//            NewsBean mNewsBean=  data.data.get(0);
//            Log.e("liuxing", mNewsBean.title);
//        }


        BaseBean<List<NewsBean>> baseBean = new Gson().fromJson(jsonString, new TypeToken<BaseBean<List<NewsBean>>>() {}.getType());
        if(baseBean.isSuccess()){
            List<NewsBean> list=baseBean.data;
        }
    }
}
