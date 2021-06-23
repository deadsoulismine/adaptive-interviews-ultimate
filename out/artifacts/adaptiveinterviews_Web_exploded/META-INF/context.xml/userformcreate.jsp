<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Добавление пользователя</title>
    <style type="text/css">
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h3>Добавление нового пользователя</h3>
<form:form method="POST"
           action="add" modelAttribute="userFormCreate">
    <table>
        <tr>
            <td><form:label path="name">Имя*</form:label></td>
            <td><form:input path="name" type="text"/></td>
            <td><form:errors path="name" cssClass="error"/></td>
        </tr>
        <tr>
            <td><form:label path="email">Email*</form:label></td>
            <td><form:input path="email" type="text"/></td>
            <td><form:errors path="email" cssClass="error"/></td>
        </tr>
        <tr>
            <td><form:label path="username">Логин*</form:label></td>
            <td><form:input path="username" type="text"/></td>
            <td><form:errors path="username" cssClass="error"/></td>
        </tr>
        <tr>
            <td><form:label path="password">Пароль*</form:label></td>
            <td><form:input path="password" type="text"/></td>
            <td><form:errors path="password" cssClass="error"/></td>
        </tr>
        <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
            <tr>
                <td><form:label path="role">Права</form:label></td>
                <td><form:select path="role">
                    <form:option value="USER"/>
                    <form:option value="ADMIN"/>
                </form:select>
                </td>
            </tr>
        </c:if>
        <tr>
            <td><input type="submit" value="Сохранить"/></td>
            </td>
        </tr>
    </table>
</form:form>
<input type="button" onclick="location.href='/users'" value="Отменить">
</body>
</html>