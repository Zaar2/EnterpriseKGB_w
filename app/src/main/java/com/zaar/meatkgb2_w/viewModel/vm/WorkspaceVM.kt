package com.zaar.meatkgb2_w.viewModel.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zaar.meatkgb2_w.data.UserDescription
import com.zaar.meatkgb2_w.model.repository.LocalDBRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WorkspaceVM(myContext: Context): BaseVM(myContext) {

    private val mldUserDescription = MutableLiveData<UserDescription>()
    fun ldUserDescription(): LiveData<UserDescription> = mldUserDescription

    private val mldProductsList = MutableLiveData<List<String>>()
    fun ldProductsList(): LiveData<List<String>> = mldProductsList

    private val mldMeProduct = MutableLiveData<String>()
    fun ldMeProduct(): LiveData<String> = mldMeProduct

    fun initDescriptionUser() {
        viewModelScope.launch {
            mldUserDescription.value = async(Dispatchers.IO) {
                LocalDBRepositoryImpl(myContext).getUserDescription()
            }.await()
        }
    }

    fun initItemsSpinnerProduct() {
        viewModelScope.launch {
            mldProductsList.value = async(Dispatchers.IO) {
                LocalDBRepositoryImpl(myContext).getProduct()
            }.await()
        }
    }

    fun getProductMe(nameProduct: String){
        viewModelScope.launch {
            mldMeProduct.value = async(Dispatchers.IO) {
                LocalDBRepositoryImpl(myContext).getMeByProduct(nameProduct)
            }.await()
        }
    }
}