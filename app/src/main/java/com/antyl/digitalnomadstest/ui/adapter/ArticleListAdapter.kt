package com.antyl.digitalnomadstest.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antyl.digitalnomadstest.R
import com.antyl.digitalnomadstest.model.room.ArticleEntity
import com.antyl.digitalnomadstest.ui.listener.OnArticleClickListener
import com.antyl.digitalnomadstest.ui.listener.OnRepeatClickListener
import com.bumptech.glide.Glide

class ArticleListAdapter(private val onArticleClickListener: OnArticleClickListener,
                         private val onRepeatClickListener: OnRepeatClickListener) :
    PagedListAdapter<ArticleEntity, RecyclerView.ViewHolder>(ArticleDiffUtil()) {

    private val DATA_VIEW = 1
    private val FOOTER_VIEW = 2
    private var state: ListState = ListState.LOADING

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW else FOOTER_VIEW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW) NormalViewHolder.create(parent) else FooterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW) {
            holder as NormalViewHolder
            getItem(position)?.let { article ->
                holder.bind(article)
                holder.itemView.setOnClickListener {
                    onArticleClickListener.onArticleClick(article.url)
                }
            }
        } else (holder as FooterViewHolder).bind(onRepeatClickListener, state)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == ListState.LOADING || state == ListState.ERROR)
    }

    fun setState(state: ListState) {
        this.state = state
        notifyDataSetChanged()
    }

    class NormalViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.image)
        private val titleText = view.findViewById<TextView>(R.id.title)
        private val descriptionText = view.findViewById<TextView>(R.id.description)
        private val dateText = view.findViewById<TextView>(R.id.date)

        fun bind(article : ArticleEntity){
            with(article) {
                titleText.text = title
                descriptionText.text = description.toString()
                dateText.text = publishedAt
                Glide.with(itemView)
                    .load(urlToImage)
                    .into(image)
            }
        }

        companion object {
            fun create(parent: ViewGroup): NormalViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.article_item, parent, false)
                return NormalViewHolder(view)
            }
        }
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val warning = view.findViewById<TextView>(R.id.warning)
        private val repeat = view.findViewById<TextView>(R.id.repeat)
        private val progress = view.findViewById<ProgressBar>(R.id.progressBar)

        fun bind(onRepeatClickListener: OnRepeatClickListener, state: ListState) {
            when (state) {
                ListState.LOADING -> {
                    warning.visibility = View.GONE
                    repeat.visibility = View.GONE
                    progress.visibility = View.VISIBLE
                }
                ListState.ERROR -> {
                    warning.visibility = View.VISIBLE
                    repeat.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
                ListState.ENDED -> {
                    itemView.layoutParams.height = 0
                }
            }
            repeat.setOnClickListener {
                onRepeatClickListener.onRepeatClick()
            }
        }

        companion object {
            fun create(parent: ViewGroup): FooterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.footer_item, parent, false)
                return FooterViewHolder(view)
            }
        }
    }
}