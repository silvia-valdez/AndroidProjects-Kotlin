package com.ivettevaldez.dependencyinjection.screens.questionslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ivettevaldez.dependencyinjection.R
import com.ivettevaldez.dependencyinjection.questions.Question

interface IQuestionsListViewMvc {

    interface Listener {

        fun onQuestionClicked(questionId: String)
        fun onRefreshClicked()
    }

    fun bindQuestions(questions: List<Question>)
    fun showProgressIndicator()
    fun hideProgressIndicator()
    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
}

class QuestionsListViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : IQuestionsListViewMvc {

    val rootView: View = inflater.inflate(
        R.layout.activity_questions_list, parent, false
    )

    private val context: Context get() = rootView.context
    private val swipeRefresh: SwipeRefreshLayout
    private val recycler: RecyclerView

    private val listeners: MutableSet<IQuestionsListViewMvc.Listener> = HashSet()

    private lateinit var questionsAdapter: QuestionsAdapter
    private lateinit var questions: List<Question>

    init {

        swipeRefresh = findViewById(R.id.questions_list_swipe_refresh)
        recycler = findViewById(R.id.questions_list_recycler)

        initRecycler()
        setListenerEvents()
    }

    override fun bindQuestions(questions: List<Question>) {
        this.questions = questions
        questionsAdapter.bindData(questions)
    }

    override fun showProgressIndicator() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressIndicator() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    override fun registerListener(listener: IQuestionsListViewMvc.Listener) {
        listeners.add(listener)
    }

    override fun unregisterListener(listener: IQuestionsListViewMvc.Listener) {
        listeners.remove(listener)
    }

    private fun initRecycler() {
        questionsAdapter = QuestionsAdapter { clickedQuestion ->
            for (listener in listeners) {
                listener.onQuestionClicked(clickedQuestion.id)
            }
        }
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = questionsAdapter
        recycler.addItemDecoration(
            getDividerItemDecoration(context)
        )
    }

    private fun setListenerEvents() {
        swipeRefresh.setOnRefreshListener {
            for (listener in listeners) {
                listener.onRefreshClicked()
            }
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

    private fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }
}

class QuestionsAdapter(
    private val onQuestionClickedListener: (Question) -> Unit
) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    private var questionsList: List<Question> = ArrayList(0)

    inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle: TextView = view.findViewById(R.id.item_question_text_title)
    }

    fun bindData(questions: List<Question>) {
        questionsList = ArrayList(questions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.textTitle.text = questionsList[position].title
        holder.itemView.setOnClickListener {
            onQuestionClickedListener.invoke(questionsList[position])
        }
    }

    override fun getItemCount(): Int {
        return questionsList.size
    }
}