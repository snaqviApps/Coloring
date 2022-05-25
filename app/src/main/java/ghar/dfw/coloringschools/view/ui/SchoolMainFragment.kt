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

  private var schoolMatchedList = ArrayList<SchoolsBasicInfo>()
  private var scoreMatchedList = ArrayList<SchoolScores>()

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

    //    return super.onCreateView(inflater, container, savedInstanceState)            // manual creation of Fragment
    //    return inflater.inflate(R.layout.fragment_school_main, container, false)
    return binding.root
  }

  private fun navigate(viewModel: SchoolViewModel) {
    viewModel.navigateToDetailsFragment.observe(viewLifecycleOwner, Observer { schoolClicked ->
//      if (schoolClicked.isNullOrEmpty()) {
      findNavController().navigate(SchoolMainFragmentDirections.actionSchoolMainFragmentToDetailsFragment(
          schoolClicked!!))
    })
  }

  private fun setupRV() {
    binding.rVSchools.layoutManager = LinearLayoutManager(requireContext())
//    binding.mainXmlViewModel = viewModel
    /** sending data back to UI, check if 'this' is correct
     * and "requireActivity()" is not needed that would provide the FragmentActivity, that this Fragment is associated with
     */
    binding.lifecycleOwner = viewLifecycleOwner
  }


  private fun getSchoolData() {
//    viewModel.schoolsData.observe(this, Observer {state ->          //activity implementation (opp to current / Fragment based)
    viewModel.schoolsData.observe(viewLifecycleOwner, Observer { state ->
      when (state) {
        is SchoolsRepository.UIState.EmptyState -> {}
        is SchoolsRepository.UIState.SuccessState -> {

          // RView populating
          val schools = state.schools
          val scores = state.scores

          val sortedSchoolsList = schools?.sortedWith(compareBy { it.schoolName })
          val sortedScoresList = scores?.sortedWith(compareBy { it.schoolName })

          for (school in sortedSchoolsList!!) {
            sortedScoresList?.forEach {
              if (it.schoolName?.replace("\\s", "")?.lowercase()
                  .equals(school.schoolName?.replace("\\s", "")?.lowercase())) {
                schoolMatchedList.add(school)
                scoreMatchedList.add(it)
              }
            }
          }

          safeLet(schoolMatchedList, scoreMatchedList) { safeSchools, _ ->
            val adapter = SchoolsMainAdapter(safeSchools,
              SchoolsMainAdapter.SchoolsBasicInfoListener { schoolName ->
                run {
                  viewModel.schoolClicked(schoolName)
                }
              })
            binding.rVSchools.adapter = adapter
          }
          // RView populating ENDS here

        }
        is SchoolsRepository.UIState.ErrorState -> {
          Toast.makeText(activity, "Error: ${state.error}", Toast.LENGTH_LONG).show()
        }
      }
    })
  }

}