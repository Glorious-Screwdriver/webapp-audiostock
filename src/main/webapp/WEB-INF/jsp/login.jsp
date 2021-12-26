<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>login</title>
    <style>
        body{
            font-family: Cambria, "Hoefler Text", "Liberation Serif", Times, "Times New Roman", "serif";
        }
        *,
        *:before,
        *:after{
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }
        body{
            background-color: #FFF;
            color:#000000
        }
        form{
            height: 520px;
            width: 400px;
            background-color: rgba(255,255,255,0.13);
            position: absolute;
            transform: translate(-50%,-50%);
            top: 50%;
            left: 50%;
            border-radius: 10px;
            backdrop-filter: blur(10px);
            border: 2px solid rgba(0,0,0,0.1);
            box-shadow: 0 0 40px rgba(8,7,16,0.6);
            padding: 50px 35px;
        }
        form *{
            font-family: 'Poppins',sans-serif;
            color: #000;
            letter-spacing: 0.5px;
            outline: none;
            border: none;
        }
        button{
            margin-top: 50px;
            width: 100%;
            background-color: #E1E1E1;
            color: #464646;
            padding: 15px 0;
            font-size: 18px;
            font-weight: 600;
            border-radius: 5px;
            cursor: pointer;
        }

        form h3{
            font-size: 32px;
            font-weight: 500;
            line-height: 42px;
            text-align: center;
        }
        label{
            display: block;
            margin-top: 30px;
            font-size: 16px;
            font-weight: 500;
        }
        input{
            display: block;
            height: 50px;
            width: 100%;
            background-color: rgba(0,0,0,0.07);
            border-radius: 3px;
            padding: 0 10px;
            margin-top: 8px;
            font-size: 14px;
            font-weight: 300;
        }
        ::placeholder{
            color: #B1B1B1;
        }
    </style>
</head>

<body>
<form id="form1" name="form1" method="post">
    <h3>WYM</h3>
    <label for="textfield">логин:</label>
    <input type="text" placeholder="abobus" name="textfield" id="textfield">
    <label for="password">пароль:</label>
    <input type="password" placeholder="*****" name="password" id="password">
    <button>Отправить</button>
</form>
</body>
</html>
