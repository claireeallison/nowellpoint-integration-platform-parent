package com.nowellpoint.aws.model.data;

public class CreateDocumentRequest extends AbstractDocumentRequest {

	private static final long serialVersionUID = 6391624974618347458L;
	
	public CreateDocumentRequest() {
		
	}
	
	public String getCollectionName() {
		return super.getCollectionName();
	}
	
	public String getDocument() {
		return super.getDocument();
	}
	
	public CreateDocumentRequest withUserContext(UserContext userContext) {
		setUserContext(userContext);
		return this;
	}

	public CreateDocumentRequest withCollectionName(String collectionName) {
		setCollectionName(collectionName);
		return this;
	}
	
	public CreateDocumentRequest withDocument(String document) {
		setDocument(document);
		return this;
	}
}