<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

	<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<!--
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>

Time: <span id="foo"></span>

	<br><br>
	<button onclick="start()">Start</button>

	<script type="text/javascript">
	function start() {

		var eventSource = new EventSource("http://localhost:8080/jobs");

		eventSource.onmessage = function(event) {

			document.getElementById('foo').innerHTML = event.data;

		};

	}
	</script>

</body>

</html>
