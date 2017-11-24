package com.iunin.demo.platformdemo.displayinfosetting;


import android.support.v4.app.Fragment;

/**
 * Created by copo on 17-11-22.
 */

public class DisplayItem {
    private String name;
    private Fragment fragment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public DisplayItem(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }
}
