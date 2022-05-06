package me.dio.bankline.ui.statement

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import me.dio.bankline.R
import me.dio.bankline.databinding.BankStatementItemBinding
import me.dio.bankline.domain.Movimentacao
import me.dio.bankline.domain.TipoMovimentacao
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Reference: https://developer.android.com/guide/topics/ui/layout/recyclerview?hl=pt-br#kotlin
 */
class BankStatementAdapter(private val dataSet: List<Movimentacao>) : RecyclerView.Adapter<BankStatementAdapter.ViewHolder>() {

    class ViewHolder(private val binding: BankStatementItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movimentacao) = with(binding) {
            tvDescription.text = item.descricao
            tvValue.text = "R$ ${item.valor}"
            val dataFormatada =
                LocalDateTime.parse(item.dataHora).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            tvDatetime.text = dataFormatada

            val  typeIcon = if(TipoMovimentacao.RECEITA ==item.tipo) R.drawable.ic_money_in else R.drawable.ic_money_out
            ivIcon.setImageResource(typeIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BankStatementItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.bind(item)
    }

    override fun getItemCount() = dataSet.size
}