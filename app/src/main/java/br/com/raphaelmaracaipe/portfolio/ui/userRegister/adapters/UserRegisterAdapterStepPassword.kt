package br.com.raphaelmaracaipe.portfolio.ui.userRegister.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.raphaelmaracaipe.portfolio.databinding.AdapterUserRegisterStepPasswordBinding
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.models.UserRegisterPasswordValidate

class UserRegisterAdapterStepPassword(
    private val context: Context
) : RecyclerView.Adapter<UserRegisterAdapterStepPassword.ViewHolder>() {

    private var items: Array<UserRegisterPasswordValidate> = arrayOf()

    fun setItems(items: Array<UserRegisterPasswordValidate>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val bind = AdapterUserRegisterStepPasswordBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindView(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(
        private val bind: AdapterUserRegisterStepPasswordBinding
    ) : RecyclerView.ViewHolder(bind.root) {

        fun bindView(item: UserRegisterPasswordValidate) {
            bind.userRegisterPasswordValidate = item
        }

    }

}