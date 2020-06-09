package com.example.proyecto.UI_Colaborador.Oportunidades

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto.R

class OpColFragment : Fragment() {

    companion object {
        fun newInstance() = OpColFragment()
    }

    private lateinit var viewModel: OpColViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.op_col_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OpColViewModel::class.java)
        // TODO: Use the ViewModel
    }

}