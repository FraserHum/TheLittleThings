# TheLittleThings
Personal project, learning kotlin & android

Structure of the app is based off apps in the kotlin android fundamentals course from google.
It has a mainlayout with two fragments, the initial fragment contains a list of tasks, it obtains this list from a database of tasks.
It also a button to clear the list and a button to add a new task.
The new task button takes the user to the task entry fragment. When the user creates a new task it is entered into the database
and the user is returned to the task list fragment. Both fragments are backed by ViewModels that handle the logic for that fragment.
Onclick is handled by databinding and observers for the vewmodels are set in the fragment


The app is intended to display simple task list, uses can enter tasks and check them off when they are completed.
The list refreshes every day


