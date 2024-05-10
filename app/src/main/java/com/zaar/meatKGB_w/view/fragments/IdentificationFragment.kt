package com.zaar.meatKGB_w.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.zaar.meatKGB_w.R
import com.zaar.meatKGB_w.data.LogPass
import com.zaar.meatKGB_w.databinding.FragmentIdentificationUserBinding
import com.zaar.meatKGB_w.utilities.types.TegExchangingBetweenFragment
import com.zaar.meatKGB_w.utilities.view.UtilitiesView
import com.zaar.meatKGB_w.viewModel.vm.IdentificationVM
import com.zaar.meatKGB_w.viewModel.factory.IdentificationFactory

class IdentificationFragment: Fragment() {

    private var _binding: FragmentIdentificationUserBinding? = null
    private val binding get() = _binding!!
    private var model: IdentificationVM? = null
    private var logPass: LogPass? = null
//    private var shopId = -1L
    private var source = ""
    private var action = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIdentificationUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initVariables()
        if (model != null) {
            initObserve()
            initView()
            if (action != getString(R.string.action_reset_access))
                initExistsAccount()
            else onResetAccess()
        } else {
            binding.tvDescriptionMain.append(getString(R.string.err_init_viewmodel) + "\n")
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initVariables() {
        model = context?.let {
            ViewModelProvider(
                this,
                IdentificationFactory(it.applicationContext)
            )[IdentificationVM::class.java]
        }
        if (arguments != null) {
            val bundle = arguments
            source = bundle?.getString(TegExchangingBetweenFragment.SOURCE.value, "") ?: ""
            action = bundle?.getString(TegExchangingBetweenFragment.ACTION.value, "") ?: ""
        }
    }

    private fun initObserve() {
        initObserveVM()
        initObserveView()
    }

    private fun initObserveVM() {
        model?.ldStageDescriptionForAppEntry()
            ?.observe(viewLifecycleOwner) { str ->
                binding.tvDescriptionMain.append(str + "\n")
            }
        model?.ldIsInitExistsEnterprise
            ?.observe(viewLifecycleOwner) {
                if (!it) visibilityFields(View.VISIBLE)
                btnOn()
            }
        model?.ldUserData()?.observe(viewLifecycleOwner) {
            it?.also {
                logPass = it
                binding.editTextIdEnterprise.setText(it.enterpriseId)
                binding.editTextUserLogin.setText(it.usrLogin)
                binding.etUsrPassWorker.setText(it.usrPass)
            }
        }
//        model?.ldShopId()?.observe(viewLifecycleOwner) { id ->
//            id?.also { if (it >= 0) shopId = it }
//        }
        model?.ldSessionID()?.observe(viewLifecycleOwner) { sessionId ->
            if (!sessionId.isNullOrEmpty()) {
                //save reg data to storage
                logPass?.also {
                    if (!it.isEmpty()) {
                        model?.saveUserData(
                            logPass = it,
                            sessionId = sessionId
                        )
                    }
                }
            } else {
                binding.tvDescriptionMain.append("received sessionId is null or empty\n")
                btnOn()
            }
        }
        model?.ldIsSavedRegData()?.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                binding.tvDescriptionMain.append("registration data is saved\n")
                model?.updatingData()
            } else {
                binding.tvDescriptionMain.append("registration data not saved\n")
                binding.layoutIdentification.visibility = View.VISIBLE
                btnOn()
            }
        }
        model?.ldIsUpdatingData()?.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.tvDescriptionMain.append("prepare a send data\n")
                    val bundle = Bundle()
                    bundle.putString(
                        TegExchangingBetweenFragment.SOURCE.value,
                        IdentificationFragment::class.simpleName
                    )
                    bundle.putString(
                        TegExchangingBetweenFragment.ACTION.value,
                        getString(R.string.action_usr_access_ok)
                    )
                    bundle.putString(
                        TegExchangingBetweenFragment.LOGIN.value,
                        logPass?.usrLogin ?: ""
                    )
                    bundle.putString(
                        TegExchangingBetweenFragment.ENTERPRISE_ID.value,
                        logPass?.enterpriseId ?: ""
                    )
//                    bundle.putLong(
//                        TegExchangingBetweenFragment.SHOP_ID.value,
//                        shopId
//                    )
                    binding.tvDescriptionMain.append("going to the workspace\n")
                    toMainFragment(bundle)
                }

                false -> {
                    binding.tvDescriptionMain.append("updating data from server - is failed !!\n")
                    btnOn()
                }
            }
        }
        model?.ldIsOnline()?.observe(viewLifecycleOwner) {
            it?.also {
                binding.tvDescriptionMain.append("Internet is connected ($it)\n")
            } ?: binding.tvDescriptionMain.append("Internet is disconnected\n")
        }
    }

    private fun initObserveView() {
        binding.btnOkIdentificationUser.setOnClickListener {
            btnOff()
            logPass = LogPass(
                enterpriseId = binding.editTextIdEnterprise.text.toString().trim(),
                usrLogin = binding.editTextUserLogin.text.toString().trim(),
                usrPass = binding.etUsrPassWorker.text.toString().trim()
            )
            logPass?.also {
                if (!it.isEmpty()) model?.initNewAccount(it)
                else binding.tvDescriptionMain.append("one of the fields of registration data is empty!" + "\n")
            }
        }
    }

    private fun initView() {

        visibilityFields(View.GONE)
    }

    private fun visibilityFields(type: Int) {
        binding.layoutIdentification.visibility = type
    }

    private fun onResetAccess() {
        model?.deleteUserData()
        visibilityFields(View.VISIBLE)
    }

    private fun initExistsAccount() {
        model?.initStoredAccount()
    }

    private fun toMainFragment(bundle: Bundle?) {
        Navigation.findNavController(binding.root).navigate(
            R.id.action_identificationFragment_to_workspaceFragment,
            bundle
        )
    }

    private fun btnOff() {
        context?.let { context ->
            UtilitiesView().activeBtnOff(
                binding.btnOkIdentificationUser,
                context.getColorStateList(R.color.tint_button_blocked),
                context.getColorStateList(R.color.black)
            )
        }
    }

    private fun btnOn() {
        context?.let { context ->
            UtilitiesView().activeBtnOn(
                binding.btnOkIdentificationUser,
                context.getColorStateList(R.color.tint_bg_selector),
                context.getColorStateList(R.color.milk_background)
            )
        }
    }
}