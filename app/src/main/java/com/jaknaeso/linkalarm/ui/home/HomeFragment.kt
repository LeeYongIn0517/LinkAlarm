package com.jaknaeso.linkalarm.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaknaeso.linkalarm.databinding.FragmentHomeBinding
import com.jaknaeso.linkalarm.model.AudioFileModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel:HomeViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.recyclerView.adapter = FileRecyclerAdapter(emptyList())

        val fileObserver = object : Observer<List<AudioFileModel>>{
            override fun onChanged(value: List<AudioFileModel>) {
                binding.recyclerView.adapter = FileRecyclerAdapter(value)
            }
        }

        viewModel.audioFiles.observe(viewLifecycleOwner, fileObserver)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
