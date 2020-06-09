package com.example.proyecto.UI_Colaborador.Inscrito

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto.R

class InscritoColFragment : Fragment() {

    companion object {
        fun newInstance() =
            InscritoColFragment()
    }

    private lateinit var viewModel: InscritoColViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.inscrito_col_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InscritoColViewModel::class.java)
        // TODO: Use the ViewModel
    }

}