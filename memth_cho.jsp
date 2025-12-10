<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="Beans.Mem_Beans" %>
<%@ page import="Beans.thcon_beans"%>
<%@ page import="java.util.List,Beans.thinf_beans"%>
<%@ page import="java.util.List,Beans.gtitle_beans,Beans.genre_beans,Beans.thsear_beans"%>
<%@page import="Beans.Mem_Beans" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>☆KCS専用オンライン掲示板☆</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/memth_cho.css?v=<%= System.currentTimeMillis() %>">

</head>

<%
List<thcon_beans> THCON = (List<thcon_beans>) request.getAttribute("thcon");
String THREAD_TITLE = (String) request.getParameter("THREAD_TITLE");
String flag = (String) request.getAttribute("flag");
List<thinf_beans> thinf = (List<thinf_beans>)request.getAttribute("thinf");

List<gtitle_beans> gameList = (List<gtitle_beans>) request.getAttribute("gameList");
List<genre_beans> genreList = (List<genre_beans>) request.getAttribute("genreList");
List<thsear_beans> Search = (List<thsear_beans>) request.getAttribute("Search");
%>

<body>
<script>
    let flag = "<%= flag %>";
</script>

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
			<strong>～スレッド閲覧画面～</strong>
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
			href="Mem_menu_servlet?action=create">スレッド作成</a> <a
			href="Memth_cho_servlet?action=view">スレッド閲覧</a> <a
			href="/WEB-INF/mem_list.jsp">会員情報削除</a> <a
			href="/WEB-INF/question.jsp">お問い合わせ</a> <a
			href="/WEB-INF/memng_opt.jsp">NGワード設定</a> <a
			href="/WEB-INF/mail_conf.jsp">メール</a>
	</nav>
	<style>
.comment-box {
	margin-bottom: 20px;
}

.comment {
	padding: 8px;
	margin-bottom: 10px;
	background: #f5f5f5;
	border-radius: 6px;
}
</style>
	<style>
.link-like {
	color: blue;
	text-decoration: underline;
	cursor: pointer;
}

.link-like:hover {
	color: #0000cc;
}
</style>


	<main>
		<div class="sidebar">
			<h3>今人気のスレッド</h3>
			<ol>
				<li><form id="myForm1" method="get" action="Memth_view_servlet">
					<input type="hidden" value="<%=(thinf.get(0)).getThred_title() %>" name="THREAD_TITLE">


				</form>

				<span class="link-like" onclick="submitMyForm1();"> <%=(thinf.get(0)).getThred_title() %> </span>
				<script>
function submitMyForm1() {
    document.getElementById("myForm1").submit();
}
</script></li>
				<li><form id="myForm2" method="get" action="Memth_view_servlet">
					<input type="hidden" value="<%=(thinf.get(1)).getThred_title() %>"
						name="THREAD_TITLE">


				</form>

				<span class="link-like" onclick="submitMyForm2();"> <%=(thinf.get(1)).getThred_title() %> </span>
				<script>
function submitMyForm2() {
    document.getElementById("myForm2").submit();
}
</script></li>
				<li><form id="myForm3" method="get" action="Memth_view_servlet">
					<input type="hidden" value="<%=(thinf.get(2)).getThred_title() %>" name="THREAD_TITLE">


				</form>

				<span class="link-like" onclick="submitMyForm3();"> <%=(thinf.get(2)).getThred_title() %> </span>
				<script>
function submitMyForm3() {
    document.getElementById("myForm3").submit();
}
</script></li>

			</ol>

			<h3>スレッド検索</h3>
	<label>～検索条件選択～</label><br>
<style>
.radio-group {
    display: flex;
    gap: 20px;       /* AND と OR の間のスペース */
    align-items: center;
}
</style>
<div class="radio-group">
    <label>ゲームタイトル検索<input type="radio" name="condition" value="TITLE" checked></label>
    <label>ゲームジャンル検索<input type="radio" name="condition" value="GENRE"></label>
</div>


<!-- ▼ タイトル検索フォーム -->
<div id="titleForm">
    <form method="get" action="Memth_sear_servlet">
        <div class="thread-form-row">
            <label for="gameTitle">ゲームタイトル</label>
            <select id="gameTitle" name="gameTitle" class="same-width">
                <option value="" selected>--未選択--</option>
                <% if (gameList != null) {
                    for (gtitle_beans t : gameList) { %>
                        <option value="<%=t.getGame_title()%>">
                            <%=t.getGame_title()%>
                        </option>
                <%  }
                   }
                %>
            </select>
        </div>
        <br>
        <button type="submit" class="thread-search-btn">検索</button>
    </form>
</div>

<!-- ▼ ジャンル検索フォーム（最初は非表示） -->
<div id="genreForm" style="display:none;">
    <form method="get" action="Memth_sear_servlet">
        <div class="thread-form-row">
            <label for="genre">ゲームジャンル</label>
            <select id="genre" name="genre" class="same-width">
                <option value="" selected>--未選択--</option>
                <% if (genreList != null) {
                    for (genre_beans g : genreList) { %>
                        <option value="<%=g.getGenre_title()%>">
                            <%=g.getGenre_title()%>
                        </option>
                <%  }
                   }
                %>
            </select>
        </div>
        <br>
        <button type="submit" class="thread-search-btn">検索</button>
    </form>
</div>




			<h3>スレッド一覧</h3>
			<%if(Search == null){ %>
				スレッド検索を行ってください。<br>
				検索したいスレッドが見つからない場合は<br>
				ゲームタイトルとゲームジャンルを切り替えて検索してみてください
			<%}else{ %>
			<ul>
			<%
			String Form = "Form";
			String submitForm = "submitForm";
			String FormID = "";
			String submitID = "";
			int num = 0;
			for(thsear_beans bean : Search) {
				FormID = Form + num;
				submitID = submitForm + num;
			%>
				<li><form id="<%=FormID %>"  method="get" action="Memth_view_servlet">
					<input type="hidden" value="<%=bean.getTh_name() %>" name="THREAD_TITLE">


				</form>

				<span class="link-like" onclick="<%=submitID%>();"> <%=bean.getTh_name() %> </span>
				<script>
function <%=submitID%>() {
    document.getElementById(<%=FormID %>).submit();
}
</script></li>
				<%
				num++;
				}
			}
				%>

			</ul>
		</div>

		<div class="content">
			<div class="thread-header">
				<div class="viewer">現在閲覧中：0人</div>
				<button class="update-btn">スレッド更新</button>
			</div>

			<div class="thread-display">
				<%
				if (THCON != null) {
					for (thcon_beans thcon : THCON) {
						
				%>
				<div class="comment-box">
					<div class="comment"><%=thcon.getCom_id()%> : <%=thcon.getCom()%></div>
				</div>
				<%
				} // for
				} else {
				%>
				閲覧するスレッドを選択してください。
				<%
				} // if
				%>
			</div>

			<div class="text-area">
			<textarea id="commentText" maxlength="300"
						placeholder="テキストエリア（300文字まで）"></textarea>
				<form id="postForm" method="POST" action="Memth_insert_servlet">
					<input type="hidden" name="COMMENT" id="hiddenComment">
					<input type="hidden" name="THREAD_TITLE" value="<%= THREAD_TITLE%>">
					<% if(THCON != null && !THCON.isEmpty()) { %>
    				<input type="hidden" name="THREAD_ID" value="<%= THCON.get(0).getThread_id() %>">
					<% } %>
				</form>
				<button class="post-btn" onclick="submitComment()">投稿</button>
			</div>
			<script>
function submitComment() {
    // textarea の内容を hidden にコピー
    document.getElementById("hiddenComment").value =
        document.getElementById("commentText").value;

    // form 送信
    document.getElementById("postForm").submit();
}
</script>

			<script>
    document.querySelector('.post-btn').addEventListener('click', function(event) {
        event.preventDefault();

        const commentText = document.getElementById("commentText").value;

        if (commentText.trim() === "") {
            alert("コメントを入力してください。");
            return;
        }

        

        document.getElementById("postForm").submit();
    });
</script>
<script>
window.onload = function() {
    // ページが読み込まれた瞬間に実行される処理
    setTimeout(function() {
    	if(flag == "2"){
    		alert("コメントが正常に投稿されないか、スレッドの投稿制限数に達しています");
    		System.out.println("学籍番号か投稿制限数");
    		return
    	}else if(flag == "3"){
    		alert("スレッドの更新が正常に行われませんでした");
    		return
    	}
    }, 300); // ← 3000ミリ秒＝3秒
	
};
</script>
<script>
document.querySelectorAll('input[name="condition"]').forEach(radio => {
    radio.addEventListener('change', () => {
        const value = document.querySelector('input[name="condition"]:checked').value;

        if (value === "TITLE") {
            document.getElementById("titleForm").style.display = "block";
            document.getElementById("genreForm").style.display = "none";
        } else {
            document.getElementById("titleForm").style.display = "none";
            document.getElementById("genreForm").style.display = "block";
        }
    });
});
</script>


		</div>
	</main>
</body>
</html>
