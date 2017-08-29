package io.finefabric.ninebanana.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import io.finefabric.ninebanana.R
import kotlinx.android.synthetic.main.container_fragment.*

/**
 * Created by laszlo on 2017-08-29.
 */
class ContainerFragment: Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.container_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        bottom_navigation.setTitleTextSizeInSp(10f,10f)
        bottom_navigation.addItem(AHBottomNavigationItem("Stats", R.drawable.ic_fiber_manual_record_black_24dp))
        bottom_navigation.addItem(AHBottomNavigationItem("Achievements", R.drawable.ic_fiber_manual_record_black_24dp))
        bottom_navigation.addItem(AHBottomNavigationItem("Top", R.drawable.ic_fiber_manual_record_black_24dp))
        bottom_navigation.addItem(AHBottomNavigationItem("Options", R.drawable.ic_fiber_manual_record_black_24dp))
        bottom_navigation.addItem(AHBottomNavigationItem("Donate", R.drawable.ic_fiber_manual_record_black_24dp))
    }
}