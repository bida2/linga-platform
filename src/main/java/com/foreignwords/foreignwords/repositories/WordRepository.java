package com.foreignwords.foreignwords.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.foreignwords.foreignwords.entities.Word;

public interface WordRepository extends JpaRepository<Word,Long> {
	@Query(nativeQuery=true, value="SELECT * from word ORDER BY random() LIMIT ?1")
	List<Word> getRandomWords(int amount);
	List<Word> findByWordStartingWith(String letter);
	Word findByWord(String word);
}
