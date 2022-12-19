package cloud.nymbow.impalapay.dataclasses


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retroinstance {

    companion object{
        const val BASE_URL ="http://10.129.128.24:8000/api/"

        fun getRetroInstance(): Retrofit {

            val logging =  HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }
}