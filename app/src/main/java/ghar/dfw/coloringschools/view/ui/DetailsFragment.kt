package ghar.dfw.coloringschools.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ghar.dfw.coloringschools.R
import ghar.dfw.coloringschools.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

  private lateinit var binding : FragmentDetailsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    binding = FragmentDetailsBinding.inflate(layoutInflater)
    val schoolNameRcvd = DetailsFragmentArgs.fromBundle(requireArguments()).schoolNameSent
    binding.navBack.setOnClickListener {
      navigateBack()
    }

    setUpObserver(binding, schoolNameRcvd)
//    return inflater.inflate(R.layout.fragment_details, container, false)
    return binding.root
  }

  private fun navigateBack() {
    findNavController().navigate(R.id.schoolMainFragment)
  }

  private fun setUpObserver(binding: FragmentDetailsBinding, schoolNameRcvd: String) {
    binding.lifecycleOwner = this
    binding.tvReceivedSchoolName.text = schoolNameRcvd
  }

}