package br.com.raphaelmaracaipe.uiauth.ui.countries

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.uiauth.databinding.ItemCountriesBinding
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import br.com.raphaelmaracaipe.uiauth.utils.CountryCodeFlags

class CountriesAdapter(
    private val assets: Assets,
    private val onClickItem: (countrySelected: CodeCountry) -> Unit,
    private val onHasFinished: () -> Unit
) : ListAdapter<CodeCountry, CountriesAdapter.ViewHolder>(CodeCountryDiffCallback()) {

    fun searchItem(text: String, items: List<CodeCountry>) {
        val itemsFound = items.filter { codeCountry ->
            (codeCountry.countryName?.lowercase()?.contains(text.lowercase()) ?: false)
        }

        submitList(itemsFound)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCountriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(
            parent.context,
            assets,
            binding,
            onClickItem
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == (currentList.size - 1)) {
            onHasFinished.invoke()
        }
        holder.bindView(getItem(position))
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.clear()
        super.onViewRecycled(holder)
    }

    inner class ViewHolder(
        private val context: Context,
        private val assets: Assets,
        private val binding: ItemCountriesBinding,
        private val onClickItem: (countrySelected: CodeCountry) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(country: CodeCountry) {
            with(binding) {
                text = country.countryName
                imvFlag.setImageDrawable(CountryCodeFlags.getFlag(assets, country))

                cltContainer.setOnClickListener {
                    cltContainer.startAnimation(
                        AnimationUtils.loadAnimation(
                            context,
                            android.R.anim.fade_out
                        )
                    )
                    onClickItem.invoke(country)
                }
            }
        }

        fun clear() {
            with(binding) {
                textView.text = ""
                imvFlag.setImageResource(0)
            }
        }

    }

    class CodeCountryDiffCallback : DiffUtil.ItemCallback<CodeCountry>() {

        override fun areItemsTheSame(oldItem: CodeCountry, newItem: CodeCountry): Boolean {
            return (oldItem.countryName == newItem.countryName && oldItem.codeCountry == newItem.codeCountry)
        }

        override fun areContentsTheSame(oldItem: CodeCountry, newItem: CodeCountry): Boolean {
            return oldItem == newItem
        }

    }
}