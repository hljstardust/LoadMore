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

import com.hao.loadmore.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @ClassName: PeopleAdapter
 * @Description: 填充器
 * @Author Yoson Hao
 * @WebSite www.haoyuexing.cn
 * @Email haoyuexing@gmail.com
 * @Date 2013-6-13 下午4:33:57
 * 
 */
public class PeopleAdapter extends BaseAdapter {
	private Context context;
	private List<People> data = new ArrayList<People>();

	public PeopleAdapter(List<People> data, Context context) {
		super();
		this.data = data;
		this.context = context;
	}

	public void setList(List<People> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		People people = data.get(arg0);
		View view;
		if (arg1 == null) {
			LayoutInflater layout = LayoutInflater.from(context);

			view = layout.inflate(R.layout.list_item, null);
		} else {
			// Log.i(TAG,"使用历史缓存view对象");
			view = arg1;
		}
		TextView t1 = (TextView) view.findViewById(R.id.firstName);
		t1.setText(people.getFirstName());
		TextView t2 = (TextView) view.findViewById(R.id.lastName);
		t2.setText(people.getLastName());
		TextView t3 = (TextView) view.findViewById(R.id.email);
		t3.setText(people.getEmail());
		return view;
	}

}
