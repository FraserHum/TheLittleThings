package com.example.thelittlethings.tasklist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.thelittlethings.R
import com.example.thelittlethings.database.TaskDatabase
import com.example.thelittlethings.databinding.FragmentTaskListBinding
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TaskListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TaskListFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val binding: FragmentTaskListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task_list, container, false)


        val application = requireNotNull(this.activity).application


        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val dayDataSource = TaskDatabase.getInstance(application).dayDatabaseDao
        Timber.i("set up datasources")



        val viewModelFactory = TaskListViewModelFactory(dataSource, dayDataSource, application)
        Timber.i("set up viewModelFactory")


        val taskListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(TaskListViewModel::class.java)
        Timber.i("set up viewModel")

        binding.taskListViewModel = taskListViewModel

        binding.setLifecycleOwner(this)





        val adapter = TaskAdapter(TaskListener { taskId ->
            taskListViewModel.onTaskClicked(taskId)
        })
        binding.taskList.adapter = adapter

        taskListViewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let{
                taskListViewModel.addNewestTaskToList()
                Timber.i("observe tasks")
                taskListViewModel.checkDate()
                adapter.submitList(taskListViewModel.currentList)

            }
        })


        taskListViewModel.navigateToTaskEntry.observe(this, Observer{ task ->
            task?.let {
                this.findNavController().navigate(
                    TaskListFragmentDirections.actionTaskListFragmentToTaskEntryFragment())
                taskListViewModel.doneNavigating()
            }

        })





        return binding.root

        }
    }



