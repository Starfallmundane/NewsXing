package com.lx.newscoorer.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.newscoorer.bean.BaseBean;
import com.lx.newscoorer.bean.CategoryBean;
import com.lx.newscoorer.bean.NewsBean;
import com.lx.newscoorer.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

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

        /**
         * 解析方法一
         */
        Gson gson = new Gson();
//        BaseBean baseBean = gson.fromJson(jsonString, BaseBean.class);
//        if (baseBean.isSuccess()) {
//            BaseBean<List<NewsBean>> data = new Gson().fromJson(jsonString, new TypeToken<BaseBean<List<NewsBean>>>() {}.getType());
//            Log.e("liuxing", data.toString());
//            NewsBean mNewsBean=  data.data.get(0);
//            Log.e("liuxing", mNewsBean.title);
//        }

        /**
         * 解析方法二
         */
//        BaseBean<List<NewsBean>> baseBean = new Gson().fromJson(jsonString, new TypeToken<BaseBean<List<NewsBean>>>() {}.getType());
//        if(baseBean.isSuccess()){
//            List<NewsBean> list=baseBean.data;
//        }

        /**
         * 解析方法三
         */
        JSONObject jsonObject = null;
        try {
            //解析数据
            jsonObject = new JSONObject(jsonString);
            //1.先用原生的json去取出来 data 里的数据
            String data = jsonObject.getString("data");
            //2.用gson插件去解析 data 里的数据
            List<NewsBean> newData = new Gson().fromJson(data, new TypeToken<List<NewsBean>>() {}.getType());
            Log.e("liuxing","新数据=="+newData.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
