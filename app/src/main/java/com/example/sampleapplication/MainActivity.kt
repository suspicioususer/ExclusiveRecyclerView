package com.example.sampleapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.exclusiverecyclerview.ExclusiveDataStatus
import com.example.exclusiverecyclerview.ExclusiveRecyclerViewScrollCallback
import com.example.exclusiverecyclerview.model.DataProcessor
import com.example.sampleapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dataViewModel: DataViewModel
    private var page = 1
    private var totalPageCount = 0
    private var loadCount = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]

        binding.ervList.setList(dataViewModel.characterData.value as ArrayList<DataProcessor>?)
        binding.ervList.setLoadCount(loadCount)

        val exclusiveRecyclerViewScrollCallback = object : ExclusiveRecyclerViewScrollCallback {
            override fun loadData() {
                if (page <= totalPageCount) {
                    dataViewModel.getCharacters(page)
                }
            }

            override fun checkBuffer(): Boolean {
                return page <= totalPageCount
            }

            override fun checkLoad(): Boolean {
                return dataViewModel.characterDataStatus.value == ExclusiveDataStatus.LOADING
            }
        }

        binding.ervList.setCallback(exclusiveRecyclerViewScrollCallback)

        dataViewModel.infoDataStatus.observe(this) { dataStatus ->
            dataStatus?.let {
                when (it) {
                    DataStatus.FETCHED -> {
                        totalPageCount = dataViewModel.infoData.value?.pages ?: 0
                        binding.ervList.setCharacterCount(dataViewModel.infoData.value?.count ?: -1)
                        dataViewModel.getCharacters(page)
                    }
                    DataStatus.LOADING -> {
                        binding.ervList.setCharacterCount(0)
                    }
                    DataStatus.NOT_FOUND -> {
                        binding.ervList.setCharacterCount(0)
                    }
                    DataStatus.ERROR -> {
                        binding.ervList.setCharacterCount(-1)
                    }
                }
            }
        }

        dataViewModel.characterDataStatus.observe(this) { dataStatus ->
            dataStatus?.let {
                binding.ervList.setDataStatus(it)
                when (it) {
                    ExclusiveDataStatus.FETCHED -> {
                        page++
                        binding.ervList.refreshPageRange(page - 1)
                        binding.ervList.illustrateScroll()
                        binding.ervList.hideProgressBar()
                    }
                    ExclusiveDataStatus.LOADING -> {
                        binding.ervList.showProgressBar()
                    }
                    ExclusiveDataStatus.NOT_FOUND -> {
                        if (page <= 1) {
                            binding.ervList.refreshIndex(0)
                        }
                        binding.ervList.hideProgressBar()
                        Toast.makeText(this@MainActivity, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        if (page <= 1) {
                            binding.ervList.refreshIndex(0)
                        }
                        binding.ervList.hideProgressBar()
                        Toast.makeText(this@MainActivity, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        dataViewModel.getInfo()
    }

}