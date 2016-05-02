package com.ccbtrust.ibatis.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 分页类
 * @author ciyuan
 *
 */
public class PageBean implements Serializable {
	private static final long serialVersionUID = 5805827525625235066L;
	private int currentPage;//当前页码
	private int length;//页面展示条数（初始化为10条）
	private int totalPage;//总页数
	private int totalRecords;//总条数
	private List<Object> results;//全部结果集
	@SuppressWarnings("rawtypes")
	private Map condition;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageBean(){
		length = 10;
		results = new ArrayList();
		condition = new HashMap();
	}
	
	public PageBean(int currentPage,int length){
		this.length = 10;
		results = new ArrayList();
		condition = new HashMap();
		setCurrentPage(currentPage);
		setLength(length);
	}
	
	@SuppressWarnings("rawtypes")
	public Map getCondition() {
		return condition;
	}
	@SuppressWarnings("rawtypes")
	public void setCondition(Map condition) {
		this.condition = condition;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		if(length < 1){
			throw new IllegalArgumentException("length must greater than 0 !");
		}else{			
			this.length = length;
			return;
		}
	}
    public List<Object> getResults() {
		return results;
	}
	public int getResultsLength() {
    	return results.size();
    }
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setResults(List results) {
		this.results = results;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
		totalPage = totalRecords / length;
		if(totalRecords % length != 0){
			totalPage++;
		}
		if(totalPage != 0){
			if(currentPage < 1){
				currentPage = 1;
			}
			if(currentPage > totalPage){
				currentPage = totalPage;
			}
		}else{
			currentPage = 1;
		}
	}
	/**
	 * 是否第一页
	 * @return
	 */
	public boolean canToFirst(){
		return currentPage > 1;
	}
	/**
	 * 是否最后一页
	 * @return
	 */
	public boolean canToLast(){
		return currentPage < totalPage;
	}
	/**
     * 是否有下一页
     * 
     * @return
     */
    public boolean canToNext() {
	return currentPage < totalPage;
    }

    /**
     * 是否有前一页
     * 
     * @return
     */
    public boolean canToPre() {
    	return currentPage > 1;
    }
    /**
     * 上一页开始条数
     * @return
     */
    public int getRsFirstNumber() {
    	return (currentPage - 1) * length + 1;
    }
    /**
     * 下一页开始条数
     * @return
     */
    public int getRsLastNumber() {
    	return currentPage * length + 1;
    }
    public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append((new StringBuilder()).append("totalRecords=").append(totalRecords).toString())
			.append((new StringBuilder()).append(";totalPages=").append(totalPage).toString())
			.append((new StringBuilder()).append(";currentPage=").append(currentPage).toString())
			.append((new StringBuilder()).append(";length=").append(length).toString()).toString();
    }
    /**
     * 返回全部结果集-泛型为Object
     * @return
     */
    public List<Object> getRows() {
    	return this.results;
    }
}
