package com.trifonovkv.production.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.preference.PreferenceManager
import com.trifonovkv.production.databinding.FragmentSettingsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val root: View = binding.root

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}