package com.example.sampleapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sampleapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dataViewModel: DataViewModel
    private lateinit var recyclerViewAdapter: CustomRecyclerViewAdapter
    private lateinit var recyclerViewScrollListener: CustomRecyclerViewScrollListener
    private var page = 1
    private var totalPageCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]

        recyclerViewAdapter = CustomRecyclerViewAdapter(dataViewModel.characterData.value)
        binding.rvList.adapter = recyclerViewAdapter

        recyclerViewScrollListener = object : CustomRecyclerViewScrollListener() {
            override fun loadData() {
                if (page <= totalPageCount) {
                    dataViewModel.getCharacters(page)
                }
            }

            override fun checkBuffer(): Boolean {
                return page <= totalPageCount
            }

            override fun checkLoad(): Boolean {
                return dataViewModel.characterDataStatus.value == DataStatus.LOADING
            }

            override fun onPageChanged(position: Int) {
                binding.tvPosition.text = getString(R.string.scroll_position, position + 1)
            }

        }
        binding.rvList.addOnScrollListener(recyclerViewScrollListener)

        dataViewModel.infoDataStatus.observe(this) { dataStatus ->
            dataStatus?.let {
                val countText: String
                when (it) {
                    DataStatus.FETCHED -> {
                        totalPageCount = dataViewModel.infoData.value?.pages ?: 0
                        countText = (dataViewModel.infoData.value?.count ?: -1).toString()
                        dataViewModel.getCharacters(page)
                    }
                    DataStatus.LOADING -> {
                        countText = "?"
                    }
                    DataStatus.NOT_FOUND -> {
                        countText = "0"
                    }
                    DataStatus.ERROR -> {
                        countText = "-1"
                    }
                }
                val text = getString(R.string.character_count, countText)
                binding.tvInfo.text = text
            }
        }

        dataViewModel.characterDataStatus.observe(this) { dataStatus ->
            dataStatus?.let {
                recyclerViewAdapter.dataStatus = it
                when (it) {
                    DataStatus.FETCHED -> {
                        page++
                        binding.pbLoading.visibility = View.GONE
                        recyclerViewAdapter.notifyDataSetChanged() // TODO: notify last page interval
                    }
                    DataStatus.LOADING -> {
                        binding.pbLoading.visibility = View.VISIBLE
                    }
                    DataStatus.NOT_FOUND -> {
                        if (page <= 1) {
                            recyclerViewAdapter.notifyItemChanged(0)
                        }
                        binding.pbLoading.visibility = View.GONE
                        Toast.makeText(this@MainActivity, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        if (page <= 1) {
                            recyclerViewAdapter.notifyItemChanged(0)
                        }
                        binding.pbLoading.visibility = View.GONE
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