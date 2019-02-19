package com.example.jalpa.firebasepushnotifications;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                profileFragment ProfileFragment = new profileFragment();
                return ProfileFragment;
            case 1:
                usersFragment UsersFragment = new usersFragment();
                return UsersFragment;
            case 2:
                notificationFragment NotificationFrament = new notificationFragment();
                return NotificationFrament;

            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
