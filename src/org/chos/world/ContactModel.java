/*
 * ContactModel.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008��10��25��
 */
package org.chos.world;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;

/**
 * 
 *
 */
public interface ContactModel {

	public static final String[] PHONES_PROJECTION = 
			new String[] {Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID};

	/**��ϵ����ʾ����**/  
	public static final int PHONES_DISPLAY_NAME_INDEX = 0;  

	/**�绰����**/  
	public static final int PHONES_NUMBER_INDEX = 1;  

	/**ͷ��ID**/  
	public static final int PHONES_PHOTO_ID_INDEX = 2;  

	/**��ϵ�˵�ID**/  
	public static final int PHONES_CONTACT_ID_INDEX = 3;  
}
