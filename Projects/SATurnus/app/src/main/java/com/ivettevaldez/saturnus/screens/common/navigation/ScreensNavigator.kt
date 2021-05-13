package com.ivettevaldez.saturnus.screens.common.navigation

import com.ivettevaldez.saturnus.screens.common.fragmentframehelper.FragmentFrameHelper
import com.ivettevaldez.saturnus.screens.invoicing.InvoicingFragment
import com.ivettevaldez.saturnus.screens.people.PeopleFragment
import com.ivettevaldez.saturnus.screens.splash.SplashFragment
import javax.inject.Inject

class ScreensNavigator @Inject constructor(private val fragmentFrameHelper: FragmentFrameHelper) {

    fun toSplash() {
        fragmentFrameHelper.replaceFragmentAndClearBackstack(
            SplashFragment.newInstance()
        )
    }

    fun toInvoicing() {
        fragmentFrameHelper.replaceFragmentAndClearBackstack(
            InvoicingFragment.newInstance()
        )
    }

    fun toPeople() {
        fragmentFrameHelper.replaceFragmentAndClearBackstack(
            PeopleFragment.newInstance()
        )
    }
}