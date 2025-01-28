package com.jaknaeso.linkalarm.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaknaeso.linkalarm.databinding.AudioFileItemBinding
import com.jaknaeso.linkalarm.model.AudioFileModel

class FileRecyclerAdapter(val audioFiles:List<AudioFileModel> ):RecyclerView.Adapter<FileRecyclerAdapter.ViewHolder>(){
    inner class ViewHolder(private val binding:AudioFileItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(audioFile:AudioFileModel){
            binding.name.text = audioFile.name
            binding.size.text = audioFile.size
            binding.time.text = audioFile.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(AudioFileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return audioFiles.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, p1: Int) {
        viewHolder.bind(audioFile = audioFiles.get(p1))
    }
}
