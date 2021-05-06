package com.ivettevaldez.dependencyinjection.screens.common.controllers

import androidx.appcompat.app.AppCompatActivity
import com.ivettevaldez.dependencyinjection.common.CustomApplication
import com.ivettevaldez.dependencyinjection.common.dependencyinjection.activity.ActivityComponent
import com.ivettevaldez.dependencyinjection.common.dependencyinjection.activity.ActivityModule
import com.ivettevaldez.dependencyinjection.common.dependencyinjection.application.ApplicationComponent
import com.ivettevaldez.dependencyinjection.common.dependencyinjection.presentation.PresentationComponent

open class BaseActivity : AppCompatActivity() {

    private val applicationComponent: ApplicationComponent by lazy {
        (application as CustomApplication).applicationComponent
    }

    val activityComponent: ActivityComponent by lazy {
        applicationComponent.newActivityComponentBuilder()
            .activityModule(ActivityModule)
            .activity(this)
            .build()
    }

    private val presentationComponent: PresentationComponent by lazy {
        activityComponent.newPresentationComponent()
    }

    protected val injector get() = presentationComponent
}