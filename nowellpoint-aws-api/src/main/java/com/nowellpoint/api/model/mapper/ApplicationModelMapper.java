package com.nowellpoint.api.model.mapper;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.TypeToken;

import com.nowellpoint.api.model.dto.Application;
import com.nowellpoint.api.model.dto.Id;

/**
 * 
 * 
 * @author jherson
 *
 * 
 */

public class ApplicationModelMapper extends AbstractModelMapper<com.nowellpoint.api.model.document.Application> {
	
	
	/**
	 * 
	 * 
	 * constructor
	 * 
	 * 
	 */
	
	public ApplicationModelMapper() {
		super(com.nowellpoint.api.model.document.Application.class);
	}
	
	public Application findApplication(Id id) {
		com.nowellpoint.api.model.document.Application document = findById(id.toString());
		return modelMapper.map(document, Application.class);
	}	
	
	public void createApplication(Application application) {
		com.nowellpoint.api.model.document.Application document = modelMapper.map(application, com.nowellpoint.api.model.document.Application.class);
		create(getSubject(), document);
		modelMapper.map(document, application);
	}
	
	public void updateApplication(Application application) {
		com.nowellpoint.api.model.document.Application document = modelMapper.map(application, com.nowellpoint.api.model.document.Application.class);
		replace(getSubject(), document);
		modelMapper.map(document, application);
	}
	
	public Set<Application> findAllByOwner() {
		Set<com.nowellpoint.api.model.document.Application> documents = findAllByOwner(getSubject());
		Set<Application> resources = modelMapper.map(documents, new TypeToken<HashSet<Application>>() {}.getType());
		return resources;
	}
	
	public void deleteApplication(Application application) {
		com.nowellpoint.api.model.document.Application document = modelMapper.map(application, com.nowellpoint.api.model.document.Application.class);
		delete(getSubject(), document);
	}
}