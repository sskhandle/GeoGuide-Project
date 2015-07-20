package com.cs442_skatkar.geoguidemod1.Adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs442_skatkar.geoguidemod1.R;
import com.cs442_skatkar.geoguidemod1.beans.Category;
//import com.androidopentutorials.spfavorites.utils.SharedPreference;

public class CategoryListAdapter extends ArrayAdapter<Category> {

	private Context context;
	List<Category> categories;
	//SharedPreference sharedPreference;

	public CategoryListAdapter(Context context, List<Category> categories) {
		super(context, R.layout.category_list_item, categories);
		this.context = context;
		this.categories = categories;
		//sharedPreference = new SharedPreference();
	}

	private class ViewHolder {
		TextView categoryNameTxt;
		//TextView productDescTxt;
		//TextView productPriceTxt;
		ImageView categoryImg;
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Category getItem(int position) {
		return categories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.category_list_item, null);
			holder = new ViewHolder();
			holder.categoryNameTxt = (TextView) convertView
					.findViewById(R.id.txt_c_name);
			holder.categoryImg = (ImageView) convertView
					.findViewById(R.id.imgbtn_category);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

        Category category = (Category) getItem(position);
		holder.categoryNameTxt.setText(category.getName());
        if(category.getName() == "ATM"){
            holder.categoryImg.setImageResource(R.drawable.atm);
        }else if(category.getName() == "Bank"){
            holder.categoryImg.setImageResource(R.drawable.bank);
        }else if(category.getName() == "Airport"){
            holder.categoryImg.setImageResource(R.drawable.airport);
        }else if(category.getName() == "Church"){
            holder.categoryImg.setImageResource(R.drawable.church);
        }else if(category.getName() == "Doctor"){
            holder.categoryImg.setImageResource(R.drawable.doctor);
        }else if(category.getName() == "Hospital"){
            holder.categoryImg.setImageResource(R.drawable.hospital);
        }else if(category.getName() == "Bus Station"){
            holder.categoryImg.setImageResource(R.drawable.busstation);
        }else if(category.getName() == "Movie Theater"){
            holder.categoryImg.setImageResource(R.drawable.cinema);
        }else if(category.getName() == "Restaurant"){
            holder.categoryImg.setImageResource(R.drawable.restaurant);
        }

		return convertView;
	}

	@Override
	public void add(Category product) {
		super.add(product);
		categories.add(product);
		notifyDataSetChanged();
	}

	@Override
	public void remove(Category product) {
		super.remove(product);
        categories.remove(product);
		notifyDataSetChanged();
	}
}

