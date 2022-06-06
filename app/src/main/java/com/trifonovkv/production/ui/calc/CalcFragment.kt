package com.trifonovkv.production.ui.calc

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.trifonovkv.production.R
import com.trifonovkv.production.databinding.FragmentCalcBinding
import com.trifonovkv.production.MainActivity
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}

class CalcFragment : Fragment() {

    private var _binding: FragmentCalcBinding? = null
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var shiftPrice: String
    private lateinit var adryPrice: String
    private lateinit var afreshPrice: String
    private lateinit var afrostPrice: String
    private lateinit var afruitPrice: String
    private lateinit var alcoPrice: String
    private lateinit var amezPrice: String
    private lateinit var holod3Price: String


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val calcViewModel =
            ViewModelProvider(this).get(CalcViewModel::class.java)

        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        (requireActivity() as MainActivity).supportActionBar!!.hide()

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

        binding.editTextAdry.doAfterTextChanged {
            update()
        }
        binding.editTextAfresh.doAfterTextChanged {
            update()
        }
        binding.editTextAfrost.doAfterTextChanged {
            update()
        }
        binding.editTextAfruit.doAfterTextChanged {
            update()
        }
        binding.editTextAlco.doAfterTextChanged {
            update()
        }
        binding.editTextAmez.doAfterTextChanged {
            update()
        }
        binding.editTextHolod3.doAfterTextChanged {
            update()
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
    }

    private fun update() {
        var handPickedResult = 0.0

        shiftPrice = sharedPrefs.getString("shift_price", "900").toString()
        adryPrice = sharedPrefs.getString("adry_price", "1.30").toString()
        afreshPrice = sharedPrefs.getString("afresh_price", "1.90").toString()
        afrostPrice = sharedPrefs.getString("afrost_price", "2.54").toString()
        afruitPrice = sharedPrefs.getString("afruit_price", "2.54").toString()
        alcoPrice = sharedPrefs.getString("alco_price", "1.50").toString()
        amezPrice = sharedPrefs.getString("amez_price", "1.50").toString()
        holod3Price = sharedPrefs.getString("holod3_price", "1.30").toString()

        "+$shiftPrice".also { binding.textViewShiftFee.text = it }

        val adryResult = (binding.editTextAdry.text.toString().toIntOrNull() ?: 0) * adryPrice.toDouble()
        val afreshResult = (binding.editTextAfresh.text.toString().toIntOrNull() ?: 0) * afreshPrice.toDouble()
        val afrostResult = (binding.editTextAfrost.text.toString().toIntOrNull() ?: 0) * afrostPrice.toDouble()
        val afruitResult = (binding.editTextAfruit.text.toString().toIntOrNull() ?: 0) * afruitPrice.toDouble()
        val alcoResult = (binding.editTextAlco.text.toString().toIntOrNull() ?: 0) * alcoPrice.toDouble()
        val amezResult = (binding.editTextAmez.text.toString().toIntOrNull() ?: 0) * amezPrice.toDouble()
        val holod3Result = (binding.editTextHolod3.text.toString().toIntOrNull() ?: 0) * holod3Price.toDouble()

        handPickedResult += adryResult
        handPickedResult += afreshResult
        handPickedResult += afrostResult
        handPickedResult += afruitResult
        handPickedResult += alcoResult
        handPickedResult += amezResult
        handPickedResult += holod3Result

        "${adryResult.roundTo(2)}${getString(R.string.ruble_sign)}".also {
            binding.textViewAdryResult.text = it
        }
        "${afreshResult.roundTo(2)}${getString(R.string.ruble_sign)}".also {
            binding.textViewAfreshResult.text = it
        }
        "${afrostResult.roundTo(2)}${getString(R.string.ruble_sign)}".also {
            binding.textViewAfrostResult.text = it
        }
        "${afruitResult.roundTo(2)}${getString(R.string.ruble_sign)}".also {
            binding.textViewAfruitResult.text = it
        }
        "${alcoResult.roundTo(2)}${getString(R.string.ruble_sign)}".also {
            binding.textViewAlcoResult.text = it
        }
        "${amezResult.roundTo(2)}${getString(R.string.ruble_sign)}".also {
            binding.textViewAmezResult.text = it
        }
        "${holod3Result.roundTo(2)}${getString(R.string.ruble_sign)}".also {
            binding.textViewHolod3Result.text = it
        }

        val total = (handPickedResult + shiftPrice.toDouble()).roundTo(2)
        "${handPickedResult.roundTo(2)}₽".also { binding.textViewHandpicked.text = it }
        "${total}₽".also { binding.textViewMainResult.text = it }
    }
}


