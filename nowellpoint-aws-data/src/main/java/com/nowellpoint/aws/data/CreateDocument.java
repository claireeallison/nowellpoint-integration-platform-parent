package com.nowellpoint.aws.data;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.nowellpoint.aws.model.Configuration;
import com.nowellpoint.aws.model.data.CreateDocumentRequest;
import com.nowellpoint.aws.model.data.CreateDocumentResponse;

public class CreateDocument implements RequestHandler<CreateDocumentRequest, CreateDocumentResponse> {
	
	private static final Logger log = Logger.getLogger(CreateDocument.class.getName());

	@Override
	public CreateDocumentResponse handleRequest(CreateDocumentRequest request, Context context) {
		
		/**
		 * 
		 */
		
		long startTime = System.currentTimeMillis();
		
		/**
		 * 
		 */
		
		CreateDocumentResponse response = new CreateDocumentResponse();
		
		/**
		 * 
		 */

		MongoClientURI mongoClientURI = new MongoClientURI("mongodb://".concat(Configuration.getMongoClientUri()));
		
		/**
		 * 
		 */
		
		MongoClient mongoClient = new MongoClient(mongoClientURI);
				
		/**
		 * 
		 */ 

		MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoClientURI.getDatabase());
		
		log.info(request.getCollectionName());
		
		Date now = Date.from(Instant.now());
		
		Document document = Document.parse(request.getDocument());
		document.put("createdDate", now);
		document.put("lastModifiedDate", now);
		
		try{
			mongoDatabase.getCollection(request.getCollectionName()).insertOne(document);
		} catch (MongoException e) {
			e.printStackTrace();
		}
		
		log.info(document.getObjectId("_id").toString());
		
		log.info("execution time: " + (System.currentTimeMillis() - startTime));
		
		response.setStatusCode(201);
		response.setId(document.getObjectId("_id").toString());
		response.setDocument(document.toJson());
		
		mongoClient.close();
		
		return response;
	}
}