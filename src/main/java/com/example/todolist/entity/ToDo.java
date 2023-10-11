package com.example.todolist.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Member;

@Entity(name = "ToDo")
@Table(name = "todo")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
@Getter
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    @Column
    private String todo;

    @NotNull
    @Column
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "member_id") // 외래키 컬럼 이름을 정의합니다. 실제 DB에는 이 컬럼이 생성됩니다.
    private MemberEntity memberId;
}