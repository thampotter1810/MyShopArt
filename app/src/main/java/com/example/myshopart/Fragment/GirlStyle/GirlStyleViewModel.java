package com.example.myshopart.Fragment.GirlStyle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GirlStyleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GirlStyleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}