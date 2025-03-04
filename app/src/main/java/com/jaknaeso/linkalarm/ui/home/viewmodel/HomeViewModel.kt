package com.jaknaeso.linkalarm.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaknaeso.linkalarm.model.AudioFileModel

class HomeViewModel : ViewModel() {

    private var _audioFiles = MutableLiveData<List<AudioFileModel>>()
    val audioFiles : LiveData<List<AudioFileModel>>
        get() = _audioFiles

    fun addNewAudioFiles(item:AudioFileModel){
        val updatedList = _audioFiles.value.orEmpty() + item //새로운 리스트 생성
        _audioFiles.value = updatedList
    }
}
