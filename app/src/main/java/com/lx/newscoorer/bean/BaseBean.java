package com.lx.newscoorer.bean;

import android.text.TextUtils;

public class BaseBean<T> {

    //以前的数据
    public String retcode;//状态码
    public T data;

    public boolean isSuccess(){
        return TextUtils.equals(retcode,"200");
    }

    //现在的数据
    public T category;

}
