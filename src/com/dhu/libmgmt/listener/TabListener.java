package com.dhu.libmgmt.listener;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.widget.Toast;

import com.dhu.libmgmt.R;
import com.dhu.libmgmt.fragment.TabFragment;

public class TabListener implements ActionBar.TabListener {
	private TabFragment mFragment;

	public TabListener(TabFragment fragment) {
		mFragment = fragment;
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.add(R.id.fragment_content, mFragment, mFragment.getText());
		mFragment.refresh();
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(mFragment);
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Toast.makeText(this.mFragment.getActivity(), "Reselected!",
				Toast.LENGTH_SHORT).show();
	}

}
