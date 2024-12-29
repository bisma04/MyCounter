package com.example.mycounter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mycounter.R
import com.example.mycounter.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {
    private lateinit var binding: FragmentScoreBinding
    private val args: ScoreFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val counterModel = args.CounterModel
        binding.scoredText.text = getString(R.string.score, counterModel.count)
    }
}