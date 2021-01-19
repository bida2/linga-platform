package com.foreignwords.foreignwords.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

// The custom analyzer needs to be changed to use a different gram size
// Also, 
@AnalyzerDef(name = "customanalyzer", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = { @Parameter(name = "language", value = "English") }),
})
@Entity
@Indexed
public class Word {
	
	@Id
	@GeneratedValue
	@Column(name="id",nullable = false)
	private Long id;
	@Field(termVector = TermVector.YES,analyze = Analyze.YES,analyzer = @Analyzer(definition = "customanalyzer"))
	@Column(name="word", unique=true)
	private String word;
	@Field(termVector = TermVector.YES,analyze = Analyze.YES,analyzer = @Analyzer(definition = "customanalyzer"))
	@Column(name="meaning", columnDefinition="TEXT")
	private String meaning;
	@Column(name="spelling_foreign")
	private String spelling;
	// Give an example use of the word in a sentence in Bulgarian
	@Column(name="ex_sentence", columnDefinition="TEXT")
	private String exampleSent;
	@Field(termVector = TermVector.YES,analyze = Analyze.YES,analyzer = @Analyzer(definition = "customanalyzer"))
	@IndexedEmbedded
	@ElementCollection(targetClass=String.class)
	@Column(name="alt_spellings")
	private Set<String> altSpellings;
	
	// Constructors
	public Word() {}
	
	public Word(String word,String meaning,String spelling,String exampleSent) {
		this.word = word;
		this.meaning = meaning;
		this.spelling = spelling;
		this.exampleSent = exampleSent;
	}
	
	public Word(String word,String meaning,String spelling,String exampleSent,Set<String> altSpellings) {
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
