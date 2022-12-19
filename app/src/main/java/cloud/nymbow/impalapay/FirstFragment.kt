package cloud.nymbow.impalapay

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cloud.nymbow.impalapay.databinding.FragmentFirstBinding
import cloud.nymbow.impalapay.dataclasses.DashboardViewModel
import cloud.nymbow.impalapay.dataclasses.JustMethods
import cloud.nymbow.impalapay.dataclasses.Transact
import cloud.nymbow.impalapay.dataclasses.UserAccountData
import cloud.nymbow.impalapay.loginApi.SignIn.Companion.USER_ID
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(),TransactionRecycler.OnItemClickListener {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var transactionListingAdapter:TransactionRecycler

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var viewModel: DashboardViewModel
    lateinit var viewModel2: DashboardViewModel
    val myFormatter = DecimalFormat("#,###,###.##")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initializeViewModel()
        initRecycler()
         val myIntentString = activity?.intent?.extras!!.getString(USER_ID)
        loadAccountData(myIntentString!!)

        listUserTransactions(myIntentString)
    }
    private fun initializeViewModel(){
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
       viewModel2 = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }
    private fun loadAccountData(uid:String){
        viewModel.userAccountDataObservable().observe(this.viewLifecycleOwner,Observer<UserAccountData?>{
            if(it != null){
//                var acbalance = _binding?.acBalance
//                myFormatter = DecimalFormat("#,###,###.##")
                binding.acBalance.text = myFormatter.format(it.acBalance).toString()
                binding.acBalance2.text = "UGX "+ myFormatter.format(it.acBalance).toString()
                binding.interest.text = myFormatter.format(it.interestEarned).toString()
                Toast.makeText(context,"Data loaded", Toast.LENGTH_LONG).show()
            }
            else{

                Toast.makeText(context, "No Data was Loaded", Toast.LENGTH_LONG).show()

            }
        })
        viewModel.returnAccountData(uid)
    }





    private fun initRecycler(){
        val context = requireContext()
        val myRecycler = binding.transactionListing
        myRecycler.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            val decoration = DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
//            addItemDecoration(decoration)

        }

    }
//    @SuppressLint("FragmentLiveDataObserve")
    private fun listUserTransactions(uid:String?){

    val view = requireView()
        viewModel.userTransactionDataObservable().observe(viewLifecycleOwner,Observer{
            if(it!=null){
                val context = requireContext()

                transactionListingAdapter = TransactionRecycler(context, it,this)
                fun internalFunc() : Double {
                    var totalTransactions = 0.00
                    for (item in it) {

                        totalTransactions += item.amount!!

                    }
                    return totalTransactions
                }

//                    totalTransactions =  myFormatter.format(totalTransactions).toDouble()
                val recycler = view.findViewById<RecyclerView>(R.id.transactionListing)
                recycler.adapter = transactionListingAdapter
                binding.totalTransactions.text="UGX " + myFormatter.format(internalFunc().toDouble()).toString()

                Toast.makeText(context,"${internalFunc()}",Toast.LENGTH_LONG).show()

            }
            else
            {
                Snackbar.make(view, "No Data Returned", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.transactionListing)
                .setAction("Action", null).show()
//
            }
        })

        viewModel.returnAccountTransactions(uid)
    }


    override fun onItemClick(transaction: Transact) {
        val myBundle = Bundle()
//        val view = requireView()
        myBundle.putString("TransactionID", transaction.id)
        findNavController().navigate(R.id.action_FirstFragment_to_transactionDetail, Bundle().apply {
            putString("TransactionID", transaction.id)
            putString("Amount", JustMethods.myFormatter.format(transaction.amount).toString())
            putString("PayingID", transaction.payingId)
            putString("AccountID", transaction.accountId)
            putString("TransactionDate", transaction.transanctionDate.toString())
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

