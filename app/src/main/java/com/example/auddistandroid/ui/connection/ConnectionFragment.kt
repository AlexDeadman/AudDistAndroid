package com.example.auddistandroid.ui.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.auddistandroid.App.Companion.preferences
import com.example.auddistandroid.R
import com.example.auddistandroid.api.AudDistApi
import com.example.auddistandroid.databinding.FragmentConnectionBinding
import com.example.auddistandroid.utils.Keys

class ConnectionFragment : Fragment() {

    private var _binding: FragmentConnectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConnectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            editTextUrl.setText(preferences.getString(Keys.BASE_URL, ""))

            buttonContinue.setOnClickListener {

                if (editTextUrl.text!!.isNotBlank()) {

                    val url = editTextUrl.text.toString()

                    preferences
                        .edit()
                        .putString(Keys.BASE_URL, url)
                        .apply()

                    AudDistApi.BASE_URL = url

                    findNavController().navigate(R.id.action_connectionFragment_to_loginFragment)
                } else {
                    textViewError.apply {
                        text = getString(R.string.incorrect_url)
                        visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}