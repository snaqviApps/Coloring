package ghar.dfw.coloringschools.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ghar.dfw.coloringschools.backEnd.model.SchoolScores
import ghar.dfw.coloringschools.backEnd.model.SchoolsBasicInfo
import ghar.dfw.coloringschools.backEnd.repo.SchoolsRepository
import ghar.dfw.coloringschools.databinding.FragmentSchoolMainBinding
import ghar.dfw.coloringschools.utils.safeLet
import ghar.dfw.coloringschools.view.adapter.SchoolsMainAdapter
import ghar.dfw.coloringschools.view.viewmodels.SchoolViewModel

class SchoolMainFragment : Fragment() {

  private lateinit var viewModel: SchoolViewModel
  private lateinit var binding: FragmentSchoolMainBinding

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

    binding = FragmentSchoolMainBinding.inflate(layoutInflater)
    viewModel = ViewModelProvider(this)[SchoolViewModel::class.java]

    setupRV()
    getSchoolData()
    navigate(viewModel)
    return binding.root
  }

  private fun navigate(viewModel: SchoolViewModel) {
    viewModel.navigateToDetailsFragment.observe(viewLifecycleOwner, Observer { schoolClicked ->
      findNavController().navigate(SchoolMainFragmentDirections.actionSchoolMainFragmentToDetailsFragment(
        schoolClicked!!))
    })
  }

  private fun setupRV() {
    binding.rVSchools.layoutManager = LinearLayoutManager(requireContext())
    binding.mainXmlViewModel = viewModel

    /** sending data back to UI, check if 'this' is correct
     * and "requireActivity()" is not needed that would provide the FragmentActivity, that this Fragment is associated with
     */
    binding.lifecycleOwner = viewLifecycleOwner
  }

  private fun getSchoolData() {
    viewModel.schoolsData.observe(viewLifecycleOwner, Observer { state ->
      when (state) {
        is SchoolsRepository.UIState.EmptyState -> {}
        is SchoolsRepository.UIState.SuccessState -> {
          val schools = state.schools
          val sortedSchoolsList = schools?.sortedWith(compareBy { it.schoolName })
          safeLet(sortedSchoolsList) { safeSchools ->
            val adapter = SchoolsMainAdapter(safeSchools,
              SchoolsMainAdapter.SchoolsBasicInfoListener { schoolName ->
                run {
                  viewModel.schoolClicked(schoolName)
                }
              })
            binding.rVSchools.adapter = adapter
          }
        }
        is SchoolsRepository.UIState.ErrorState -> {
          Toast.makeText(activity, "Error: ${state.error}", Toast.LENGTH_LONG).show()
        }
      }
    })
  }


}