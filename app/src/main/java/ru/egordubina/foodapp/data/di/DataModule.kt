package ru.egordubina.foodapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import ru.egordubina.foodapp.BuildConfig
import ru.egordubina.foodapp.data.api.CategoryApi
import ru.egordubina.foodapp.data.api.CategoryApiImpl
import ru.egordubina.foodapp.data.api.MealApi
import ru.egordubina.foodapp.data.api.MealApiImpl
import ru.egordubina.foodapp.data.repository.CategoriesRepositoryImpl
import ru.egordubina.foodapp.data.repository.MealsRepositoryImpl
import ru.egordubina.foodapp.domain.repository.CategoriesRepository
import ru.egordubina.foodapp.domain.repository.MealsRepository

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesHttpClient(): HttpClient = HttpClient(CIO)

    @Provides
    fun providesMealApi(client: HttpClient): MealApi =
        MealApiImpl(client = client, baseUrl = BuildConfig.API_URL)

    @Provides
    fun providesCategoryApi(client: HttpClient): CategoryApi =
        CategoryApiImpl(client = client, baseUrl = BuildConfig.API_URL)

    @Provides
    fun providesMealRepository(mealApi: MealApi): MealsRepository =
        MealsRepositoryImpl(mealApi = mealApi)

    @Provides
    fun providesCategoriesRepository(categoryApi: CategoryApi): CategoriesRepository =
        CategoriesRepositoryImpl(categoryApi = categoryApi)
}