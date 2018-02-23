package se.goteborg.retursidan.model;

import java.util.List;

/**
 * List class that will hold a subset of objects for a specific page in a page set, and 
 * provide logic for page handling.
 * 
 * @param <T> The type of objects held in the list
 */
public class PagedList<T> {
	private List<T> list;
	private int page;
	private int pageSize;
	private int totalCount;
	
	public PagedList(List<T> list, int page, int pageSize, int totalCount) {
		this.list = list;
		this.page = page;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}
	public List<T> getList() {
		return list;
	}
	public int getPage() {
		return page;
	}
	public long getNumberOfPages() {
		return (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
	}
	public long getTotalCount() {
		return totalCount;
	}
	public int getStartIndex() {
		return (totalCount == 0) ? 0 : (page - 1) * pageSize + 1;
	}
	public int getEndIndex() {
		return (totalCount == 0) ? 0 : (page - 1) * pageSize + list.size();
	}
	public boolean getHasNext() {
		return getEndIndex() < totalCount;
	}
	public boolean getHasPrevious() {
		return getStartIndex() > 1;
	}
}
