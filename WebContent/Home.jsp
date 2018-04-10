<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="edu.neu.ccs.community.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="./css/fresh-bootstrap-table.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/css/select2.min.css"
	rel="stylesheet" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/select2.min.js"></script>
</head>
<body background="./image/background.jpg">
	<div>
		<header> <nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div id="navbar" class="collapse navbar-collapse"
				aria-expanded="true">
				<ul class="nav navbar-nav navbar-right">
					<%
						if (request.getAttribute("username") == null) {
					%>
					<li><a href="/Community/login">Log in</a></li>
					<li><a href="/Community/register">Sign up</a></li>
					<%
						} else {
					%>
					<li><a href="profile?user=<%=request.getAttribute("username")%>">Hi ${username}</a></li>
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
	<br />
	<br />
	<div class="text-center">
		<img src="./image/title.png" alt="Mountain View">
	</div>
	<!-- Search -->
	<div class="container">
		<form class="form" method="get" action="home">
			<div class="col-lg-3"></div>
			<div class="col-lg-6">
				<div class="input-group">
					<input type="text" name="search" value="${search}"
						class="form-control" placeholder="Seaching for forum ...">
					<span class="input-group-btn">
						<button class="btn btn-primary btn-block" type="submit">Go!</button>
					</span>
				</div>
			</div>
		</form>
	</div>
	<br />
	<br />
	<div class=" container">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="fresh-table full-color-orange">
					<table id="fresh-table" class="table">
						<tbody>
							<%
								if (request.getAttribute("result") == null || request.getAttribute("search") == null
										|| request.getAttribute("search").equals("")) {
							%>
							<tr>
								<td class="text-center">
									<button type="button" class="btn btn-info" data-toggle="modal"
										data-target="#create">Create Forum</button>
								</td>
							</tr>
							<%
								} else if (request.getAttribute("result").equals(new ArrayList<Forum>())) {
							%>
							<tr>
								<td class="text-center"><strong>We can't find any
										forum related to '${search}'</strong></td>
							</tr>
							<tr>
								<td class="text-center">
									<button type="button" class="btn btn-info" data-toggle="modal"
										data-target="#create">Create Forum</button>
								</td>
							</tr>
							<%
								} else {
									List<Forum> forums = (List<Forum>) request.getAttribute("result");
									for (Forum forum : forums) {
										System.out.println(forum.getForumName());
							%>
							<tr>
								<td class="text-center"><strong><a
										href="/Community/Forum?id=<%=forum.getForumID()%>"><%=forum.getForumName()%></a></strong>
								</td>
								<td><%=forum.getDescription()%></td>
							<tr>
								<%
									}
									}
								%>
							
							<tr>
								<p align="middle">
									<font color="red">${message }</font>
								</p>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="create">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Create New Forum</h4>
				</div>
				<div class="modal-body">
					<form class="form form-horizontal" method="post"
						action="createForum">
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
									required />
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
						<div class="form-group">
							<label class="col-sm-3 control-label">Parent Forum</label>
							<div class="col-sm-8">
								<script type="text/javascript">
									$(document).ready(
											function() {
												$(".js-example-basic-single")
														.select2();
											});
								</script>

								<select class="js-example-basic-single">
									<option>No Parent Forum</option>
									<%
										if (request.getAttribute("forums") == null) {
									%>
									<%
										} else {
											List<Forum> forums = (List<Forum>) request.getAttribute("forums");
											for (Forum forum : forums) {
									%>
									<option value=<%=forum.getForumID()%>><%=forum.getForumName()%></option>
									<%
										}
										}
									%>
								</select>
								<button type="button" class="btn btn-default"
									data-toggle="tooltip" data-placement="bottom"
									title="Is this a sub forum of any others?">
									<span class="glyphicon glyphicon-question-sign"
										aria-hidden="true"> </span>
								</button>
								<script>
									$(document).ready(function() {
										$('[data-toggle="tooltip"]').tooltip();
									});
								</script>
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