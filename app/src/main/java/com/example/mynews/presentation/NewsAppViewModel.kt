package com.example.mynews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.data.NewRepo
import com.example.mynews.data.model.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsAppViewModel: ViewModel(){
    val repo = NewRepo()
    private val _state = MutableStateFlow(AppState())
    val state =_state.asStateFlow()


    init{
        getHeadLines()
    }

    fun getHeadLines(country:String="us" ){
        viewModelScope.launch{
            repo.getHeadLine(country).collectLatest{
                if(it.loading == true){
                    _state.value = AppState(loading = true)
                }else if(it.error.isNullOrBlank().not()){
                    _state.value = AppState(error = it.error)
                }else{
                    _state.value = AppState(data = it.data,loading = false)
                }
            }
        }
    }
    fun getEveryThing(q:String="us" ){
        viewModelScope.launch{
            repo.getEveryThing( q).collectLatest{
                if(it.loading == true){
                    _state.value = AppState(loading = true)
                }else if(it.error.isNullOrBlank().not()){
                    _state.value = AppState(error = it.error)
                }else{
                    _state.value = AppState(data = it.data,loading = false)
                }
            }
        }
    }
}

data class AppState(
    var loading : Boolean? = false,
    var error: String?= "",
    var data: ApiResponse?=null
)