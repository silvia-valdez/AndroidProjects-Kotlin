package com.ivettevaldez.cleanarchitecture.screens.questionslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ivettevaldez.cleanarchitecture.R
import com.ivettevaldez.cleanarchitecture.questions.Question

class QuestionsListAdapter(
    context: Context,
    private val listener: Listener
) : ArrayAdapter<Question>(context, 0) {

    interface Listener {

        fun onQuestionClicked(question: Question?)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val question = getItem(position)

        if (convertView == null) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.questions_list_item, parent, false)

            val viewHolder = ViewHolder(
                view.findViewById(R.id.questions_list_item_text_title)
            )
            view.tag = viewHolder
        } else {
            view = convertView
        }

        val viewHolder = view.tag as ViewHolder
        viewHolder.textTitle.text = question?.title ?: ""
        
        view.setOnClickListener { onQuestionClicked(question) }

        return view
    }

    private fun onQuestionClicked(question: Question?) {
        listener.onQuestionClicked(question)
    }

    private class ViewHolder(val textTitle: TextView)
}