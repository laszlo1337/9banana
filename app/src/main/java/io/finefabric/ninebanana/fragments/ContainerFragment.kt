package io.finefabric.ninebanana.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import io.finefabric.ninebanana.R
import kotlinx.android.synthetic.main.layout_fragment_container.*

/**
 * Created by laszlo on 2017-08-29.
 */
class ContainerFragment: Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_fragment_container, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        val fragments = arrayListOf<Fragment>(GoalsFragment(), SettingsFragment())
        val adapter = FragmentsPagerAdapter(activity.supportFragmentManager)
        adapter.setFragments(fragments)
        fragment_view_pager.adapter = adapter

        bottom_navigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

        bottom_navigation.addItem(AHBottomNavigationItem("Goals", R.drawable.ic_fiber_manual_record_black_24dp))
        bottom_navigation.addItem(AHBottomNavigationItem("Leaderboard", R.drawable.ic_fiber_manual_record_black_24dp))
        bottom_navigation.addItem(AHBottomNavigationItem("Options", R.drawable.ic_fiber_manual_record_black_24dp))
        bottom_navigation.addItem(AHBottomNavigationItem("Donate", R.drawable.ic_fiber_manual_record_black_24dp))

        bottom_navigation.setOnTabSelectedListener { position, wasSelected ->
            fragment_view_pager.setCurrentItem(position, false)
            true
        }
    }
}