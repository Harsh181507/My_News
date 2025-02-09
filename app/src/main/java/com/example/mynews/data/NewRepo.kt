package com.example.mynews.data


import com.example.mynews.data.apiBuilder.ApiBuilder
import com.example.mynews.data.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class NewRepo {
    val apiInstance = ApiBuilder.retrofitObject()

     fun getHeadLine(country:String="us"):Flow<ApiState>{
        return flow{
            emit(ApiState(loading = true))
            try {
               val response:ApiResponse = apiInstance.getHeadLines(country = country)
                emit(ApiState(data = response))
            }catch (e:HttpException)
            {
                emit(ApiState(error = e.localizedMessage))
            }
            catch (e:Exception){
                emit(ApiState(error = e.localizedMessage))
            }

        }
    }
    fun getEveryThing(q:String="us"):Flow<ApiState>{
        return flow{
            emit(ApiState(loading = true))
            try {
                val response = apiInstance.getEveryThing(q = q)
                emit(ApiState(data = response))
            }catch (e:HttpException)
            {
                emit(ApiState(error = e.localizedMessage))
            }
            catch (e:Exception){
                emit(ApiState(error = e.localizedMessage))
            }

        }
    }
}

data class ApiState(    //Response Class
    var loading : Boolean? = false,
    var error: String?= "",
    var data: ApiResponse?=null
)