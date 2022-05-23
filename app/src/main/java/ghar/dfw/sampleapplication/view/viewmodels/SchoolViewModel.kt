package ghar.dfw.sampleapplication.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ghar.dfw.sampleapplication.backEnd.repo.SchoolsRepository
import ghar.dfw.sampleapplication.backEnd.repo.UIState
import kotlinx.coroutines.launch

class SchoolViewModel : ViewModel() {

  var schoolsData: LiveData<UIState>

  private val schoolsRepository =  SchoolsRepository()

init {
  prepareSchoolData()
  schoolsData = schoolsRepository.schoolsApiCallResponse
}

   private fun prepareSchoolData() {
    viewModelScope.launch {
      schoolsRepository.networkCall()
    }
   }
}

