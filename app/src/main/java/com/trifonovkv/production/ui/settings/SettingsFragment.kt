package com.trifonovkv.production.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.preference.PreferenceManager
import com.trifonovkv.production.MainActivity
import com.trifonovkv.production.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val root: View = binding.root

        (requireActivity() as MainActivity).supportActionBar!!.hide()

        val shiftPrice = sharedPrefs.getString("shift_price", "900").toString()
        val adryPrice = sharedPrefs.getString("adry_price", "1.30").toString()
        val afreshPrice = sharedPrefs.getString("afresh_price", "1.90").toString()
        val afrostPrice = sharedPrefs.getString("afrost_price", "2.54").toString()
        val afruitPrice = sharedPrefs.getString("afruit_price", "2.54").toString()
        val alcoPrice = sharedPrefs.getString("alco_price", "1.50").toString()
        val amezPrice = sharedPrefs.getString("amez_price", "1.50").toString()
        val holod3Price = sharedPrefs.getString("holod3_price", "1.30").toString()

        binding.editTextShiftPrice.setText(shiftPrice)
        binding.editTextAdryPrice.setText(adryPrice)
        binding.editTextAfreshPrice.setText(afreshPrice)
        binding.editTextAfrostPrice.setText(afrostPrice)
        binding.editTextAfruitPrice.setText(afruitPrice)
        binding.editTextAlcoPrice.setText(alcoPrice)
        binding.editTextAmezPrice.setText(amezPrice)
        binding.editTextHolod3Price.setText(holod3Price)

        val editor = sharedPrefs.edit()

        binding.editTextShiftPrice.doAfterTextChanged {
            editor.putString("shift_price", "$it").apply()
        }

        binding.editTextAdryPrice.doAfterTextChanged {
            editor.putString("adry_price", "$it").apply()
        }
        binding.editTextAfreshPrice.doAfterTextChanged {
            editor.putString("afresh_price", "$it").apply()
        }
        binding.editTextAfrostPrice.doAfterTextChanged {
            editor.putString("afrost_price", "$it").apply()
        }
        binding.editTextAfruitPrice.doAfterTextChanged {
            editor.putString("afruit_price", "$it").apply()
        }
        binding.editTextAlcoPrice.doAfterTextChanged {
            editor.putString("alco_price", "$it").apply()
        }
        binding.editTextAmezPrice.doAfterTextChanged {
            editor.putString("amez_price", "$it").apply()
        }
        binding.editTextHolod3Price.doAfterTextChanged {
            editor.putString("holod3_price", "$it").apply()
        }

        return root
    }

}