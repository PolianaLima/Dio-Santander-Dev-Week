package me.dio.bankline.ui.statement

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import me.dio.bankline.data.State
import me.dio.bankline.databinding.ActivityBankStatementBinding
import me.dio.bankline.domain.Correntista

class BankStatementActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ACCOUNT_HOLDER = "me.dio.bankline.ui.statement.EXTRA_ACCOUNT_HOLDER"
    }

    private val binding by lazy {
        ActivityBankStatementBinding.inflate(layoutInflater)
    }

    private val accountHolder by lazy {
        intent.getParcelableExtra<Correntista>(EXTRA_ACCOUNT_HOLDER)
            ?: throw  IllegalArgumentException()
    }

    private val viewModel by viewModels<BankStatementViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvBankStatement.layoutManager = LinearLayoutManager(this)


        findByStatement()

        binding.srlBankStatement.setOnRefreshListener { findByStatement() }

    }

    private fun findByStatement() {
        viewModel.findBankStatement(accountHolder.id).observe(this) {state ->
            when(state) {
            is State.Sucess->{
                binding.rvBankStatement.adapter = state.data?.let {BankStatementAdapter(it)}
                binding.srlBankStatement.isRefreshing = false
            }
                is State.Error -> {
                    state.message?.let { Snackbar.make(binding.rvBankStatement, it, Snackbar.LENGTH_LONG).show() }

                    binding.srlBankStatement.isRefreshing = false
                }
                State.Wait -> binding.srlBankStatement.isRefreshing = true

        }
        }
    }
}