package cloud.nymbow.nfcrw

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

object NFCUtil {
    //    fun defineMessage(MerchantName: String, MerchantId:String, intent: Intent?): Boolean{
    fun defineMessage(payload:String, MerchantName: String, MerchantId:String, intent: Intent?): Boolean{
        val pathPrefix = "cloud.nymbow.nfcrw:nfcapp"
//        val payload:String=""
        val nfcRecord = NdefRecord(NdefRecord.TNF_EXTERNAL_TYPE, pathPrefix.toByteArray(), ByteArray(0),payload.toByteArray())
        Log.d("NFCRecord:","$nfcRecord")
        val nfcMessage = NdefMessage(arrayOf(nfcRecord))
        Log.d("NFCMessage:","$nfcRecord")

        val msg = NdefMessage(arrayOf(
//            NdefRecord.createTextRecord("en", nfcMessage.toString()),
            NdefRecord.createUri("vnd.android.nfc://ext/cloud.nymbow.nfcrw:nfcapp"),
//            NdefRecord.createApplicationRecord("cloud.nymbow.nfcrw"),

            NdefRecord.createTextRecord("en",payload),
            NdefRecord.createTextRecord("en",MerchantName),
            NdefRecord.createTextRecord("en",MerchantId),
        ))

        intent?.let {
            val tag = it.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            Log.d("MyInfo", "$tag")

            return  writeToNFC(msg,tag)
        }
        return false
    }



    @RequiresApi(Build.VERSION_CODES.S)
    fun <T>enableNFCInForeground(nfcAdapter: NfcAdapter, activity: Activity, classType : Class<T>) {
//        val flags = when {
//            true -> PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            else -> PendingIntent.FLAG_UPDATE_CURRENT
//        }
        val pendingIntent = PendingIntent.getActivity(activity, 0, Intent(activity,classType).addFlags(
            Intent.FLAG_ACTIVITY_SINGLE_TOP),PendingIntent.FLAG_MUTABLE)
        val nfcIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val nfcIntentFilter2 = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val filters = arrayOf(nfcIntentFilter)

        val TechLists = arrayOf(arrayOf(Ndef::class.java.name), arrayOf(NdefFormatable::class.java.name))

        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, TechLists)
    }



    fun disableNFCInForeground(nfcAdapter: NfcAdapter, activity: Activity) {
        nfcAdapter.disableForegroundDispatch(activity)
    }
    private fun writeToNFC(nfcMessage: NdefMessage, tag: Tag?): Boolean{
//    private fun writeToNFC(nfcMessage: NdefMessage, nfcMessage1: NdefMessage, tag: Tag?): Boolean{
        try{

            val nDefTag  = Ndef.get(tag)

            nDefTag?.let {
                it.connect()
                if(it.maxSize < nfcMessage.toByteArray().size){

//                    Toast.makeText(this, "Message too large to write on Tag", Toast.LENGTH_SHORT).show()
                    return false
                }
                if(it.isWritable){
                    it.writeNdefMessage(nfcMessage)
//                    it.writeNdefMessage(nfcMessage1)
//                    it.makeReadOnly()
                    it.close()
//                    Toast.makeText(this, "Message written to tag", Toast.LENGTH_SHORT).show()

                    return true
                }
                else{
//                    Toast.makeText(this, "Tag is not writable", Toast.LENGTH_SHORT).show()
                    return false
                }
            }

            val nDefFormatableTag = NdefFormatable.get(tag)

            nDefFormatableTag?.let {
                try{
                    it.connect()
                    it.format(nfcMessage)
                    it.close()
//                    Toast.makeText(this, "Tag formatted with Data", Toast.LENGTH_SHORT).show()

                    return true
                }catch (e:Exception){
//                    Toast.makeText(this, "There was an exception${e}", Toast.LENGTH_SHORT).show()
                    Log.d("Formatable Data Exception", "${e}")
                    return false
                }
            }

//            Toast.makeText(this, "NDEF is not supported", Toast.LENGTH_SHORT).show()

            return false
        }catch (e:Exception){
//            Toast.makeText(this, "Write operation failed: ${e}", Toast.LENGTH_SHORT).show()
            Log.d("Write operation failed", "${e}")
        }
        return false

    }


}
