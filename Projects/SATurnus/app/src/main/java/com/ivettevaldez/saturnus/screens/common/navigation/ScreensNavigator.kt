package com.ivettevaldez.saturnus.screens.common.navigation

import com.ivettevaldez.saturnus.screens.common.fragmentframehelper.FragmentFrameHelper
import com.ivettevaldez.saturnus.screens.invoicing.InvoiceIssuingPeopleFragment
import com.ivettevaldez.saturnus.screens.people.form.PersonFormFragment
import com.ivettevaldez.saturnus.screens.people.main.PeopleMainFragment
import com.ivettevaldez.saturnus.screens.splash.SplashFragment
import javax.inject.Inject

class ScreensNavigator @Inject constructor(private val fragmentFrameHelper: FragmentFrameHelper) {

    fun navigateUp() {
        fragmentFrameHelper.navigateUp()
    }

    fun toSplash() {
        fragmentFrameHelper.replaceFragmentAndClearBackstack(
            SplashFragment.newInstance()
        )
    }

    fun toInvoiceIssuingPeople() {
        fragmentFrameHelper.replaceFragmentAndClearBackstack(
            InvoiceIssuingPeopleFragment.newInstance()
        )
    }

    fun toPeople() {
        fragmentFrameHelper.replaceFragment(
            PeopleMainFragment.newInstance()
        )
    }

    fun toPersonForm(rfc: String?) {
        fragmentFrameHelper.replaceFragment(
            PersonFormFragment.newInstance(rfc)
        )
    }
}