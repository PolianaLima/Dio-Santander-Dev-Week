package me.dio.bankline.data

import android.util.Log
import androidx.lifecycle.liveData
import me.dio.bankline.data.remote.BanklineApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BankLineRepository {

    val TAG = javaClass.simpleName

    private val restApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://bankline-santander.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BanklineApi::class.java)
    }

    fun findBankStatement(accountHolderId: Int) = liveData {
        emit(State.Wait)
        try {
            emit(State.Sucess(data = restApi.findBankStatement(accountHolderId)))
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            emit(State.Error(e.message))
        }
    }
}