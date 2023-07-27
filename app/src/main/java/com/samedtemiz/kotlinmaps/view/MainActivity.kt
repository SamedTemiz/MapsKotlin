package com.samedtemiz.kotlinmaps.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.samedtemiz.kotlinmaps.R
import com.samedtemiz.kotlinmaps.adapter.PlaceAdapter
import com.samedtemiz.kotlinmaps.databinding.ActivityMainBinding
import com.samedtemiz.kotlinmaps.model.Place
import com.samedtemiz.kotlinmaps.roomdb.PlaceDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(applicationContext, PlaceDatabase::class.java,"Places").build()
        val placeDao = db.placeDao()

        compositeDisposable.add(
            placeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(placeList : List<Place>){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PlaceAdapter(placeList)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Menüyü bağlıyoruz
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.place_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Menüde tıklanınca ne olacak
        if(item.itemId == R.id.add_place){
            val intent = Intent(this@MainActivity, MapsActivity::class.java)
            intent.putExtra("info", "new")
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}