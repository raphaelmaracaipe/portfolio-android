package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.EVENT_KEY_LOADING
import br.com.raphaelmaracaipe.portfolio.databinding.FragmentUserRegisterStepThreeBinding
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
import br.com.raphaelmaracaipe.portfolio.ui.main.MainActivity
import br.com.raphaelmaracaipe.portfolio.utils.events.Event
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.ceil

class UserRegisterStepThreeFragment : Fragment() {

    @Inject
    @EventModule.Events
    lateinit var event: Event

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<UserRegisterViewModel> { viewModelFactory }

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var bind: FragmentUserRegisterStepThreeBinding

    private var userRegisterModel = UserRegisterModel()
    private val codeText: Array<String> = arrayOf("", "", "", "")
    private val codeField by lazy {
        var views = arrayOf<EditText>()
        bind.apply {
            views += edtNumber1
            views += edtNumber2
            views += edtNumber3
            views += edtNumber4
        }
        views
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prepareInject()
    }

    private fun prepareInject() {
        (requireActivity().application as App).appComponent.userRegisterSubcomponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentUserRegisterStepThreeBinding.inflate(inflater).apply {
        bind = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getValueOfArgs()
        applyActionEditText()
        applyActionInButton()
        prepareLiveData()
        callServerToSendCode()
    }

    private fun getValueOfArgs() {
        arguments?.let {
            val ars: UserRegisterStepTwoFragmentArgs = UserRegisterStepTwoFragmentArgs.fromBundle(
                it
            )
            userRegisterModel = ars.userRegisterModel ?: UserRegisterModel()
            Log.i("RAPHAEL", "A")
        }
    }

    private fun prepareLiveData() {
        viewModel.afterCallToRegister.observe(viewLifecycleOwner) {
            event.send(EVENT_KEY_LOADING, false)
            countDownTimer.cancel()
            findNavController().navigate(UserRegisterStepThreeFragmentDirections.actionUserRegisterStepThreeFragmentToNavGraphInternal())
        }

        viewModel.afterCallCode.observe(viewLifecycleOwner) { isSuccess ->
            event.send(EVENT_KEY_LOADING, false)

            val msgError = if (isSuccess) {
                startCountDown()
                resources.getString(R.string.reg_code_success)
            } else {
                resources.getString(R.string.reg_code_error)
            }

            Snackbar.make(bind.root, msgError, Snackbar.LENGTH_LONG).show()
        }

        viewModel.errors.observe(viewLifecycleOwner) { msgError ->
            event.send(EVENT_KEY_LOADING, false)
            Snackbar.make(bind.root, msgError, Snackbar.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun startCountDown() {
        val simpleDateFormat = SimpleDateFormat("HH:mm (ss)")

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 4)

        val timeFuture = (4 * 60 * 1000).toLong()
        countDownTimer = object : CountDownTimer(timeFuture, 1000) {
            override fun onTick(p0: Long) {
                val percentage = (p0.toDouble() / timeFuture.toDouble()) * 100
                val progress = ceil(percentage).toInt()

                bind.apply {
                    pbrTimeExpire.progress = progress
                    tvwTimeExpire.text = simpleDateFormat.format(p0)
                    isShowExpired = true
                }
            }

            override fun onFinish() {
                bind.isShowExpired = false
                showMessageOfCodeExpired()
            }

        }
        countDownTimer.start()
    }

    private fun showMessageOfCodeExpired() {
        context?.let { ctx ->
            AlertDialog.Builder(ctx).setMessage(R.string.reg_erro_code_expired_text)
                .setTitle(R.string.reg_erro_code_expired_title).setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes) { _, _ ->
                    callServerToSendCode()
                }.show()
        }
    }

    private fun callServerToSendCode() {
        event.send(EVENT_KEY_LOADING, true)
        viewModel.requestCode(userRegisterModel.email)
    }

    private fun applyActionInButton() {
        with(bind) {
            tvwResentCode.setOnClickListener {
                callServerToSendCode()
            }
            btnConfirmedRegister.setOnClickListener {
                sendDataToServer()
            }
        }
    }

    private fun applyActionEditText(index: Int = 0) {
        codeField[index].addTextChangedListener { valueOfField ->
            if (valueOfField.toString().isNotEmpty()) {
                addValueInArray(index, valueOfField)
                checkIfLastField(index)
            } else {
                setFocusInFieldPrevious(index)
            }
        }
    }

    private fun setFocusInFieldPrevious(index: Int) {
        val newIndex = index - 1
        if (newIndex >= 0) {
            codeField[newIndex].requestFocus()
        }
    }

    private fun addValueInArray(index: Int, valueOfField: Editable?) {
        codeText[index] = valueOfField.toString()
    }

    private fun checkIfLastField(index: Int) {
        val nextIndex = (index + 1)
        if (nextIndex < codeField.size) {
            codeField[nextIndex].requestFocus()
            applyActionEditText(nextIndex)
        } else {
            closeKeyboard()
            sendDataToServer()
        }
    }

    private fun closeKeyboard() {
        (activity as MainActivity).closeKeyboard()
    }

    private fun sendDataToServer() {
        userRegisterModel.code = ""
        codeText.forEach { code ->
            userRegisterModel.code += code
        }

        val regex = Regex("[0-4]{2}[5-9]([6-8])")
        if (regex.containsMatchIn(userRegisterModel.code)) {
            callServerToRegisterUser()
        } else {
            Snackbar.make(bind.root, R.string.reg_code_invalid, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun callServerToRegisterUser() {
        event.send(EVENT_KEY_LOADING, true)
        viewModel.registerUserInServer(userRegisterModel)
    }

}