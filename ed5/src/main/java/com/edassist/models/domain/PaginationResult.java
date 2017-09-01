package com.edassist.models.domain;

import java.util.ArrayList;
import java.util.List;

public class PaginationResult<T> {

	private List<T> result;
	private long totalRecordsCount;

	public PaginationResult() {
		super();
	}

	public PaginationResult(List<T> result, long totalRecordsCount) {
		super();
		this.result = result;
		this.totalRecordsCount = totalRecordsCount;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public long getTotalRecordsCount() {
		return totalRecordsCount;
	}

	public void setTotalRecordsCount(long totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}

	public void add(T t) {
		if (result == null) {
			result = new ArrayList<T>();
		}
		result.add(t);
	}
}
