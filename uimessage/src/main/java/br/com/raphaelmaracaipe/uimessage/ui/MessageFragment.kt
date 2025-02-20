package br.com.raphaelmaracaipe.uimessage.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import br.com.raphaelmaracaipe.uimessage.databinding.FragmentMessageBinding
import br.com.raphaelmaracaipe.uimessage.workers.StatusWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MessageFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentMessageBinding
    private val timeConsultStatusContact = 30L
    private val workName = "ConsultStatusContact"
    private val args: MessageFragmentArgs by navArgs()
    private val mViewModel: MessageViewModel by viewModels()

    private val contact: ContactEntity by lazy {
        args.contact.fromJSON()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMessageBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        viewModel = mViewModel
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initObservable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cancelWork()
        setupWorkManager()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("RAPHAEL", "DESTROY VIEW OF MESSAGE")
        cancelWork()
    }

    private fun initViewModel() {
        with(mViewModel) {
            connect()
            returnConsultFlow(contact.phone)
            iAmOnline()
        }
    }

    private fun initObservable() {
        with(mViewModel) {
            onIAmOnline.observe(viewLifecycleOwner) {
            }

            onBack.observe(viewLifecycleOwner) {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<StatusWorker>(
            timeConsultStatusContact, TimeUnit.SECONDS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            workName,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    private fun cancelWork() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork(workName)
    }
}