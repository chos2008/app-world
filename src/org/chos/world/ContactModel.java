/*
 * ContactModel.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008年10月25日
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

	/**联系人显示名称**/  
	public static final int PHONES_DISPLAY_NAME_INDEX = 0;  

	/**电话号码**/  
	public static final int PHONES_NUMBER_INDEX = 1;  

	/**头像ID**/  
	public static final int PHONES_PHOTO_ID_INDEX = 2;  

	/**联系人的ID**/  
	public static final int PHONES_CONTACT_ID_INDEX = 3;  
}
