package com.lx.newscoorer.adapter;

import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lx.newscoorer.R;
import com.lx.newscoorer.bean.CategoryBean;


import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 主页面--左侧菜单的列表布局--Adapter
 */
public class HomeAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> implements LoadMoreModule {

    /**
     * 1. List<String>  字符串集合时这样写
     * 2. List<XxxBean>  对象集合时这样写，导致这样下面item 的类型就得改成XxxBean，可以直接用属性了
     *
     * @param data
     */
    public HomeAdapter(List<CategoryBean> data) {
        /**
         *  布局传递
         *  *****
         *  这里一定要说明一点
         *  列表竖直方向时，item_left_menu最外层布局LinearLayout，高度必须用 android:layout_height="wrap_content"
         *  列表竖横方向时，item_left_menu最外层布局LinearLayout，宽度必须用 android:layout_height="layout_width"
         *  这种情况是为了防止一个条目就把屏幕占满了只能显示一条的情况
         *
         *  以后所有列表都是这样情况设置的
         */
        super(R.layout.item_home_category);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull CategoryBean item) {
        int position = helper.getLayoutPosition();
//        Log.e("liuxing",position+"=========="+item.getTitle());
        /**
         * BaseQuickAdapter  基本用法参考文档
         * https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/1-BaseQuickAdapter.md
         *
         * 这里控件有两种用法，注意我的item里控件命名，多规范啊
         */
        //写法一。框架自己通用的，如果像图片其他控件，没有提供，就得用第二种了
        helper.setText(R.id.tv_item_homecate_name, position+"=="+item.getTitle());
        helper.setText(R.id.tv_item_homecate_color, item.getColor());

        //写法二,先获取控件，在用安卓自己的方法去做
//       TextView tv_item_menu_name= helper.getView(R.id.tv_item_menu_name);
//        tv_item_menu_name.setText(item);
    }
}
