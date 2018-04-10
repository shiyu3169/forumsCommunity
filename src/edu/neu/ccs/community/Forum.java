/**
 * 
 */
package edu.neu.ccs.community;
import java.sql.Timestamp;
/**
 * @author Zhenhuan
 *
 */
public class Forum {
	private int forumID;
	private Integer parentID;
	private String forumName;
	private String owner;
    private String category;
    private String description;
    
    private Timestamp creationTime;
    private Timestamp lastPostTime;
    
    private boolean isVerified;
    
	/**
	 * @param forumID
	 * @param parentID
	 * @param forumName
	 * @param owner
	 * @param category
	 * @param description
	 * @param creationTime
	 * @param lastPostTime
	 * @param isVerified
	 */
	public Forum(int forumID, Integer parentID, String forumName, String owner, String category, String description,
			Timestamp creationTime, Timestamp lastPostTime, boolean isVerified) {
		super();
		this.forumID = forumID;
		this.parentID = parentID;
		this.forumName = forumName;
		this.owner = owner;
		this.category = category;
		this.description = description;
		this.creationTime = creationTime;
		this.lastPostTime = lastPostTime;
		this.isVerified = isVerified;
	}
	

	/**
	 * @param parentID
	 * @param forumName
	 * @param owner
	 * @param category
	 * @param description
	 * @param isVerified
	 */
	public Forum(Integer parentID, String forumName, String owner, String category, String description,
			boolean isVerified) {
		super();
		this.parentID = parentID;
		this.forumName = forumName;
		this.owner = owner;
		this.category = category;
		this.description = description;
		this.isVerified = isVerified;
		this.creationTime = new Timestamp(System.currentTimeMillis());
	}


	public int getForumID() {
		return forumID;
	}

	public void setForumID(int forumID) {
		this.forumID = forumID;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Timestamp getLastPostTime() {
		return lastPostTime;
	}

	public void setLastPostTime(Timestamp lastPostTime) {
		this.lastPostTime = lastPostTime;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
    
}
