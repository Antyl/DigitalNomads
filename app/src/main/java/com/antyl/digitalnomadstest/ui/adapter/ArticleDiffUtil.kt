package com.antyl.digitalnomadstest.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.antyl.digitalnomadstest.model.room.ArticleEntity

class ArticleDiffUtil : DiffUtil.ItemCallback<ArticleEntity>() {

    override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
        return oldItem.title == newItem.title && oldItem.publishedAt == newItem.publishedAt
    }
}