package com.example.newsapp.registered_events

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentRegisteredBinding
import com.example.newsapp.news.NewsAdapter
import com.example.newsapp.news.NewsListFragmentDirections

/**
 * A simple [Fragment] subclass.
 */
class RegisteredFragment : Fragment(){
    private lateinit var binding: FragmentRegisteredBinding
    private lateinit var viewModel: RegisteredViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisteredBinding.inflate(inflater)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(RegisteredFragmentDirections.actionRegisteredFragmentToHomeFragment(0))
            }

        })
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = "Mis eventos"
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Presione un evento para eliminarlo"
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(RegisteredViewModel::class.java)
        binding.viewModel = viewModel
        binding.registeredEvents.adapter = NewsAdapter(NewsAdapter.OnClickListener{
            viewModel.openNewsUrl(it)
        })

        viewModel.currentNews.observe(viewLifecycleOwner, Observer {
            sendNotGoingEmail(it.name, it.type[0].substringBefore(" "), it.formatted_date, it.hours)
        })
    }

    private fun sendNotGoingEmail(name: String, recipient: String, date: String, hours:String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("$recipient@uvg.edu.gt"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Retiro de asistencia del evento $name")
        intent.putExtra(
            Intent.EXTRA_TEXT, "Retiro mi asistencia en el evento $name de la" +
                    " fecha $date.\n\n Saludos cordiales, \n \n\n")
        try {
            startActivity(Intent.createChooser(intent, "Enviar correo de retiro"))
            try {
                findNavController().navigate(RegisteredFragmentDirections.actionRegisteredFragmentToHomeFragment(hours.toInt() * -1))
            } catch (e: Exception) {
            }
        } catch (e: Exception) {
            Toast.makeText(this.context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


}
