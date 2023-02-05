package com.example.jokerapp.data

import com.example.jokerapp.model.Joke
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException


class JokeDayRemoteDataSource {
    fun findRandom(callBack : JokeCallBack) {
        HTTPClient.retrofit()
            .create(ChuckNorrisAPI::class.java)
            .findRandom()
            .enqueue(object : Callback<Joke> {
                override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                    if(response.isSuccessful) {
                        val joke = response.body()
                        callBack.onSuccess(joke ?: throw RuntimeException("Piada Não encontrada"))
                        callBack.onComplete()
                    } else {
                        val error = response.errorBody()?.string()
                        callBack.onError(error ?: "Erro desconhecido")
                        callBack.onComplete()
                    }                }

                override fun onFailure(call: Call<Joke>, t: Throwable) {
                    callBack.onError(t.message ?:"Erro interno")
                    callBack.onComplete()                }

            })

    }
}