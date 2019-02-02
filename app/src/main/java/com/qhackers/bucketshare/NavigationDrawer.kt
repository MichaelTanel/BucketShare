package com.qhackers.bucketshare

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem


class NavigationDrawer : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            // TODO ic_menu
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val baseTransaction = supportFragmentManager.beginTransaction()
        baseTransaction.add(R.id.content_frame, ResourceFragment())
        baseTransaction.commit()

        mDrawerLayout = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            print("DEBUG: menu item is: ")
            println(menuItem)

            val fragment = when (menuItem.toString()) {
                "Maps" -> MapFragment()
                "Resources" -> ResourceFragment()
                else -> MapFragment()
            }

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.content_frame, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}