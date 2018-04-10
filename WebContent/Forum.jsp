<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="edu.neu.ccs.community.*, java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${forumName }</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="./css/fresh-bootstrap-table.css">
</head>
<body background="./image/forum.jpg">
	<div>
		<header> <nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div id="navbar" class="collapse navbar-collapse"
				aria-expanded="true">
				<form class="navbar-form navbar-left" method="get" action="home">
					<div class="form-group">
						<input type="text" name="search" value="${forumName }"
							class="form-control" placeholder="Seaching for forum ...">
					</div>
					<button class="btn btn-primary" type="submit">Go!</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<%=request.getContextPath()%>/home">Home</a></li>
					<%
						if (request.getAttribute("username") == null) {
					%>
					<li><a href="/Community/login">Log in</a></li>
					<li><a href="/Community/register">Sign up</a></li>
					<%
						} else {
					%>
					<li><a
						href="profile?user=<%=request.getAttribute("username")%>">Hi
							${username}</a></li>
					<li><a href="/Community/login">Log out</a></li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
		</nav> </header>
	</div>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<!-- body -->
	<div class="row">
		<div class="col-md-3">
			<h1 align="middle">
				<font color="white">${forumName }</font>
			</h1>
			<hr />
			<p align="middle">
				<font color="white">${description}</font>
			</p>
			<div align="middle">
				<button data-toggle="modal" data-target="#edit"
					<%if (!request.getAttribute("owner").equals(request.getAttribute("username"))
					&& !(boolean) request.getAttribute("isAdmin")) {%>
					style="visibility: hidden;" <%}%> class="btn btn-primary">Edit</button>
				<button data-toggle="modal" data-target="#delete"
					<%if (!(boolean) request.getAttribute("isAdmin")) {%>
					style="visibility: hidden;" <%}%> class="btn btn-danger">Delete</button>
			</div>
			<br />
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<div class="panel panel-warning">
					<div class="panel-heading">
						<h3 class="text-center panel-title">Related Sub Forums</h3>
					</div>
					<div class="panel-body">
						<%
							if (request.getAttribute("subforums") == null) {
						%>
						<p>No related sub forum is found</p>
						<%
							} else {
								List<Forum> forums = (List<Forum>) request.getAttribute("subforums");
								for (Forum forum : forums) {
						%>
						<a href="forum?id=<%=forum.getForumID()%>"><%=forum.getForumName()%></a>
						<hr />
						<%
							}
							}
						%>
					</div>
				</div>
				<div align="middle">
					<button type="button" class="btn btn-warning" data-toggle="modal"
						data-target="#create">Create Sub Forum</button>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="fresh-table">
				<table id="fresh-table" class="table">
					<thead>
						<tr>
							<th>Thread</th>
							<th>Initiators</th>
							<th>Create Date</th>
							<th>Last Update</th>
						</tr>
					</thead>
					<tbody>
						<%
							if (request.getAttribute("threadList") == null) {
						%>Error! You found the secreat place<%
							} else {
								List<edu.neu.ccs.community.Thread> threads = (List<edu.neu.ccs.community.Thread>) request
										.getAttribute("threadList");
								for (edu.neu.ccs.community.Thread thread : threads) {
						%>
						<tr>
							<td><strong> <a
									href="/Community/thread?id=<%=thread.getThreadID()%>"><%=thread.getTitle()%></a>
							</strong></td>
							<td><%=thread.getAuthor()%></td>
							<td><%=thread.getCreationTime()%></td>
							<td><%=thread.getLastUpdateTime()%> by <%=thread.getLastUpdator()%>
							</td>
						<tr>
							<%
								}
								}
							%>
						
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-md-3">
			<hr />
			<p>
				<strong><font color="white"> Owner: ${owner}</font></strong>
			</p>
			<p>
				<strong><font color="white"> Created at:
						${creationTime}</font></strong>
			</p>
			<p>
				<strong><font color="white"> Total Threads: <%
					if (request.getAttribute("threadList") == null) {

					} else {
						List<edu.neu.ccs.community.Thread> threads = (List<edu.neu.ccs.community.Thread>) request
								.getAttribute("threadList");
				%><%=threads.size()%> <%
 	}
 %>
				</font></strong>
			</p>
			<p>
				<strong><font color="white"> Recent Post Time:
						${lastPostTime}</font></strong>
			</p>
			<hr />
			<div>
				<%
					DataAccessObject dao = new DataAccessObject();
					if (!dao.isForumFavoriteWithUser(Integer.valueOf(request.getParameter("id")),
							String.valueOf(request.getAttribute("username")))) {
				%>
				<form method="post" action="addFavorite">
					<div align="middle">
						<input type="hidden" name="forumID"
							value="<%=request.getParameter("id")%>" />
						<button type="submit" class="btn btn-warning ">
							<span class="glyphicon glyphicon-star"></span> Add to My Favorite
						</button>
					</div>
				</form>
				<%
					} else {
				%>
				<form method="post" action="removeFavorite">
					<div align="middle">
						<input type="hidden" name="forumID"
							value="<%=request.getParameter("id")%>" />
						<button type="submit" class="btn btn-danger">
							<span class="glyphicon glyphicon-star-empty"></span> Remove from
							My Favorite
						</button>
					</div>
				</form>
				<%
					}
				%>
			</div>
		</div>
	</div>
	<form class="container" method="post" action="createThread">
		<input type="hidden" name="forumID"
			value="<%=request.getParameter("id")%>" />
		<h3>
			<font color="white">New Thread</font>
		</h3>
		<label class="col-sm-3 control-label"><font color="white">Topic</font></label>
		<input id="title" name="title" type="text" class="form-control"
			placeholder="The topic of the thread" required /> <label
			class="col-sm-3 control-label"><font color="white">Content</font></label>
		<textarea id="content" name="content" class="form-control"
			maxlength="400" placeholder="The content of the first post"></textarea>
		<br />
		<button class="btn btn-warning pull-right">Post</button>
	</form>
	<!-- Edit Modal -->
	<div class="modal fade" id="edit">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Edit Forum</h4>
				</div>
				<div class="modal-body">
					<form class="form form-horizontal" method="post"
						action="updateForum">
						<input type="hidden" id="owner" name="owner" value="${owner}" />
						<input type="hidden" id="forumID" name="forumID"
							value="<%=request.getParameter("id")%>" />
						<div class="form-group">
							<label class="col-sm-3 control-label">Forum Name</label>
							<div class="col-sm-8">
								<input id="name" name="name" type="text" class="form-control"
									value="${forumName}" placeholder="Forum Name" required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Category</label>
							<div class="col-sm-8">
								<input id="catagory" name="catagory" type="input"
									class="form-control" maxlength="10" value="${category}"
									placeholder="Category" required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Description</label>
							<div class="col-sm-8">
								<textarea id="description" name="description"
									class="form-control" maxlength="400"
									placeholder="A short discription of this forum">${description}</textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success" id="ok">Confirm</button>
							<button type="button" data-dismiss="modal" class="btn btn-danger"
								id="cancelAdd">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- Delete Modal-->
	<div class="modal fade" id="delete">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Delete Forum</h4>
				</div>
				<div class="modal-body">
					<form class="form form-horizontal" method="post"
						action="deleteForum">
						<input type="hidden" name="forumID"
							value="<%=request.getParameter("id")%>" />
						<h3>Are you sure to delete this forum?</h3>
						<div class="modal-footer">
							<button type="submit" class="btn btn-danger" id="ok">Delete</button>
							<button type="button" data-dismiss="modal"
								class="btn btn-success" id="cancelAdd">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- Create -->
	<div class="modal fade" id="create">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Create New Forum</h4>
				</div>
				<div class="modal-body">
					<form class="form form-horizontal" method="post"
						action="createForum">
						<input type="hidden" name="parentID"
							value="<%=request.getParameter("id")%>">
						<div class="form-group">
							<label class="col-sm-3 control-label">Forum Name</label>
							<div class="col-sm-8">
								<input id="name" name="name" type="text" class="form-control"
									placeholder="Forum Name" required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Category</label>
							<div class="col-sm-8">
								<input id="catagory" name="catagory" type="input"
									class="form-control" maxlength="20" placeholder="Category"
									value="${category}" required />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Description</label>
							<div class="col-sm-8">
								<textarea id="description" name="description"
									class="form-control" maxlength="400"
									placeholder="A short discription of this forum" required></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success" id="ok">Create</button>
							<button type="button" data-dismiss="modal" class="btn btn-danger"
								id="cancelAdd">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>