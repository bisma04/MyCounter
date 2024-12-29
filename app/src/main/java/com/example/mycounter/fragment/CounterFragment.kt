package com.example.mycounter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mycounter.databinding.FragmentCounterBinding
import com.example.mycounter.event.CounterEvent
import com.example.mycounter.model.CounterModel
import com.example.mycounter.viewmodel.CounterViewModel
import kotlinx.coroutines.launch

class CounterFragment : Fragment() {
    private lateinit var binding: FragmentCounterBinding
    private val viewModel: CounterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCounterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()
        clicks()

    }

    private fun clicks() {
        with(binding) {
            incrementText.setOnClickListener {
                viewModel.increment()
            }
            decrementText.setOnClickListener {
                viewModel.decrement()
            }
            resetBtn.setOnClickListener {
                viewModel.reset()
            }
            nextBtn.setOnClickListener {
                viewModel.onNextClicked()
            }
        }

    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.counterState.collect { state ->
                binding.scoreText.text = state.count.toString()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is CounterEvent.ShowMessage -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }

                    is CounterEvent.NavigateToNextFragment -> {
                        val counterModel = CounterModel(binding.scoreText.text.toString().toInt())
                        val action = CounterFragmentDirections.actionCounterFragmentToScoreFragment(
                            counterModel
                        )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}