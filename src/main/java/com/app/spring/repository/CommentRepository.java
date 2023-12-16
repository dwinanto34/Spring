package com.app.spring.repository;

import com.app.spring.entity.Comment;
import com.app.spring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
// Access the API:
// ${hostname}/comments?page=0&size=20&sort=message,asc
@RepositoryRestResource(path = "comments")
public interface CommentRepository extends JpaRepository<Comment, String> {

}
