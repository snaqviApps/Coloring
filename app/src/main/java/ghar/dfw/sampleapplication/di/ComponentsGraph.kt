package ghar.dfw.sampleapplication.di

import dagger.Component
import ghar.dfw.sampleapplication.backEnd.repo.SchoolsRepository
import ghar.dfw.sampleapplication.view.viewmodels.SchoolViewModel

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