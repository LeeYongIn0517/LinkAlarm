package com.jaknaeso.linkalarm.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaknaeso.linkalarm.model.AudioFileModel

class HomeViewModel : ViewModel() {

    private var _audioFiles = MutableLiveData<List<AudioFileModel>>()
    val audioFiles : LiveData<List<AudioFileModel>>
        get() = _audioFiles

    private var files = mutableListOf<AudioFileModel>()
    init {
        files = arrayListOf(
            AudioFileModel("O Holy Night","30.0KB","3:51"),
            AudioFileModel("Santa Tell Me","20.1KB","3:43"),
            AudioFileModel("Proud Corazon","16.0KB","2:45"),
            AudioFileModel("You've got a friend in me","15.6KB","2:36")
        )
        _audioFiles.postValue(files)
    }
}
