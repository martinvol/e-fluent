package com.example.user.efluent;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterPatient extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public LoginManager login;

    public PagerAdapterPatient(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragmentPatient1 tab1 = new TabFragmentPatient1();
                tab1.login = login;
                login.getListOfExercises(tab1);
                return tab1;
            case 1:
                TabFragmentPatient2 tab2 = new TabFragmentPatient2();
                login.getListOfMeetings(tab2);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}