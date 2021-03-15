package com.ivettevaldez.cleanarchitecture.screens.questionslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ivettevaldez.cleanarchitecture.R
import com.ivettevaldez.cleanarchitecture.questions.Question
import com.ivettevaldez.cleanarchitecture.screens.common.ViewMvcFactory
import com.ivettevaldez.cleanarchitecture.screens.common.views.BaseObservableViewMvc
import com.ivettevaldez.cleanarchitecture.screens.common.views.IObservableViewMvc
import kotlinx.android.synthetic.main.activity_questions_list.view.*

interface IQuestionsListViewMvc : IObservableViewMvc<IQuestionsListViewMvc.Listener> {

    interface Listener {

        fun onQuestionClicked(question: Question?)
    }

    fun bindQuestions(questions: List<Question>)
}

class QuestionsListViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    private val viewMvcFactory: ViewMvcFactory
) : BaseObservableViewMvc<IQuestionsListViewMvc.Listener>(),
    IQuestionsListViewMvc,
    QuestionsRecyclerAdapter.Listener {

    private var recyclerQuestions: RecyclerView

    private lateinit var questionsRecyclerAdapter: QuestionsRecyclerAdapter

    init {

        setRootView(
            inflater.inflate(R.layout.activity_questions_list, parent, false)
        )

        recyclerQuestions = getRootView().questions_recycler_items

        setList()
    }

    override fun bindQuestions(questions: List<Question>) {
        questionsRecyclerAdapter.updateData(questions)
    }

    override fun onQuestionClicked(question: Question?) {
        for (listener in getListeners()) {
            listener.onQuestionClicked(question)
        }
    }

    private fun setList() {
        questionsRecyclerAdapter = QuestionsRecyclerAdapter(
            this,
            viewMvcFactory
        )

        val linearLayoutManager = LinearLayoutManager(getContext())
        linearLayoutManager.isSmoothScrollbarEnabled = true

        with(recyclerQuestions) {
            layoutManager = linearLayoutManager
            adapter = questionsRecyclerAdapter
            addItemDecoration(
                getDividerItemDecoration(context)
            )
        }
    }

    private fun getDividerItemDecoration(context: Context): DividerItemDecoration {
        val divider = ContextCompat.getDrawable(context, R.drawable.shape_divider)
        val dividerItemDecoration = DividerItemDecoration(
            context,
            LinearLayoutManager.VERTICAL
        )

        if (divider != null) {
            dividerItemDecoration.setDrawable(divider)
        }

        return dividerItemDecoration
    }
}