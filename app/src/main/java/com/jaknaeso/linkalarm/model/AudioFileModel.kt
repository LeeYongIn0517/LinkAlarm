package com.jaknaeso.linkalarm.model

import android.net.Uri

data class AudioFileModel(
    val name:String,
    val size:String,
    val duration:String,
    val uri: Uri
)
