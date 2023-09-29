package com.example.eval8.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.eval8.DataApplication
import com.example.eval8.network.Data
import com.example.eval8.repository.DataRepository
import kotlinx.coroutines.launch

enum class ApiStatus {
    SUCCESS, FAILURE, LOADING
}

class DataViewModel(
    private val dataRepo: DataRepository
) : ViewModel() {

    private var _allData = MutableLiveData<List<Data>>()
    val allData get() = _allData

    private var _status = MutableLiveData<ApiStatus>()
    val status get() = _status

    init {
        _status.value = ApiStatus.LOADING
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                _allData.value = dataRepo.fetchData()
                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                println("EXCEPTION : $e")
                _status.value = ApiStatus.FAILURE

            }
        }
    }
}


class DataViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        this
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            val app = application as DataApplication
            return DataViewModel(app.container.dataRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}