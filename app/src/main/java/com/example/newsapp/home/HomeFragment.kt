package com.example.newsapp.home

import FirebaseUserLiveData
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.HomeFragmentBinding
import com.example.newsapp.login.LoginViewModel
import com.example.newsapp.network.AlgoliaApiStatus
import com.firebase.ui.auth.AuthUI
import com.example.newsapp.R

/**
 * <h1>HomeFragment</h1>
 *<p>
 * This fragment displays parameters used to search for news articles
 * using the Algolia API
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 1.0
 * @since 2020-06-02
 **/
class HomeFragment : Fragment() {

    // ViewModel and DataBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding


    /**
     * Builds the app initialization displays the information requested
     * @param savedInstanceState saved App data
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Updates title and subtitle
        (activity as AppCompatActivity).supportActionBar?.show()
        binding = HomeFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true) // Menu contains logout
        return binding.root
    }

    /**
     * Builds the app initialization displays the information requested
     * @param savedInstanceState saved App data
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        (activity as AppCompatActivity).supportActionBar?.title = "¡Hola, ${viewModel.user}!"
        (activity as AppCompatActivity).supportActionBar?.subtitle = ""
        // Search button goes to the next fragment
        binding.search.setOnClickListener {
            viewModel.actionViewNews()
        }

        binding.logOut.setOnClickListener{AuthUI.getInstance().signOut(requireContext())
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment()) }
        // Observer of the state of ViewNews navigates with parameters

        viewModel.viewNews.observe(viewLifecycleOwner, Observer {
            // Navigates to the next fragment if button was pressed
            if(it) {
                requireView().hideKeyboard()
                requireView().findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToNewsListFragment()
                )

                viewModel.viewNewsComplete()
            }
        })

        // Not found or API error
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it == AlgoliaApiStatus.ERROR) {
                Toast.makeText(this.context, "Error de red o parametros no encontrados",
                    Toast.LENGTH_SHORT).show()
                viewModel.startStatus()
            }
        })
    }

    // Hides the keyboard
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    /**
     * Generates the menu
     * @param menu
     * @param inflater reads XML
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout, menu)
    }

    /**
     * Adds functionality to the different menu options
     * @param item from the menu
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Item clicked
        when(item.itemId) {
            // Register guest
            R.id.logout_menu-> {
                AuthUI.getInstance().signOut(requireContext())
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
