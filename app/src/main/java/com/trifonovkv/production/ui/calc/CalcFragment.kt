package com.trifonovkv.production.ui.calc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.trifonovkv.production.MainActivity
import com.trifonovkv.production.R
import com.trifonovkv.production.databinding.FragmentCalcBinding
import com.trifonovkv.production.setRubles
import com.trifonovkv.production.ui.PricesPreferences
import com.trifonovkv.production.ui.journal.ProductionDbHelper
import com.trifonovkv.production.ui.journal.ProductionEntry
import com.trifonovkv.production.ui.journal.ProductionJournal


class CalcFragment : Fragment() {

    private var _binding: FragmentCalcBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dbHelper: ProductionDbHelper
    private lateinit var pricesPreferences: PricesPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        val root: View = binding.root


        (requireActivity() as MainActivity).supportActionBar!!.hide()

        binding.etAdry.doAfterTextChanged { update() }
        binding.etAfresh.doAfterTextChanged { update() }
        binding.etAfrost.doAfterTextChanged { update() }
        binding.etAfruit.doAfterTextChanged { update() }
        binding.etAlco.doAfterTextChanged { update() }
        binding.etAmez.doAfterTextChanged { update() }
        binding.etHolod3.doAfterTextChanged { update() }


        dbHelper = ProductionDbHelper(requireContext())
        pricesPreferences = PricesPreferences(context!!)


        binding.imageButtonSave.setOnClickListener {
            // if entry with same date exist show dialog, otherwise save entry
            if (ProductionJournal(dbHelper).hasEntryWithSameDate(System.currentTimeMillis())) {
                SaveDialogFragment(this).show(savedInstanceState)
            } else {
                save()
            }
        }

        binding.tvClear.setOnClickListener {
            binding.etAdry.setText(R.string.zero)
            binding.etAfresh.setText(R.string.zero)
            binding.etAfrost.setText(R.string.zero)
            binding.etAfruit.setText(R.string.zero)
            binding.etAlco.setText(R.string.zero)
            binding.etAmez.setText(R.string.zero)
            binding.etHolod3.setText(R.string.zero)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        update()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        dbHelper.close()
    }

    private fun update() {
        var handPickedResult = 0

        val prices = pricesPreferences.getPrices()

        binding.tvShiftFee.setRubles(prices.shift, isHaveKopecks = false, isRubleSign = true)
        val adryPicks = binding.etAdry.text.toString().toIntOrNull() ?: 0
        val afreshPicks = binding.etAfresh.text.toString().toIntOrNull() ?: 0
        val afrostPicks = binding.etAfrost.text.toString().toIntOrNull() ?: 0
        val afruitPicks = binding.etAfruit.text.toString().toIntOrNull() ?: 0
        val alcoPicks = binding.etAlco.text.toString().toIntOrNull() ?: 0
        val amezPicks = binding.etAmez.text.toString().toIntOrNull() ?: 0
        val holod3Picks = binding.etHolod3.text.toString().toIntOrNull() ?: 0

        val adryResult = adryPicks * prices.adry
        val afreshResult = afreshPicks * prices.afresh
        val afrostResult = afrostPicks * prices.afrost
        val afruitResult = afruitPicks * prices.afruit
        val alcoResult = alcoPicks * prices.alco
        val amezResult = amezPicks * prices.amez
        val holod3Result = holod3Picks * prices.holod3

        handPickedResult += adryResult
        handPickedResult += afreshResult
        handPickedResult += afrostResult
        handPickedResult += afruitResult
        handPickedResult += alcoResult
        handPickedResult += amezResult
        handPickedResult += holod3Result


        binding.tvAdryResult.setRubles(adryResult, isHaveKopecks = true, isRubleSign = true)
        binding.tvAfreshResult.setRubles(afreshResult, isHaveKopecks = true, isRubleSign = true)
        binding.tvAfrostResult.setRubles(afrostResult, isHaveKopecks = true, isRubleSign = true)
        binding.tvAfruitResult.setRubles(afruitResult, isHaveKopecks = true, isRubleSign = true)
        binding.tvAlcoResult.setRubles(alcoResult, isHaveKopecks = true, isRubleSign = true)
        binding.tvAmezResult.setRubles(amezResult, isHaveKopecks = true, isRubleSign = true)
        binding.tvHolod3Result.setRubles(holod3Result, isHaveKopecks = true, isRubleSign = true)
        binding.tvHandpicked.setRubles(handPickedResult, isHaveKopecks = false, isRubleSign = true)
        binding.tvMainResult.setRubles(
            (handPickedResult + prices.shift),
            isHaveKopecks = false,
            isRubleSign = true
        )

        binding.tvTotalPicks.text = (
                adryPicks +
                afreshPicks +
                afrostPicks +
                afruitPicks +
                alcoPicks +
                amezPicks +
                holod3Picks).toString()
    }

    private fun save() {
        val productionJournal = ProductionJournal(dbHelper)
        val date = System.currentTimeMillis()

        val adry = binding.etAdry.text.toString().toIntOrNull() ?: 0
        val afresh = binding.etAfresh.text.toString().toIntOrNull() ?: 0
        val afrost = binding.etAfrost.text.toString().toIntOrNull() ?: 0
        val afruit = binding.etAfruit.text.toString().toIntOrNull() ?: 0
        val alco = binding.etAlco.text.toString().toIntOrNull() ?: 0
        val amez = binding.etAmez.text.toString().toIntOrNull() ?: 0
        val holod3 = binding.etHolod3.text.toString().toIntOrNull() ?: 0
        val total = adry + afresh + afrost + afruit + alco + amez + holod3
        val productionEntry = ProductionEntry(
            date, adry, afresh, afrost, afruit, alco, amez, holod3, total
        )

        productionJournal.addEntry(productionEntry)
        Toast.makeText(context, getString(R.string.saved), Toast.LENGTH_SHORT).show()
    }

    fun doPositiveClick() {
        save()
    }
}


