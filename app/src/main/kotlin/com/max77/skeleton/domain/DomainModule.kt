package com.max77.skeleton.domain

import com.max77.skeleton.domain.address.repo.AddressRepository
import com.max77.skeleton.domain.address.repo.AddressRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<AddressRepository> { AddressRepositoryImpl(get()) }
}