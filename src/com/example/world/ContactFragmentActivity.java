/*
 * MainFragmentActivity.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008年06月25日
 */
package com.example.world;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.chos.world.ContactModel;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 
 *
 */
public class ContactFragmentActivity extends FragmentActivity {

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.contact_fragment_activity);
		
		Context context = this;

		ContentResolver resolver = context.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, ContactModel.PHONES_PROJECTION, null, null, null);

		List<Map<String, Object>> listData = new LinkedList<Map<String,Object>>();
		if (phoneCursor != null) {  
			while (phoneCursor.moveToNext()) {  

				//得到手机号码  
				String phoneNumber = phoneCursor.getString(ContactModel.PHONES_NUMBER_INDEX);  
				//当手机号码为空的或者为空字段 跳过当前循环  
				if (TextUtils.isEmpty(phoneNumber)) {
					continue;
				}

				//得到联系人名称  
				String contactName = phoneCursor.getString(ContactModel.PHONES_DISPLAY_NAME_INDEX);  

				//得到联系人ID  
				Long contactid = phoneCursor.getLong(ContactModel.PHONES_CONTACT_ID_INDEX);  

				//得到联系人头像ID  
				Long photoid = phoneCursor.getLong(ContactModel.PHONES_PHOTO_ID_INDEX);  

				//得到联系人头像Bitamp  
				Bitmap contactPhoto = null;  

				//photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的  
				if(photoid > 0 ) {  
					Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);  
					InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);  
					contactPhoto = BitmapFactory.decodeStream(input);
				} else {  
					contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
				}  

				Map<String, Object> map = new HashMap<String, Object>();  
				map.put("ItemImage", contactPhoto);//图像资源的ID  
				map.put("ItemTitle", contactName);  
				map.put("ItemText", phoneNumber);  
				listData.add(map);  
			}  

			phoneCursor.close();  
		} 

		for(int i=0; i<10; i++) {
			Map<String, Object> map = new HashMap<String, Object>();  
			map.put("ItemImage", R.drawable.ic_launcher);//图像资源的ID  
			map.put("ItemTitle", "Level "+i);  
			map.put("ItemText", "Finished in 1 Min 54 Secs, 70 Moves! ");  
			listData.add(map);  
		}

		ListView listView = (ListView) findViewById(R.id.contact);


		//生成适配器的Item和动态数组对应的元素  
		SimpleAdapter listItemAdapter = new SimpleAdapter(context, listData,//数据源   
				R.layout.contact,//ListItem的XML实现  
				//动态数组与ImageItem对应的子项          
				new String[] {"ItemImage","ItemTitle", "ItemText"},   
				//ImageItem的XML文件里面的一个ImageView,两个TextView ID  
				new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}  
				);  

		//添加并且显示  
		listView.setAdapter(listItemAdapter);  
	}

}
