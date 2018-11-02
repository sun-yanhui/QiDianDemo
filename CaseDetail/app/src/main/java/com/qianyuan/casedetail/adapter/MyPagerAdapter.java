package com.qianyuan.casedetail.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qianyuan.casedetail.fragment.ReportInfoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2017/4/24.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private String[] titles={"上报信息","案件进展","结案信息"};
    private List<Fragment> list=new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
      return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
