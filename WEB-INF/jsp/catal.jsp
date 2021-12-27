<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>main</title>
    <link rel="stylesheet" href="../../css/main.css">
</head>
<body>
<div class="container">
    <%@include file="guest-header.jsp"%>
    <section class="catalogue">
        <ul>
            <li>
                <div class="track">
                    <div>
                        <a>
                            <img src="#">
                        </a>
                    </div>
                    <div class="info">
                        <a>Название</a>
                        <a>Автор</a>
                    </div>
                    <div class="audio">
                        <audio controls src="#">audio is not supported</audio>
                        <p>джаз/грусть</p>
                    </div>
                    <div class = "buttons">
                        <button>star</button>
                        <button>cart</button>
                    </div>
                </div>
            </li>
        </ul>
    </section>
</div>
</body>
</html>
