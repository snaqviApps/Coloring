package ghar.dfw.coloringschools.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ghar.dfw.coloringschools.backEnd.repo.SchoolsRepository
import kotlinx.coroutines.launch

class SchoolViewModel : ViewModel() {

  var schoolsData: LiveData<SchoolsRepository.UIState>
  private var schoolsRepository: SchoolsRepository = SchoolsRepository()

  init {
    prepareSchoolData()
    schoolsData = schoolsRepository.schoolsApiCallResponse
  }

  private fun prepareSchoolData() {
    viewModelScope.launch {
      schoolsRepository.networkCall()
    }
  }

  fun schoolClicked(name: String?) {
    _navigateToDetailsFragment.value = name
  }

  private val _navigateToDetailsFragment = MutableLiveData<String?>()
  val navigateToDetailsFragment
    get() = _navigateToDetailsFragment

}

