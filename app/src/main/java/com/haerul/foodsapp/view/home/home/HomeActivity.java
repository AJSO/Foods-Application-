/*-----------------------------------------------------------------------------
 - Developed by Haerul Muttaqin                                               -
 - Last modified 3/24/19 10:35 PM                                             -
 - Subscribe : https://www.youtube.com/haerulmuttaqin                         -
 - Copyright (c) 2019. All rights reserved                                    -
 -----------------------------------------------------------------------------*/
package com.haerul.foodsapp.view.home.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.haerul.foodsapp.R;
import com.haerul.foodsapp.Utils;
import com.haerul.foodsapp.adapter.RecyclerViewHomeAdapter;
import com.haerul.foodsapp.adapter.ViewPagerHeaderAdapter;
import com.haerul.foodsapp.model.Categories;
import com.haerul.foodsapp.model.Meals;
import com.haerul.foodsapp.view.category.CategoryActivity;


import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


// TODO 31 implement the HomeView interface at the end
public class HomeActivity extends AppCompatActivity implements HomeView {

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";

    /*
     * TODO 32 Add @BindView Annotation (1)
     *
     * 1. viewPagerHeader
     * 2. recyclerCategory
     *
     */
    @BindView(R.id.viewPagerHeader) ViewPager viewPagerMeal;
    @BindView(R.id.recyclerCartegory) RecyclerView recyclerViewCategory;

    /*
     *  TODO 33 Create variable for presenter;
     */
    HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*
         *  TODO 34 bind the ButterKnife (2)
         */
        ButterKnife.bind(this);

        /*
         *  TODO 35 Declare the presenter
         *  new HomePresenter(this)
         */
        presenter = new HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();

    }

    @Override
    public void showLoading() {

        findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {

        findViewById(R.id.shimmerMeal).setVisibility(View.GONE);
        findViewById(R.id.shimmerCategory).setVisibility(View.GONE);
    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {

        //Check for the results
       /* for (Meals.Meal mealResult: meal){
            Log.w("meal name:", mealResult.getStrMeal());

            OK, EVERTHING WORKS FINE
        }*/
       ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meal, this);
       viewPagerMeal.setAdapter(headerAdapter);
       viewPagerMeal.setPadding(20, 0 , 150, 0);
       headerAdapter.notifyDataSetChanged();

       headerAdapter.setOnItemClickListener((v, position) -> {

           Toast.makeText(this,meal.get(position).getStrMeal(), Toast.LENGTH_LONG).show();

       });

    }

    @Override
    public void setCategory(List<Categories.Category> category) {
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        //recyclerViewCategory.setClipToPadding(false);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();

        homeAdapter.setOnItemClickListener((view, position) -> {

           // Toast.makeText(this,category.get(position).getStrCategory(), Toast.LENGTH_LONG).show();
            //TODO 8: Add extra data (put to intent)
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
            intent.putExtra(EXTRA_POSITION, position);
            startActivity(intent);

        });
    }

    @Override
    public void onErrorLoading(String message) {
       Utils.showDialogMessage(this, "Title", message);

    }

    // TODO 36 Overriding the interface

}
