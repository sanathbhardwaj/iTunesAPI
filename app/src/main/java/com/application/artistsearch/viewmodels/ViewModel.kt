package com.application.artistsearch.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.artistsearch.repositories.Repository
import com.example.sanathitunes.models.Model

class ViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository?=null
    var modelListLiveData : LiveData<Model>?=null

    init {
        repository = Repository()
        modelListLiveData = MutableLiveData()
    }

    fun getResults(term:String){
        modelListLiveData = repository?.getResults(term)
    }
}