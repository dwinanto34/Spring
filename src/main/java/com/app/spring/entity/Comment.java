package com.app.spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = Comment.TABLE_NAME)
public class Comment {
    public static final String TABLE_NAME = "comments";
    private static final String ID = "id";
    private static final String MESSAGE = "message";

    @Id
    @Column(name = Comment.ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @org.springframework.data.annotation.Id
    private String id;

    @Column(name = Comment.MESSAGE)
    private String message;

}
