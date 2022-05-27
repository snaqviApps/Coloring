package ghar.dfw.coloringschools.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ghar.dfw.coloringschools.R
import ghar.dfw.coloringschools.backEnd.repo.SchoolsRepository
import ghar.dfw.coloringschools.databinding.FragmentDetailsBinding
import ghar.dfw.coloringschools.utils.safeLet
import ghar.dfw.coloringschools.view.viewmodels.SchoolViewModel

class DetailsFragment : Fragment() {

  private lateinit var binding: FragmentDetailsBinding
  private lateinit var inDetailedViewModel: SchoolViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

    binding = FragmentDetailsBinding.inflate(layoutInflater)
    binding.lifecycleOwner = viewLifecycleOwner

    inDetailedViewModel = ViewModelProvider(requireActivity())[SchoolViewModel::class.java]
    /**  share School-Scores using ViewModel*/
    val schoolNameReceived = getArgs()
    setUpObserver(inDetailedViewModel, schoolNameReceived)

    return binding.root
  }

  private fun getArgs(): String {
    val schoolNameReceived = DetailsFragmentArgs.fromBundle(requireArguments()).schoolNameSent
    binding.navBack.setOnClickListener {
      findNavController().navigate(R.id.schoolMainFragment)
    }
    return schoolNameReceived
  }

  private fun setUpObserver(inDetailedViewModel: SchoolViewModel, schoolNameRcvd: String) {
    inDetailedViewModel.schoolsData.observe(viewLifecycleOwner) { detailsState ->
      when (detailsState) {
        is SchoolsRepository.UIState.EmptyState -> {}
        is SchoolsRepository.UIState.SuccessState -> {

          val scores = detailsState.scores
          val sortedScoresList = scores?.sortedWith(compareBy { it.schoolName })

          sortedScoresList?.forEach {
            if (it.schoolName?.replace("\\s", "")?.lowercase()
                .equals(schoolNameRcvd.replace("\\s", "").lowercase())) {

              val readingScore = it.readingScore
              val writingScore = it.writingScore
              val mathScore = it.mathScore

              binding.tvReceivedSchoolName.visibility = View.VISIBLE
              binding.tvReceivedSchoolName.text = schoolNameRcvd

              safeLet(mathScore, readingScore, writingScore) { math, reading, writing ->
                binding.mTextViewMathAvgScore.visibility = View.VISIBLE
                binding.mTextViewMathAvgScore.text = math
                binding.mTextViewCriticalReadingAvgScore.visibility = View.VISIBLE
                binding.mTextViewCriticalReadingAvgScore.text = reading
                binding.mTextViewWritingAvgScore.visibility = View.VISIBLE
                binding.mTextViewWritingAvgScore.text = writing
              }
            }
          }
        }

        is SchoolsRepository.UIState.ErrorState -> {
          Toast.makeText(activity, "Error: ${detailsState.error}", Toast.LENGTH_LONG).show()
        }
      }
    }
  }

}

