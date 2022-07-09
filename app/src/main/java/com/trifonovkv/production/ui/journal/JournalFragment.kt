package com.trifonovkv.production.ui.journal

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.trifonovkv.production.MainActivity
import com.trifonovkv.production.databinding.FragmentJournalBinding


class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: ProductionDbHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = ProductionDbHelper(requireContext())
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as MainActivity).supportActionBar!!.hide()

        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        binding.recyclerviewStatistic.layoutManager = LinearLayoutManager(activity)
        val productionEntries = ProductionJournal(dbHelper).getEntries()
        binding.recyclerviewStatistic.adapter = JournalRecyclerAdapter(productionEntries)

        binding.textViewAdryTotal.text = productionEntries.sumOf { it.adry }.toString()
        binding.textViewAfreshTotal.text = productionEntries.sumOf { it.afresh }.toString()
        binding.textViewAfrostTotal.text = productionEntries.sumOf { it.afrost }.toString()
        binding.textViewAfruitTotal.text = productionEntries.sumOf { it.afruit }.toString()
        binding.textViewAlcoTotal.text = productionEntries.sumOf { it.alco }.toString()
        binding.textViewAmezTotal.text = productionEntries.sumOf { it.amez }.toString()
        binding.textViewHolod3Total.text = productionEntries.sumOf { it.holod3 }.toString()
        binding.textViewTotalTotal.text = productionEntries.sumOf { it.total }.toString()


        return root
    }

    override fun onStop() {
        super.onStop()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        dbHelper.close()
    }
}