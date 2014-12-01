/*
 * MyFragmentPagerAdapter.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008Äê10ÔÂ25ÈÕ
 */
package com.example.world;

import java.util.ArrayList;  
import java.util.List;

import android.support.v4.app.Fragment;  
import android.support.v4.app.FragmentManager;  
import android.support.v4.app.FragmentPagerAdapter;  
  
/**
 * 
 *
 */
public class WorldFragmentPagerAdapter extends FragmentPagerAdapter {
	
    private List<Fragment> fragmentsList;  
  
    public WorldFragmentPagerAdapter(FragmentManager fm) {  
        super(fm);  
    }  
  
    public WorldFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {  
        super(fm);  
        this.fragmentsList = fragments;  
    }  
  
    @Override  
    public int getCount() {  
        return fragmentsList.size();  
    }  
  
    @Override  
    public Fragment getItem(int arg0) {  
        return fragmentsList.get(arg0);  
    }  
  
    @Override  
    public int getItemPosition(Object object) {  
        return super.getItemPosition(object);  
    }  
  
}  