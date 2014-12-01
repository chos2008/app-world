/*
 * MainFragmentActivity.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008Äê10ÔÂ25ÈÕ
 */
package com.example.world;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.world.account.LoginFragmentActivity;
import com.example.world.account.RegisterAccountFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * 
 *
 */
public class MainFragmentActivity extends FragmentActivity {

	private int currIndex = 0; 
	
	private int position_one;  
	private int position_two;  
	private int position_three;  
	private Resources resources;  
	    
	private List<TextView> tvTabList;
	    
	private ViewPager mPager;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main_fragment_activity);
		
		resources = getResources();
		
		DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        int screenW = dm.widthPixels;  
//        offset = (int) ((screenW / 4.0 - bottomLineWidth) / 2);  
//        Log.i("MainActivity", "offset=" + offset);  
  
        position_one = (int) (screenW / 4.0);  
        position_two = position_one * 2;  
        position_three = position_one * 3;  
        
		LayoutInflater mInflater = getLayoutInflater();  
		View activityView = mInflater.inflate(R.layout.main_fragment_activity_content, null);  
		
		tvTabList = new LinkedList<TextView>();
		TextView tvTabContact = (TextView) findViewById(R.id.mContact);
		TextView tvTabAffairs = (TextView) findViewById(R.id.mAffairs);
		TextView tvTabMessage = (TextView) findViewById(R.id.mMessage);
		TextView tvTabMore = (TextView) findViewById(R.id.mMore);
		TextView tvTabLogin = (TextView) findViewById(R.id.mLogin);
		TextView tvTabRegister = (TextView) findViewById(R.id.mRegister);
		
		tvTabList.add(tvTabContact);
		tvTabList.add(tvTabAffairs);
		tvTabList.add(tvTabMessage);
		tvTabList.add(tvTabMore);

		tvTabContact.setOnClickListener(new MyOnClickListener(0));
        tvTabAffairs.setOnClickListener(new MyOnClickListener(1));
        tvTabMessage.setOnClickListener(new MyOnClickListener(2));
        tvTabMore.setOnClickListener(new MyOnClickListener(3));
        tvTabLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("on click " + arg0.getId());
	        	Context context = MainFragmentActivity.this;
				Intent intent = new Intent(context, LoginFragmentActivity.class);
	        	context.startActivity(intent);
			}
		});
        
        tvTabRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("on click " + arg0.getId());
	        	Context context = MainFragmentActivity.this;
				Intent intent = new Intent(context, RegisterAccountFragmentActivity.class);
	        	context.startActivity(intent);
			}
		});
        
        mPager = (ViewPager) findViewById(R.id.viewPager);
  
        Fragment contactFragment = DefaultFragment.newInstance(R.layout.contact_fragment, "Contact");
        Fragment affairsfragment = DefaultFragment.newInstance(R.layout.main_fragment_activity_content, "Affairs");
        Fragment messageFragment = DefaultFragment.newInstance(R.layout.main_fragment_activity_content, "Message");
        Fragment moreFragment = MoreFragment.newInstance(R.layout.more_fragment, "More");
  
        List<Fragment> fragmentsList = new ArrayList<Fragment>();
        fragmentsList.add(contactFragment);  
        fragmentsList.add(affairsfragment);  
        fragmentsList.add(messageFragment);  
        fragmentsList.add(moreFragment);
        
        mPager.setAdapter(new WorldFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));  
        mPager.setCurrentItem(0);  
        mPager.setOnPageChangeListener(new MyOnPageChangeListener(tvTabList)); 
	}

	public class MyOnClickListener implements View.OnClickListener {  
        private int index = 0;
        
        public MyOnClickListener(int i) {  
            index = i;  
        }  
        
        @Override  
        public void onClick(View v) {
        	System.out.println("set current item: " + index);
            mPager.setCurrentItem(index);  
        }  
    };
    
    public class MyOnPageChangeListener implements OnPageChangeListener {  
    	
    	private List<TextView> tvTabList;
    	
    	public MyOnPageChangeListener(List<TextView> tvTabList) {
    		this.tvTabList = tvTabList;
    	}
    	
        @Override  
        public void onPageSelected(int arg0) {
        	for (int i = 0; i < tvTabList.size(); i++) {
        		if (i == arg0) {
        			tvTabList.get(i).setTextColor(resources.getColor(R.color.coral));
        		} else {
        			tvTabList.get(i).setTextColor(resources.getColor(R.color.black));
        		}
        	}
        	currIndex = arg0;
        	
////            Animation animation = null;  
//            switch (arg0) {  
//            case 0:  
//                if (currIndex == 1) {  
////                    animation = new TranslateAnimation(position_one, 0, 0, 0);  
//                    tvTabAffairs.setTextColor(resources.getColor(R.color.black));  
//                } else if (currIndex == 2) {  
////                    animation = new TranslateAnimation(position_two, 0, 0, 0);  
//                    tvTabMessage.setTextColor(resources.getColor(R.color.black));  
//                }
//                tvTabContact.setTextColor(resources.getColor(R.color.coral));  
//                break;  
//            case 1:  
//                if (currIndex == 0) {  
////                    animation = new TranslateAnimation(offset, position_one, 0, 0);  
//                    tvTabContact.setTextColor(resources.getColor(R.color.black));  
//                } else if (currIndex == 2) {  
////                    animation = new TranslateAnimation(position_two, position_one, 0, 0);  
//                    tvTabMessage.setTextColor(resources.getColor(R.color.black));  
//                }
//                tvTabAffairs.setTextColor(resources.getColor(R.color.coral));  
//                break;  
//            case 2:  
//                if (currIndex == 0) {  
////                    animation = new TranslateAnimation(offset, position_two, 0, 0);  
//                	tvTabContact.setTextColor(resources.getColor(R.color.black));  
//                } else if (currIndex == 1) {  
////                    animation = new TranslateAnimation(position_one, position_two, 0, 0);  
//                	tvTabAffairs.setTextColor(resources.getColor(R.color.black));  
//                }
//                tvTabMessage.setTextColor(resources.getColor(R.color.coral));  
//                break;  
//            }  
//            currIndex = arg0;  
////            animation.setFillAfter(true);
////            animation.setDuration(300);
////            ivBottomLine.startAnimation(animation);
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        	
        }  
  
        @Override  
        public void onPageScrollStateChanged(int arg0) {
        	
        }  
    } 
}
