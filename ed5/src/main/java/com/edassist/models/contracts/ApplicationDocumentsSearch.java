package com.edassist.models.contracts;

import java.util.Collection;
import java.util.Date;

public class ApplicationDocumentsSearch {

	Collection<Long> applicationStatusIds;
	Collection<Long> applicationTypeIds;
	Collection<Long> DefaultDocumentIds;
	Collection<Long> excludeApplicationIds;
	Date cutoffDate;

	public Collection<Long> getExcludeApplicationIds() {
		return excludeApplicationIds;
	}

	public void setExcludeApplicationIds(Collection<Long> excludeApplicationIds) {
		this.excludeApplicationIds = excludeApplicationIds;
	}

	public Collection<Long> getApplicationStatusIds() {
		return applicationStatusIds;
	}

	public void setApplicationStatusId(Collection<Long> applicationStatusIds) {
		this.applicationStatusIds = applicationStatusIds;
	}

	public Collection<Long> getApplicationTypeIds() {
		return applicationTypeIds;
	}

	public void setApplicationTypeId(Collection<Long> applicationTypeIds) {
		this.applicationTypeIds = applicationTypeIds;
	}

	public Collection<Long> getDefaultDocumentIds() {
		return DefaultDocumentIds;
	}

	public void setDefaultDocumentIds(Collection<Long> defaultDocumentIds) {
		DefaultDocumentIds = defaultDocumentIds;
	}

	public Date getCutoffDate() {
		return cutoffDate;
	}

	public void setCutoffDate(Date cutoffDate) {
		this.cutoffDate = cutoffDate;
	}

}
