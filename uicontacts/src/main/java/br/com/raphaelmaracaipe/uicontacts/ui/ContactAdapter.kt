package br.com.raphaelmaracaipe.uicontacts.ui

import android.content.Context
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.uicontacts.databinding.ItemContactBinding
import com.bumptech.glide.Glide
import br.com.raphaelmaracaipe.core.R as RCore

class ContactAdapter : ListAdapter<ContactEntity, ContactAdapter.ViewHolder>(
    ContactDiffCallback()
) {

    private var onClickItem: ((ContactEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(
            parent.context,
            binding = binding,
            onClickItem = { contactEntity ->
                onClickItem?.invoke(contactEntity)
            }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    fun chargeContacts(contacts: ArrayList<ContactEntity>) {
        submitList(ArrayList(contacts))
    }

    fun setOnClickItem(onClickItem: (ContactEntity) -> Unit) {
        this.onClickItem = onClickItem
    }

    inner class ViewHolder(
        private val context: Context,
        private val onClickItem: (item: ContactEntity) -> Unit,
        val binding: ItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: ContactEntity?) {
            item?.let { ce ->
                with(binding) {
                    contactEntity = ce
                    applyImage(context, ce)

                    cltContact.setOnClickListener {
                        onClickItem.invoke(ce)
                    }
                }
            }
        }
    }

    private fun ItemContactBinding.applyImage(context: Context, item: ContactEntity?) {
        try {
            item?.photo?.let { photo ->
                if (photo.isEmpty()) {
                    return@let
                }

                val byteArray = Base64.decode(photo, Base64.DEFAULT)

                Glide.with(context)
                    .load(byteArray)
                    .apply {
                        isMemoryCacheable
                    }
                    .placeholder(RCore.drawable.ic_profile)
                    .into(sivProfile)
            }
        } catch (_: Exception) {
            sivProfile.setImageResource(RCore.drawable.ic_profile)
        }
    }

    private class ContactDiffCallback : DiffUtil.ItemCallback<ContactEntity>() {

        override fun areItemsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
            return (oldItem.phone == newItem.phone)
        }

        override fun areContentsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
            return oldItem == newItem
        }
    }

}