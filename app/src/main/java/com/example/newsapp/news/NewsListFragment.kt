package com.example.newsapp.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.NewsListFragmentBinding

/**
 * <h1>NewsListFragment</h1>
 *<p>
 * Fragment that displays a RecyclerView with news
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 1.0
 * @since 2020-06-02
 **/
class NewsListFragment : Fragment() {

    private lateinit var viewModelFactory: NewsListViewModelFactory
    private lateinit var viewModel: NewsListViewModel
    private lateinit var binding: NewsListFragmentBinding

    /**
     * Builds the app initialization displays the information requested
     * @param savedInstanceState saved App data
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Noticias relacionadas"
        (activity as AppCompatActivity).supportActionBar?.subtitle =
            "Presione una noticia para visitar el enlace"
        binding = NewsListFragmentBinding.inflate(inflater)
        return binding.root
    }
    /**
     * Builds the app initialization displays the information requested
     * @param savedInstanceState saved App data
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        val args: NewsListFragmentArgs by navArgs<NewsListFragmentArgs>()
        viewModelFactory = NewsListViewModelFactory(args.query, args.username, args.points)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsListViewModel::class.java)
        binding.viewModel = viewModel
        binding.newsList.adapter = NewsAdapter(NewsAdapter.OnClickListener{
            viewModel.openNewsUrl(it)
        })

        viewModel.currentNews.observe(viewLifecycleOwner, Observer {
            openNewsPage(it.name, it.type[0].substringBefore(" "), it.formatted_date)
        })
    }

    // Opens a new page if url exists

    private fun openNewsPage(name: String, recipient: String, date: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("$recipient@uvg.edu.gt"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Confirmo asistencia del evento $name")
        intent.putExtra(Intent.EXTRA_TEXT, "Confirmo mi asistencia en el evento $name de la" +
                " fecha $date.\n\n Saludos cordiales, \n \n\n")
        try {
            startActivity(Intent.createChooser(intent, "Elija su aplicación de correo"))
        } catch (e: Exception) {
            Toast.makeText(this.context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
