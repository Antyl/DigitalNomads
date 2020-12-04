package com.antyl.digitalnomadstest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.antyl.digitalnomadstest.App
import com.antyl.digitalnomadstest.R
import com.antyl.digitalnomadstest.di.component.DaggerArticleListComponent
import com.antyl.digitalnomadstest.di.module.ArticleListModule
import com.antyl.digitalnomadstest.model.room.ArticleEntity
import com.antyl.digitalnomadstest.presenter.ArticleListPresenter
import com.antyl.digitalnomadstest.presenter.view.ArticleListView
import com.antyl.digitalnomadstest.ui.adapter.ArticleListAdapter
import com.antyl.digitalnomadstest.ui.adapter.ListState
import com.antyl.digitalnomadstest.ui.listener.OnArticleClickListener
import com.antyl.digitalnomadstest.ui.listener.OnRepeatClickListener
import kotlinx.android.synthetic.main.footer_item.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import javax.inject.Inject

class ArticleListFragment : Fragment(R.layout.fragment_article_list), ArticleListView,
    OnArticleClickListener, OnRepeatClickListener {

    @Inject
    lateinit var articleListPresenter: ArticleListPresenter

    private lateinit var adapter: ArticleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        DaggerArticleListComponent.builder()
            .articleListModule(ArticleListModule(this))
            .appComponent(App.instance.getAppComponent())
            .build()
            .inject(this)

        adapter = ArticleListAdapter(this, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        articlesRecyclerview.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        articlesRecyclerview.adapter = adapter

        articleListPresenter.getArticlesLiveData()
    }

    private fun setToolbar() {
        articlesListToolbar.title = "News"
    }

    override fun setLiveData(liveData: LiveData<PagedList<ArticleEntity>>) {
        liveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onDataError(error: String) {
        if (adapter.itemCount == 0) {
            infoFrame.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            warning.visibility = View.VISIBLE
            repeat.visibility = View.VISIBLE
            repeat.setOnClickListener {
                articleListPresenter.loadArticles()
            }
        } else {
            adapter.setState(ListState.ERROR)
        }
    }

    override fun onDataEnded() {
        adapter.setState(ListState.ENDED)
    }

    override fun showLoading() {
        if (adapter.itemCount == 0) {
            infoFrame.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            warning.visibility = View.GONE
            repeat.visibility = View.GONE
        } else {
            adapter.setState(ListState.LOADING)
        }
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onArticleClick(urlToDetails: String?) {
        fragmentManager?.beginTransaction()
            ?.add(R.id.fragmentContainer, ArticleDetailsFragment.newInstance(urlToDetails),
                ArticleDetailsFragment::class.simpleName)
            ?.addToBackStack("details")
            ?.commit()
    }

    override fun onRepeatClick() {
        articleListPresenter.loadArticles()
    }
}