package com.lx.newscoorer.fragment.guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lx.newscoorer.R;
//import com.smm.yindao.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GuideFragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView=new ImageView(getActivity());
        imageView.setImageResource(R.drawable.guide_1);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
}
