package com.tiknil.app.di

import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.core.services.IDataService
import com.tiknil.app.core.services.IRestService
import com.tiknil.app.services.AppContainer
import com.tiknil.app.services.DataService
import com.tiknil.app.services.restservice.RestService
import com.tiknil.app.services.restservice.MockedRestService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val defaultCommonModule = module {
    singleOf(::DataService) { bind<IDataService>() }
    singleOf(::RestService) { bind<IRestService>() }
    singleOf(::AppContainer) { bind<IAppContainer>() }
}


val demoCommonModule = module {
    singleOf(::DataService) { bind<IDataService>() }
    singleOf(::MockedRestService) { bind<IRestService>() }
    singleOf(::AppContainer) { bind<IAppContainer>() }
}




