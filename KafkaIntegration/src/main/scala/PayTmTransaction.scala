package main.scala

case class PayTmTransaction(tx_date: String,activity: String,sourceDestination: String,walletTxId: String,status: String){

  var usr_comment : String = "";
  var debit : Integer = 0;
  var credit : Integer = 0;
  var transaction_breakup : String = "";

  def set_Usr_comment(i_usr_comment: String){
    this.usr_comment = i_usr_comment;
  }
  def get_Usr_comment(): String ={
    return usr_comment;
  }
  def set_Debit(i_debit: Integer){
    this.debit = i_debit;
  }
  def get_Debit: Integer ={
    return debit;
  }
  def set_Credit(i_credit: Integer){
    this.credit = i_credit;
  }
  def get_Credit: Integer ={
    return credit;
  }
  def set_Transaction_breakup(i_transaction_breakup: String){
    this.transaction_breakup = i_transaction_breakup;
  }
  def get_Transaction_breakup: String ={
    return transaction_breakup;
  }
}
