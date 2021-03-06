package com.ivettevaldez.saturnus.screens.invoices.form.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.ivettevaldez.saturnus.R
import com.ivettevaldez.saturnus.screens.common.toolbar.IToolbarViewMvc
import com.ivettevaldez.saturnus.screens.common.viewsmvc.BaseObservableViewMvc
import com.ivettevaldez.saturnus.screens.common.viewsmvc.IObservableViewMvc
import com.ivettevaldez.saturnus.screens.common.viewsmvc.ViewMvcFactory
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError

interface IInvoiceFormMainViewMvc : IObservableViewMvc<IInvoiceFormMainViewMvc.Listener> {

    interface Listener {

        fun onNavigateUpClicked()
        fun onStepSelected()
        fun onReturnToPreviousStep()
        fun onStepError()
        fun onCompletedSteps()
    }

    fun initStepper(folio: String?, issuingRfc: String?)
    fun setToolbarTitle(title: String)
    fun showProgressIndicator()
    fun hideProgressIndicator()
}

class InvoiceFormMainViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    viewMvcFactory: ViewMvcFactory,
    private val fragmentManager: FragmentManager
) : BaseObservableViewMvc<IInvoiceFormMainViewMvc.Listener>(
    inflater,
    parent,
    R.layout.layout_invoice_form_main
), IInvoiceFormMainViewMvc,
    StepperLayout.StepperListener {

    private val toolbarContainer: Toolbar = findViewById(R.id.invoice_form_main_toolbar)
    private val toolbar: IToolbarViewMvc = viewMvcFactory.newToolbarViewMvc(toolbarContainer)

    private val layoutProgress: FrameLayout = findViewById(R.id.invoice_form_main_progress)
    private val stepper: StepperLayout = findViewById(R.id.invoice_form_main_stepper)

    init {

        initToolbar()
    }

    override fun initStepper(folio: String?, issuingRfc: String?) {
        stepper.setListener(this)
        stepper.adapter = InvoiceFormMainStepperAdapter(
            context,
            fragmentManager,
            folio,
            issuingRfc
        )
    }

    override fun setToolbarTitle(title: String) {
        toolbar.setTitle(title)
    }

    override fun showProgressIndicator() {
        layoutProgress.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        layoutProgress.visibility = View.GONE
    }

    override fun onCompleted(completeButton: View?) {
        for (listener in listeners) {
            listener.onCompletedSteps()
        }
    }

    override fun onError(verificationError: VerificationError?) {
        for (listener in listeners) {
            listener.onStepError()
        }
    }

    override fun onStepSelected(newStepPosition: Int) {
        for (listener in listeners) {
            listener.onStepSelected()
        }
    }

    override fun onReturn() {
        for (listener in listeners) {
            listener.onReturnToPreviousStep()
        }
    }

    private fun initToolbar() {
        toolbar.setTitle(context.getString(R.string.invoices_new))

        toolbar.enableNavigateUpAndListen(object : IToolbarViewMvc.NavigateUpClickListener {
            override fun onNavigateUpClicked() {
                for (listener in listeners) {
                    listener.onNavigateUpClicked()
                }
            }
        })

        toolbarContainer.addView(toolbar.getRootView())
    }
}