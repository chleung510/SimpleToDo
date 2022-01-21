package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File


import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    // gives users ability to add list of tasks to the list.
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter // lateinit is for assigning variable later.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Will call and create interface OnLongClickListener in TaskItemAdapter class
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the list
                listOfTasks.removeAt(position)

                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }
        // To detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            //executes when user clicks on it
//            Log.i("Jeff", "User clicked on btn")
//        }

        loadItems()

        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter =TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Set up the button and input field, so that the user can enter a task and add it to the list

        // Get a reference to the button
        // and then set an onClickListener
        findViewById<Button>(R.id.button).setOnClickListener{
            // 1. Grab the text the user has inputted into @id/addTaskField
            // .text is an object not a string.
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            // 3. Reset text field
            inputTextField.setText("")
            saveItems()
        }
    }
    // Save the data that user inputted
    // Save data by writing and reading from a file

    // Get the file we need
    fun getDataFile() : File {

        // Every line is going to be a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }
    // load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    // Save items by writing them into our data file
    fun saveItems() {
        try {
           FileUtils.writeLines(getDataFile(), listOfTasks) // saves listOfTasks to the file
        } catch (ioException: IOException){ // in case the data file does not exist
            ioException.printStackTrace()   // print the message
        }

    }

}