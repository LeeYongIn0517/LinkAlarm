package com.jaknaeso.linkalarm.ui.home.view

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jaknaeso.linkalarm.databinding.FragmentHomeBinding
import com.jaknaeso.linkalarm.model.AudioFileModel
import com.jaknaeso.linkalarm.ui.home.FileRecyclerAdapter
import com.jaknaeso.linkalarm.ui.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val binding get() = _binding!!
    // 파일 선택 창 실행
    private val pickAudioLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            displaySelectedAudioInfo(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = FileRecyclerAdapter(emptyList())

        val fileObserver = object : Observer<List<AudioFileModel>>{
            override fun onChanged(value: List<AudioFileModel>) {
                Log.d("HomeFragment","value:${value}")
                binding.recyclerView.adapter = FileRecyclerAdapter(value)
            }
        }

        viewModel.audioFiles.observe(viewLifecycleOwner, fileObserver)

        return binding.root
    }

// 오디오 파일 선택을 위한 메서드
    fun addNewAudioFile(view: View) {
        pickAudioLauncher.launch("audio/*")
    }

    // 선택된 오디오 파일 정보 표시
    private fun displaySelectedAudioInfo(uri: Uri) {
        val context = requireContext()
        val fileName = getFileNameFromUri(context, uri)
        val fileSize = getFileSize(context, uri)
        val duration = getAudioDuration(context, uri)
        viewModel.addNewAudioFiles(AudioFileModel(name = fileName, size = fileSize, duration = duration, uri = uri))
    }

    // URI에서 파일 이름을 가져오는 함수
    private fun getFileNameFromUri(context: Context, uri: Uri): String {
        var fileName = "알 수 없는 파일"
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                fileName = cursor.getString(nameIndex)
            }
        }
        return fileName
    }

    fun getAudioDuration(context: Context, uri: Uri): String {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(context, uri)
            val durationMs = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0L
            retriever.release()

            // 밀리초(ms) -> 초(s) 변환
            val seconds = (durationMs / 1000) % 60
            val minutes = (durationMs / 1000) / 60
            "%02d:%02d".format(minutes, seconds) // MM:SS 형식 리턴
        } catch (e: Exception) {
            retriever.release()
            "알 수 없음"
        }
    }

    fun getFileSize(context: Context, uri: Uri): String {
        var fileSize = "알 수 없음"
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst() && sizeIndex != -1) {
                val sizeInBytes = cursor.getLong(sizeIndex)
                fileSize = "%.2f MB".format(sizeInBytes / (1024.0 * 1024.0)) // MB 단위 변환
            }
        }
        return fileSize
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
