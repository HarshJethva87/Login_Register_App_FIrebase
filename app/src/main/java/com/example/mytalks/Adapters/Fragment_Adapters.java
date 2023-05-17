package com.example.mytalks.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mytalks.Fragments.Calls_Fragment;
import com.example.mytalks.Fragments.Chat_Fragment;
import com.example.mytalks.Fragments.Status_Fragment;

public class Fragment_Adapters extends FragmentPagerAdapter {

    public Fragment_Adapters(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) // Fragment Call karvaa mate use Thay Chee
    {
        switch (position) {

            case 0 : return new Chat_Fragment();

            case 1 : return new Status_Fragment();

            case 2 :return  new Calls_Fragment();

            default: return new Chat_Fragment();


    }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) // Page Titale Name Set Karava And Display karava Matee
    {

        String Title = null;

        if (position == 0)
        {
            Title = "CHATS";
        }
        if (position == 1)
        {
            Title = "STATUS";
        }
        if (position == 2)
        {
            Title = "CALLS";
        }
        return Title;
    }
}
