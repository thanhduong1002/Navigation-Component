package com.example.navigationcomponent.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationcomponent.databinding.FragmentToDoBinding
import com.example.navigationcomponent.ui.adapters.ToDoAdapter
import com.example.navigationcomponent.ui.viewmodels.ToDoViewModel
import com.example.navigationcomponent.ui.viewmodels.UserViewModel


class ToDoFragment : Fragment() {
    private lateinit var toDoViewModel: ToDoViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var toDoAdapter: ToDoAdapter
    private lateinit var binding: FragmentToDoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoBinding.inflate(inflater, container, false)
        return  binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        toDoAdapter = ToDoAdapter(emptyList())
        binding.recyclerViewToDos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewToDos.adapter = toDoAdapter

        val actionBar = (activity as AppCompatActivity).supportActionBar

        actionBar?.title = "List To Dos"
        actionBar?.setDisplayHomeAsUpEnabled(false)

        toDoViewModel.toDosList.observe(viewLifecycleOwner) { toDoList ->
            val userIdList = toDoList.map { it.userId }
            val imageList = mutableListOf<String?>()

            userIdList.forEach { userId ->
                if (userId != null) {
                    userViewModel.fetchUserImage(userId) { image ->
                        imageList.add(image)

                        if (imageList.size == userIdList.size) {
                            val todosWithImages = toDoList.mapIndexed { index, todo ->
                                todo.copy(image = imageList[index])
                            }
                            toDoAdapter.setToDosList(todosWithImages)
                            toDoAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

        toDoViewModel.getAllToDos()
    }
}