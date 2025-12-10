<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,Beans.gtitle_beans,Beans.genre_beans"%>
<%@page import="Beans.Mem_Beans" %>

<%
List<gtitle_beans> gameList = (List<gtitle_beans>) request.getAttribute("gameList");
List<genre_beans> genreList = (List<genre_beans>) request.getAttribute("genreList");
%>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>☆KCS専用オンライン掲示板☆</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/memth.css" />

</head>

<body>

	<header>
		<div class="header-title">☆KCS専用オンライン掲示板☆</div>
		<div class="window-buttons">
			<button class="window-btn">🗖</button>
			<button class="window-btn">🗕</button>
			<button class="close-button">×</button>
		</div>
	</header>

	<div class="top-bar">
		<div>
			<strong>～スレッド作成画面～</strong>
		</div>
		<div>
			<span class="login-status"><%= ((Mem_Beans)session.getAttribute("user")).getUser_name() %> さんがログイン中</span>
			<button class="logout-btn" onclick="logout()">ログアウト</button>
			<script>
				function logout() {
					window.location.href = "Logout_servlet";
				}
			</script>
		</div>
	</div>

	<nav>
		<a href="Menu_move_servlet">メニュー画面</a> <a
			href="Mem_menu_servlet?action=update">スレッド作成</a> <a
			href="Memth_cho_servlet?action=view">スレッド閲覧</a> <a
			href="Mem_menu_servlet?action=list">会員情報削除</a> <a
			href="Mem_menu_servlet?action=question">お問い合わせ</a> <a
			href="Mem_menu_servlet?action=ngword">NGワード設定</a> <a
			href="Mem_menu_servlet?action=mail">メール</a>
	</nav>

	<div class="main-content">

		<form id="createForm" method="post" action="Memth_cre_servlet">

			<div class="form-row">
				<label for="title">スレッドタイトル</label> <input type="text" id="title"
					name="THREAD_TITLE" value=""maxlength="50" placeholder="テキストエリア（50文字まで）"> <span class="red-text"
					id="titleError">※タイトルが未入力です</span><br> <span class="red-text"
					id="ngWordError">※NGワードが含まれています</span>
			</div>

			<div class="form-row">
				<label for="gameTitle">ゲームタイトル</label> <select id="gameTitle"
					name="gameTitle">
					<option value="" selected>--未選択--</option>
					<%
					if (gameList != null) {
					%>
					<%
					for (gtitle_beans t : gameList) {
					%>
					<option value="<%=t.getGame_title()%>" name="GAME_TITLE">
						<%=t.getGame_title()%>
					</option>
					<%
					}
					%>
					<%
					}
					%>
				</select> <label for="genre" style="margin-left: 30px;">ゲームジャンル</label> <select
					id="genre" name="genre">
					<option value="" selected>--未選択--</option>
					<%
					if (genreList != null) {
					%>
					<%
					for (genre_beans g : genreList) {
					%>
					<option value="<%=g.getGenre_title()%>" name="GENRE_NAME">
						<%=g.getGenre_title()%>
					</option>
					<%
					}
					%>
					<%
					}
					%>
				</select>
			</div>

			<div class="textarea-box">
				<label for="content">本文</label>
				<textarea id="content" name="COMMENT" maxlength="300" placeholder="テキストエリア（300文字まで）"></textarea>
				<span class="red-text" id="contentError">※本文が未入力です</span>
			</div>

			<div class="button-area">
				<button class="cancel-btn" onclick="confirmCancel()">キャンセル</button>
				<button class="create-btn" onclick="confirmCreate()">スレッド作成</button>
			</div>
		</form>

		<script>
			function confirmCancel() {
				if (confirm('本当に戻りますか？')) {
					window.location.href = 'mem_menu.jsp';
				}
			}

			function confirmCreate() {
				document.getElementById('titleError').style.display = 'none';
				document.getElementById('contentError').style.display = 'none';

				let isValid = true;

				if (document.getElementById('title').value.trim() === "") {
					document.getElementById('titleError').style.display = 'inline';
					isValid = false;
				}

				if (document.getElementById('content').value.trim() === "") {
					document.getElementById('contentError').style.display = 'inline';
					isValid = false;
				}

				if (isValid) {
					if (confirm('作成しますか？')) {
						document.getElementById('createForm').submit();
					}
				}
			}
		</script>

	</div>
</body>

</html>
