package com.ivettevaldez.cleanarchitecture.common.dependencyinjection

import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ivettevaldez.cleanarchitecture.common.permissions.PermissionsHelper
import com.ivettevaldez.cleanarchitecture.networking.StackOverflowApi
import com.ivettevaldez.cleanarchitecture.questions.FetchQuestionDetailsUseCase
import com.ivettevaldez.cleanarchitecture.questions.FetchQuestionsUseCase
import com.ivettevaldez.cleanarchitecture.screens.common.ViewMvcFactory
import com.ivettevaldez.cleanarchitecture.screens.common.dialogs.DialogsEventBus
import com.ivettevaldez.cleanarchitecture.screens.common.dialogs.DialogsManager
import com.ivettevaldez.cleanarchitecture.screens.common.fragmentframehelper.FragmentFrameHelper
import com.ivettevaldez.cleanarchitecture.screens.common.fragmentframehelper.IFragmentFrameWrapper
import com.ivettevaldez.cleanarchitecture.screens.common.navigation.INavDrawerHelper
import com.ivettevaldez.cleanarchitecture.screens.common.navigation.ScreensNavigator

class ControllerCompositionRoot(
    private val activityCompositionRoot: ActivityCompositionRoot
) {

    private fun getActivity(): FragmentActivity {
        return activityCompositionRoot.activity
    }

    private fun getContext(): Context {
        return activityCompositionRoot.activity.applicationContext
    }

    private fun getStackOverflowApi(): StackOverflowApi {
        return activityCompositionRoot.getStackOverflowApi()
    }

    private fun getFragmentManager(): FragmentManager {
        return getActivity().supportFragmentManager
    }

    private fun getFragmentFrameWrapper(): IFragmentFrameWrapper {
        return getActivity() as IFragmentFrameWrapper
    }

    private fun getLayoutInflater(): LayoutInflater {
        return LayoutInflater.from(getActivity())
    }

    private fun getNavDrawerHelper(): INavDrawerHelper {
        return getActivity() as INavDrawerHelper
    }

    private fun getFragmentFrameHelper(): FragmentFrameHelper {
        return FragmentFrameHelper(
            getActivity(),
            getFragmentFrameWrapper(),
            getFragmentManager()
        )
    }

    fun getScreensNavigator(): ScreensNavigator {
        return ScreensNavigator(getFragmentFrameHelper())
    }

    fun getViewMvcFactory(): ViewMvcFactory {
        return ViewMvcFactory(
            getLayoutInflater(),
            getNavDrawerHelper()
        )
    }

    fun getDialogsManager(): DialogsManager {
        return DialogsManager(
            getContext(),
            getFragmentManager()
        )
    }

    fun getDialogsEventBus(): DialogsEventBus {
        return activityCompositionRoot.getDialogsEventBus()
    }

    fun getPermissionsHelper(): PermissionsHelper {
        return activityCompositionRoot.getPermissionsHelper()
    }

    fun getFetchQuestionsUseCase(): FetchQuestionsUseCase {
        return FetchQuestionsUseCase(getStackOverflowApi())
    }

    fun getFetchQuestionDetailsUseCase(): FetchQuestionDetailsUseCase {
        return FetchQuestionDetailsUseCase(getStackOverflowApi())
    }
}