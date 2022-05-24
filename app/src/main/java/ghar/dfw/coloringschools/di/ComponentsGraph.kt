package ghar.dfw.coloringschools.di

import dagger.Component
import ghar.dfw.coloringschools.backEnd.repo.SchoolsRepository

@Component(modules = [SchoolsModule::class])
interface ComponentsGraph {

//  fun inject(schoolViewModel: SchoolViewModel)
  fun inject(schoolsRepository: SchoolsRepository)

  @Component.Builder
  interface Builder {
    fun networkModule(schoolsModule: SchoolsModule) : Builder
    fun build() : ComponentsGraph
  }

}