package com.antyl.digitalnomadstest.model.repository

import androidx.paging.DataSource
import com.antyl.digitalnomadstest.App
import com.antyl.digitalnomadstest.Utils
import com.antyl.digitalnomadstest.model.entity.ArticlesResponse
import com.antyl.digitalnomadstest.model.entity.Status
import com.antyl.digitalnomadstest.model.room.ArticleEntity
import com.antyl.digitalnomadstest.network.NetworkService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImplArticlesRepository: ArticlesRepository {

    private val disposables = CompositeDisposable()

    private var page: Int = 1
    private var isEnded: Boolean = false

    init {
        disposables.add(
            App.instance.getAppDatabase().articlesDao().getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty() && it.size > ArticlesRepository.PAGE_LIMITS) {
                        page = it.size / ArticlesRepository.PAGE_LIMITS
                        if (page >= 5)
                            isEnded = true
                    }
                }, {
                })
        )
    }

    override fun fetchArticles(): Single<Status> {
        return Single.create { observer ->
            if (isEnded) {
                observer.onSuccess(Status.ENDED)
                return@create
            }
            NetworkService()
                .getInstance()
                .getServerAPI()
                .getArticles(page)
                .enqueue(object : Callback<ArticlesResponse> {
                    override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                        observer.onError(Error("Response error"))
                    }

                    override fun onResponse(
                        call: Call<ArticlesResponse>,
                        response: Response<ArticlesResponse>
                    ) {
                        if (response.code() == 200 && response.body()?.status == "ok") {
                            val articles = response.body()!!.articles.map {
                                it.publishedAt = Utils.formatDate(it.publishedAt)
                                return@map it
                            }

                            disposables.add(
                                App.instance.getAppDatabase().articlesDao()
                                    .insertArticles(articles)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe{
                                        if (page >= 5)
                                            isEnded = true
                                        page++
                                        observer.onSuccess(Status.SUCCESS)
                                    }
                            )
                        } else {
                            observer.onError(Error("Unknown Error"))
                        }
                    }
                })
        }
    }

    override fun getArticlesFromDatabase(): Single<DataSource.Factory<Int, ArticleEntity>> {
        return Single.create {
            it.onSuccess(App.instance.getAppDatabase().articlesDao().getArticlesRoom())
        }
    }
}