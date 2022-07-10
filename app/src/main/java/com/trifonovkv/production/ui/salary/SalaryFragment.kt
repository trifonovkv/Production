package com.trifonovkv.production.ui.salary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trifonovkv.production.MainActivity
import com.trifonovkv.production.databinding.FragmentSalaryBinding
import com.trifonovkv.production.setRubles
import com.trifonovkv.production.ui.PricesPreferences
import com.trifonovkv.production.ui.journal.ProductionDbHelper
import com.trifonovkv.production.ui.journal.ProductionJournal


class SalaryFragment : Fragment() {

    private var _binding: FragmentSalaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: ProductionDbHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar!!.hide()

        dbHelper = ProductionDbHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSalaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val productionEntries = ProductionJournal(dbHelper).getEntriesForLastMonth()

        val numberOfShifts = productionEntries.size
        binding.tvShiftCount.text = numberOfShifts.toString()
        if (numberOfShifts > 0) {
            binding.tvAveragePackages.text =
                (productionEntries.sumOf { it.total } / numberOfShifts).toString()
        }
        else {
            binding.tvAveragePackages.text = "0"
        }


        val prices = PricesPreferences(context!!).getPrices()

        val totalResult =
            productionEntries.size * prices.shift +
            productionEntries.sumOf { it.adry } * prices.adry +
            productionEntries.sumOf { it.afresh } * prices.afresh +
            productionEntries.sumOf { it.afrost } * prices.afrost +
            productionEntries.sumOf { it.afruit } * prices.afruit +
            productionEntries.sumOf { it.alco } * prices.alco +
            productionEntries.sumOf { it.amez } * prices.amez +
            productionEntries.sumOf { it.holod3 } * prices.holod3


        binding.tvEarned.setRubles(totalResult, isHaveKopecks = false, isRubleSign = true)


        // Inflate the layout for this fragment
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()

    }
}