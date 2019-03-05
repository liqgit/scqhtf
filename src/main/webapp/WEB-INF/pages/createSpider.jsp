<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 16/7/14
  Time: 下午12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>爬虫新建</title>
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">

</head>
<body class="sticky-header">


<section>
    <%@include file="menu.jsp" %>
    <!-- main content start-->
    <div class="main-content">
        <%@include file="header.jsp" %>

        <!--body wrapper start-->
        <div class="wrapper">
            <div class="row">
                <div class="col-md-12">
                    <section class="panel">
                        <header class="panel-heading">
                            爬虫新建
                        </header>
                        <div class="panel-body">
                            <form role="form" action="${pageContext.request.contextPath}/spider/createAndEditSpider.do"
                                  method="post">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">爬虫名称</label>
                                    <input type="text" class="form-control" id="exampleInputEmail1" name="name"
                                           placeholder="爬虫名称" value="${spider.name}">
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputPassword2">起始URL</label>
                                    <input type="text" class="form-control" id="exampleInputPassword2" name="startUrl"
                                           placeholder="起始URL" value="${spider.startUrl}">
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputPassword3">标题Xpath</label>
                                    <input type="text" class="form-control" id="exampleInputPassword3" name="titleXpath"
                                           placeholder="标题Xpath" value="${spider.titleXpath}">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword6">列表链接Xpath</label>
                                    <input type="text" class="form-control" id="exampleInputPassword6" name="fetchUrlXpath"
                                           placeholder="列表链接Xpath" value="${spider.fetchUrlXpath}">
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputPassword4">分页Xpath</label>
                                    <input type="text" class="form-control" id="exampleInputPassword4"
                                           name="pageUrlXpath"
                                           placeholder="分页Xpath" value="${spider.pageUrlXpath}">
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputPassword5">内容Xpath</label>
                                    <input type="text" class="form-control" id="exampleInputPassword5"
                                           name="contentXpath"
                                           placeholder="内容Xpath" value="${spider.contentXpath}">
                                </div>
                                <button type="submit" class="btn btn-primary">确认</button>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
        </div>
</section>
<!-- main content end-->


</section>


<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>

<script src="${pageContext.request.contextPath}/adminex/js/jquery.isotope.js"></script>

<script src="${pageContext.request.contextPath}/adminex/js/scripts.js"></script>
</body>

</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
