package com.example.newsapp.registered_events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.NewsLayoutBinding
import com.example.newsapp.network.News

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists. Based on MyGithub app by Oscar Gil and Android
 * Codelabs (Mars Real Estate).
 * @param onClick
 */
class RegisteredAdapter( val onClickListener: OnClickListener ) :
    ListAdapter<News, RegisteredAdapter.NewsViewHolder>(DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holderNews: NewsViewHolder, position: Int) {
        val githubRepo = getItem(position)
        holderNews.itemView.setOnClickListener {
            onClickListener.onClick(githubRepo)
        }
        holderNews.bind(githubRepo)
    }

    /**
     * The RepoViewHolder constructor takes the binding variable from the associated
     * NewsItemLayout, which nicely gives it access to the full [News] information.
     */
    class NewsViewHolder(private var binding: NewsLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.news = news
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : NewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsLayoutBinding.inflate(layoutInflater, parent, false)

                return NewsViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [News]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [News]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [News]
     */
    class OnClickListener(val clickListener: (news: News) -> Unit) {
        fun onClick(news: News) = clickListener(news)
    }
}