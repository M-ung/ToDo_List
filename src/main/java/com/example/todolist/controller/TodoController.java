package com.example.todolist.controller;

import com.example.todolist.entity.MemberEntity;
import com.example.todolist.entity.ToDo;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TodoController  {
    private final ToDoRepository toDoRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/member/main")
    public String main(Model model, HttpSession session){
        Long memberId = (Long) session.getAttribute("loginId");
        System.out.println(memberId+" memberId 로그인하셨습니다.");

//        List<ToDo> todos = toDoRepository.findAll();
//        model.addAttribute("todos", todos);

        // 모든 할 일(Todo) 목록을 가져와서 todos 리스트에 전부 저장
        List<ToDo> allTodos = toDoRepository.findAll();

        // 현재 로그인한 사용자의 memberId와 할 일의 memberId를 비교하여 해당 사용자의 할 일만 선택
        List<ToDo> userTodos = new ArrayList<>();

        for (int i=0; i<allTodos.size(); i++) {
            Long currentTodo = allTodos.get(i).getMemberId().getId();
            if(currentTodo == memberId) {
                userTodos.add(allTodos.get(i));
            }
        }

        model.addAttribute("todos", userTodos);

        return "main";
    }

    //todolist 항목 추가
    @PostMapping("/addTodo")
    public String addTodo(@RequestParam("todo") String todo, HttpSession session) {
        Long memberId = (Long) session.getAttribute("loginId");
        MemberEntity member = memberRepository.findById(memberId).orElse(null); // 멤버 엔티티를 찾아옵니다.

        if (member != null) {
            ToDo toDo = new ToDo();
            toDo.setTodo(todo);
            toDo.setCompleted(false); // 초기값으로 설정해도 좋습니다.
            toDo.setMemberId(member); // 찾은 멤버 엔티티를 설정합니다.

            toDoRepository.save(toDo);
        }

        return "redirect:/member/main";
    }
//todolist 항목 삭제
    @PostMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id){
        toDoRepository.deleteById(id); // 받아온 id로 해당 항목 삭제
        return "redirect:/member/main"; //초기화면으로 이동
    }

    @PostMapping("/completed/{id}")
    public String completedTodo(@PathVariable Long id){
        Optional<ToDo> todoOptional = toDoRepository.findById(id);

        if (todoOptional.isPresent()) {
            ToDo todo = todoOptional.get();
            boolean check = todo.isCompleted();
            if(check) {
                todo.setCompleted(false);
            }
            else {
                todo.setCompleted(true);
            }

            toDoRepository.save(todo);
        }
        return "redirect:/member/main"; //초기화면으로 이동
    }
}