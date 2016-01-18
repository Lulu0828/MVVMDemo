package com.lulu.mvvmdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.lulu.mvvmdemo.databinding.FragmentMainBinding;

/**
 *
 * Created by Lu on 2016/1/12.
 */
public class MainActivityFragment extends Fragment{

    // Android Studio会根据Layout的名称自动生成ViewModel类，
    // 比如fragment_main.xml会自动生成一个FragmentMainBinding类，
    // ActivityMainBinding中的方法，会根据layout中的属性自动生成。
    private FragmentMainBinding mBinding;

    private MainModel mViewModel;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mBinding = FragmentMainBinding.bind(view);
        mViewModel = new MainModel(this, getResources());
        mBinding.setData(mViewModel);
        attachButtonListener();
        return view;
    }

    private void attachButtonListener() {
        mBinding.loginOrCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.logInClicked();
            }
        });

        mBinding.returningUserRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mViewModel.isExistingUserChecked.set(isChecked);
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ensureModelDataIsLodaded();
    }

    private void ensureModelDataIsLodaded() {
        if (!mViewModel.isLoaded()) {
            mViewModel.loadAsync();
        }
    }

    public void showShortToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
