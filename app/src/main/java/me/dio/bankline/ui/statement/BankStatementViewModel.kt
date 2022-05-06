package me.dio.bankline.ui.statement

import androidx.lifecycle.ViewModel
import me.dio.bankline.data.BankLineRepository

class BankStatementViewModel:ViewModel() {
    fun findBankStatement(accountHolderId: Int) = BankLineRepository.findBankStatement(accountHolderId)

}