/**
 * 
 */
package edu.neu.ccs.community;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * @author Zhenhuan
 *
 */
public class DataAccessObject {

	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "root";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/**
	 * The name of the database we are testing with (this default is installed
	 * with MySQL)
	 */
	private final String dbName = "community";

	public static void main(String[] args) {
		DataAccessObject dao = new DataAccessObject();
		try {
			// dao.create(new User("String username", "String password", "String
			// email", "String loginIpAddress", false,
			// false));

			dao.getPostsByThreadID(1);

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public Connection getConnection() {
	 * Class.forName("com.mysql.jdbc.Driver").newInstance(); String
	 * connectionUrl = ""; return null; }
	 */
	public Connection getConnection()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		connection = DriverManager.getConnection(
				"jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName, connectionProps);

		return connection;
	}

	/** Create User */
	public void create(User user)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "SELECT create_user(?,?,?,?,?,?,?,?,?,?,?,?)"; // No need
																	// for
																	// the
																	// function
																	// result

		try (Connection connection = this.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getLoginIpAddress());
			statement.setBoolean(5, user.isAdministrator());
			statement.setBoolean(6, user.isBanned());
			statement.setTimestamp(7, user.getRegisterationTime());
			statement.setTimestamp(8, user.getLastLoginTime());
			if (user.getGender() == null) {
				statement.setNull(9, Types.CHAR);
			} else {
				statement.setString(9, user.getAutobiography());
			}
			if (user.getAutobiography() == null) {
				statement.setNull(10, java.sql.Types.LONGVARCHAR);
			} else {
				statement.setString(10, user.getAutobiography());
			}
			if (user.getDateOfBirth() == null) {
				statement.setNull(11, java.sql.Types.DATE);
			} else {
				statement.setDate(11, user.getDateOfBirth());
			}
			statement.setInt(12, user.getNewMessages());
			statement.execute();

		}
		;
	}

	public User getUserByName(String username)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL get_user_by_name(?)";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				// String username = rs.getString("UserName");
				String password = rs.getString("User_Password");
				String email = rs.getString("User_EMail");
				String loginIpAddress = rs.getString("User_LoginIpAddress");
				Timestamp registerationTime = rs.getTimestamp("User_RegisterationTime");
				Timestamp lastLoginTime = rs.getTimestamp("User_LastLoginTime");
				Timestamp lastPostTime = rs.getTimestamp("User_LastPostTime");
				boolean isAdministrator = rs.getBoolean("User_IsAdministrator");
				boolean isBanned = rs.getBoolean("User_IsBanned");
				String genderString = rs.getString("User_Gender");
				Character gender = genderString == null ? null : genderString.charAt(0);
				String autobiography = rs.getString("User_Autobiography");
				Date dateOfBirth = rs.getDate("User_DateOfBirth");
				int newMessages = rs.getInt("User_NumberOfNewMessages");
				return new User(username, password, email, loginIpAddress, isAdministrator, isBanned, gender,
						autobiography, dateOfBirth, newMessages);
			} else {
				return null;
			}
		}
	}

	/*	*//** Delete User */
	/*
	 * public void delete(User user) throws SQLException,
	 * InstantiationException, IllegalAccessException, ClassNotFoundException {
	 * String sql = "CALL delete_user(?)";
	 * 
	 * try (Connection connection = this.getConnection(); PreparedStatement
	 * statement = connection.prepareStatement(sql)) { statement.setString(1,
	 * user.getUsername());
	 * 
	 * statement.execute();
	 * 
	 * } ; }
	 */
	/*	*//** Delete user by name *//*
									 * public void deleteUser(String username)
									 * throws SQLException,
									 * InstantiationException,
									 * IllegalAccessException,
									 * ClassNotFoundException { String sql =
									 * "CALL delete_user(?)";
									 * 
									 * try (Connection connection =
									 * this.getConnection(); PreparedStatement
									 * statement =
									 * connection.prepareStatement(sql)) {
									 * statement.setString(1, username);
									 * 
									 * statement.execute(); } }
									 */

	/**
	 * Create forum
	 * 
	 * @return
	 */
	public int create(Forum forum)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "{? = CALL create_forum(?,?,?,?,?,?,?)}";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			if (forum.getParentID() == null)
				statement.setNull(2, Types.INTEGER);
			else
				statement.setInt(2, forum.getParentID());
			statement.setString(3, forum.getForumName());
			statement.setString(4, forum.getOwner());
			statement.setString(5, forum.getCategory());
			statement.setString(6, forum.getDescription());
			statement.setTimestamp(7, forum.getCreationTime());
			statement.setBoolean(8, forum.isVerified());

			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.execute();

			return statement.getInt(1);
		}
	};

	public void update(Forum forum)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String sql = "CALL update_forum(?,?,?,?,?,?,?)";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, forum.getForumID());
			if (forum.getParentID() == null)
				statement.setNull(2, Types.INTEGER);
			else
				statement.setInt(2, forum.getParentID());
			statement.setString(3, forum.getForumName());
			statement.setString(4, forum.getOwner());
			statement.setString(5, forum.getCategory());
			statement.setString(6, forum.getDescription());
			statement.setBoolean(7, forum.isVerified());

			statement.execute();

		}
	}

	public void update(Post post)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String sql = "CALL update_post(?,?,?,?)";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, post.getPostID());
			statement.setString(2, post.getContent());
			statement.setTimestamp(3, post.getLastModificationTime());
			statement.setBoolean(4, post.isDeleted());
			statement.execute();

		}
	}

	public void update(String oldUsername, User user)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL update_user(?,?,?,?,?,?,?,?,?)";
		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			/*
			 * Old_UserName VARCHAR(50), New_UserName VARCHAR(50),
			 * New_User_Password VARCHAR(512), New_User_EMail VARCHAR(63),
			 * New_User_IsAdministrator BOOLEAN, New_User_IsBanned BOOLEAN,
			 * New_User_Gender CHAR(1), New_User_Autobiography LONGTEXT,
			 * New_User_DateOfBirth DATE
			 */
			statement.setString(1, oldUsername);
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getEmail());
			statement.setBoolean(5, user.isAdministrator());
			statement.setBoolean(6, user.isBanned());
			if (user.getGender() == null) {
				statement.setNull(7, Types.CHAR);
			} else {
				statement.setString(7, user.getGender().toString());
			}
			if (user.getAutobiography() == null) {
				statement.setNull(8, Types.CHAR);
			} else {
				statement.setString(8, user.getAutobiography());
			}
			if (user.getDateOfBirth() == null) {
				statement.setNull(9, Types.DATE);
			} else {
				statement.setDate(9, user.getDateOfBirth());
			}

			statement.execute();
		}
	}

	public List<Forum> searchForumByName(String name)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL search_forum_by_name(?)";
		ArrayList<Forum> result = new ArrayList<Forum>();
		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int forumID = rs.getInt("ForumID");
				Integer parentID = rs.getInt("ParentID");
				String forumName = rs.getString("ForumName");
				String owner = rs.getString("Forum_Owner");
				String category = rs.getString("Forum_Category");
				String description = rs.getString("Forum_Description");
				Timestamp creationTime = rs.getTimestamp("Forum_CreationTime");
				Timestamp lastPostTime = rs.getTimestamp("Forum_LastPostTime");
				boolean isVerified = rs.getBoolean("Forum_IsVerified");
				result.add(new Forum(forumID, parentID, forumName, owner, category, description, creationTime,
						lastPostTime, isVerified));
			}
		}
		return result;
	}

	public List<Forum> getAllForums()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL get_all_forums()";
		ArrayList<Forum> result = new ArrayList<Forum>();
		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int forumID = rs.getInt("ForumID");
				Integer parentID = rs.getInt("ParentID");
				String forumName = rs.getString("ForumName");
				String owner = rs.getString("Forum_Owner");
				String catagory = rs.getString("Forum_Category");
				String description = rs.getString("Forum_Description");
				Timestamp creationTime = rs.getTimestamp("Forum_CreationTime");
				Timestamp lastPostTime = rs.getTimestamp("Forum_LastPostTime");
				boolean isVerified = rs.getBoolean("Forum_IsVerified");
				result.add(new Forum(forumID, parentID, forumName, owner, catagory, description, creationTime,
						lastPostTime, isVerified));
			}
		}
		return result;
	}

	public List<Forum> getChildForums(Integer parentID)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL get_child_forums(?)";
		ArrayList<Forum> result = new ArrayList<Forum>();
		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, parentID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int forumID = rs.getInt("ForumID");
				// Integer parentID = rs.getInt("ParentID");
				String forumName = rs.getString("ForumName");
				String owner = rs.getString("Forum_Owner");
				String catagory = rs.getString("Forum_Category");
				String description = rs.getString("Forum_Description");
				Timestamp creationTime = rs.getTimestamp("Forum_CreationTime");
				Timestamp lastPostTime = rs.getTimestamp("Forum_LastPostTime");
				boolean isVerified = rs.getBoolean("Forum_IsVerified");
				result.add(new Forum(forumID, parentID, forumName, owner, catagory, description, creationTime,
						lastPostTime, isVerified));
				// System.out.println(forumName);
			}
		}
		return result;
	}

	public Forum getForumByID(int forumID)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL get_forum_by_id(?)";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, forumID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				// int forumID = rs.getInt("ForumID");
				Integer parentID = rs.getInt("ParentID");
				String forumName = rs.getString("ForumName");
				String owner = rs.getString("Forum_Owner");
				String catagory = rs.getString("Forum_Category");
				String description = rs.getString("Forum_Description");
				Timestamp creationTime = rs.getTimestamp("Forum_CreationTime");
				Timestamp lastPostTime = rs.getTimestamp("Forum_LastPostTime");
				boolean isVerified = rs.getBoolean("Forum_IsVerified");
				return new Forum(forumID, parentID, forumName, owner, catagory, description, creationTime, lastPostTime,
						isVerified);
			} else {
				return null;
			}
		}
	}

	public void deleteForumByID(int forumID)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL delete_forum(?)";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, forumID);
			statement.execute();
		}
	}

	public boolean userValidation(String username, String password)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String sql = "{ ? = CALL user_login_validation(?,?) }";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setString(2, username);
			statement.setString(3, password);
			statement.registerOutParameter(1, java.sql.Types.BOOLEAN);

			statement.execute();
			return statement.getBoolean(1);

		}
	}

	public int create(Thread thread)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String sql = "{ ? = CALL create_thread(?,?,?,?,?,?,?,?) }";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(2, thread.getForumID());
			statement.setString(3, thread.getTitle());
			statement.setString(4, thread.getAuthor());
			statement.setString(5, thread.getLastUpdator());
			statement.setTimestamp(6, thread.getCreationTime());
			statement.setTimestamp(7, thread.getLastUpdateTime());
			statement.setBoolean(8, thread.isSticky);
			statement.setBoolean(9, thread.isDeleted);
			statement.registerOutParameter(1, java.sql.Types.INTEGER);

			statement.execute();
			thread.setThreadID(statement.getInt(1));
			return thread.getThreadID();

		}
	}

	public int create(Post post)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String sql = "{ ? = CALL create_post(?,?,?,?,?,?,?) }";
		/*
		 * 
		 * Given_ThreadID INT, -- foreign key to Threads(ThreadID)
		 * Given_ReplyToPost INT, -- foreign key to Post(PostID)
		 * 
		 * Given_Post_Author VARCHAR(50), Given_Post_LastModifier VARCHAR(50),
		 * 
		 * Given_Post_Content LONGTEXT,
		 * 
		 * Given_Post_CreationTime DATETIME, Given_Post_LastModificationTime
		 * DATETIME,
		 * 
		 * Given_Post_IsDeleted BOOLEAN
		 */
		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(2, post.getThreadID());
			if (post.getReplyToPost() == null)
				statement.setNull(3, Types.INTEGER);
			else
				statement.setInt(3, post.getReplyToPost());
			statement.setString(4, post.getAuthor());
			// statement.setNull(5, Types.VARCHAR);
			statement.setString(5, post.getContent());
			statement.setTimestamp(6, post.getCreationTime());
			if (post.getLastModificationTime() == null) {
				statement.setNull(7, Types.TIMESTAMP);
			} else {
				statement.setTimestamp(7, post.getLastModificationTime());
			}
			statement.setBoolean(8, post.isDeleted());
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.execute();
			post.setPostID(statement.getInt(1));
			return post.getPostID();

		}
	}

	public ArrayList<Thread> getThreadsByForumID(int forumID)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL get_thread_list_by_forumID(?)";
		ArrayList<Thread> result = new ArrayList<Thread>();
		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, forumID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int threadID = rs.getInt("ThreadID");
				// Integer forumID = rs.getInt("ForumID");
				String title = rs.getString("Thread_Title");
				String author = rs.getString("Thread_Author");
				String lastUpdator = rs.getString("Thread_LastUpdator");
				Timestamp creationTime = rs.getTimestamp("Thread_CreationTime");
				Timestamp lastUpdateTime = rs.getTimestamp("Thread_LastUpdateTime");
				boolean isSticky = rs.getBoolean("Thread_IsSticky");
				boolean isDeleted = rs.getBoolean("Thread_IsDeleted");
				int numberOfViews = rs.getInt("Thread_NumberOfViews");
				result.add(new Thread(threadID, forumID, title, author, lastUpdator, creationTime, lastUpdateTime,
						isSticky, isDeleted, numberOfViews));
			}
		}

		return result;
	}

	public List<Thread> getRecentThreadsUpdatedByUser(String username)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL get_recent_threads_updated_by_user(?)";
		ArrayList<Thread> result = new ArrayList<Thread>();
		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int threadID = rs.getInt("ThreadID");
				Integer forumID = rs.getInt("ForumID");
				String title = rs.getString("Thread_Title");
				String author = rs.getString("Thread_Author");
				String lastUpdator = rs.getString("Thread_LastUpdator");
				Timestamp creationTime = rs.getTimestamp("Thread_CreationTime");
				Timestamp lastUpdateTime = rs.getTimestamp("Thread_LastUpdateTime");
				boolean isSticky = rs.getBoolean("Thread_IsSticky");
				boolean isDeleted = rs.getBoolean("Thread_IsDeleted");
				int numberOfViews = rs.getInt("Thread_NumberOfViews");
				result.add(new Thread(threadID, forumID, title, author, lastUpdator, creationTime, lastUpdateTime,
						isSticky, isDeleted, numberOfViews));
				System.out.println(title);
			}
		}

		return result;
	}

	public ArrayList<Post> getPostsByThreadID(int threadID)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL get_post_list_by_threadID(?)";
		ArrayList<Post> result = new ArrayList<Post>();
		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, threadID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Integer postID = rs.getInt("PostID");
				Integer replyToPost = rs.getInt("ReplyToPost");
				String author = rs.getString("Post_Author");
				String content = rs.getString("Post_Content");
				Timestamp creationTime = rs.getTimestamp("Post_CreationTime");
				Timestamp lastModificationTime = rs.getTimestamp("Post_LastModificationTime");
				boolean isDeleted = rs.getBoolean("Post_IsDeleted");
				result.add(new Post(postID, threadID, replyToPost, author, content, creationTime, lastModificationTime,
						isDeleted));
				System.out.println(content);
			}
		}

		return result;
	}

	public Thread getThreadByID(int threadID)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL get_thread_by_id(?)";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, threadID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				// int threadID = rs.getInt("ThreadID");
				Integer forumID = rs.getInt("ForumID");
				String title = rs.getString("Thread_Title");
				String author = rs.getString("Thread_Author");
				String lastUpdator = rs.getString("Thread_LastUpdator");
				Timestamp creationTime = rs.getTimestamp("Thread_CreationTime");
				Timestamp lastUpdateTime = rs.getTimestamp("Thread_LastUpdateTime");
				boolean isSticky = rs.getBoolean("Thread_IsSticky");
				boolean isDeleted = rs.getBoolean("Thread_IsDeleted");
				int numberOfViews = rs.getInt("Thread_NumberOfViews");
				return new Thread(threadID, forumID, title, author, lastUpdator, creationTime, lastUpdateTime, isSticky,
						isDeleted, numberOfViews);
			}
			throw new NoSuchElementException();
		}
	}

	public Post getPostByID(int postID)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String sql = "CALL get_post_by_id(?)";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, postID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				postID = rs.getInt("PostID");
				int threadID = rs.getInt("ThreadID");
				Integer replyToPost = rs.getInt("ReplyToPost");
				String author = rs.getString("Post_Author");
				String content = rs.getString("Post_Content");
				Timestamp creationTime = rs.getTimestamp("Post_CreationTime");
				Timestamp lastModificationTime = rs.getTimestamp("Post_LastModificationTime");
				boolean isDeleted = rs.getBoolean("Post_IsDeleted");
				return new Post(postID, threadID, replyToPost, author, content, creationTime, lastModificationTime,
						isDeleted);
			}
			throw new NoSuchElementException();
		}
	}

	public List<Forum> getFavoriteForumsByUsername(String username)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ArrayList<Forum> result = new ArrayList<>();
		String sql = "CALL get_FavoriteForums_by_UserName(?)";
		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int forumID = rs.getInt("ForumID");
				Forum forum = this.getForumByID(forumID);
				result.add(forum);
			}
		}
		return result;
	}

	public boolean isForumFavoriteWithUser(int forumID, String username)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String sql = "{ ? = CALL is_forum_favorite_with_user(?,?) }";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(2, forumID);
			statement.setString(3, username);
			statement.registerOutParameter(1, java.sql.Types.BOOLEAN);

			statement.execute();
			return statement.getBoolean(1);

		}

	}
	public void addForumFavoriteWithUser(int forumID, String username)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String sql = "SELECT add_forum_favorite_with_user(?,?)";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, forumID);
			statement.setString(2, username);


			statement.execute();

		}

	}
	public void removeForumFavoriteWithUser(int forumID, String username)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String sql = "CALL remove_forum_favorite_with_user(?,?)";

		try (Connection connection = this.getConnection(); CallableStatement statement = connection.prepareCall(sql)) {
			statement.setInt(1, forumID);
			statement.setString(2, username);

			statement.execute();

		}

	}
}
