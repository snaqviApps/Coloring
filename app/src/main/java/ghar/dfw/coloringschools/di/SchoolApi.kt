package ghar.dfw.coloringschools.di

import ghar.dfw.coloringschools.Constants.CoreConstants.Companion.SCHOOLS_END_POINT
import ghar.dfw.coloringschools.Constants.CoreConstants.Companion.SCORES_END_POINT
import ghar.dfw.coloringschools.backEnd.model.SchoolScores
import ghar.dfw.coloringschools.backEnd.model.SchoolsBasicInfo
import retrofit2.http.GET

interface SchoolApi {
    @GET(SCHOOLS_END_POINT)
    suspend fun getSchools() : List<SchoolsBasicInfo>

    @GET(SCORES_END_POINT)
    suspend fun getScores() : List<SchoolScores>
}