package br.com.raphaelmaracaipe.uiprofile.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.raphaelmaracaipe.uiprofile.databinding.BottomSheetProfileOptionsItemBinding
import br.com.raphaelmaracaipe.uiprofile.ui.ProfileImageOptionsBottomSheetAdapter.ViewHolder

class ProfileImageOptionsBottomSheetAdapter(
    private val context: Context,
    private val items: Array<String>,
    private val setOnClickItem: (position: Int) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    inner class ViewHolder(
        private val binding: BottomSheetProfileOptionsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var view = binding.cltContainer

        var title = ""
            set(value) {
                field = value
                applyInBinding(value)
                checkPosition(value)
            }

        private fun checkPosition(value: String) {
            val isLastElementOfList = (items.indexOf(value) == (items.size - 1))
            binding.isLast = isLastElementOfList
        }

        private fun applyInBinding(value: String) {
            binding.title = value
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        BottomSheetProfileOptionsItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.title = items[position]
        holder.view.setOnClickListener {
            setOnClickItem.invoke(position)
        }
    }

    override fun getItemCount(): Int = items.size
}