package com.riki.api.util;

import com.riki.api.domain.pagination.Paging;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PaginationUtil {

	public static <T> Paging<T> createResultPagingDTO(List<T> contacts, Long totalElement, Integer pages, Integer page) {
		Paging<T> result = new Paging<T>();
		result.setPages(pages);
		result.setPage(page);
		result.setElements(totalElement);
		result.setResults(contacts);
		return result;
	}
	
	public static Sort.Direction getSortBy(String sortBy) {
		if (sortBy.equalsIgnoreCase("asc")) {
			return Sort.Direction.ASC;
		} else {
			return Sort.Direction.DESC;
		}
	}
	
}
