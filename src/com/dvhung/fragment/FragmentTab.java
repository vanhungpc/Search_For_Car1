package com.dvhung.fragment;

import java.util.ArrayList;

import com.example.search_for_car.R;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class FragmentTab extends Fragment {

	TabHost mTabHost;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Fragment fragment = new AddFragmentModel();
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		ft.replace(R.id.pager, fragment);
		ft.commit();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tab, container,
				false);

		Fragment fragment = new AddFragmentModel();
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		ft.replace(R.id.pager, fragment);
		ft.commit();

		mTabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
		mTabHost.setup();

		TextView textTab = new TextView(getActivity());
		textTab.setText("Model");
		textTab.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

		TextView textTab1 = new TextView(getActivity());
		textTab1.setText("Detail");

		textTab1.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

		TextView textTab2 = new TextView(getActivity());
		textTab2.setText("Images");

		textTab2.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

		textTab.setTextSize(16);
		textTab1.setTextSize(16);
		textTab2.setTextSize(16);

		textTab.setTextColor(Color.WHITE);
		textTab1.setTextColor(Color.WHITE);
		textTab2.setTextColor(Color.WHITE);
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);

		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
		mTabsAdapter.addTab(mTabHost.newTabSpec("Model").setIndicator(textTab),
				AddFragmentModel.class, null);
		mTabsAdapter.addTab(mTabHost.newTabSpec("Detail")
				.setIndicator(textTab1), AddFragmentDetail.class, null);
		mTabsAdapter.addTab(mTabHost.newTabSpec("Images")
				.setIndicator(textTab2), AddFragmentImages.class, null);
		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
			mTabHost.getTabWidget()
					.getChildAt(i)
					.setBackgroundDrawable(
							getResources().getDrawable(R.drawable.tab_selector));

		}

		return rootView;
	}

	public static class TabsAdapter extends FragmentPagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(Fragment fragment, TabHost tabHost, ViewPager pager) {
			super(fragment.getActivity().getSupportFragmentManager());
			mContext = fragment.getActivity();
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			// Unfortunately when TabHost changes the current tab, it kindly
			// also takes care of putting focus on it when not in touch mode.
			// The jerk.
			// This hack tries to prevent this from pulling focus out of our
			// ViewPager.
			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}
}