package com.trifonovkv.production.ui.journal

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.trifonovkv.production.MainActivity
import com.trifonovkv.production.databinding.FragmentJournalBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatisticFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: ProductionDbHelper


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        dbHelper = ProductionDbHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        (requireActivity() as MainActivity).supportActionBar!!.hide()

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatisticFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onStop() {
        super.onStop()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        dbHelper.close()
    }
}