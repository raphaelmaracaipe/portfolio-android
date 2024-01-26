package br.com.raphaelmaracaipe.uiprofile.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.core.alerts.BottomSheetMessages
import br.com.raphaelmaracaipe.core.extensions.toBase64
import br.com.raphaelmaracaipe.core.extensions.toByteArray
import br.com.raphaelmaracaipe.core.navigation.NavigationURI
import br.com.raphaelmaracaipe.core.navigation.NavigationURI.CONTACTS
import br.com.raphaelmaracaipe.uiprofile.R
import br.com.raphaelmaracaipe.uiprofile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.system.exitProcess
import br.com.raphaelmaracaipe.core.R as RCore

@AndroidEntryPoint
class ProfileFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val mViewModel: ProfileViewModel by viewModels()
    private val TYPE_PERMISSION_GENERIC = 0
    private val TYPE_PERMISSION_GALLERY = 1
    private val TYPE_PERMISSION_CAMERA = 2
    private var imageProfile: Pair<Bitmap, String>? = null
    private var typePermissionRequested = TYPE_PERMISSION_GENERIC
    private var msgWhenPermissionNotGranted = ""

    private val resultIntentGallery = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        result.data?.data?.let { uriData ->
            val inputStream = requireContext().contentResolver.openInputStream(uriData)
            val bytes = inputStream?.readBytes()
            val base64 = bytes?.toBase64() ?: ""

            bytes?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                saveBitmap(bitmap)
                mViewModel.addImage(bitmap)
                imageProfile = Pair(bitmap, base64)
            }
        }
    }

    private val resultIntentCapture = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        result?.data?.let { data ->
            val bitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data.getParcelableExtra("data", Bitmap::class.java)
            } else {
                data.getParcelableExtra("data")
            }

            bitmap?.let {
                val bytes = it.toByteArray()
                val base64 = bytes.toBase64()

                saveBitmap(it)
                base64?.let { b64 ->
                    mViewModel.addImage(bitmap)
                    imageProfile = Pair(it, b64)
                }
            }
        }
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context?.openFileOutput("profile.png", Context.MODE_PRIVATE)!!
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val requestPermission = registerForActivityResult(
        RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            when (typePermissionRequested) {
                TYPE_PERMISSION_GALLERY -> {
                    openGallery()
                }

                TYPE_PERMISSION_CAMERA -> {
                    openCamera()
                }
            }
        } else {
            showMessagePermissionNotGranted(msgWhenPermissionNotGranted)
        }
        typePermissionRequested = 0
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentProfileBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        viewModel = mViewModel

        addOnBack()
        addObservable()
    }.root

    private fun addObservable() {
        mViewModel.msgError.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        mViewModel.profileSaved.observe(viewLifecycleOwner) {
            findNavController().navigate(CONTACTS)
        }
    }

    private fun addOnBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitProcess(0)
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyActionInButtons()
        checkIfPermissionWriteFile()
    }

    private fun checkIfPermissionWriteFile() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            msgWhenPermissionNotGranted = getString(R.string.permission_not_granted_gallery)
            requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun applyActionInButtons() {
        with(binding) {
            sivProfile.setOnClickListener {
                callOptionsToCapture()
            }

            btnContinue.setOnClickListener {
                checkField()
            }
        }
    }

    private fun callOptionsToCapture() {
        ProfileImageOptionsBottomSheet.show(
            childFragmentManager
        ) { positionItemClicked ->
            if (positionItemClicked == 0) {
                checkIfPermissionGalleryAndRequest()
            } else {
                checkIfPermissionCameraAndRequest()
            }
        }
    }

    private fun checkIfPermissionGalleryAndRequest() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> openGallery()

            else -> {
                typePermissionRequested = TYPE_PERMISSION_GALLERY
                msgWhenPermissionNotGranted = getString(R.string.permission_not_granted_gallery)
                requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkIfPermissionCameraAndRequest() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) -> openCamera()

            else -> {
                typePermissionRequested = TYPE_PERMISSION_CAMERA
                msgWhenPermissionNotGranted = getString(R.string.permission_not_granted_gallery)
                requestPermission.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showMessagePermissionNotGranted(messageBody: String) {
        BottomSheetMessages.show(
            childFragmentManager,
            getString(RCore.string.alert_empty_title),
            messageBody,
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

    private fun openGallery() {
        resultIntentGallery.launch(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        )
    }

    private fun openCamera() {
        val intent = Intent(ACTION_IMAGE_CAPTURE)
        resultIntentCapture.launch(intent)
    }

    private fun checkField() {
        if (mViewModel.ifExistData()) {
            mViewModel.sendProfileToServer()
        } else {
            showAlertOfDataEmpty()
        }
    }

    private fun showAlertOfDataEmpty() {
        BottomSheetMessages.show(
            childFragmentManager,
            getString(RCore.string.alert_empty_title),
            getString(R.string.alert_empty_name),
            getString(R.string.alert_yes),
            onButtonYesClicked = {
                imageProfile?.second?.let {
                    mViewModel.markWhichProfileSaved(it)
                }

                findNavController().navigate(NavigationURI.CONTACTS)
            }
        )
    }

}