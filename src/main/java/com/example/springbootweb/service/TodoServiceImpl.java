package com.example.springbootweb.service;
import com.example.springbootweb.exception.TodoCollectionException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootweb.model.TodoDTO;
import com.example.springbootweb.repo.TodoRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository repo;
	
	@Override
	public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
		Optional<TodoDTO> todoOptional =  repo.findByTodo(todo.getTodo());
		if(todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExistException(todo.getTodo()));
		}else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			repo.save(todo);
		}
		
	}

	@Override
	public List<TodoDTO> getAllTodo() {
		List<TodoDTO> todoList = repo.findAll();
		if(!(todoList.size() > 0)) {
			return new ArrayList<TodoDTO>();
		}else {
			return todoList;
		}
	}

	@Override
	public TodoDTO getTodoById(String id) throws TodoCollectionException {
	    Optional<TodoDTO> todoOptional = repo.findById(id);
	    if(!(todoOptional.isPresent())) {
	    	throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
	    }else {
	    	return todoOptional.get();
	    }
	}

	@Override
	public TodoDTO deleteTodoById(String id) throws TodoCollectionException {
		Optional<TodoDTO> optionalTodo = repo.findById(id);
		if(optionalTodo.isPresent()) {
		  TodoDTO deleted = optionalTodo.get();
          repo.deleteById(id);
          return deleted;
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
	}

	@Override
	public TodoDTO updateTodo(String id, TodoDTO todoDTO) throws TodoCollectionException {
		Optional<TodoDTO> optionalTodo = repo.findById(id);
		Optional<TodoDTO> todoWithSameName = repo.findByTodo(todoDTO.getTodo());
		if(!(optionalTodo.isPresent())) {
			if(todoWithSameName.isPresent() && !(todoWithSameName.get().getId().equals(id))) {
				throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExistException(id));
			}
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}else {
			TodoDTO todo = optionalTodo.get();
			todo.setCompleted(todoDTO.getCompleted());
			todo.setTodo(todoDTO.getTodo());
			todo.setDescription(todoDTO.getDescription());
			todo.setUpdatedAt(new Date(System.currentTimeMillis()));
			repo.save(todo);
			return todo;
		}
		
	}

}
