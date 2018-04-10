/**
 * 
 */
package edu.neu.ccs.community;
import java.sql.Timestamp;
/**
 * @author Zhenhuan
 *
 */
public class Thread {
	Integer threadID;
    int forumID;
    String title;
    String author;
    String lastUpdator;
    
    Timestamp creationTime;
    Timestamp lastUpdateTime;
    
    boolean isSticky;
    boolean isDeleted;
    
    int numberOfViews;
	/**
	 * @param threadID
	 * @param forumID
	 * @param title
	 * @param author
	 * @param lastUpdator
	 * @param creationTime
	 * @param lastUpdateTime
	 * @param isSticky
	 * @param isDeleted
	 */
	public Thread(Integer threadID, int forumID, String title, String author, String lastUpdator,
			Timestamp creationTime, Timestamp lastUpdateTime, boolean isSticky, boolean isDeleted, int numberOfViews) {
		super();
		this.threadID = threadID;
		this.forumID = forumID;
		this.title = title;
		this.author = author;
		this.lastUpdator = lastUpdator;
		this.creationTime = creationTime;
		this.lastUpdateTime = lastUpdateTime;
		this.isSticky = isSticky;
		this.isDeleted = isDeleted;
		this.numberOfViews = numberOfViews;
	}
	/**
	 * @param forumID
	 * @param title
	 * @param author
	 * @param isSticky
	 * @param isDeleted
	 */
	public Thread(int forumID, String title, String author, boolean isSticky, boolean isDeleted) {
		super();
		//this.threadID = null;
		this.forumID = forumID;
		this.title = title;
		this.author = author;
		this.lastUpdator = author;
		this.creationTime = new Timestamp(System.currentTimeMillis());
		this.lastUpdateTime = new Timestamp(System.currentTimeMillis());
		this.isSticky = isSticky;
		this.isDeleted = isDeleted;
	}
	/**
	 * @return the threadID
	 */
	public Integer getThreadID() {
		return threadID;
	}
	/**
	 * @param threadID the threadID to set
	 */
	public void setThreadID(Integer threadID) {
		this.threadID = threadID;
	}
	/**
	 * @return the forumID
	 */
	public int getForumID() {
		return forumID;
	}
	/**
	 * @param forumID the forumID to set
	 */
	public void setForumID(int forumID) {
		this.forumID = forumID;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the lastUpdator
	 */
	public String getLastUpdator() {
		return lastUpdator;
	}
	/**
	 * @param lastUpdator the lastUpdator to set
	 */
	public void setLastUpdator(String lastUpdator) {
		this.lastUpdator = lastUpdator;
	}
	/**
	 * @return the creationTime
	 */
	public Timestamp getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the lastUpdateTime
	 */
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void getLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return the isSticky
	 */
	public boolean isSticky() {
		return isSticky;
	}
	/**
	 * @param isSticky the isSticky to set
	 */
	public void setSticky(boolean isSticky) {
		this.isSticky = isSticky;
	}
	/**
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * @return the numberOfViews
	 */
	public int getNumberOfViews() {
		return numberOfViews;
	}
	/**
	 * @param numberOfViews the numberOfViews to set
	 */
	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
    
}
