package br.com.raphaelmaracaipe.portfolio.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.raphaelmaracaipe.portfolio.databinding.BottomSheetMainMessageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainMessageBottomSheet : BottomSheetDialogFragment() {

    private lateinit var bind: BottomSheetMainMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = BottomSheetMainMessageBinding.inflate(inflater)
        return bind.root
    }

    companion object {
        const val TAG = "MessageBottomSheet"
    }

}