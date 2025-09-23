package by.terminal.posterminalandroidklient.di.base

import by.terminal.posterminalandroidklient.BuildConfig
import by.terminal.posterminalandroidklient.data.remote.api.HttpResult
import by.terminal.posterminalandroidklient.data.remote.api.TransactionApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal object ApiModule {
    private const val BASE_URL = BuildConfig.BASE_URL

    @Singleton
    @Provides
    internal fun provideHttpClientLoginInterceptor() =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    internal fun provideGsonConverter(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    internal fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(3, TimeUnit.SECONDS)
        .readTimeout(3, TimeUnit.SECONDS)
        .writeTimeout(3, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()

    @Singleton
    @Provides
    internal fun provideApi(retrofit: Retrofit): TransactionApi =
        retrofit.create(TransactionApi::class.java)
}

internal suspend fun <T> safeApiCall(
    retries: Int = 2,
    request: suspend () -> Response<T>
): HttpResult<T> {
    var lastError: Throwable? = null
    repeat(retries + 1) { attempt ->
        try {
            val response = request()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return HttpResult.Success(body = body)
                return HttpResult.Failure("Empty body.")
            } else {
                return HttpResult.Error(Exception("HTTP ${response.code()}"))
            }
        } catch (e: SocketTimeoutException) {
            lastError = e
            if (attempt == retries) return HttpResult.Error(e)
        } catch (e: Exception) {
            return HttpResult.Error(e)
        }
    }
    return HttpResult.Error(lastError ?: Exception("Unknown exception."))
}