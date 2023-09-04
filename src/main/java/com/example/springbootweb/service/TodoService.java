package com.example.springbootweb.service;

import java.util.List;

import com.example.springbootweb.exception.TodoCollectionException;
import com.example.springbootweb.model.TodoDTO;

import jakarta.validation.ConstraintViolationException;

public interface TodoService {
	public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;
	public List<TodoDTO> getAllTodo();
	public TodoDTO getTodoById(String id) throws TodoCollectionException;
	public TodoDTO deleteTodoById(String id) throws TodoCollectionException;
	public TodoDTO updateTodo(String id, TodoDTO todoDTO) throws TodoCollectionException;
}
