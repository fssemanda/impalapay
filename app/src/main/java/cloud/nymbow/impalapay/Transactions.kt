package cloud.nymbow.impalapay

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cloud.nymbow.impalapay.databinding.FragmentTransactionsBinding
import cloud.nymbow.impalapay.dataclasses.DashboardViewModel
import cloud.nymbow.impalapay.dataclasses.JustMethods.myFormatter
import cloud.nymbow.impalapay.dataclasses.Transact
import cloud.nymbow.impalapay.loginApi.SignIn
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Transactions.newInstance] factory method to
 * create an instance of this fragment.
 */
class Transactions : Fragment(),TransactionRecycler.OnItemClickListener {

    private var _binding:FragmentTransactionsBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: DashboardViewModel
    private lateinit var transAdapter:TransactionRecycler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_transactions, container, false)
        _binding = FragmentTransactionsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myIntentString = activity?.intent?.extras!!.getString(SignIn.USER_ID)

        initViewModel()
        initTransactionsRecycler()
        listAllTransactions(myIntentString)
        searchItems(myIntentString)
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    private fun initTransactionsRecycler(){
        val myRecycler = binding.transactionPageRecycler
        myRecycler.apply {
            layoutManager = LinearLayoutManager(this.context)
        }


    }

    private fun listAllTransactions(uid:String?){
        val context = requireContext()
        val view = requireView()
        viewModel.userTransactionDataObservable().observe(viewLifecycleOwner, Observer<List<Transact>?>{
            if(it!=null){

                transAdapter = TransactionRecycler(context, it, this)
                binding.transactionPageRecycler.adapter=transAdapter
                Snackbar.make(view, "All previous Transactions", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.floatingActionButton)
                    .setAction("Action", null).show()

            }
            else
            {
                Snackbar.make(view, "No Data Returned", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.floatingActionButton)
                    .setAction("Action", null).show()
            }
        })



viewModel.returnAccountTransactions(uid!!)
    }


    private fun searchItems(PayingID:String?){
        binding.searchContainer.editText?.setOnKeyListener OnKeyListener@{ view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_UP) {

                viewModel.returnQuery(PayingID,binding.searchContainer.editText?.text.toString())


                return@OnKeyListener true
            }
            listAllTransactions(PayingID)
            false
        }

    }

    override fun onItemClick(transaction: Transact) {
        val myBundle =Bundle()
//        val view = requireView()
        myBundle.putString("TransactionID",transaction.id)
        findNavController().navigate(R.id.action_Transactions_to_transactionDetail, Bundle().apply {
            putString("TransactionID",transaction.id)
            putString("Amount",myFormatter.format(transaction.amount).toString())
            putString("PayingID",transaction.payingId)
            putString("AccountID",transaction.accountId)
            putString("TransactionDate",transaction.transanctionDate.toString())
        })


    }

}
