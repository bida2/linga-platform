package com.foreignwords.foreignwords.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.foreignwords.foreignwords.entities.Word;

@Repository
@Transactional
public class WordSearchRepository {

	 @PersistenceContext
	  private EntityManager entityManager;
	 
	 
	  
	  public List<Word> searchByWord(String text) throws InterruptedException {
		  
		  	
		    // get the full text entity manager
		    FullTextEntityManager fullTextEntityManager =
		      org.hibernate.search.jpa.Search.
		      getFullTextEntityManager(entityManager);
		    // Test line - purge and optimize on start - used for reindexing
		    // If NoSuchFileException occurs, MANUALLY delete all the directories related to indexing
		    // Examples are com.springboot.studentservices.entities.Stock and com.springboot.studentservices.entities.Companies
		    //fullTextEntityManager.createIndexer(Stock.class).purgeAllOnStart(true).optimizeAfterPurge(true).optimizeOnFinish(true).start();
		    fullTextEntityManager.createIndexer(Word.class).startAndWait();
		    // The above line of code should NOT BE usually executed, only in cases when you need to completely reindex
		    // As it messes with indexing otherwise
		    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Word.class)
		    	    // Here come the assignments of "query" analyzers
		    	    .overridesForField( "word", "customanalyzer" )
		    	    .overridesForField("meaning", "customanalyzer")
		    	    .overridesForField("altSpellings", "customanalyzer")
		    	    .get();
		    // a very basic query by keywords
		   /* org.apache.lucene.search.Query query =
		      queryBuilder
		      	.bool()
		      	.must(queryBuilder.keyword().onFields("word").matching(text).createQuery())
		      	.createQuery(); */
		    Query fuzzyQuery = queryBuilder
		    		  .keyword()
		    		  .onFields("word","meaning", "altSpellings")
		    		  .matching(text)
		    		  .createQuery();
		    
		    org.hibernate.search.jpa.FullTextQuery jpaQuery =
		      fullTextEntityManager.createFullTextQuery(fuzzyQuery, Word.class);
		  
		    // execute search and return results (sorted by relevance as default)
		    @SuppressWarnings("unchecked")
		    List<Word> results = jpaQuery.getResultList();
		    
		    return results;
		  } // method search

	  
	  
}
