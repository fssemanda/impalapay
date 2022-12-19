package cloud.nymbow.impalapay.apis


import cloud.nymbow.impalapay.dataclasses.ApiUser
import cloud.nymbow.impalapay.dataclasses.Transact
import cloud.nymbow.impalapay.dataclasses.TransactionItems

import cloud.nymbow.impalapay.dataclasses.UserAccountData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("account/{userId}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun returnAccountData(@Path("userId") userId:String): Call<UserAccountData>

    @GET("transaction_listing/{userId}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun returnAccountTransactions(@Path("userId") userId:String): Call<List<Transact>>

    @GET("invoice-items/{TransactionID}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun returnInvoiceItems(@Path("TransactionID") TransactionID:String): Call<List<TransactionItems>>


    @GET("query/{PayingID}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun queryTransactions(@Path("PayingID") PayingID:String, @Query("term") Ast_Tag_nbr:String): Call<List<Transact>>

}