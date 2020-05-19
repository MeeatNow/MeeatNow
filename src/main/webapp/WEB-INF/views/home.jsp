<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>test page</title>
    <script src="https://code.jquery.com/jquery-2.2.1.js"></script>
</head>
<body>
Welcome, this is home
1. 멤버 insert
<form id="form-insert">
    email : <input type="text" name="email">
    name : <input type="text" name="name">
    <button type="button" id="submitBtn">제출</button>
</form>

2. 멤버 findAll
<button type="button" id="listBtn">유저목록</button>
<div id="listView">

</div>

3. 로비 findAll
<button type="button" id="lobyListBtn">리스트 목록</button>
<div id = "lobyListView">

</div>
</body>
</html>

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
    $('#submitBtn').on('click', function () {

        var data = {
            email: $('input[name=email]').val(),
            name: $('input[name=name]').val()
        };

        console.log("data: ", data);
        console.log(JSON.stringify(data));

        $.ajax({
            url: "https://meeatnow.herokuapp.com/user/insert",
            type: "POST",
            data: JSON.stringify(data),
            // dataType: 'json', --> 쓰면 안됨. 이거 쓰면 200이지만 error결과 실행함.
            contentType: 'application/json; charset=utf-8',


            beforeSend: function () {
                alert("submit_user()");
            },
            success: function (data) {
                alert(JSON.stringify(data) + '추가되었습니다.');
            },
            error: function (error) {
                alert("error: " + JSON.stringify(error));
            }
        })
    })

    $('#listBtn').on('click',function () {

        console.log("list버튼 눌렀습니다.");
        $.ajax({
            url: "https://meeatnow.herokuapp.com/user/findAll",
            type: "GET",
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded; charset=utf-8',
            // Origin: 'http://host.com',
            // Access-Control-Request-Method: 'POST',
            // crossDomain: true,
            headers : {
                // 'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
                // "accept": "application/json",
                // "Access-Control-Allow-Origin":"*"
            },
            success: function (data) {
                alert('success');
                $('#listView').html(JSON.stringify(data));
            },
            error: function (error) {
                alert("error: " + JSON.stringify(error));
            }

        })
    })

    $('#lobyListBtn').on('click',function () {

        console.log("lobyList버튼 눌렀습니다.");
        $.ajax({
            url: "https://meeatnow.herokuapp.com/loby/findAll",
            type: "GET",
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded; charset=utf-8',
            // Origin: 'http://host.com',
            // Access-Control-Request-Method: 'POST',
            // crossDomain: true,
            headers : {
                'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
                // "accept": "application/json",
                // "Access-Control-Allow-Origin":"*"
            },
            success: function (data) {
                alert('success');
                $('#lobyListView').html(JSON.stringify(data));
            },
            error: function (error) {
                alert("error: " + JSON.stringify(error));
            }

        })
    })

</script>