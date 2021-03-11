package com.application.artistsearch

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.application.artistsearch.Adapters.MainViewAdapter
import com.application.artistsearch.viewmodels.ViewModel
import com.example.sanathitunes.AppDatabase
import com.example.sanathitunes.Data
import com.example.sanathitunes.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel
    private lateinit var adapter: MainViewAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var editText: EditText
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recycler)
        editText = findViewById(R.id.search)
        progressBar = findViewById(R.id.progressBar)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        editText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                progressBar.visibility = View.VISIBLE
                initAdapter()

                if (isNetworkAvailable()) {
                    showToast("online")

                    searchOnline(editText?.text.toString())
                } else {
                    showToast("offline")
                    GlobalScope.launch(Dispatchers.Main) { searchOffline(editText?.text.toString()) }
                }
                return@OnEditorActionListener true
            }
            false
        })
    }
    private suspend fun searchOffline(term: String) {
        progressBar.visibility = View.GONE
        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, term
        ).build()
        val resultDao = db.resultsDao()
        val data: List<Data> = resultDao.loadAllBySearchResult()
        val itemList:MutableList<Result> = ArrayList()
        var i = 0
        while (i < data.size) {
            itemList.add(Result(artistName = data[i].artist.toString(), trackName = data[i].song.toString(), artworkUrl100 =  data[i].image.toString()))
            i++
        }
        adapter.setData(itemList as ArrayList<Result>)
    }

    private fun searchOnline(term: String) {
        viewModel.getResults(term)
        viewModel.modelListLiveData?.observe(this, Observer {
            if (it != null) {
                progressBar.visibility = View.GONE
                GlobalScope.launch (Dispatchers.Main) {
                    var i = 0
                    while (i < it.resultCount) {
                        updateOfflineData(term, i, it.results[i].artistName, it.results[i].trackName, it.results[i].artworkUrl100)
                        i++
                    }
                }
                adapter.setData(it.results as ArrayList<Result>)
            } else {
                showToast("Something went wrong")
            }
        })
    }
    private suspend fun updateOfflineData(term: String, id:Int, artist:String, song:String, image:String) {
        var data = Data(id ,artist,song,image)
        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, term
        ).build()
        val resultDao = db.resultsDao()
        resultDao.insertBySearchResult(data)
    }
    private fun initAdapter() {
        adapter = MainViewAdapter(this)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}