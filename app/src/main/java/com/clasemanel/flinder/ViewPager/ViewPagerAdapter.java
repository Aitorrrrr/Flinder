package com.clasemanel.flinder.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.clasemanel.flinder.CartaCentro.CartasFragmentTab;
import com.clasemanel.flinder.Chat.ChatTab;
import com.clasemanel.flinder.Perfil.PerfilTab;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numeroTabs;

    public ViewPagerAdapter(FragmentManager fm, int numeroTabs) {
        super(fm);
        this.numeroTabs = numeroTabs;
    }

    @Override
    public Fragment getItem(int posicion) {
        switch (posicion) {
            case 0:
                PerfilTab tab1 = new PerfilTab();
                return tab1;
            case 1:
                CartasFragmentTab cart1 = new CartasFragmentTab();
                return cart1;
            case 2:
                ChatTab chatTab1 = new ChatTab();
                return chatTab1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numeroTabs;
    }
}
