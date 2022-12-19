package cloud.nymbow.impalapay.paymentsystem

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.navigation.ui.setupActionBarWithNavController
import cloud.nymbow.impalapay.R
import cloud.nymbow.impalapay.databinding.ActivityMainBinding
import cloud.nymbow.impalapay.databinding.ActivityNfcPayBinding
import cloud.nymbow.nfcrw.NFCUtil
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NfcPayActivity : AppCompatActivity() {
    private lateinit var item1:String
    private lateinit var item2:String
    private var mNfcAdapter: NfcAdapter? = null
    private lateinit var binding: ActivityNfcPayBinding
    private lateinit var myToolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_nfc_pay)

        binding = ActivityNfcPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myToolbar = binding.myToolbar
        supportActionBar?.show()
        setSupportActionBar(myToolbar)


        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        readNFCFunc()


        binding.payBtn.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle("Make Payment to Merchant")
                .setMessage("By Ckicking Accept, you are consenting to pay the merchant ${binding.amountPaid.editText?.text.toString()}")
                .setNeutralButton("Cancel") { dialog, which ->
                    // Respond to neutral button press
                }
                .setNegativeButton("Decline") { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton("Accept") { dialog, which ->
                    // Respond to positive button press
                }
                .show()

        }


    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onResume() {
        super.onResume()
        mNfcAdapter?.let {
            NFCUtil.enableNFCInForeground(it, this, javaClass)
        }
    }

    override fun onPause() {
        super.onPause()
        mNfcAdapter?.let {
            NFCUtil.disableNFCInForeground(it, this)
        }
    }



    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        var item1 = binding.messageText.text.toString()
        var item2 = binding.merchantId.text.toString()
        if (item1 != "" && item2 != "") {

//            try {
//            }
        }
    }
    fun readNFCFunc(){
        try{

            val action = intent?.action
            if (NfcAdapter.ACTION_TECH_DISCOVERED == action
                || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
//                binding.textView.text = "SHOP NO"
                val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                with(parcelables) {
                    try {
                        val inNdefMessage = this!![0] as NdefMessage
                        val inNdefRecords = inNdefMessage.records
//                        val messages
                        for (item in inNdefRecords) {
                            Log.d("MyInfo", "$item")
                        }

                        //if there are many records, you can call inNdefRecords[1] as array
                        var ndefRecord_0 = inNdefRecords[1]
                        var inMessage = String(ndefRecord_0.payload)
                        item1 = (inMessage.drop(3))
                        binding.messageText.setText(item1)

                        ndefRecord_0 = inNdefRecords[2]
                        inMessage = String(ndefRecord_0.payload)
                        item2 = (inMessage.drop(3))
                        binding.merchantId.setText(item2)

                    } catch (ex: Exception) {
                        Toast.makeText(
                            applicationContext,
                            "There are no Machine and Shop information found!, please click write data to add data!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        catch (
            e:Exception
        ){
            Log.d("Exception","$e")
            Toast.makeText(applicationContext, "There was an exception:$e", Toast.LENGTH_SHORT).show()
        }
    }

    fun<T> ifElse(condition: Boolean, primaryResult: T, secondaryResult: T) = if (condition) primaryResult else secondaryResult

}