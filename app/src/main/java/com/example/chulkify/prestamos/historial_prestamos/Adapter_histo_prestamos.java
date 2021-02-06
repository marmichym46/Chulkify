package com.example.chulkify.prestamos.historial_prestamos;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chulkify.transacciones_pg.reportes.Fragment_histo_ap;
import com.example.chulkify.transacciones_pg.reportes.Fragment_histo_rt;
import com.example.chulkify.transacciones_pg.reportes.Fragment_histo_tran;

public class Adapter_histo_prestamos extends FragmentPagerAdapter {

    public Adapter_histo_prestamos(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new Fragment_historial_prestamo();
        }
        return null;

    }

    @Override
    public int getCount() {

        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "historial_prestamo";

    }
        return null;

    }
}

