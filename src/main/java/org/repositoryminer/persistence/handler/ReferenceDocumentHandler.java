package org.repositoryminer.persistence.handler;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.repositoryminer.model.ReferenceType;
import org.repositoryminer.persistence.Connection;

import com.mongodb.BasicDBObject;

public class ReferenceDocumentHandler extends DocumentHandler{

	private static final String COLLECTION_NAME = "references";

	public ReferenceDocumentHandler(){
		super.collection = Connection.getInstance().getCollection(COLLECTION_NAME);
	}

	public List<Document> getAllByRepository(String repository) {
		BasicDBObject whereClause = new BasicDBObject();
		whereClause.put("repository", repository);
		return findMany(whereClause, null);
	}

	public List<Document> getBranchesByRepository(String idRepository) {
		BasicDBObject query = getTreesByTypeAndRepository(idRepository, ReferenceType.BRANCH);		
		return findMany(query, null);
	}

	public List<Document> getTagsByRepository(String idRepository) {
		BasicDBObject query = getTreesByTypeAndRepository(idRepository, ReferenceType.TAG);		
		return findMany(query, null);
	}
	
	public Document getMaster(String idRepository) {
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> conditions = new ArrayList<BasicDBObject>();

		conditions.add(new BasicDBObject("repository", idRepository));
		conditions.add(new BasicDBObject("name", "master"));
		andQuery.put("$and", conditions);
		return findOne(andQuery, null);
	}
	
	public List<Document> getTagsAndMasterByRepository(String idRepository) {
		List<Document> documents = getTagsByRepository(idRepository);
		documents.add(getMaster(idRepository));
		return documents;
	}

	private BasicDBObject getTreesByTypeAndRepository(String repository, ReferenceType type) {
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> conditions = new ArrayList<BasicDBObject>();

		conditions.add(new BasicDBObject("repository", repository));
		conditions.add(new BasicDBObject("type", type.toString()));
		andQuery.put("$and", conditions);
		return andQuery;
	}

}