package com.foreignwords.foreignwords.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UnapprovedWord {
	@Id
	@GeneratedValue
	@Column(name="id",nullable = false)
	private Long id;
	@Column(name="word", unique=true)
	private String word;
	@Column(name="meaning", columnDefinition="TEXT")
	private String meaning;
	@Column(name="spelling_foreign")
	private String spelling;
	// Give an example use of the word in a sentence in Bulgarian
	@Column(name="ex_sentence", columnDefinition="TEXT")
	private String exampleSent;
	@ElementCollection
	@Column(name="alt_spellings")
	private Set<String> altSpellings;
	
	// Constructors
	public UnapprovedWord() {}
	
	public UnapprovedWord(String word,String meaning,String spelling,String exampleSent) {
		this.word = word;
		this.meaning = meaning;
		this.spelling = spelling;
		this.exampleSent = exampleSent;
	}
	
	public UnapprovedWord(String word, String meaning, String spelling, String exampleSent, Set<String> altSpellings) {
		super();
		this.word = word;
		this.meaning = meaning;
		this.spelling = spelling;
		this.exampleSent = exampleSent;
		this.altSpellings = altSpellings;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getSpelling() {
		return spelling;
	}
	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}
	public String getExampleSent() {
		return exampleSent;
	}
	public void setExampleSent(String exampleSent) {
		this.exampleSent = exampleSent;
	}

	public Set<String> getAltSpellings() {
		return altSpellings;
	}

	public void setAltSpellings(Set<String> altSpellings) {
		this.altSpellings = altSpellings;
	}
}
