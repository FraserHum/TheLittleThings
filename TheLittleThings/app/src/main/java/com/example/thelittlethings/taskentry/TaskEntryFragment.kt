package com.example.thelittlethings.taskentry

import android.content.Context
import android.net.Uri
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
import com.example.thelittlethings.databinding.FragmentTaskEntryBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TaskEntryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TaskEntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TaskEntryFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentTaskEntryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task_entry, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = TaskEntryViewModelFactory(dataSource, application)

        val taskEntryViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TaskEntryViewModel::class.java)

        binding.taskEntryViewModel = taskEntryViewModel

        taskEntryViewModel.navigateToTaskList.observe(this, Observer {
            if (it == true){
                this.findNavController().navigate(
                    TaskEntryFragmentDirections.actionTaskEntryFragmentToTaskListFragment()
                )
                taskEntryViewModel.doneNavigating()
            }
        })


        return binding.root
    }






}
