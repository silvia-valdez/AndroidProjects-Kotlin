package com.ivettevaldez.unittesting.tutorialandroidapp.questions

import com.ivettevaldez.unittesting.tutorialandroidapp.common.BaseObservable
import com.ivettevaldez.unittesting.tutorialandroidapp.networking.questions.details.FetchQuestionDetailsEndpoint
import com.ivettevaldez.unittesting.tutorialandroidapp.networking.questions.details.QuestionDetailsSchema

class FetchQuestionDetailsUseCase(
    private val fetchQuestionDetailsEndpoint: FetchQuestionDetailsEndpoint
) : BaseObservable<FetchQuestionDetailsUseCase.Listener>(),
    FetchQuestionDetailsEndpoint.Listener {

    interface Listener {

        fun onQuestionDetailsFetched(details: QuestionDetails)
        fun onFetchQuestionDetailsFailed()
    }

    fun fetchAndNotify(questionId: String) {
        fetchQuestionDetailsEndpoint.fetchQuestionDetails(questionId, this)
    }

    override fun onQuestionDetailsFetched(questionDetailsSchema: QuestionDetailsSchema?) {
        if (questionDetailsSchema != null) {
            notifySuccess(questionDetailsSchema)
        } else {
            notifyFailure()
        }
    }

    override fun onQuestionDetailsFetchFailed() {
        notifyFailure()
    }

    private fun notifySuccess(schema: QuestionDetailsSchema) {
        for (listener in listeners) {
            listener.onQuestionDetailsFetched(
                QuestionDetails(
                    id = schema.id,
                    title = schema.title,
                    body = schema.body
                )
            )
        }
    }

    private fun notifyFailure() {
        for (listener in listeners) {
            listener.onFetchQuestionDetailsFailed()
        }
    }
}