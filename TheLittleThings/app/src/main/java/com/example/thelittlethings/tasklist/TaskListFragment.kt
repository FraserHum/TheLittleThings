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


        val viewModelFactory = TaskListViewModelFactory(dataSource, application)


        val taskListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(TaskListViewModel::class.java)

        binding.taskListViewModel = taskListViewModel

        binding.setLifecycleOwner(this)



        val adapter = TaskAdapter()
        binding.taskList.adapter = adapter

        taskListViewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.data = it
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



