package ghar.dfw.coloringschools.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ghar.dfw.coloringschools.backEnd.repo.SchoolsRepository
import ghar.dfw.coloringschools.backEnd.repo.UIState
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

  fun schoolClicked(school: String) {}

}

