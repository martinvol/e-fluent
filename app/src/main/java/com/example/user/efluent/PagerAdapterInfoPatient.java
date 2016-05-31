package com.example.user.efluent;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterInfoPatient extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public LoginManager login;

    public PagerAdapterInfoPatient(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragmentInfoPatient1 tab1 = new TabFragmentInfoPatient1();
                //login.patientList(tab1);
                return tab1;
            case 1:
                TabFragmentInfoPatient2 tab2 = new TabFragmentInfoPatient2();
/*
                login.resultsOfMyPatient(tab2, patient);
*/
                return tab2;
            case 2:
                TabFragmentInfoPatient3 tab3 = new TabFragmentInfoPatient3();
/*
                login.resultsOfMyPatient(tab3, patient);
*/
                return tab3;
            case 3:
                TabFragmentInfoPatient4 tab4 = new TabFragmentInfoPatient4();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}