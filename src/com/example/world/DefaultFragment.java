/*
 * TestFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008��11��25��
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

					//�õ��ֻ�����  
					String phoneNumber = phoneCursor.getString(ContactModel.PHONES_NUMBER_INDEX);
					//���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��  
					if (TextUtils.isEmpty(phoneNumber)) {
						continue;
					}

					//�õ���ϵ������  
					String contactName = phoneCursor.getString(ContactModel.PHONES_DISPLAY_NAME_INDEX);

					//�õ���ϵ��ID  
					Long contactid = phoneCursor.getLong(ContactModel.PHONES_CONTACT_ID_INDEX);

					//�õ���ϵ��ͷ��ID  
					Long photoid = phoneCursor.getLong(ContactModel.PHONES_PHOTO_ID_INDEX);

					//�õ���ϵ��ͷ��Bitamp  
					Bitmap contactPhoto = null;

					//photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�  
					if(photoid > 0 ) {
						Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
						InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
						contactPhoto = BitmapFactory.decodeStream(input);
					} else {
						contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
					}

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ItemImage", contactPhoto);//ͼ����Դ��ID
					map.put("ItemTitle", contactName.trim());
					map.put("ItemText", phoneNumber.trim());
					listData.add(map);
				}
				phoneCursor.close();
			} 

//			//������������Item�Ͷ�̬�����Ӧ��Ԫ��  
//			SimpleAdapter listItemAdapter = new SimpleAdapter(context, listData,//����Դ   
//					R.layout.contact,//ListItem��XMLʵ��  
//					//��̬������ImageItem��Ӧ������          
//					new String[] {"ItemImage","ItemTitle", "ItemText"}, 
//					//ImageItem��XML�ļ������һ��ImageView,����TextView ID  
//					new int[] {R.id.ItemImage, R.id.ItemTitle, R.id.ItemText}
//					) {
//				public View getView(int position, View convertView, ViewGroup parent) {  
//					return null;
//				}
//			};  
//
//			//��Ӳ�����ʾ  
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

	/**��ȡ��Phon���ֶ�**/  
	private static final String[] PHONES_PROJECTION = new String[] {Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };  

	/**��ϵ����ʾ����**/  
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;  

	/**�绰����**/  
	private static final int PHONES_NUMBER_INDEX = 1;  

	/**ͷ��ID**/  
	private static final int PHONES_PHOTO_ID_INDEX = 2;  

	/**��ϵ�˵�ID**/  
	private static final int PHONES_CONTACT_ID_INDEX = 3;  


//	/**��ϵ������**/  
//	private ArrayList<String> mContactsName = new ArrayList<String>();  
//
//	/**��ϵ��ͷ��**/  
//	private ArrayList<String> mContactsNumber = new ArrayList<String>();  
//
//	/**��ϵ��ͷ��**/  
//	private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();  
	List<Map<String, Object>> listData = new LinkedList<Map<String,Object>>();
	

	ListView mListView = null;  
	MyListAdapter myAdapter = null;  

	@Override  
	public void onCreate(Bundle savedInstanceState) {  
		mContext = this;  
		mListView = this.getListView();  
		/**�õ��ֻ�ͨѶ¼��ϵ����Ϣ**/  
		getPhoneContacts();  

		myAdapter = new MyListAdapter(this, listData);
		setListAdapter(myAdapter);  


		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  

			@Override  
			public void onItemClick(AdapterView<?> adapterView, View view,  
					int position, long id) {  
				//����ϵͳ��������绰  
//				Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri  
//						.parse("tel:" + mContactsNumber.get(position)));  
				Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri  
						.parse("tel:" + listData.get(position).get("ItemText")));  
				startActivity(dialIntent);  
			}  
		});  

		super.onCreate(savedInstanceState);  
	}  

	/**�õ��ֻ�ͨѶ¼��ϵ����Ϣ**/  
	private void getPhoneContacts() {  
		ContentResolver resolver = mContext.getContentResolver();  

		// ��ȡ�ֻ���ϵ��  
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  


		if (phoneCursor != null) {  
			while (phoneCursor.moveToNext()) {  

				//�õ��ֻ�����  
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
				//���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��  
				if (TextUtils.isEmpty(phoneNumber))  
					continue;  

				//�õ���ϵ������  
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  

				//�õ���ϵ��ID  
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);  

				//�õ���ϵ��ͷ��ID  
				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);  

				//�õ���ϵ��ͷ��Bitamp  
				Bitmap contactPhoto = null;  

				//photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�  
				if(photoid > 0 ) {  
					Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);  
					InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);  
					contactPhoto = BitmapFactory.decodeStream(input);  
				}else {  
					contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
				}  

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ItemImage", contactPhoto);//ͼ����Դ��ID
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

	/**�õ��ֻ�SIM����ϵ������Ϣ**/  
	private void getSIMContacts() {  
		ContentResolver resolver = mContext.getContentResolver();  
		// ��ȡSims����ϵ��  
		Uri uri = Uri.parse("content://icc/adn");  
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,  
				null);  

		if (phoneCursor != null) {  
			while (phoneCursor.moveToNext()) {  

				// �õ��ֻ�����  
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
				// ���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��  
				if (TextUtils.isEmpty(phoneNumber))  
					continue;  
				// �õ���ϵ������  
				String contactName = phoneCursor  
						.getString(PHONES_DISPLAY_NAME_INDEX);  

				//Sim����û����ϵ��ͷ��  

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
		//���û�������  
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
		//������ϵ������  
		title.setText((String) listData.get(position).get("ItemTitle"));  
		//������ϵ�˺���  
		text.setText((String) listData.get(position).get("ItemText"));  
		//������ϵ��ͷ��  
		iamge.setImageBitmap((Bitmap) listData.get(position).get("ItemImage"));  
		return convertView;  
	}  

}  