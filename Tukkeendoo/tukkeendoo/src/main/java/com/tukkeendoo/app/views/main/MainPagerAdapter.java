package com.tukkeendoo.app.views.main;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tukkeendoo.app.views.base.BaseActivity;
import com.tukkeendoo.app.views.base.BasePage;

import java.util.List;

/**
 * Created by fallou on 15/06/2016.
 */
public class MainPagerAdapter extends PagerAdapter {

    private List<BasePage> pages;
    private BaseActivity context;
    private LayoutInflater inflater;

    public MainPagerAdapter(BaseActivity context, List<BasePage> pages) {
        this.pages = pages;
        this.context = context;
        inflater = LayoutInflater.from(context);
        for (BasePage page : pages){
            page.setActivity(context);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePage page = pages.get(position);
        View view = page.getView();
        if (view == null) {
            view = page.onCreateView(inflater, container);
            page.setView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return pages.indexOf(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).getTitle();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }
}
