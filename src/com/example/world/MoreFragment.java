/*
 * TestFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008Äê10ÔÂ25ÈÕ
 */
package com.example.world;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.chos.world.ContactModel;

import com.example.world.MainFragmentActivity.MyOnClickListener;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;  
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;  
import android.text.TextUtils;
import android.util.Log;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;  
  
/**
 * 
 *
 */
public class MoreFragment extends Fragment {

    private static final String TAG = "TestFragment";
    
    private static final String TITLE = "title";
    
    private static final String RESOURDCE = "resource";
    
    private int resource;
    
    private String hello;// = "hello android";
    
    static MoreFragment newInstance(int resource, String s) {
        MoreFragment newFragment = new MoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, s);
        bundle.putInt(RESOURDCE, resource);
        newFragment.setArguments(bundle);
        return newFragment;
    }
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "TestFragment-----onCreate");
        Bundle args = getArguments();
        hello = args.getString(TITLE);
        resource = args.getInt(RESOURDCE);
    }
  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d(TAG, "TestFragment-----onCreateView");
        View view = inflater.inflate(resource, container, false);
        
        TextView bluetooth = (TextView) view.findViewById(R.id.bluetooth);
        bluetooth.setOnClickListener(new MyOnClickListener(view));
        return view;
    }  

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "TestFragment-----onDestroy");
    }
    
    public class MyOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public MyOnClickListener(View view) {
        	this.view = view;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
        	Context context = view.getContext();
			Intent intent = new Intent(context, BluetoothFragmentActivity.class);
        	context.startActivity(intent);
        }  
    };
}