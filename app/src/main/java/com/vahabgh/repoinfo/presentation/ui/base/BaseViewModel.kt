package com.vahabgh.repoinfo.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val showLoading = MutableLiveData(true)

    val errorMessage = MutableLiveData<String>("The Operation Failed")


    open fun showProgress(){
        showLoading.postValue(true)
    }

    open fun hideProgress(){
        showLoading.postValue(false)
    }

    open fun setErrorMessage(message : String?){
        if (message.isNullOrEmpty()) return
        errorMessage.postValue(message)
    }
}
