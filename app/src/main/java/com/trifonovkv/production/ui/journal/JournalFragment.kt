package com.trifonovkv.production.ui.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerviewStatistic.layoutManager = LinearLayoutManager(activity)
        val productionEntries = ProductionJournal(dbHelper).getEntriesForLastMonth()
        binding.recyclerviewStatistic.adapter = JournalRecyclerAdapter(productionEntries)

        binding.tvAdryTotal.text = productionEntries.sumOf { it.adry }.toString()
        binding.tvAfreshTotal.text = productionEntries.sumOf { it.afresh }.toString()
        binding.tvAfrostTotal.text = productionEntries.sumOf { it.afrost }.toString()
        binding.tvAfruitTotal.text = productionEntries.sumOf { it.afruit }.toString()
        binding.tvAlcoTotal.text = productionEntries.sumOf { it.alco }.toString()
        binding.tvAmezTotal.text = productionEntries.sumOf { it.amez }.toString()
        binding.tvHolod3Total.text = productionEntries.sumOf { it.holod3 }.toString()
        binding.tvTotalTotal.text = productionEntries.sumOf { it.total }.toString()


        return root
    }
}