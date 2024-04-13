package com.zaar.meatkgb2_w.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.zaar.meatkgb2_w.R
import com.zaar.meatkgb2_w.data.LogPass
import com.zaar.meatkgb2_w.databinding.FragmentWorkspaceBinding
import com.zaar.meatkgb2_w.utilities.types.tegsExchangingBetweenFragment
import com.zaar.meatkgb2_w.utilities.view.ViewUtilities
import com.zaar.meatkgb2_w.viewModel.vm.WorkspaceVM
import com.zaar.meatkgb2_w.viewModel.factory.WorkspaceFactory
import java.util.Calendar

class WorkspaceFragment: Fragment() {

    private var _binding: FragmentWorkspaceBinding? = null
    private val binding get() = _binding!!
    private var model: WorkspaceVM? = null
    private var source = ""
    private var action = ""
    private val logPass = LogPass()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkspaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initVariables()
        if (model != null) {
            initObserveVM()
            initObserveView()
            initView()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initVariables() {
        model = context?.let {
            ViewModelProvider(
                this,
                WorkspaceFactory(it.applicationContext)
            )[WorkspaceVM::class.java]
        }
        if (arguments != null) {
            val bundle = arguments
            source = bundle?.getString(tegsExchangingBetweenFragment.SOURCE.value, "") ?: ""
            action = bundle?.getString(tegsExchangingBetweenFragment.ACTION.value, "") ?: ""
            logPass.usrLogin =
                bundle?.getString(tegsExchangingBetweenFragment.LOGIN.value, "") ?: ""
            logPass.enterpriseId =
                bundle?.getString(tegsExchangingBetweenFragment.ENTERPRISE_ID.value, "") ?: ""
        }
    }

    private fun initObserveVM() {
        model?.ldUserDescription()?.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.textViewWorker.text =
                    "${it.shopShortName}/${it.appointment} - ${it.userShortName}"
            }
        }
        model?.ldProductsList()?.observe(viewLifecycleOwner) { items ->
            if (items != null) {
                binding.spinnerProduct.adapter = context?.let {
                    fillingAdapterSpinner(
                        it,
                        R.layout.spiner_item_custom,
                        items.toTypedArray()
                    )
                }
            }
            setMeByProductName()
        }
        model?.ldMeProduct()?.observe(viewLifecycleOwner) {
            binding.textViewMe.text = it
        }
    }

    private fun initObserveView() {
        binding.btnSetting.setOnClickListener {
            toSetting()
        }
        tvDateProducedOnClick(binding.textViewDateProduced)
        spinnerOnItemSelectedProduct()
    }

    private fun initView() {
        model?.initDescriptionUser()
        model?.initItemsSpinnerProduct()
        binding.spinnerHour.adapter = context?.let {
            fillingAdapterSpinner(
                it,
                R.layout.spiner_item_custom,
                resources.getStringArray(R.array.time_of_produced)
            )
        }
        initDateDefaultForTextView(binding.textViewDateProduced, 0)

    }

    private fun fillingAdapterSpinner(
        context: Context,
        resIDItemLayout: Int,
        spinnerItemsList: Array<String>
    ): ArrayAdapter<String> {
        return ArrayAdapter(
            context,
            resIDItemLayout,
            spinnerItemsList
        )
    }

    private fun toSetting() {
        Navigation.findNavController(binding.root).navigate(
            R.id.action_workspaceFragment_to_settingFragment
        )
    }

    /**
     * вставляет в textView дату в нужном для этого приложения формате
     *
     * @param textView куда вставить
     * @param value    кол-во дней, которые нужно добавить к текущей дате
     *                 (может быть положительным/увеличение или отрицательным/уменьшение)
     */
    private fun initDateDefaultForTextView(textView: TextView, value: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, value)
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH) + 1
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val date: String =
            dateFormatIntToString(day)
                .plus("." + dateFormatIntToString(month))
                .plus(".$year")
        textView.text = date
    }

    private fun dateFormatIntToString(num: Int): String {
        return if (num in 0..9) {
            "0$num";
        } else if (num < 0 && num > -10) {
            "-0" + (num * (-1));
        } else
            num.toString();
    }

    private fun tvDateProducedOnClick(
        textView: TextView
    ) {
        textView.setOnClickListener {
            context?.apply {
                ViewUtilities().callDatePicker(it as TextView, this)
            }
        }
    }

    private fun setMeByProductName(){
        val productName = binding.spinnerProduct.selectedItem.toString()
        model?.getProductMe(productName)
    }

    private fun setMeByProductName(productName: String){
        model?.getProductMe(productName)
    }

    private fun spinnerOnItemSelectedProduct(){
        binding.spinnerProduct.onItemSelectedListener=
            object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val nameProduct = (view as TextView).text.toString()
                    setMeByProductName(nameProduct)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.textViewMe.text="non"
                }
            }
    }
}