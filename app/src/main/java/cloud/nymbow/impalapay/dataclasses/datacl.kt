package cloud.nymbow.impalapay.dataclasses

data class ApiUser(
    val userID:String?,
    val user:String?

)

data class UserAccountData(
    val userId:String?,
    val acBalance:Float?,
    val interestEarned:Float?,
    val lastUpdateDate:String?
)

data class Transact(
    val id:String?,
    val accountId:String?,
    val transactionType:String?,
    val amount:Double?,
    val transanctionDate: String?,
    val payingId:String?

)

data class TransactionItems(
    val transactionsId:String?,
//    val transactionsId:Transact,
    val lineItem:String?,
    val lineItemCost:Double?
)

data class Interest(
    val accountid:UserAccountData?,
    val daysInterest:Float?,
    val daysRate:Float?,
    val dateEarned:String?,
    val timeCredited:String?
)