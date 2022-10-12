<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meals List</title>
    <style>
        .red {
            color: red;
        }

        .green {
            color: green;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="meals">Home</a></h2>
    <hr>
    <h1>Meals</h1>
    <p>
        <a href="meals?action=add">
            <button type="submit">Add</button>
        </a>
    </p>
    <table border="1" cellpadding="8" cellspacing="0" title="Meals list">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess?"red":"green"}">
                <td>${meal.dateTime.format(formatter)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
                <td><a href="meals?id=${meal.id}&action=edit">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
