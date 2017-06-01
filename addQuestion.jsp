<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="question">
<div>Question<input type="text" size ="150" name="q"></div>
<div>Option1: <input type="text" size ="100" name="opt1"></div>
<div>Option2: <input type="text" size ="100" name="opt2"></div>

<div>Option3: <input type="text" size ="100" name="opt3"></div>
<div>Option4: <input type="text" size ="100" name="opt4"></div>
<div>Answer: <input type="text" size ="50" name="answer"></div>
<div>Type: <input type="text" size ="50" name="type"></div>
<div>SubType: <input type="text" size ="50" name="subtype"></div>

<br>
<div><input type="submit" value="Add Question"></div>
</form>
</body>
</html>