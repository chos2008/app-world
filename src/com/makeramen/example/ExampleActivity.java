package com.makeramen.example;

import com.example.world.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

@SuppressLint("NewApi")
public class ExampleActivity extends Activity implements ActionBar.OnNavigationListener {

  @Override 
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActionBar ab = getActionBar();
    ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    
    SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(
            ab.getThemedContext(), 
            R.array.action_list, 
            android.R.layout.simple_spinner_dropdown_item);
    ab.setListNavigationCallbacks(spinnerAdapter, this);

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .replace(android.R.id.content, new RoundedFragment())
          .commit();
    }

  }

  @Override 
  public boolean onNavigationItemSelected(int itemPosition, long itemId) {

    Fragment newFragment;
    switch (itemPosition) {
      default:
      case 0:
        newFragment = RoundedFragment.getInstance(false);
        break;
      case 1:
        newFragment = RoundedFragment.getInstance(true);
        break;
      case 2:
        newFragment = new ColorFragment();
        break;
//      case 3:
//    	  newFragment = new PicassoFragment();
//    	  break;
    }

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(android.R.id.content, newFragment);
       fragmentTransaction.commit();

    return true;
  }
}
