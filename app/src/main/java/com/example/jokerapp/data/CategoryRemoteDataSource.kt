package com.example.jokerapp.data


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Onde acontecera a chamada para o servidor

class CategoryRemoteDataSource {

    fun findAllCategories (callBack: ListCategoryCallBack) {
        HTTPClient.retrofit()
            .create(ChuckNorrisAPI::class.java)
            .findAllCategories(HTTPClient.API_KEY)
            .enqueue(object : Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    if(response.isSuccessful) {
                        val objCategories = response.body()
                        callBack.onSuccess(objCategories ?: emptyList())
                        callBack.onComplete()
                    } else {
                        val error = response.errorBody()?.string()
                        callBack.onError(error ?: "Erro desconhecido")
                        callBack.onComplete()
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    callBack.onError(t.message ?:"Erro interno")
                    callBack.onComplete()
                }

            })
    }

}