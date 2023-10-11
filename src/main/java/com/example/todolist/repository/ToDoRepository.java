package com.example.todolist.repository;

import com.example.todolist.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository  extends JpaRepository<ToDo, Long> {
//JpaRepository 이용하고 있기에 추가, 삭제 항목을 만들때 서비스를 따로 구현하지 않아도 되는 장점이 있다. 편하다!
}