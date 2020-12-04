package com.antyl.digitalnomadstest.presenter.view

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.antyl.digitalnomadstest.model.room.ArticleEntity

interface ArticleListView {
    fun setLiveData(liveData: LiveData<PagedList<ArticleEntity>>)
    fun onDataError(error: String)
    fun onDataEnded()
    fun showLoading()
    fun hideLoading()
}