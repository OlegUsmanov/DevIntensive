package com.softdesign.devintensive.ui.activities;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ConstantManager.TAG_PREFIX + "Main Activity";

    private DataManager mDataManager;
    private int mCurrentEditMode = 0;

    private ImageView mCalling;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigatorDrawer;
    private FloatingActionButton mFab;

    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUserBio;
    private List<EditText> mUserInfoViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        mDataManager = DataManager.getInstance();
        mCalling = (ImageView) findViewById(R.id.call_img);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigatorDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mUserPhone = (EditText) findViewById(R.id.user_number);
        mUserMail = (EditText) findViewById(R.id.e_mail);
        mUserVk = (EditText) findViewById(R.id.vk);
        mUserGit = (EditText) findViewById(R.id.git);
        mUserBio = (EditText) findViewById(R.id.myself);


        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);

        mFab.setOnClickListener(this);
        setupToolBar();
        setupDrawer();
        saveUserInfoValue();


        /**
         * List<String> test = mDataManager.getPreferencesManager().loadUserProfileData();
         * loadUserInfoValue();
         **/

        if (savedInstanceState == null) {
            showSnackbar("активити загружается впервые");

        } else {
            showSnackbar("активити уже загружалось");
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentEditMode);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mNavigatorDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        saveUserInfoValue();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:

                if (mCurrentEditMode == 0) {
                    changeEditMode(1);
                    mCurrentEditMode = 1;
                } else {
                    changeEditMode(0);
                    mCurrentEditMode = 0;
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void setupToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerHeaderImg(navigationView, BitmapFactory.decodeResource(getResources(), R.drawable.test),
                getString(R.string.app_name),getString(R.string.user_email_txt));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackbar(item.getTitle().toString());
                item.setChecked(true);
                mNavigatorDrawer.closeDrawer(GravityCompat.START);
                return false;

            }

        });

    }

    private void setupDrawerHeaderImg(NavigationView parent, Bitmap ava, String name, String email) {
        View view = parent.getHeaderView(0);
        if (ava != null) {
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), ava);
            dr.setCircular(true);
            ImageView imageView = (ImageView) view.findViewById(R.id.avatar_view);
            imageView.setImageDrawable(dr);
        }
        if (name != null) {
            TextView textView = (TextView) view.findViewById(R.id.user_name_h);
            textView.setText(name);
        }
        if (email != null) {
            TextView textView = (TextView) view.findViewById(R.id.user_email_h);
            textView.setText(email);
        }
    }


    /**
     * Переключение режима редактирования, 1 - редактирование, 0 - чтение
     **/
    private void changeEditMode(int mode) {
        if (mode == 1) {
            mFab.setImageResource(R.drawable.ic_check_black_24dp);
            for (EditText userValue : mUserInfoViews) {

                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);

            }
        } else {
            mFab.setImageResource(R.drawable.ic_mode_edit_black_24dp);
            for (EditText userValue : mUserInfoViews) {

                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                saveUserInfoValue();
            }
        }
    }


    private void loadUserInfoValue() {
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue() {
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userData);
    }


}





