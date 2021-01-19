package com.foreignwords.foreignwords.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foreignwords.foreignwords.entities.UnapprovedWord;

public interface UnapprovedWordRepository extends JpaRepository<UnapprovedWord,Long> {

}
