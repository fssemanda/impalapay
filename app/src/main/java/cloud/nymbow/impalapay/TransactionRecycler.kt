package cloud.nymbow.impalapay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cloud.nymbow.impalapay.dataclasses.JustMethods.myFormatter
import cloud.nymbow.impalapay.dataclasses.Transact

class TransactionRecycler(context:Context,var transactionDetails:List<Transact>, val TransactionClickListener: OnItemClickListener): RecyclerView.Adapter<TransactionRecycler.myViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewholder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.transaction_row,parent,false)
        return myViewholder(inflater)

    }

    override fun onBindViewHolder(holder: myViewholder, position: Int) {
       holder.bind(transactionDetails[position])
        holder.itemView.setOnClickListener{
            TransactionClickListener.onItemClick(transactionDetails[position])
        }
    }

    override fun getItemCount(): Int {

        return transactionDetails.size
    }


    class myViewholder(view: View):RecyclerView.ViewHolder(view)

    {
        val transactionNo = view.findViewById<TextView>(R.id.transactionId)
        val transactionAmount = view.findViewById<TextView>(R.id.transactionAmount)
        val transactionType = view.findViewById<TextView>(R.id.transactionType)

        fun bind(transaction:Transact){

            val tAmount = transaction.amount?.toFloat()
            val formattedAmount =  myFormatter.format(tAmount)

            transactionNo.setText(transaction.id)
            transactionAmount.setText("UGX "+ formattedAmount)
            transactionType.setText(transaction.transactionType)


        }
    }

    interface OnItemClickListener {
        fun onItemClick(transaction: Transact)
    }

}