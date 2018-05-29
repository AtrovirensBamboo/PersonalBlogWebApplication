<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 89288
  Date: 2018/5/26
  Time: 8:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <script type="text/javascript" src="/javascript/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="/javascript/typelist.js"></script>
    <title>文章分类管理</title>
</head>
<body>
    <table>
        <thead>
            <tr>
                <th><label for="select-all"> </label><input id="select-all" type="checkbox"/></th>
                <th>排列顺序</th>
                <th>分类</th>
            </tr>
        </thead>
        <tbody id="type-list">
            <c:forEach var="type" items="${typeList}">
                <tr>
                    <td><label for="select"> </label><input id="select" type="checkbox" value="${type.id}"/></td>
                    <td>
                        <label for="sort"> </label>
                            <input id="sort" class="verify" type="text" value="${type.sort}"/>
                    </td>
                    <td>
                        <label for="name"> </label>
                            <input id="name" class="verify" type="text" value="${type.name}"/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div>
        <button id="add-type">添加</button>
        <button id="save-type">保存</button>
    </div>
</body>
</html>
