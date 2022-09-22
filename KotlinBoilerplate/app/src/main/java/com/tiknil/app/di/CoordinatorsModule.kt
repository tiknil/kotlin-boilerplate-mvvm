package com.tiknil.app.di

import com.tiknil.app.coordinators.AppCoordinator
import com.tiknil.app.core.services.ICoordinator
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coordinatorsModule = module {
    singleOf(::AppCoordinator) { bind<ICoordinator>()}
}