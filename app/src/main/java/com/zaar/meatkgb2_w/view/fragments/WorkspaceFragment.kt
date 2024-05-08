package com.zaar.meatkgb2_w.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaar.meatkgb2_w.R
import com.zaar.meatkgb2_w.data.LogPass
import com.zaar.meatkgb2_w.data.entity.RecordUi
import com.zaar.meatkgb2_w.databinding.FragmentWorkspaceBinding
import com.zaar.meatkgb2_w.utilities.types.TegExchangingBetweenFragment
import com.zaar.meatkgb2_w.utilities.view.UtilitiesView
import com.zaar.meatkgb2_w.view.adapter.RecordsRecViewAdapter
import com.zaar.meatkgb2_w.viewModel.vm.WorkspaceVM
import com.zaar.meatkgb2_w.viewModel.factory.WorkspaceFactory
import kotlin.NumberFormatException

class WorkspaceFragment: Fragment() {

    private var _binding: FragmentWorkspaceBinding? = null
    private lateinit var itemOnClickListener: RecordsRecViewAdapter.ItemOnClickListener
    private val binding get() = _binding!!
    private var model: WorkspaceVM? = null
    private var source = ""
    private var action = ""
    private val logPass = LogPass()
    private var shopId = -1L
    private var accuracyDB = -1

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

    private fun initVariables() {
        model = context?.let {
            ViewModelProvider(
                this,
                WorkspaceFactory(it.applicationContext)
            )[WorkspaceVM::class.java]
        }
        arguments?.also {
            source = it.getString(TegExchangingBetweenFragment.SOURCE.value, "")
            action = it.getString(TegExchangingBetweenFragment.ACTION.value, "")
            logPass.usrLogin =
                it.getString(TegExchangingBetweenFragment.LOGIN.value, "")
            logPass.enterpriseId =
                it.getString(TegExchangingBetweenFragment.ENTERPRISE_ID.value, "")
        }
    }

    @SuppressLint("SetTextI18n")
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
            binding.textViewMe.text =
                if (accuracyDB >= 0) "$it ($accuracyDB)"
                else it
//            binding.labelForListViewMainActivity.tvCountRecView.text = "$it\n${getString(R.string.label_count)}"
        }
        model?.ldShopId()?.observe(viewLifecycleOwner) {
            shopId = it
            btnInsertOnCheck()
        }
        model?.ldAccuracy()?.observe(viewLifecycleOwner) {
            accuracyDB = it
            binding.editTextCount.setText("")
            binding.textViewMe.text =
                binding.textViewMe.text.toString() + " ($it)"
            btnInsertOnCheck()
        }
        model?.ldRecords()?.observe(viewLifecycleOwner) {
            binding.editTextCount.setText("")
            btnOff(binding.panelOfButtons.btnDelete)
            btnOn(binding.panelOfButtons.btnSelect)
            binding.checkboxIdDB.isChecked = false
            binding.textViewIdDB.text = ""
            initRecView(it)
            visibilityLayoutRecords(true)
        }
    }

    private fun initObserveView() {
        binding.btnSetting.setOnClickListener {
            toSetting()
        }
        tvDateProducedOnClick(binding.textViewDateProduced)
        spinnerOnItemSelectedProduct()
        spinnerOnItemSelectedHour()
        btnInsertOnClick()
        btnDeleteOnClick()
        btnUpdateOnClick()
        editTextAddTextChangedListener()
        itemOnClickListener = object : RecordsRecViewAdapter.ItemOnClickListener {
            override fun onItemClick(idRecord: Long) {
                if (idRecord >= 0) {
                    binding.textViewIdDB.text = idRecord.toString()
                    binding.checkboxIdDB.isChecked = true
                    btnOn(binding.panelOfButtons.btnDelete)
                }
            }
        }
    }

    private fun initView() {
        btnOff(binding.panelOfButtons.btnInsert)
        btnOff(binding.panelOfButtons.btnDelete)
        model?.initDescriptionUser()
        model?.initItemsSpinnerProduct()
        binding.spinnerHour.adapter = context?.let {
            fillingAdapterSpinner(
                it,
                R.layout.spiner_item_custom,
                resources.getStringArray(R.array.time_of_produced)
            )
        }
        UtilitiesView().initDateDefaultForTextView(binding.textViewDateProduced, 0)
        val recView = binding.recViewMain
        recView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        recView.addItemDecoration(
            DividerItemDecoration(
                recView.context,
                LinearLayoutManager.VERTICAL
            )
        )
        visibilityLayoutRecords(false)
        binding.panelOfButtons.btnSelect.performClick()
        binding.textViewDateProduced.paintFlags = Paint.UNDERLINE_TEXT_FLAG
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

    private fun initRecView(items: List<RecordUi>? = null) {
        if (!items.isNullOrEmpty()) {
            binding.recViewMain.adapter = RecordsRecViewAdapter(items, itemOnClickListener)
        }
    }

    private fun tvDateProducedOnClick(
        textView: TextView
    ) {
        textView.setOnClickListener {
            context?.apply {
                UtilitiesView().callDatePicker(it as TextView, this)
            }
        }
    }

    private fun setMeByProductName() {
        val productName = binding.spinnerProduct.selectedItem.toString()
        model?.getProductMe(productName)
    }

    private fun setMeByProductName(productName: String) {
        model?.getProductMe(productName)
    }

    private fun spinnerOnItemSelectedProduct() {
        binding.spinnerProduct.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val nameProduct = view?.let {
                        (it as TextView).text.toString()
                    } ?: "non"
                    setMeByProductName(nameProduct)
                    shopId = -1L
                    accuracyDB = -1
                    model?.getShopId(nameProduct)
                    model?.getAccuracy(nameProduct)
                }

                @SuppressLint("SetTextI18n")
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.textViewMe.text = "non"
                }
            }
    }

    private fun spinnerOnItemSelectedHour() {
        binding.spinnerHour.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    btnInsertOnCheck()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun btnInsertOnClick() {
        binding.panelOfButtons.btnInsert.setOnClickListener {
            btnOff()
            visibilityLayoutRecords(false)
            val recordUi = RecordUi(
                userLogin = logPass.usrLogin ?: "",
                dateProduced = binding.textViewDateProduced.text.toString(),
                productName = binding.spinnerProduct.selectedItem.toString(),
                timeProduced = binding.spinnerHour.selectedItem.toString().toByte(),
                count = binding.editTextCount.text.toString().toFloat(),
                enterpriseId = logPass.enterpriseId ?: "",
                shopId = shopId,
            )
            model?.addRecord(recordUi)
        }
    }

    private fun btnDeleteOnClick() {
        binding.panelOfButtons.btnDelete.setOnClickListener {
            btnOff()
            visibilityLayoutRecords(false)
            val idStr = binding.textViewIdDB.text.toString()
            if (idStr.isNotEmpty()) {
                val idRecord =
                    try {
                        idStr.toLong()
                    } catch (e: Exception) {
                        return@setOnClickListener
                    }
                if (idRecord >= 0) model?.delete(idRecord)
            }
        }
    }

    private fun btnUpdateOnClick() {
        binding.panelOfButtons.btnSelect.setOnClickListener {
            btnOff()
            visibilityLayoutRecords(false)
            model?.update()
        }
    }

    private fun btnInsertOnCheck() {
        try {
            if (
                !binding.textViewMe.text.isNullOrEmpty()
                && !binding.textViewDateProduced.text.isNullOrEmpty()
                && binding.spinnerProduct.selectedItem.toString().isNotEmpty()
                && binding.spinnerHour.selectedItem.toString().isNotEmpty()
                && binding.editTextCount.text.toString().toDouble() > 0
                && shopId != -1L
                && accuracyDB != -1
            )
                btnOn(binding.panelOfButtons.btnInsert)
        } catch (e: NumberFormatException) {
            btnOff(binding.panelOfButtons.btnInsert)
        } catch (e: Exception) {
            btnOff(binding.panelOfButtons.btnInsert)
        }
    }

    private fun editTextAddTextChangedListener() {
        binding.editTextCount.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    var string = editable?.toString() ?: ""
                    string = string.trim()
                    try {
                        if (string.isNotEmpty() && string.toDouble() > 0) {
                            var strings = string.split(",")
                            //---------------------------------------------
//                            val a1=UtilitiesTextFormat().findingNumberOfDecimalPlaces(string)
//                            val a2=UtilitiesTextFormat().findingNumberOfDecimalPlaces(string.toDouble())
                            //---------------------------------------------
                            if (strings.size != 2) {
                                strings = string.split(".")
                                if (strings.size != 2) {
                                    btnInsertOnCheck()
                                    return
                                }
                            }
                            if (strings[1].length > accuracyDB) {
                                string = strings[0] + "." + strings[1].substring(0, accuracyDB)
                                binding.editTextCount.setText(string)
                                binding.editTextCount.setSelection(string.length)
                            }
                            btnInsertOnCheck()
                        } else btnOff(binding.panelOfButtons.btnInsert)
                    } catch (e: NumberFormatException) {
                        var strings = string.split(",")
                        if (strings.size != 2) {
                            strings = string.split(".")
                            if (strings.size != 2) {
                                binding.editTextCount.setText("")
                                return
                            }
                        }
                        string = if (strings[0].isNotEmpty()) {
                            strings[0] + "." + strings[1].substring(0, accuracyDB)
                        } else ""
                        binding.editTextCount.setText(string)
                        binding.editTextCount.setSelection(string.length)
                    } catch (_: Exception) {
                        binding.editTextCount.setText("")
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
        )
    }

    private fun btnOff(button: Button) {
        if (button.isEnabled) {
            context?.let { context ->
                UtilitiesView().activeBtnOff(
                    button,
                    context.getColorStateList(R.color.tint_button_blocked),
                    context.getColorStateList(R.color.black)
                )
            }
        }
    }

    /**
     * off the all buttons
     */
    private fun btnOff() {
        btnOff(binding.panelOfButtons.btnSelect)
        btnOff(binding.panelOfButtons.btnInsert)
        btnOff(binding.panelOfButtons.btnDelete)
    }

    private fun btnOn(button: Button) {
        if (!button.isEnabled) {
            context?.let { context ->
                UtilitiesView().activeBtnOn(
                    button,
                    context.getColorStateList(R.color.tint_bg_selector),
                    context.getColorStateList(R.color.milk_background)
                )
            }
        }
    }

    private fun visibilityLayoutRecords(isVisible: Boolean) {
        val records: Int; val progress: Int
        if (isVisible) {
            records = View.VISIBLE
            progress = View.GONE
        } else {
            records = View.GONE
            progress = View.VISIBLE
        }
        binding.recViewMain.visibility = records
        binding.progressBar.root.visibility = progress
    }
}