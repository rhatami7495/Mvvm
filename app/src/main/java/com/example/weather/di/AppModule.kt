package net.instami.publisher.di


import android.content.Context
import androidx.room.Room
import com.example.weather.application.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.weather.data.api.Api
import com.example.weather.data.db.AppDatabase
import com.example.weather.helper.UnsafeOkHttpClient
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val REQUEST_TIMEOUT = 15L

    @Provides
    @Singleton
    fun provideRoomDB(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "weather_db")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideAlarmDao(
        db: AppDatabase
    ) = db.alarmDao()

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideInterceptor(
    ) = Interceptor { chain ->

        val request = chain.request()
        val url = request.url.newBuilder()
        val newRequest = request.newBuilder()
            .addHeader("User-Agent", "whether")
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json;charset=UTF-8")
            .url(url.build())

        chain.proceed(newRequest.build())

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClientBuilder()
        .addInterceptor(interceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
    .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApi(
        retrofit: Retrofit
    ): Api = retrofit.create(Api::class.java)



}