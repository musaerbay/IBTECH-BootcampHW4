package com.mr.bootcampweek4.base


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.mr.bootcampweek4.R
import com.mr.bootcampweek4.adapter.TodoAdapter
import com.mr.bootcampweek4.model.Todo
import com.mr.bootcampweek4.response.TodoResponse
import com.mr.bootcampweek4.response.UpdateResponse
import com.mr.bootcampweek4.service.BaseCallBack
import com.mr.bootcampweek4.service.ServiceConnector
import com.mr.bootcampweek4.utils.gone
import com.mr.bootcampweek4.utils.toast
import com.mr.bootcampweek4.utils.visible
import kotlinx.android.synthetic.main.fragment_todo.*


class TodoFragment : BaseFragment() {

    override fun getLayoutID(): Int = R.layout.fragment_todo


    private lateinit var recyclerView: RecyclerView
    private val LIMIT = 10
    private var SKIP = 10

    private var page_count = 0
    private var page_no = 1
    private var limit = 0
    private val data_list = mutableListOf<Todo>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TodoAdapter(
            data_list,
            object : BaseRecyclerViewItemClickListener<Todo> {
                override fun onItemClicked(clickedObject: Todo, id: Int) {
                    when (id) {
                        R.id.complete_btn -> {
                            val flag: Boolean
                            when {
                                clickedObject.completed -> {
                                    clickedObject.completed = false
                                    flag = false
                                }
                                else -> {
                                    clickedObject.completed = true
                                    flag = true
                                }
                            }

                            val param = mutableMapOf<String, Boolean>().apply {
                                put("completed", flag)
                            }
                            ServiceConnector.restInterface.UpdateTaskbyId(
                                clickedObject._id!!,
                                param
                            )
                                .enqueue(object : BaseCallBack<UpdateResponse>() {
                                    override fun onSuccess(updateResponse: UpdateResponse) {
                                        super.onSuccess(updateResponse)
                                        toast(getString(R.string.completed_successfully), 200)
                                    }

                                    override fun onFailure() {
                                        super.onFailure()
                                        toast(getString(R.string.failed))
                                    }
                                })
                        }
                        R.id.delete_btn -> {
                            val clickedIndex = data_list.indexOf(clickedObject)
                            data_list.removeAt(clickedIndex)
                            recyclerView.adapter?.notifyItemRemoved(clickedIndex)
                            ServiceConnector.restInterface.DeleteTaskbyId(clickedObject._id!!)
                                .enqueue(object : BaseCallBack<UpdateResponse>() {
                                    override fun onSuccess(updateResponse: UpdateResponse) {
                                        super.onSuccess(updateResponse)
                                        toast(getString(R.string.completed_successfully), 200)
                                    }

                                    override fun onFailure() {
                                        super.onFailure()
                                        toast(getString(R.string.failed))
                                    }
                                })
                        }
                    }
                }
            })

        setpage_count()
        getMyTodos(0, adapter)

        when (page_count) {
            0 -> toast(getString(R.string.todos_loading), Toast.LENGTH_SHORT)
        }


        recyclerView =
            activity?.todo_row ?: RecyclerView(requireContext())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && page_no < page_count
                    && !recyclerView.canScrollVertically(1)
                ) {
                    getMyTodos(
                        SKIP, adapter
                    )
                    SKIP += LIMIT
                    page_no++
                } else if (page_no == page_count) toast(
                    getString(R.string.no_more_todos),
                    500
                )
            }
        })
        activity?.float_btn?.setOnClickListener {
            MaterialDialog(requireContext()).show {
                val dialog = input { dialog, text ->
                    val param = mutableMapOf<String, String>().apply {
                        put("description", dialog.getInputField().text.toString())
                    }
                    ServiceConnector.restInterface.addTask(param)
                        .enqueue(object : BaseCallBack<UpdateResponse>() {
                            override fun onSuccess(updateResponse: UpdateResponse) {
                                super.onSuccess(updateResponse)
                                data_list.add(updateResponse.data)
                                recyclerView.adapter?.notifyItemInserted(data_list.size - 1)
                                scrollToLast()
                                toast(getString(R.string.completed_successfully))
                            }

                            override fun onFailure() {
                                super.onFailure()
                                toast(getString(R.string.failed))
                            }
                        })
                }
                positiveButton(R.string.add_task)
            }
        }
    }

    private fun getMyTodos(skip: Int, adapter: TodoAdapter) {
        ServiceConnector.restInterface.getTaskByPagination(LIMIT, skip)
            .enqueue(object : BaseCallBack<TodoResponse>() {
                override fun onSuccess(TodoResponse: TodoResponse) {
                    super.onSuccess(TodoResponse)
                    limit = TodoResponse.count
                    if (TodoResponse.count == 0) {
                        activity?.todo_row?.gone()
                        activity?.no_task?.visible()
                    } else {
                        data_list.addAll(TodoResponse.data)
                        scrollToLast()
                        recyclerView.adapter = adapter
                    }
                }

                override fun onFailure() {
                    super.onFailure()
                    toast(getString(R.string.login_error))
                }
            })
    }

    private fun setpage_count() {
        ServiceConnector.restInterface.getAllTasks().enqueue(object : BaseCallBack<TodoResponse>() {
            override fun onSuccess(todoResponse: TodoResponse) {
                super.onSuccess(todoResponse)
                val quotient = todoResponse.count / SKIP
                page_count = if (todoResponse.count == quotient * SKIP) quotient
                else quotient + 1
            }

            override fun onFailure() {
                super.onFailure()
                toast(getString(R.string.login_error))
            }
        })
    }

    fun scrollToLast() {
        recyclerView.scrollToPosition(data_list.size - 1)
    }
}