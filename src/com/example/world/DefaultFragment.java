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
					map.put("ItemTitle", contactName);
					map.put("ItemText", phoneNumber);
					listData.add(map);
				}
				phoneCursor.close();
			} 
	
			for(int i=0; i<10; i++) {
				Map<String, Object> map = new HashMap<String, Object>();  
				map.put("ItemImage", R.drawable.ic_launcher);//ͼ����Դ��ID  
				map.put("ItemTitle", "Level "+i);  
				map.put("ItemText", "Finished in 1 Min 54 Secs, 70 Moves! ");  
				listData.add(map);  
			}
	
			//������������Item�Ͷ�̬�����Ӧ��Ԫ��  
			SimpleAdapter listItemAdapter = new SimpleAdapter(context, listData,//����Դ   
					R.layout.contact,//ListItem��XMLʵ��  
					//��̬������ImageItem��Ӧ������          
					new String[] {"ItemImage","ItemTitle", "ItemText"},   
					//ImageItem��XML�ļ������һ��ImageView,����TextView ID  
					new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}  
					);  
	
			//��Ӳ�����ʾ  
			listView.setAdapter(listItemAdapter);
        }
        return view;
    }  

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "TestFragment-----onDestroy");
    }
}  