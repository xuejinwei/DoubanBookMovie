package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base;

import android.support.v4.app.Fragment;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.ApiFactory;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.ApiWrapper;

/**
 * Created by xuejinwei on 16/3/24.
 */
public class BaseFragment extends Fragment{
    public static final ApiWrapper mApiWrapper = ApiFactory.getApiWrapper();

}
