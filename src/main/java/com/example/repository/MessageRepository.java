package com.example.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("FROM Message WHERE posted_by = :postVar")
    List<Message> findByposted_by(@Param("postVar") Integer id);

    // List<Message> findMessagesByposted_by(Integer id);
}
