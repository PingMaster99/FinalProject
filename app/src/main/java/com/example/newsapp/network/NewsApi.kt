package com.example.newsapp.network
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Base url for the news API
private const val BASE_URL = "http://hn.algolia.com/api/v1/"

// API status
enum class AlgoliaApiStatus {
    START,
    LOADING,
    ERROR,
    DONE
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getNewsAsync] and [getNoQueryNewsAsync] methods
 */
interface AlgoliaApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [News] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("search?tags=story")
    fun getNewsAsync(@Query("query", encoded = true) query: String?,
                     @Query("numericFilters", encoded = true) points: String?,
                     @Query("tags", encoded = true) author: String?):
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<Website>

    /**
     * Similar method as the one above, but uses only point number and author as queries
     * it also returns news articles by date (most recent)
     */
    @GET("search_by_date?tags=story")
    fun getNoQueryNewsAsync(@Query("numericFilters", encoded = true) points: String?,
                            @Query("tags", encoded = true) author: String?):
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<Website>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AlgoliaApi {
    val retrofitService :
            AlgoliaApiService by lazy { retrofit.create(AlgoliaApiService::class.java) }
}