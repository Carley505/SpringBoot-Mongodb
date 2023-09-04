package com.example.springbootweb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootweb.exception.TodoCollectionException;
import com.example.springbootweb.model.TodoDTO;
import com.example.springbootweb.repo.TodoRepository;
import com.example.springbootweb.service.TodoService;

import jakarta.validation.ConstraintViolationException;

@RestController
public class TodoController {
	
	@Autowired
	TodoRepository repo;
	
	@Autowired
	TodoService todoService;
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos(){
		List<TodoDTO> todoList = todoService.getAllTodo();
		return new ResponseEntity<>(todoList, todoList.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
		try {
			todoService.createTodo(todo);
			return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
		}catch(ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id){
		try {
			TodoDTO optionalTodo = todoService.getTodoById(id);
			return new ResponseEntity<>(optionalTodo, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO updateTodo){
		try {
			todoService.updateTodo(id, updateTodo);
			return new ResponseEntity<>("Todo with id "+id+" updated successfully.", HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteTodoById(@PathVariable("id") String id){
	try {
		todoService.deleteTodoById(id);
		return new ResponseEntity<>("Todo with id "+id+" deleted Successfully.", HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
}
}











