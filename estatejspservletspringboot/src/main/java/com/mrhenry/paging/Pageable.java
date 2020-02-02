package com.mrhenry.paging;

public interface Pageable {
	Integer getPage();
	Integer getOffset();
	Integer getLimit();
	Sorter getSorter();
}
