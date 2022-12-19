package cloud.nymbow.impalapay.dataclasses

import android.net.DnsResolver.Callback
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cloud.nymbow.impalapay.apis.ApiService
import retrofit2.Call
import retrofit2.Response

class DashboardViewModel:ViewModel() {

    var userAccountData:MutableLiveData<UserAccountData?> = MutableLiveData()
    var userTransactionData:MutableLiveData<List<Transact>?> = MutableLiveData()
    var invoiceItems:MutableLiveData<List<TransactionItems>?> = MutableLiveData()
//    var transactionsQuery:MutableLiveData<List<Transact>?>


    init{

        userAccountData = MutableLiveData()
        userTransactionData = MutableLiveData()
        invoiceItems = MutableLiveData()
    }

    fun userAccountDataObservable():MutableLiveData<UserAccountData?>
    {
        return  userAccountData
    }

    fun userTransactionDataObservable():MutableLiveData<List<Transact>?>{

        return userTransactionData
    }

    fun invoiceItemsObservable():MutableLiveData<List<TransactionItems>?>{
        return invoiceItems
    }


    fun returnAccountData(userId:String?)
    {
        val retroFitInstance = Retroinstance.getRetroInstance().create(ApiService::class.java)
        val call = retroFitInstance.returnAccountData(userId!!)
        call.enqueue(object :retrofit2.Callback<UserAccountData?>{
            override fun onFailure(call: Call<UserAccountData?>, t: Throwable) {
                Log.d("Failure-at-user-AccountLoad","Failed to load data"+t.message)
                userAccountData.postValue(null)

            }

            override fun onResponse(call: Call<UserAccountData?>, response: Response<UserAccountData?>) {
                if (response.isSuccessful){
                    userAccountData.postValue(response.body())
                    Log.d("Response Successful", "Data returned:  ${response}")
                }
                else{
                    Log.d("Response Error", "Response received but with an error:  ${response}")
                    userAccountData.postValue(null)

                }
            }
        })
    }

    fun returnInvoiceItems(transactionId:String?){

        val retroInstance = Retroinstance.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.returnInvoiceItems(transactionId!!)
        call.enqueue(object :retrofit2.Callback<List<TransactionItems>?>{
            override fun onResponse(call: Call<List<TransactionItems>?>, response: Response<List<TransactionItems>?>) {
                if(response.isSuccessful) {
                    invoiceItems.postValue(response.body())
                }
                else {
                    invoiceItems.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<TransactionItems>?>, t: Throwable) {
                invoiceItems.postValue(null)
                Log.d("Failure-at-user-AccountLoad","Failed to load data"+t.message)
            }
        })
    }

    fun returnAccountTransactions(userId:String?)
    {
        val retroFitInstance = Retroinstance.getRetroInstance().create(ApiService::class.java)
        val call = retroFitInstance.returnAccountTransactions(userId!!)
        call.enqueue(object :retrofit2.Callback<List<Transact>?>{
            override fun onFailure(call: Call<List<Transact>?>, t: Throwable) {
                Log.d("Transactions load failure","Failed to load transaction data"+t.message)
                userTransactionData.postValue(null)

            }

            override fun onResponse(call: Call<List<Transact>?>, response: Response<List<Transact>?>) {
                if (response.isSuccessful){
                    userTransactionData.postValue(response.body())
                    Log.d("Response Successful", "Transaction Data returned:  ${response}")
                }
                else{
                    Log.d("Response Error", "Transaction response received but with an error:  ${response}")
                    userTransactionData.postValue(null)

                }
            }
        })
    }

    fun returnQuery(PayingID:String?, queryString: String?){

        val retroInstance = Retroinstance.getRetroInstance().create(ApiService::class.java)
        val call = retroInstance.queryTransactions(PayingID!!,queryString!!).enqueue(
            object :retrofit2.Callback<List<Transact>?>{
                override fun onResponse(
                    call: Call<List<Transact>?>,
                    response: Response<List<Transact>?>
                ) {
                    if(response.isSuccessful){
                        userTransactionData.postValue(response.body())
                    }
                    else{
                        userTransactionData.postValue(null)
                        Log.d("No response","$response")
                    }

                }

                override fun onFailure(call: Call<List<Transact>?>, t: Throwable) {
                    userTransactionData.postValue(null)
                    Log.d("General Failure", "There was an error returning data: ${t.message}")
                }
            }
        )

    }


//    fun returnAccountData(userId:String?)
//    {
//        val retroFitInstance = Retroinstance.getRetroInstance().create(ApiService::class.java)
//        val call = retroFitInstance.returnAccountData(userId!!)
//        call.enqueue(object :retrofit2.Callback<UserAccountData?>{
//            override fun onFailure(call: Call<List<UserAccountData>?>, t: Throwable) {
//                Log.d("Failure-at-user-AccountLoad","Failed to load data"+t.message)
//                userAccountData.postValue(null)
//
//            }
//
//            override fun onResponse(call: Call<List<UserAccountData>?>, response: Response<List<UserAccountData>?>) {
//                if (response.isSuccessful){
//                    userAccountData.postValue(response.body())
//                    Log.d("Response Successful", "Data returned:  ${response}")
//                }
//                else{
//                    Log.d("Response Error", "Response received but with an error:  ${response}")
//                    userAccountData.postValue(null)
//
//                }
//            }
//        })
//    }
}