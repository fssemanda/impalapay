package cloud.nymbow.impalapay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cloud.nymbow.impalapay.apis.ApiService
import cloud.nymbow.impalapay.databinding.FragmentTransactionDetailBinding
import cloud.nymbow.impalapay.dataclasses.DashboardViewModel
import cloud.nymbow.impalapay.dataclasses.TransactionItems

class TransactionDetail : Fragment() {
//    InvoiceItemsRecycler.OnItemClickListener

    private var _binding:FragmentTransactionDetailBinding? = null
    private val binding get() =  _binding!!
    private lateinit var viewModel: DashboardViewModel
    private lateinit var invoicesAdapter:InvoiceItemsRecycler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container,false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.amountRO.setText(requireArguments().getString("Amount"))
        binding.dateR0.setText(requireArguments().getString("TransactionDate"))

        initRecycler()
        initViewModel()
        listInvoiceItems(requireArguments().getString("TransactionID"))

    }

    private fun initRecycler()
    {
        var context= requireContext()
        binding.invoiceItemsRecycleView.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        }
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    private fun listInvoiceItems(transactionId:String?){

        viewModel.invoiceItemsObservable().observe(viewLifecycleOwner,Observer<List<TransactionItems>?>{
            try {
                if(it!=null){
                    val context =  requireContext()
                    invoicesAdapter = InvoiceItemsRecycler(context, it)
                    binding.invoiceItemsRecycleView.adapter = invoicesAdapter

                }
                else
                {
                    Toast.makeText(this.context, "Failed", Toast.LENGTH_LONG).show()
                }
            }catch (
                e:Exception
            ){
                Toast.makeText(this.context, "$e", Toast.LENGTH_LONG).show()
            }


        })
viewModel.returnInvoiceItems(transactionId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //not implementing an OnClick Listerner
//
//    override fun onItemClicked(invoiceItem: TransactionItems) {
//        TODO("Not yet implemented")
//    }


}