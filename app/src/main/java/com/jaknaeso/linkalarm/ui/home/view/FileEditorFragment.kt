package com.jaknaeso.linkalarm.ui.home.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaknaeso.linkalarm.R
import com.jaknaeso.linkalarm.ui.home.viewmodel.FileEditorViewModel

class FileEditorFragment : Fragment() {

    companion object {
        fun newInstance() = FileEditorFragment()
    }

    private val viewModel: FileEditorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_file_editor, container, false)
    }
}
