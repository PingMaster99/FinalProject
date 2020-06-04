package com.example.newsapp.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.NewsLayoutBinding
import com.example.newsapp.network.News

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick
 */
class NewsAdapter( val onClickListener: OnClickListener ) :
    ListAdapter<News, NewsAdapter.RepoViewHolder>(DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RepoViewHolder {
        return RepoViewHolder.from(parent)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holderRepo: RepoViewHolder, position: Int) {
        val githubRepo = getItem(position)
        holderRepo.itemView.setOnClickListener {
            onClickListener.onClick(githubRepo)
        }
        holderRepo.bind(githubRepo)
    }

    /**
     * The RepoViewHolder constructor takes the binding variable from the associated
     * RepoItemLayout, which nicely gives it access to the full [GithubRepo] information.
     */
    class RepoViewHolder(private var binding: NewsLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(githubRepo: News) {
            binding.news = githubRepo
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : RepoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsLayoutBinding.inflate(layoutInflater, parent, false)

                return RepoViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [News]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.created_at == newItem.created_at
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