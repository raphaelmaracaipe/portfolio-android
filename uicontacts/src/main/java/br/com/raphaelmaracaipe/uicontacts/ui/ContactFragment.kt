package br.com.raphaelmaracaipe.uicontacts.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.raphaelmaracaipe.core.alerts.BottomSheetMessages
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.uicontacts.R
import br.com.raphaelmaracaipe.uicontacts.databinding.FragmentContactBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.exitProcess
import br.com.raphaelmaracaipe.core.R as RCore

@AndroidEntryPoint
class ContactFragment @Inject constructor() : Fragment() {

    private val itemPerPage = 20
    private var pageCurrent = 0
    private var inLoading = false
    private val contacts: ArrayList<ContactEntity> = arrayListOf()
    private val mViewModel: ContactViewModel by viewModels()
    private lateinit var binding: FragmentContactBinding
    private lateinit var adapter: ContactAdapter
    private lateinit var adapterToSearch: ContactAdapter

    private val requestPermission = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            mViewModel.sendContactsToConsult()
        } else {
            showMessagePermissionNotGranted()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentContactBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsAdapter()
        addBackPressed()
        initObservable()

    }

    override fun onStart() {
        super.onStart()
        requestPermission()
        loadingData()
    }

    private fun settingsAdapter() {
        adapter = ContactAdapter()
        adapterToSearch = ContactAdapter()

        with(binding) {
            rvwItemNotSearch.adapter = adapter
            rvwItemSearch.adapter = adapterToSearch

            nsvItems.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY) {
                    val view = v.getChildAt(v.childCount - 1) as View
                    val diff = (view.bottom - (nsvItems.height + nsvItems.scrollY))

                    if (diff == 0 && !inLoading) {
                        inLoading = true
                        loadingData()
                    }
                }
            })

            searchView.editText.addTextChangedListener {
                mViewModel.searchItem(it.toString(), itemPerPage, pageCurrent)
            }
        }
    }

    private fun loadingData() {
        mViewModel.contactSaved(itemPerPage, (pageCurrent * itemPerPage))
    }

    private fun initObservable() {
        mViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            inLoading = false
            pageCurrent += 1

            this.contacts.addAll(contacts)
            adapter.chargeContacts(this.contacts)
        }

        mViewModel.contactsOfSearch.observe(viewLifecycleOwner) { contacts ->
            adapterToSearch.chargeContacts(contacts)
        }
    }

    private fun requestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) -> mViewModel.sendContactsToConsult()

            else -> requestPermission.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun showMessagePermissionNotGranted() {
        BottomSheetMessages.show(
            childFragmentManager,
            getString(RCore.string.alert_empty_title),
            getString(R.string.error_permission_contact),
            onButtonYesClicked = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts(
                        "package",
                        requireContext().packageName,
                        null
                    )
                }
                startActivity(intent)
            }
        )
    }

    private fun addBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitProcess(0)
                }
            }
        )
    }

}