package ghar.dfw.sampleapplication.di

import ghar.dfw.sampleapplication.Constants.CoreConstants.Companion.SCHOOLS_END_POINT
import ghar.dfw.sampleapplication.Constants.CoreConstants.Companion.SCORES_END_POINT
import ghar.dfw.sampleapplication.backEnd.model.SchoolScores
import ghar.dfw.sampleapplication.backEnd.model.SchoolsBasicInfo
import retrofit2.http.GET

interface SchoolApi {
    @GET(SCHOOLS_END_POINT)
    suspend fun getSchools() : List<SchoolsBasicInfo>

    @GET(SCORES_END_POINT)
    suspend fun getScores() : List<SchoolScores>
}