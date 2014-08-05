<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  ~ Copyright (C) Denis Pavlov 2013 www.inspire-software.com
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~         http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<html>
<head><title>Items</title></head>
<body>
  <h3>Items:</h3>
  <c:forEach var="item" items="${items}">
	<div>${item.articleNo} @ ${item.price}, qty: ${item.quantity}  <a href="${pageContext.request.contextPath}/cart/${pk}/removefromcart/${item.articleNo}">[-] remove</a> <a href="${pageContext.request.contextPath}/cart/${pk}/addtocart/${item.articleNo}">[+] add</a></div>
  </c:forEach>
  <a href="${pageContext.request.contextPath}/cart/${pk}/addtocart/${add}">add "${add}"</a>
</body>
</html>