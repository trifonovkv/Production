package com.trifonovkv.production.ui.calc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.trifonovkv.production.MainActivity
import com.example.production.R
import com.example.production.databinding.FragmentCalcBinding
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}

class CalcFragment : Fragment() {

    private var _binding: FragmentCalcBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(CalcViewModel::class.java)

        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        (requireActivity() as MainActivity).supportActionBar!!.hide()

        binding.textViewShiftFee.text = "900₽"

        fun update() {
            var handPickedResult = 0.0


            val adryResult = (binding.editTextAdry.text.toString().toIntOrNull() ?: 0) * 1.30
            val afreshResult = (binding.editTextAfresh.text.toString().toIntOrNull() ?: 0) * 1.90
            val afrostResult = (binding.editTextAfrost.text.toString().toIntOrNull() ?: 0) * 2.54
            val afruitResult = (binding.editTextAfruit.text.toString().toIntOrNull() ?: 0) * 2.54
            val alcoResult = (binding.editTextAlco.text.toString().toIntOrNull() ?: 0) * 1.50
            val amezResult = (binding.editTextAmez.text.toString().toIntOrNull() ?: 0) * 1.50
            val holod3Result = (binding.editTextHolod3.text.toString().toIntOrNull() ?: 0) * 1.30


            handPickedResult += adryResult
            handPickedResult += afreshResult
            handPickedResult += afrostResult
            handPickedResult += afruitResult
            handPickedResult += alcoResult
            handPickedResult += amezResult
            handPickedResult += holod3Result

            "${adryResult}${getString(R.string.ruble_sign)}".also {
                binding.textViewAdryResult.text = it
            }
            "${afreshResult}${getString(R.string.ruble_sign)}".also {
                binding.textViewAfreshResult.text = it
            }
            "${afrostResult}${getString(R.string.ruble_sign)}".also {
                binding.textViewAfrostResult.text = it
            }
            "${afruitResult}${getString(R.string.ruble_sign)}".also {
                binding.textViewAfruitResult.text = it
            }
            "${alcoResult}${getString(R.string.ruble_sign)}".also {
                binding.textViewAlcoResult.text = it
            }
            "${amezResult}${getString(R.string.ruble_sign)}".also {
                binding.textViewAmezResult.text = it
            }
            "${holod3Result}${getString(R.string.ruble_sign)}".also {
                binding.textViewHolod3Result.text = it
            }

            val total = (handPickedResult + 900).roundTo(2)
            "${handPickedResult.roundTo(2)}₽".also { binding.textViewHandpicked.text = it }
            "${total}₽".also { binding.textViewMainResult.text = it }
        }


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


