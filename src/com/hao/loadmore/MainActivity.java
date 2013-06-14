/**
 * Copyright (c) 2013, Yoson Hao 郝悦兴 (haoyuexing@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hao.loadmore;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.hao.loadmore.R;

/**
 * @ClassName: MainActivity
 * @Description: 主页面
 * @Author Yoson Hao
 * @WebSite www.haoyuexing.cn
 * @Email haoyuexing@gmail.com
 * @Date 2013-6-13 下午5:23:53
 * 
 */
public class MainActivity extends Activity {

	private LoadMoreListView mListView;
	private PeopleAdapter mAdapter;
	private List<People> mList;
	private FinalHttp mFinalHttp;
	private ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化
		init();
		// 显示dialog
		mDialog.setMessage("正在加载数据...");
		mDialog.setCancelable(false);
		mDialog.show();
		// 第一次加载数据
		firstLoad();
	}

	/**
	 * 初始化
	 */
	private void init() {
		// 初始化list
		mList = new ArrayList<People>();
		// 初始化fh
		mFinalHttp = new FinalHttp();
		// 初始化dialog
		mDialog = new ProgressDialog(this);
		// 得到控件
		mListView = (LoadMoreListView) findViewById(R.id.loadmorelist);
		// 设置监听
		mListView.setOnLoadMoreListener(new LoadMoreLsnr());
	}

	/**
	 * 第一次加载数据
	 */
	private void firstLoad() {
		mFinalHttp.get("http://xxapp-hao.stor.sinaapp.com/interface1.json",
				new AjaxCallBack<String>() {
					@Override
					public void onSuccess(String t) {
						mDialog.cancel();
						List<People> res = new ArrayList<People>();
						res = JSON.parseArray(t, People.class);
						for (int i = 0; i < res.size(); i++) {
							mList.add(res.get(i));
						}
						mAdapter = new PeopleAdapter(mList, MainActivity.this);
						mListView.setAdapter(mAdapter);
						super.onSuccess(t);
					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						mDialog.cancel();
						Toast.makeText(MainActivity.this, "第一次获取数据失败",
								Toast.LENGTH_SHORT).show();
						super.onFailure(t, strMsg);
					}
				});
	}

	/**
	 * 
	 * @ClassName: LoadMoreLsnr
	 * @Description: 加载更多监听
	 * @Author Yoson Hao
	 * @WebSite www.haoyuexing.cn
	 * @Email haoyuexing@gmail.com
	 * @Date 2013-6-13 下午5:27:22
	 * 
	 */
	class LoadMoreLsnr implements OnLoadMoreListener {

		@Override
		public void onLoadMore() {
			mFinalHttp.get("http://xxapp-hao.stor.sinaapp.com/interface2.json",
					new AjaxCallBack<String>() {
						@Override
						public void onSuccess(String t) {
							mDialog.dismiss();
							List<People> res = new ArrayList<People>();
							res = JSON.parseArray(t, People.class);
							for (int i = 0; i < res.size(); i++) {
								mList.add(res.get(i));
							}
							mAdapter.setList(mList);
							mAdapter.notifyDataSetChanged();
							mListView.onLoadMoreComplete();
							super.onSuccess(t);
						}

						@Override
						public void onFailure(Throwable t, String strMsg) {
							mDialog.dismiss();
							super.onFailure(t, strMsg);
						}
					});
		}
	}

}
