package io.finefabric.ninebanana.fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by laszlo on 2017-08-29.
 */
class FragmentsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private lateinit var fragments: ArrayList<Fragment>

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    fun setFragments(fragments: ArrayList<Fragment>){
        this.fragments = fragments
    }


}