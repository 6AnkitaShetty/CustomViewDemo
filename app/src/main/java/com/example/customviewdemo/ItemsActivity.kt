package com.example.customviewdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.app_bar_items.*
import kotlinx.android.synthetic.main.content_items.*

class ItemsActivity : AppCompatActivity() ,NavigationView.OnNavigationItemSelectedListener {

    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val noteRecyclerAdapter by lazy {
        NoteRecyclerAdapter(this)
    }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }

    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val activityIntent = Intent(this, NoteActivity::class.java)
            startActivity(activityIntent)
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        displayNotes()

    }
    override fun onResume() {
        super.onResume()
        noteRecyclerAdapter.notifyDataSetChanged()
    }

    private fun displayNotes() {
        recyclerItems.layoutManager = noteLayoutManager
        recyclerItems.adapter = noteRecyclerAdapter
        nav_view.menu.findItem(R.id.nav_notes).isChecked = true
    }

    fun displayCourses() {
        recyclerItems.layoutManager = courseLayoutManager
        recyclerItems.adapter = courseRecyclerAdapter
        nav_view.menu.findItem(R.id.nav_courses).isChecked = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                showSnackbar("Display settings")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_courses -> {
                displayCourses()
            }
            R.id.nav_share -> {
                showSnackbar("Don't you think you've shared enough")
            }
            R.id.nav_send -> {
                showSnackbar("Send")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(recyclerItems, message, Snackbar.LENGTH_LONG).show()
    }
}