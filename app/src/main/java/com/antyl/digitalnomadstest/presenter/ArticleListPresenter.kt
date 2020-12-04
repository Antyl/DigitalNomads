package com.antyl.digitalnomadstest.presenter

interface ArticleListPresenter {
    fun getArticlesLiveData()
    fun loadArticles()
    fun onDestroy()
}