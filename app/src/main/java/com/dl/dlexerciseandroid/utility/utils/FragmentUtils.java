package com.dl.dlexerciseandroid.utility.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by dannylin on 2016/12/2.
 */

public class FragmentUtils {

    public static Fragment getFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass, String tag) {
        Fragment fragment = fm.findFragmentByTag(tag);

        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();

            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return fragment;
    }

    public static void addFragmentTo(FragmentManager fm, Class<? extends Fragment> fragmentClass,
                                     int containerId, String fragmentTag) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(fragmentTag);

        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                transaction.add(containerId, fragment, fragmentTag);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        transaction.commit();
    }

    public static void replaceFragmentTo(FragmentManager fm, Class<? extends Fragment> fragmentClass,
                                         int containerId, String fragmentTag) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment fragment = getFragment(fm, fragmentClass, fragmentTag);
        fragmentTransaction.replace(containerId, fragment, fragmentTag);

        fragmentTransaction.commit();
    }
}
