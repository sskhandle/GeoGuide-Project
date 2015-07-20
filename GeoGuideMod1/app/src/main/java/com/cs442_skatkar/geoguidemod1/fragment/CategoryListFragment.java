package com.cs442_skatkar.geoguidemod1.fragment;

/**
 * Created by Trofey on 4/18/2015.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cs442_skatkar.geoguidemod1.R;
import com.cs442_skatkar.geoguidemod1.Adapter.CategoryListAdapter;
import com.cs442_skatkar.geoguidemod1.beans.Category;

public class CategoryListFragment extends Fragment{

    public static final String ARG_ITEM_ID = "category_list";

    Activity activity;
    ListView productListView;
    List<Category> categories;
    CategoryListAdapter categoryListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container,
                false);
        findViewsById(view);

        setCategories();

        categoryListAdapter = new CategoryListAdapter(activity, categories);
        productListView.setAdapter(categoryListAdapter);
        return view;
    }

    private void setCategories() {

        Category c1 = new Category(1, "ATM");
        Category c2 = new Category(2, "Bank");

        categories = new ArrayList<Category>();
        categories.add(c1);
        categories.add(c2);

    }

    private void findViewsById(View view) {
        productListView = (ListView) view.findViewById(R.id.list_category);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }
}
