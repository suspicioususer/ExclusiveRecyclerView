package com.example.sampleapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapplication.model.DataResponse
import com.example.sampleapplication.model.Information
import com.example.sampleapplication.model.Result
import com.example.sampleapplication.network.ConnectionManager
import com.example.sampleapplication.network.DataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel: ViewModel() {

    private val TAG = "DataVM"
    private var dataSource: DataSource = ConnectionManager.INSTANCE.create(DataSource::class.java)

    var infoData = MutableLiveData<Information>()
    var infoDataStatus= MutableLiveData<DataStatus?>()

    var characterData = MutableLiveData<ArrayList<Result>>(ArrayList())
    var characterDataStatus = MutableLiveData<DataStatus>()
    var characterExclusiveDataStatus2 = MutableLiveData<com.example.exclusiverecyclerview.ExclusiveDataStatus>()

    fun getInfo() {
        infoDataStatus.value = DataStatus.LOADING
        dataSource.getInfo().enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val result = response.body()
                val info = result?.info
                info?.let {
                    infoData.value = it
                    infoDataStatus.value= DataStatus.FETCHED
                } ?: run {
                    infoDataStatus.value = DataStatus.NOT_FOUND
                }
            }

            override fun onFailure(call: Call<DataResponse>, error: Throwable) {
                infoDataStatus.value = DataStatus.ERROR
                Log.d(TAG, "onFailure: ", error)
            }
        })
    }

    fun getCharacters(index: Int) {
        //characterDataStatus.value = DataStatus.LOADING
        characterExclusiveDataStatus2.value = com.example.exclusiverecyclerview.ExclusiveDataStatus.LOADING
        dataSource.getPage(index).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val result = response.body()
                val list = result?.results
                list?.let {
                    val baseList = characterData.value
                    baseList?.addAll(it)
                    characterData.value = baseList
                    //characterDataStatus.value= DataStatus.FETCHED
                    characterExclusiveDataStatus2.value= com.example.exclusiverecyclerview.ExclusiveDataStatus.FETCHED
                } ?: run {
                    //characterDataStatus.value = DataStatus.NOT_FOUND
                    characterExclusiveDataStatus2.value = com.example.exclusiverecyclerview.ExclusiveDataStatus.NOT_FOUND
                }
            }

            override fun onFailure(call: Call<DataResponse>, error: Throwable) {
                //characterDataStatus.value = DataStatus.ERROR
                characterExclusiveDataStatus2.value = com.example.exclusiverecyclerview.ExclusiveDataStatus.ERROR
                Log.d(TAG, "onFailure: ", error)
            }
        })
    }
}