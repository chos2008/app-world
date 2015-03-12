/*
 * TestFragment.java
 * 
 * 
 * 
 * @author ada
 * @version 1.0  2008Äê10ÔÂ25ÈÕ
 */
package com.example.world;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.chos.world.ContactModel;

import com.alan.roundimageview.Example2Activity;
import com.example.world.MainFragmentActivity.MyOnClickListener;
import com.makeramen.example.ExampleActivity;

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
    
    private static int MY_SCAN_REQUEST_CODE = 100; // arbitrary int
    
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
        Log.d(TAG, "MoreFragment-----onCreateView");
        View view = inflater.inflate(resource, container, false);
        
        TextView yihaodian = (TextView) view.findViewById(R.id.yihaodian);
        yihaodian.setOnClickListener(new TheStoreOnClickListener(view));
        
        TextView tgou = (TextView) view.findViewById(R.id.tgou);
        tgou.setOnClickListener(new TianGouOnClickListener(view));
        
        TextView pull_refresh = (TextView) view.findViewById(R.id.pull_refresh);
        pull_refresh.setOnClickListener(new PullRefreshOnClickListener(view));
        
        TextView bluetooth = (TextView) view.findViewById(R.id.bluetooth);
        bluetooth.setOnClickListener(new JoystickOnClickListener(view));
        
        TextView joystick = (TextView) view.findViewById(R.id.joystick);
        joystick.setOnClickListener(new JoystickOnClickListener(view));
        
        TextView remoteControl = (TextView) view.findViewById(R.id.remoteControl);
        remoteControl.setOnClickListener(new RemoteControlOnClickListener(view));
        
        TextView touchControl = (TextView) view.findViewById(R.id.touchControl);
        touchControl.setOnClickListener(new TouchControlOnClickListener(view));
        
        TextView scan = (TextView) view.findViewById(R.id.scan);
        scan.setOnClickListener(new ScanOnClickListener(view));
        
        TextView examples = (TextView) view.findViewById(R.id.example1);
        examples.setOnClickListener(new ExamplesOnClickListener(view));
        
        TextView example2 = (TextView) view.findViewById(R.id.example2);
        example2.setOnClickListener(new Example2OnClickListener(view));
        
        TextView scanCard = (TextView) view.findViewById(R.id.scanCard);
        scanCard.setOnClickListener(new ScanCardOnClickListener(view));
        return view;
    }  

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MoreFragment-----onDestroy");
    }
    
    
    public class TianGouOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public TianGouOnClickListener(View view) {
        	this.view = view;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
        	Context context = view.getContext();
			Intent intent = new Intent(context, TianGou.class);
        	context.startActivity(intent);
        }  
    };
    
    public class TheStoreOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public TheStoreOnClickListener(View view) {
        	this.view = view;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
        	Context context = view.getContext();
			Intent intent = new Intent(context, TheStore.class);
        	context.startActivity(intent);
        } 
    };
    
    public class PullRefreshOnClickListener implements View.OnClickListener {  
    	
    	private View view;
    	
    	public PullRefreshOnClickListener(View view) {
    		this.view = view;
    	}
    	
    	@Override  
    	public void onClick(View v) {
    		System.out.println("on click " + v.getId());
    		Context context = view.getContext();
    		Intent intent = new Intent(context, RefreshableViewActivity.class);
    		context.startActivity(intent);
    	}  
    };
    
    public class BluetoochOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public BluetoochOnClickListener(View view) {
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
    
    public class JoystickOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public JoystickOnClickListener(View view) {
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
    
    public class RemoteControlOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public RemoteControlOnClickListener(View view) {
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
    
    public class TouchControlOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public TouchControlOnClickListener(View view) {
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
    
    public class ScanOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public ScanOnClickListener(View view) {
        	this.view = view;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
        	Context context = view.getContext();
			Intent intent = new Intent(context, CaptureFragmentActivity.class);
        	context.startActivity(intent);
        }  
    };
    

    
    public class ScanCardOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public ScanCardOnClickListener(View view) {
        	this.view = view;
        }
        
        public void onScanPress(View v) {
        	Context context = view.getContext();
    		// This method is set up as an onClick handler in the layout xml
    		// e.g. android:onClick="onScanPress"

    		Intent scanIntent = new Intent(context, CardIOActivity.class);

    		// customize these values to suit your needs.
    		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: true
    		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
    		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

    		// hides the manual entry button
    		// if set, developers should provide their own manual entry mechanism in the app
    		scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false

    		// MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
    		startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    	}
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
        	Context context = view.getContext();
        	
        	onScanPress(view);
        }  
    }
    
    public class ExamplesOnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public ExamplesOnClickListener(View view) {
        	this.view = view;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
        	Context context = view.getContext();
			Intent intent = new Intent(context, ExampleActivity.class);
        	context.startActivity(intent);
        }  
    };
    
    public class Example2OnClickListener implements View.OnClickListener {  
        
    	private View view;
    	
        public Example2OnClickListener(View view) {
        	this.view = view;
        }
        
        @Override  
        public void onClick(View v) {
        	System.out.println("on click " + v.getId());
        	Context context = view.getContext();
			Intent intent = new Intent(context, Example2Activity.class);
        	context.startActivity(intent);
        }  
    };

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		String resultStr;
		if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
			CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

			// Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
			resultStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

			// Do something with the raw number, e.g.:
			// myService.setCardNumber( scanResult.cardNumber );

			if (scanResult.isExpiryValid()) {
				resultStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n"; 
			}

			if (scanResult.cvv != null) { 
				// Never log or display a CVV
				resultStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
			}

			if (scanResult.postalCode != null) {
				resultStr += "Postal Code: " + scanResult.postalCode + "\n";
			}
		}
		else {
			resultStr = "Scan was canceled.";
		}
//		resultTextView.setText(resultStr);
	};
}