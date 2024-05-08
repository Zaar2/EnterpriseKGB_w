package com.zaar.meatkgb2_w.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.zaar.meatkgb2_w.R
import com.zaar.meatkgb2_w.databinding.FragmentSettingBinding
import com.zaar.meatkgb2_w.utilities.types.TegExchangingBetweenFragment
import com.zaar.meatkgb2_w.viewModel.vm.SettingVM
import com.zaar.meatkgb2_w.viewModel.factory.SettingFactory

class SettingFragment: Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private var model: SettingVM? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        initVariables()
        if (model != null) {
            initObserveView()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initVariables() {
        model = context?.let {
            ViewModelProvider(
                this,
                SettingFactory(it.applicationContext)
            )[SettingVM::class.java]
        }
    }

    private fun initObserveView() {
        binding.btnResetIdentification.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                TegExchangingBetweenFragment.SOURCE.value, SettingFragment::class.simpleName
            )
            bundle.putString(
                TegExchangingBetweenFragment.ACTION.value, getString(R.string.action_reset_access)
            )
            toNextView(
                R.id.action_settingFragment_to_identificationFragment, bundle
            )
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                toNextView(
                    R.id.action_settingFragment_to_workspaceFragment,
                    null
                )
            }
        })
    }

    private fun toNextView(idAction: Int, bundle: Bundle?) {
        Navigation.findNavController(binding.root).navigate(idAction, bundle)
    }
}