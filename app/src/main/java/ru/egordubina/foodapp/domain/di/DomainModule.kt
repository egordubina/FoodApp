package ru.egordubina.foodapp.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.egordubina.foodapp.data.api.CategoryApi
import ru.egordubina.foodapp.domain.repository.CategoriesRepository
import ru.egordubina.foodapp.domain.repository.MealsRepository
import ru.egordubina.foodapp.domain.usecases.LoadCategoriesUseCase
import ru.egordubina.foodapp.domain.usecases.LoadMealsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun providesLoadMealsUseCase(mealsRepository: MealsRepository): LoadMealsUseCase =
        LoadMealsUseCase(mealsRepository = mealsRepository)

    @Provides
    fun providesLoadCategoriesUseCase(categoriesRepository: CategoriesRepository): LoadCategoriesUseCase =
        LoadCategoriesUseCase(categoriesRepository = categoriesRepository)
}