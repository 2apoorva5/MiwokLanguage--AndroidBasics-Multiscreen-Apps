package com.developerdepository.miwoklanguage.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.developerdepository.miwoklanguage.fragment.ColorsFragment;
import com.developerdepository.miwoklanguage.fragment.FamilyMembersFragment;
import com.developerdepository.miwoklanguage.fragment.NumbersFragment;
import com.developerdepository.miwoklanguage.fragment.PhrasesFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyMembersFragment();
            case 2:
                return new ColorsFragment();
            case 3:
                return new PhrasesFragment();
        }

        return new NumbersFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
