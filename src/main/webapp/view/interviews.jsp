<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Беседы</title>
</head>
<body>

<h2>Беседы</h2>
<input type="button" class="floated" onclick="location.href='/employees'" value="На главную">
<input type="button" class="floated" onclick="location.href='/departments'" value="Отделы">
<input type="button" class="floated" onclick="location.href='/users'" value="Пользователи">
<input type="button" class="floated" onclick="location.href='/logout'" value="Выйти из системы">
<h2></h2>
<input type="button" class="floated" onclick="location.href='/interviews/coming'" value="Предстоящие">
<input type="button" class="floated" onclick="location.href='/interviews/review'" value="Без отзыва">
<input type="button" class="floated" onclick="location.href='/interviews/all'" value="Все беседы">
<c:if test="${pageContext.request.isUserInRole('ADMIN')}">
    <input type="button" class="floated" onclick="location.href='/interviews/create'" value="Новая беседа">
</c:if>
<h2></h2>
<form action="/interviews/lastName" style="display:inline;" method="GET">
    Фамилия: <input type="text" name="lastName"/>
    <input type="submit" value="Найти"/>
</form>
<h2></h2>
<form action="/interviews/listByDate" style="display:inline;" method="GET">
    Дата: <input type="date" name="listByDate"/>
    <input type="submit" value="Найти"/>
</form>
<h2></h2>
<table>
    <style type="text/css">
        TABLE {
            border-collapse: collapse;
        }

        TD, TH {
            padding: 3px;
            border: 1px solid black;
        }

        TH {
            background: #b0e0e6;
        }
    </style>
    <tr>
        <th>Дата</th>
        <th>Название</th>
        <th>Сотрудник</th>
        <th>Пользователь</th>
        <th>Отзыв</th>
        <th></th>
        <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
            <th></th>
        </c:if>
    </tr>
    <c:forEach var="interview" items="${interviews}">
        <tr style="background-color:${empty interview.description ? 'lightsalmon' : 'PaleGreen'}">
            <td>${interview.date}</td>
            <td>${interview.name}</td>
            <td>${interview.employee.firstName} ${interview.employee.lastName}</td>
            <td>${interview.user.name}</td>
            <td><c:out value="${empty interview.description ? 'НЕТ' : 'ЕСТЬ'}"/></td>
            <td>
                <c:choose>
                <c:when test="${pageContext.request.isUserInRole('ADMIN')}">
                <input type="button" onclick="location.href='/interviews/edit/${interview.id}'"
                       value="Редактировать анкету">
                </c:when>
                <c:otherwise>
                <input type="button" onclick="location.href='/interviews/edit/${interview.id}'" value="Оставить отзыв">
                </c:otherwise>
                </c:choose>
                <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
            <td><input type="button" onclick="location.href='/interviews/delete/${interview.id}'"
                       value="Удалить"></td>
            </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>