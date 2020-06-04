package com.example.newsapp

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.network.News
import com.example.newsapp.news.NewsAdapter

/**
 * Allows to submit a list to be displayed on the RecyclerView
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<News>?) {
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data)
}