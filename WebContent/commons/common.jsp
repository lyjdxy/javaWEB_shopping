<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   

<!-- base标签设置jsp页面的基本路径，用于设置完整的绝对路径 --> 
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath}/">
<!-- 解析为：<base href="http:			   //localhost						   :8080							  /1.9_javaWEB_bookstore			/"> -->
