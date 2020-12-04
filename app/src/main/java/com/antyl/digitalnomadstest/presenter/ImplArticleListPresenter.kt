package com.antyl.digitalnomadstest.presenter

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.antyl.digitalnomadstest.model.entity.Status
import com.antyl.digitalnomadstest.model.repository.ArticlesRepository
import com.antyl.digitalnomadstest.model.room.ArticleEntity
import com.antyl.digitalnomadstest.presenter.view.ArticleListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class ImplArticleListPresenter(
    private val articleListView: ArticleListView,
    private val articlesRepository: ArticlesRepository)
    : ArticleListPresenter {

    private val disposables = CompositeDisposable()

    override fun loadArticles() {
        articleListView.showLoading()
        disposables.add(articlesRepository
                .fetchArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    articleListView.hideLoading()
                    if (it == Status.ENDED) {
                        articleListView.onDataEnded()
                    }
                }, {
                    articleListView.onDataError(it.localizedMessage)
                }))
    }

    override fun getArticlesLiveData() {
        val config = PagedList.Config.Builder()
            .setPrefetchDistance(5)
            .setPageSize(ArticlesRepository.PAGE_LIMITS)
            .build()

        disposables.add(
            articlesRepository.getArticlesFromDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val pagedListLiveData = LivePagedListBuilder(it, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .setBoundaryCallback(object : PagedList.BoundaryCallback<ArticleEntity>() {

                            override fun onItemAtEndLoaded(itemAtEnd: ArticleEntity) {
                                super.onItemAtEndLoaded(itemAtEnd)
                                loadArticles()
                            }

                            override fun onZeroItemsLoaded() {
                                super.onZeroItemsLoaded()
                                loadArticles()
                            }
                        })
                        .build()
                    articleListView.setLiveData(pagedListLiveData)
                }, {
                    articleListView.onDataError(it.localizedMessage)
                })
        )
    }

    override fun onDestroy() {
        disposables.clear()
    }
}