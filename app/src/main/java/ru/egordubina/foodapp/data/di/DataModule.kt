package ru.egordubina.foodapp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import ru.egordubina.foodapp.BuildConfig
import ru.egordubina.foodapp.data.api.CategoryApi
import ru.egordubina.foodapp.data.api.CategoryApiImpl
import ru.egordubina.foodapp.data.api.MealApi
import ru.egordubina.foodapp.data.api.MealApiImpl
import ru.egordubina.foodapp.data.database.CategoryDao
import ru.egordubina.foodapp.data.database.FoodAppDatabase
import ru.egordubina.foodapp.data.database.MealDao
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
    fun providesMealRepository(mealApi: MealApi, mealDao: MealDao): MealsRepository =
        MealsRepositoryImpl(mealApi = mealApi, mealDao = mealDao)

    @Provides
    fun providesCategoriesRepository(
        categoryApi: CategoryApi,
        categoryDao: CategoryDao,
    ): CategoriesRepository =
        CategoriesRepositoryImpl(categoryApi = categoryApi, categoryDao = categoryDao)

    @Provides
    fun providesDatabase(@ApplicationContext context: Context): FoodAppDatabase =
        Room.databaseBuilder(context, FoodAppDatabase::class.java, "food_app_database").build()

    @Provides
    fun providesMealDao(database: FoodAppDatabase): MealDao = database.mealDao()

    @Provides
    fun providesCategoryDao(database: FoodAppDatabase): CategoryDao = database.categoryDao()
}