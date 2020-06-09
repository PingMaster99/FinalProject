package com.example.newsapp.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsListFragmentBinding
import com.example.newsapp.network.AlgoliaApi
import com.example.newsapp.network.AlgoliaApiService
import com.example.newsapp.network.Website
import com.example.newsapp.registered_events.RegisteredFragmentDirections
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.selects.select

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
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(NewsListFragmentDirections.actionNewsListFragmentToHomeFragment(0))
            }

        })
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = "Eventos disponibles"
        (activity as AppCompatActivity).supportActionBar?.subtitle =
            "Presione un evento para inscribirse"
        binding = NewsListFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }
    /**
     * Builds the app initialization displays the information requested
     * @param savedInstanceState saved App data
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this

        viewModelFactory = NewsListViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsListViewModel::class.java)
        binding.viewModel = viewModel
        binding.newsList.adapter = NewsAdapter(NewsAdapter.OnClickListener{
            viewModel.openNewsUrl(it)
        })

        viewModel.currentNews.observe(viewLifecycleOwner, Observer {
            sendConfirmationEmail(it.name, it.type[0].substringBefore(" "), it.formatted_date, it.hours)
        })
    }

    // Sends the confirmation email

    private fun sendConfirmationEmail(name: String, recipient: String, date: String, hours:String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("$recipient@uvg.edu.gt"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Confirmo asistencia del evento $name")
        intent.putExtra(Intent.EXTRA_TEXT, "Confirmo mi asistencia en el evento $name de la" +
                " fecha $date.\n\n Saludos cordiales, \n \n\n")
        try {
            startActivity(Intent.createChooser(intent, "Elija su aplicación de correo"))
            try {
                findNavController().navigate(NewsListFragmentDirections.actionNewsListFragmentToHomeFragment(hours.toInt()))
            } catch (e: Exception) {
            }
        } catch (e: Exception) {
            Toast.makeText(this.context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.only_valid_events, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(!viewModel.can_assist) {
            viewModel.updateEvents((java.util.GregorianCalendar.MONTH + 4).toString())
            viewModel.can_assist = true
        } else {
            viewModel.getEvents()
            viewModel.can_assist = false
        }

        return super.onOptionsItemSelected(item)
    }
}
