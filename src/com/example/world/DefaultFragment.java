/*
 * TestFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008年11月25日
 */
package com.example.world;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.chos.world.ContactModel;

import android.app.ListActivity;
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
import android.provider.ContactsContract.Contacts.Photo;
import android.support.v4.app.Fragment;  
import android.text.TextUtils;
import android.util.Log;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;  

/**
 * 
 *
 */
public class DefaultFragment extends Fragment {

	private static final String TAG = "TestFragment";

	private static final String TITLE = "title";

	private static final String RESOURDCE = "resource";

	private int resource;

	private String hello;// = "hello android";

	static DefaultFragment newInstance(int resource, String s) {
		DefaultFragment newFragment = new DefaultFragment();
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
		TextView title = (TextView) view.findViewById(R.id.title);
		if (title != null) {
			title.setText(hello);
		}

		ListView listView = (ListView) view.findViewById(R.id.contact);
		if (listView != null) {
			Context context = view.getContext();

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
					map.put("ItemTitle", contactName.trim());
					map.put("ItemText", phoneNumber.trim());
					listData.add(map);
				}
				phoneCursor.close();
			} 

//			//生成适配器的Item和动态数组对应的元素  
//			SimpleAdapter listItemAdapter = new SimpleAdapter(context, listData,//数据源   
//					R.layout.contact,//ListItem的XML实现  
//					//动态数组与ImageItem对应的子项          
//					new String[] {"ItemImage","ItemTitle", "ItemText"}, 
//					//ImageItem的XML文件里面的一个ImageView,两个TextView ID  
//					new int[] {R.id.ItemImage, R.id.ItemTitle, R.id.ItemText}
//					) {
//				public View getView(int position, View convertView, ViewGroup parent) {  
//					return null;
//				}
//			};  
//
//			//添加并且显示  
//			listView.setAdapter(listItemAdapter);
			
			MyListAdapter myAdapter = new MyListAdapter(context, listData);
			listView.setAdapter(myAdapter);
		}
		return view;
	}  

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "TestFragment-----onDestroy");
	}
}  


class ContactsActivity extends ListActivity {  

	Context mContext = null;  

	/**获取库Phon表字段**/  
	private static final String[] PHONES_PROJECTION = new String[] {Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };  

	/**联系人显示名称**/  
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;  

	/**电话号码**/  
	private static final int PHONES_NUMBER_INDEX = 1;  

	/**头像ID**/  
	private static final int PHONES_PHOTO_ID_INDEX = 2;  

	/**联系人的ID**/  
	private static final int PHONES_CONTACT_ID_INDEX = 3;  


//	/**联系人名称**/  
//	private ArrayList<String> mContactsName = new ArrayList<String>();  
//
//	/**联系人头像**/  
//	private ArrayList<String> mContactsNumber = new ArrayList<String>();  
//
//	/**联系人头像**/  
//	private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();  
	List<Map<String, Object>> listData = new LinkedList<Map<String,Object>>();
	

	ListView mListView = null;  
	MyListAdapter myAdapter = null;  

	@Override  
	public void onCreate(Bundle savedInstanceState) {  
		mContext = this;  
		mListView = this.getListView();  
		/**得到手机通讯录联系人信息**/  
		getPhoneContacts();  

		myAdapter = new MyListAdapter(this, listData);
		setListAdapter(myAdapter);  


		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  

			@Override  
			public void onItemClick(AdapterView<?> adapterView, View view,  
					int position, long id) {  
				//调用系统方法拨打电话  
//				Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri  
//						.parse("tel:" + mContactsNumber.get(position)));  
				Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri  
						.parse("tel:" + listData.get(position).get("ItemText")));  
				startActivity(dialIntent);  
			}  
		});  

		super.onCreate(savedInstanceState);  
	}  

	/**得到手机通讯录联系人信息**/  
	private void getPhoneContacts() {  
		ContentResolver resolver = mContext.getContentResolver();  

		// 获取手机联系人  
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  


		if (phoneCursor != null) {  
			while (phoneCursor.moveToNext()) {  

				//得到手机号码  
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
				//当手机号码为空的或者为空字段 跳过当前循环  
				if (TextUtils.isEmpty(phoneNumber))  
					continue;  

				//得到联系人名称  
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  

				//得到联系人ID  
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);  

				//得到联系人头像ID  
				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);  

				//得到联系人头像Bitamp  
				Bitmap contactPhoto = null;  

				//photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的  
				if(photoid > 0 ) {  
					Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);  
					InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);  
					contactPhoto = BitmapFactory.decodeStream(input);  
				}else {  
					contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
				}  

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ItemImage", contactPhoto);//图像资源的ID
				map.put("ItemTitle", contactName.trim());
				map.put("ItemText", phoneNumber.trim());
				listData.add(map);
//				mContactsName.add(contactName);  
//				mContactsNumber.add(phoneNumber);  
//				mContactsPhonto.add(contactPhoto);  
			}  

			phoneCursor.close();  
		}  
	}  

	/**得到手机SIM卡联系人人信息**/  
	private void getSIMContacts() {  
		ContentResolver resolver = mContext.getContentResolver();  
		// 获取Sims卡联系人  
		Uri uri = Uri.parse("content://icc/adn");  
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,  
				null);  

		if (phoneCursor != null) {  
			while (phoneCursor.moveToNext()) {  

				// 得到手机号码  
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
				// 当手机号码为空的或者为空字段 跳过当前循环  
				if (TextUtils.isEmpty(phoneNumber))  
					continue;  
				// 得到联系人名称  
				String contactName = phoneCursor  
						.getString(PHONES_DISPLAY_NAME_INDEX);  

				//Sim卡中没有联系人头像  

//				mContactsName.add(contactName);  
//				mContactsNumber.add(phoneNumber);  
			}  

			phoneCursor.close();  
		}  
	}  
}

class MyListAdapter extends BaseAdapter {
	
	private Context mContext;
	
	List<Map<String, Object>> listData;
	
	public MyListAdapter(Context context, List<Map<String, Object>> listData) {  
		mContext = context;
		this.listData = listData;
	}  

	public int getCount() {  
		//设置绘制数量  
		return listData.size();  
	}  

	@Override  
	public boolean areAllItemsEnabled() {  
		return false;  
	}  

	public Object getItem(int position) {  
		return position;  
	}  

	public long getItemId(int position) {  
		return position;  
	}  

	public View getView(int position, View convertView, ViewGroup parent) {  
		ImageView iamge = null;  
		TextView title = null;  
		TextView text = null;  
		if (convertView == null || position < listData.size()) {  
			convertView = LayoutInflater.from(mContext).inflate(R.layout.contact, null);  
			iamge = (ImageView) convertView.findViewById(R.id.ItemImage);  
			title = (TextView) convertView.findViewById(R.id.ItemTitle);  
			text = (TextView) convertView.findViewById(R.id.ItemText);  
		}  
		//绘制联系人名称  
		title.setText((String) listData.get(position).get("ItemTitle"));  
		//绘制联系人号码  
		text.setText((String) listData.get(position).get("ItemText"));  
		//绘制联系人头像  
		iamge.setImageBitmap((Bitmap) listData.get(position).get("ItemImage"));  
		return convertView;  
	}  

}  