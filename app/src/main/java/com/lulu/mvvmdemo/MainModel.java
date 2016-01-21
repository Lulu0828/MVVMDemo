package com.lulu.mvvmdemo;

import android.content.res.Resources;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.AsyncTask;
import android.view.View;
import android.widget.CompoundButton;

import java.util.Random;

/**
 *
 * Created by Lu on 2016/1/12.
 */
public class MainModel {

    public ObservableField<String> numberOfUsersLoggedIn = new ObservableField<>();
    public ObservableField<Boolean> isExistingUserChecked = new ObservableField<>();
    public ObservableField<Integer> emailBlockVisibility = new ObservableField<>();
    public ObservableField<String> loginOrCreateButtonText = new ObservableField<>();

    private boolean mIsLoaded;

    private MainActivityFragment mView;
    private Resources mResources;

    public MainModel(MainActivityFragment view, Resources resources) {
        mView = view;
        mResources = resources;
        setInitialState();
        updateDependentViews();
        hookUpDependencies();
    }

    private void hookUpDependencies() {
        isExistingUserChecked.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                updateDependentViews();
            }
        });
    }

    private void updateDependentViews() {
        if (isExistingUserChecked.get()) {
            emailBlockVisibility.set(View.GONE);
            loginOrCreateButtonText.set(mResources.getString(R.string.log_in));
        } else {
            emailBlockVisibility.set(View.VISIBLE);
            loginOrCreateButtonText.set(mResources.getString(R.string.create_user));
        }
    }

    private void setInitialState() {
        numberOfUsersLoggedIn.set("...");
        isExistingUserChecked.set(true);
    }

    public void loginOrCreateButtonOnClick(View view) {
        if (isExistingUserChecked.get()) {
            mView.showShortToast("Invalid username or password");
        } else {
            mView.showShortToast("Please enter a valid email address");
        }
    }

    public CompoundButton.OnCheckedChangeListener checkedChange = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isExistingUserChecked.set(isChecked);
        }
    };

//    public TextWatcher watcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            loginOrCreateButtonText.set(s.toString());
//        }
//    };

    public boolean isLoaded() {
        return  mIsLoaded;
    }

    public void loadAsync() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                numberOfUsersLoggedIn.set("" + new Random().nextInt(1000));
                mIsLoaded = true;
                return null;
            }
        }.execute((Void) null);
    }

}
