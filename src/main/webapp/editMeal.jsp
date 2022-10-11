<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${meal.description eq null?"Add meal":"Edit meal"}</title>
</head>
<body>
<section>
    <h2><a href="meals">Home</a></h2>
    <hr>
    <h1>${meal.description eq null?"Add meal":"Edit meal"}</h1>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        <p>
            <tr>
                <td>DateTime</td>
                <td><input required type="datetime-local" name="dateTime" size="50" value="${meal.dateTime}"></td>
            </tr>
        </p>
        <p>
            <tr>
                <td>Description</td>
                <td><input required type="text" name="description" size="50" value="${meal.description}"></td>
            </tr>
        </p>
        <p>
            <tr>
                <td>Calories</td>
                <td><input required type="number" name="calories" size="25" value="${meal.calories}"></td>
            </tr>
        </p>
        <hr>
        <button type="submit">Save</button>
        <button type="reset" onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>
