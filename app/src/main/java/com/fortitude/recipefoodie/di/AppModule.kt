package com.fortitude.recipefoodie.di

import android.content.Context
import androidx.room.Room
import com.fortitude.recipefoodie.BuildConfig
import com.fortitude.recipefoodie.core.Constants
import com.fortitude.recipefoodie.data.DbRepository
import com.fortitude.recipefoodie.data.MealsApiRepository
import com.fortitude.recipefoodie.data.MealsRecipeApi
import com.fortitude.recipefoodie.db.AppDao
import com.fortitude.recipefoodie.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMealsRepository(api: MealsRecipeApi) = MealsApiRepository(api)

    @Provides
    @Singleton
    fun provideOkHttp() = if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    } else OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(okClient: OkHttpClient): MealsRecipeApi = Retrofit.Builder()
        .baseUrl(Constants.SERVICE_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okClient)
        .build()
        .create(MealsRecipeApi::class.java)

    @Provides
    @Singleton
    fun provideDatabaseRepository(dao: AppDao) = DbRepository(dao)

    @Provides
    @Singleton
    fun provideAppDao(db: AppDatabase) = db.dao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration(false)
            .build()
}














