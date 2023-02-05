package com.example.jokerapp.presentation

import android.graphics.Color
import com.example.jokerapp.data.CategoryRemoteDataSource
import com.example.jokerapp.data.ListCategoryCallBack
import com.example.jokerapp.model.Category
import com.example.jokerapp.view.HomeFragment

class HomePresenter (
    private val view:HomeFragment,
    private val dataSource: CategoryRemoteDataSource
) : ListCategoryCallBack {

    fun findAllCategories () {
        view.showProgress()
        dataSource.findAllCategories(this)
    }

    override fun onSuccess(response: List<String>) {
        val start = 40
        val end = 190
        val diff = (end - start) / response.size
        val categories = response.mapIndexed { index, s ->
            val hsv = floatArrayOf(
                start + (diff * index).toFloat(),
                100.0f,
                100.0f,
            )
            Category(s, Color.HSVToColor(hsv).toLong())
        }

        view.showCategories(categories)
            }

    override fun onComplete () {
        view.hideProgress()
    }

    override fun onError(messager : String) {
        view.showFailure(messager)
    }
}