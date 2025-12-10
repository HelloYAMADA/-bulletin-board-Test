<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Beans.thcon_beans"%>
<%@page import="Beans.Mem_Beans" %>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="ja">

<head>
	<meta charset="UTF-8" />
	<title>☆KCS専用オンライン掲示板☆</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/mem_menu.css?v=<%= System.currentTimeMillis() %>">
</head>

<body>

	<header>
		<div class="header-title">☆KCS専用オンライン掲示板☆</div>
		<div class="window-buttons">
			<button id="fullscreen-btn" class="window-btn">🗖</button>
			<button id="exit-fullscreen-btn" class="window-btn">🗕</button>
			<button id="close-btn" class="close-button">×</button>
		</div>
	</header>

	<script>
		document.getElementById("fullscreen-btn").addEventListener("click", () => {
			if (!document.fullscreenElement) {
				document.documentElement.requestFullscreen().catch(err => {
					alert(`フルスクリーンにできませんでした: ${err.message}`);
				});
			}
		});

		document.getElementById("exit-fullscreen-btn").addEventListener("click", () => {
			if (document.fullscreenElement) {
				document.exitFullscreen();
			}
		});

		document.getElementById("close-btn").addEventListener("click", () => {
			window.close();

			setTimeout(() => {
				if (!window.closed) {
					alert("このタブはブラウザの制限により閉じられません。手動で閉じてください。");
				}
			}, 200);
		});
	</script>
	<div class="top-bar">
		<div>
			<strong>～メニュー画面～</strong>
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
		<a href="Menu_move_servlet">メニュー画面</a>
		<a href="Mem_menu_servlet?action=create">スレッド作成</a>
		<a href="Memth_cho_servlet?action=view">スレッド閲覧</a>
		<a href="mem_list.jsp">会員情報削除</a>
		<a href="question.jsp">お問い合わせ</a>
		<a href="memng_opt.jsp">NGワード設定</a>
		<a href="mail_conf.jsp	">メール</a>
	</nav>

	<div class="content">
		<div class="background-box">
	    <a href="Mem_menu_servlet?action=create" class="menu-btn">
	        <h2>☆スレッドを作成☆</h2>
	    </a>

	    <a href="memth_cho.jsp" class="menu-btn">
	        <h2>☆スレッドを閲覧☆</h2>
	    </a>

	    <div class="message-box">
	        ～管理者からのお知らせ～
	    </div>

	</div>
	</div>
</body>

</html>