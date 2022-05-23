package ghar.dfw.sampleapplication.backEnd.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ghar.dfw.sampleapplication.Constants.CoreConstants.Companion.MAX_TIME_OUT
import ghar.dfw.sampleapplication.Constants.CoreConstants.Companion.SCHOOLS_BASE_URL
import ghar.dfw.sampleapplication.backEnd.model.SchoolScores
import ghar.dfw.sampleapplication.backEnd.model.SchoolsBasicInfo
import ghar.dfw.sampleapplication.di.DaggerComponentsGraph
import ghar.dfw.sampleapplication.di.SchoolApi
import ghar.dfw.sampleapplication.di.SchoolsModule
import ghar.dfw.sampleapplication.view.viewmodels.SchoolViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

//open class SchoolsRepository(schoolViewModel : SchoolViewModel) {
class SchoolsRepository {

  private val _schoolsApiCallResponse = MutableLiveData<UIState>()
  val schoolsApiCallResponse : LiveData<UIState>
    get() = _schoolsApiCallResponse

  @Inject
  lateinit var retrofit: Retrofit

  var schoolsApi: SchoolApi
  private val schoolsGraph = DaggerComponentsGraph
    .builder()
    .networkModule(schoolsModule = SchoolsModule(SCHOOLS_BASE_URL))
    .build()

  init {
//    schoolsGraph.inject(schoolViewModel)
    schoolsGraph.inject(this)
    schoolsApi = retrofit.create(SchoolApi::class.java)
  }

  suspend fun networkCall() {
    withContext(Dispatchers.IO){
      withTimeout(MAX_TIME_OUT){
        try {
          val schoolsInfo = schoolsApi.getSchools()
          val schoolScore = schoolsApi.getScores()
          _schoolsApiCallResponse.postValue(
            UIState.SuccessState(schools = schoolsInfo, scores = schoolScore)
          )

        }catch (exception : Exception){
          exception.message.let {
            _schoolsApiCallResponse.postValue(UIState.ErrorState(it!!))
          }
        }
      }
    }
  }

}

sealed class UIState {
  object EmptyState : UIState()
  class SuccessState (
    val schools: List<SchoolsBasicInfo>?,
    val scores: List<SchoolScores>?
  ) : UIState()

  class ErrorState(val error : String) : UIState()


}

