package cloud.nymbow.impalapay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cloud.nymbow.impalapay.dataclasses.JustMethods.myFormatter
import cloud.nymbow.impalapay.dataclasses.TransactionItems

class InvoiceItemsRecycler (context:Context, val invoiceitems:List<TransactionItems>):RecyclerView.Adapter<InvoiceItemsRecycler.InvoiceItemsViewHolder>() {
//    ,val myClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.invoice_item_row,parent,false)
        return InvoiceItemsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: InvoiceItemsViewHolder, position: Int) {
        holder.bind(invoiceitems[position])
//        holder.itemView.setOnClickListener {
//            myClickListener.onItemClicked(invoiceitems[position])
//        }
    }

    override fun getItemCount(): Int {
        return invoiceitems.size
    }

    class InvoiceItemsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val lineItem = view.findViewById<TextView>(R.id.invoiceItemRO)

        val lineItemAmount = view.findViewById<TextView>(R.id.invoiceItemAmountRO)

//        val iamount:TextView = view.findViewById(R.id.iItemAmount)


        fun bind(invoiceItem:TransactionItems) {

            var newInvoiceItem = myFormatter.format(invoiceItem.lineItemCost).toString()

            lineItem.setText(invoiceItem.lineItem)
            lineItemAmount.setText(newInvoiceItem)
    }


    }

//    interface OnItemClickListener {
//            fun onItemClicked(invoiceItem:TransactionItems)
//    }

}
