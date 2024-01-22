package br.com.raphaelmaracaipe.uiprofile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import br.com.raphaelmaracaipe.uiprofile.R
import br.com.raphaelmaracaipe.uiprofile.databinding.BottomSheetProfileOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileImageOptionsBottomSheet(
    private val setOnClickItem: (position: Int) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetProfileOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = BottomSheetProfileOptionsBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyValuesOfItems()
    }

    private fun applyValuesOfItems() {
        with(binding) {
            rvwItem.adapter = ProfileImageOptionsBottomSheetAdapter(
                requireContext(),
                arrayOf(
                    getString(R.string.bottom_sheet_options_gallery),
                    getString(R.string.bottom_sheet_options_camera)
                )
            ) { positionItemClicked ->
                setOnClickItem.invoke(positionItemClicked)
                dismiss()
            }
        }
    }

    companion object {

        fun show(
            fragmentManager: FragmentManager,
            setOnClickItem: (position: Int) -> Unit
        ) {
            ProfileImageOptionsBottomSheet(setOnClickItem).show(
                fragmentManager,
                "ProfileImageOptionsBottomSheet"
            )
        }

    }

}