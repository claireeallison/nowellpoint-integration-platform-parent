package com.nowellpoint.aws.event;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nowellpoint.aws.CacheManager;
import com.nowellpoint.aws.client.DataClient;
import com.nowellpoint.aws.model.admin.Properties;
import com.nowellpoint.aws.model.data.AbstractDocument;
import com.nowellpoint.aws.model.data.CreateDocumentRequest;
import com.nowellpoint.aws.model.data.CreateDocumentResponse;
import com.nowellpoint.aws.model.data.UpdateDocumentRequest;
import com.nowellpoint.aws.model.data.UpdateDocumentResponse;

public abstract class AbstractDocumentEventHandler implements AbstractEventHandler {
	
	final DataClient dataClient = new DataClient();
	
	public CreateDocumentResponse createDocument(String mongoClientUri, String collectionName, AbstractDocument document) throws JsonProcessingException {
		
		//
		//
		//
		
		Date now = Date.from(Instant.now());	
		
		//
		//
		//
		
		document.setCreatedDate(now);
		document.setLastModifiedDate(now);
		
		//
		//
		//
		
		CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest()
				.withMongoDBConnectUri(mongoClientUri)
				.withCollectionName(collectionName)
				.withDocument(objectMapper.writeValueAsString(document));
		
		CreateDocumentResponse createDocumentResponse = dataClient.create(createDocumentRequest);
		
		//
		//
		//
		
		return createDocumentResponse;
	}
	
	public UpdateDocumentResponse updateDocument(String mongoClientUri, String collectionName, AbstractDocument document) throws JsonProcessingException {
		
		//
		//
		//
		
		Date now = Date.from(Instant.now());	
		
		//
		//
		//
		
		document.setCreatedDate(now);
		
		//
		//
		//
		
		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest()
				.withMongoDBConnectUri(mongoClientUri)
				.withCollectionName(collectionName)
				.withDocument(objectMapper.writeValueAsString(document));
		
		UpdateDocumentResponse updateDocumentResponse = dataClient.update(updateDocumentRequest);
		
		//
		//
		//
		
		return updateDocumentResponse;
	}
	
	public void addDocumentToCache(AbstractDocument document, Map<String, String> properties) {
		
		//
		//
		//
		
		String endpoint = properties.get(Properties.REDIS_ENDPOINT);
		Integer port = Integer.valueOf(properties.get(Properties.REDIS_PORT));
		String password = properties.get(Properties.REDIS_PASSWORD);
		
		//
		//
		//
		
		CacheManager cacheManager = new CacheManager(endpoint, port);
		cacheManager.auth(password);
		cacheManager.setex(document.getId(), 259200, document);
		cacheManager.close();
	}
}