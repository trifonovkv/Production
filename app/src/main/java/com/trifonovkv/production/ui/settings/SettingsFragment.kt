package com.trifonovkv.production.ui.settings

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.trifonovkv.production.MainActivity
import com.trifonovkv.production.databinding.FragmentSettingsBinding
import com.trifonovkv.production.getKopecksFromRubles
import com.trifonovkv.production.setRubles
import com.trifonovkv.production.toFloat
import com.trifonovkv.production.ui.PricesPreferences

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var pricesPreferences: PricesPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (requireActivity() as MainActivity).supportActionBar!!.hide()

        pricesPreferences = PricesPreferences(context!!)
        val prices = pricesPreferences.getPrices()

        binding.etShiftPrice.setRubles(prices.shift, isHaveKopecks = false, isRubleSign = false)
        binding.etAdryPrice.setRubles(prices.adry, isHaveKopecks = true, isRubleSign = false)
        binding.etAfreshPrice.setRubles(prices.afresh, isHaveKopecks = true, isRubleSign = false)
        binding.etAfrostPrice.setRubles(prices.afrost, isHaveKopecks = true, isRubleSign = false)
        binding.etAfruitPrice.setRubles(prices.afruit, isHaveKopecks = true, isRubleSign = false)
        binding.etAlcoPrice.setRubles(prices.alco, isHaveKopecks = true, isRubleSign = false)
        binding.etAmezPrice.setRubles(prices.amez, isHaveKopecks = true, isRubleSign = false)
        binding.etHolod3Price.setRubles(prices.holod3, isHaveKopecks = true, isRubleSign = false)


        binding.etShiftPrice.doAfterTextChanged {
            pricesPreferences.putShiftPrice(it.getKopecksFromRubles())
        }

        binding.etAdryPrice.doAfterTextChanged {
            pricesPreferences.putAdryPrice(it.getKopecksFromRubles())
        }
        binding.etAfreshPrice.doAfterTextChanged {
            pricesPreferences.putAfreshPrice(it.getKopecksFromRubles())
        }
        binding.etAfrostPrice.doAfterTextChanged {
            pricesPreferences.putAfrostPrice(it.getKopecksFromRubles())
        }
        binding.etAfruitPrice.doAfterTextChanged {
            pricesPreferences.putAfruitPrice(it.getKopecksFromRubles())
        }
        binding.etAlcoPrice.doAfterTextChanged {
            pricesPreferences.putAlcoPrice(it.getKopecksFromRubles())
        }
        binding.etAmezPrice.doAfterTextChanged {
            pricesPreferences.putAmezPrice(it.getKopecksFromRubles())
        }
        binding.etHolod3Price.doAfterTextChanged {
            pricesPreferences.putHolod3Price(it.getKopecksFromRubles())
        }

        return root
    }

}