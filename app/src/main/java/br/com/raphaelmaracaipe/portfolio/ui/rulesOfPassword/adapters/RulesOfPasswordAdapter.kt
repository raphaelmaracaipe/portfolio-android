package br.com.raphaelmaracaipe.portfolio.ui.rulesOfPassword.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.raphaelmaracaipe.portfolio.databinding.AdapterRulesOfPasswordBinding
import br.com.raphaelmaracaipe.portfolio.ui.rulesOfPassword.models.RulesOfPasswordDTO

class RulesOfPasswordAdapter(
    private val context: Context
) : RecyclerView.Adapter<RulesOfPasswordAdapter.ViewHolder>() {

    private var items: Array<RulesOfPasswordDTO> = arrayOf()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val bind = AdapterRulesOfPasswordBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindView(item)
    }

    fun setItems(items: Array<RulesOfPasswordDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    class ViewHolder(
        private val bind: AdapterRulesOfPasswordBinding
    ) : RecyclerView.ViewHolder(bind.root) {

        fun bindView(item: RulesOfPasswordDTO) {
            bind.rulesOfPasswordModel = item
        }

    }

}