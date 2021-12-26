<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>main</title>
    <style>
        *,
        *:before,
        *:after{
            padding: 0;
            margin: 0;
            box-sizing: border-box;
            font-family: 'Poppins',sans-serif;
            letter-spacing: 0.5px;
        }
        ul{
            list-style: none;
        }
        .container{
            box-shadow: 0 0 40px rgba(8,7,16,0.6);
            margin: auto;
            width:800px;
        }
        header{
            overflow: auto;
            height: 70px;
            border:2px solid #A3A3A3;
            border-radius: 4px;
            padding: 14px 10px;
            background-color: #E8E8E8;
            position: relative;
            display: block;
            vertical-align: middle;
        }
        header h1{
            display:inline-block;
        }
        header a{
            color:#969696;
            position: relative;
            display: inline-block;
            vertical-align: middle;
            float:right;
            margin-top:8px;
            margin-right:20px;
        }
        .searchbar{
            width: auto;
            height: 40px;
            border: 2px solid #A2A2A2;
            border-radius:4px;
        }
        .searchbar div{
            margin:3px;
        }
        .searchbar input{
            font-size:16px;
            padding:8px;
            height: 30px;
        }
        .searchbar .bar{
            float: left;
        }
        .searchbar .mood{
            float:right;
        }
        .searchbar .genre{
            float: right;
        }
        .catalogue .track{
            height: 84px;
            border: 2px solid #CBCBCB;
        }
        .catalogue .track img{
            float:left;
            width:80px;
            height:80px;
            border-radius: 40px;
            border: 1px solid #D3D3D3
        }
        .track .info{
            margin-left: 10px;
            float: left;
            width: 140px;
        }
        .track .info a{
            margin-top: 5px;
            display:block;
        }
        .track .info a:nth-of-type(2){
            color:#A0A0A0;
        }
        .track .audio{
            display: inline-block;
            margin: auto;
            width: 100px;
            height: 50px;
        }
        .track audio{
            margin-top:10px;
            width:350px;
            height: 40px;
        }
        .track .audio p{
            text-align:center;
        }
        .track .buttons{
            display:inline-block;
            float:right;
        }
        .track .buttons button{
            width: 40px;
            height:40px;
            margin-right: 20px;
            margin-top:20px;
            border-radius: 20px;
            cursor: pointer;
            border: 1px solid #DCDCDC;
        }
    </style>
</head>

<body>
<div class="container">
    <header>
        <h1>WYM</h1>
        <a href="http://127.0.0.1:8080/login">Вход</a>
        <a href="http://127.0.0.1:8080/register">Регистрация</a>
    </header>
    <section class="searchbar">
        <div class="bar">
            <input type="text" id="search" placeholder="поиск">
        </div>
        <div class="mood">
            <input type="text" id="mood" placeholder="настроение">
        </div>
        <div class="genre">
            <input type="text" id="genre" placeholder="жанр">
        </div>
    </section>
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