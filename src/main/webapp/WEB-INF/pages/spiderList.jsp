<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 16/7/14
  Time: 下午12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>爬虫列表</title>
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">
    <!--dropzone css-->
    <link href="${pageContext.request.contextPath}/adminex/js/dropzone/css/dropzone.css" rel="stylesheet"/>

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
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            爬虫实例查询
                        </div>
                        <form action="${pageContext.request.contextPath}/spider/toSpiderListPage.do" method="post">
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-lg-4 form-group">
                                        <label class="control-label">爬虫名称</label>
                                        <input type="text" name='spiderName' value="spiderName" class="form-control" placeholder="爬虫名称">
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <button type="submit" class="btn btn-primary">查询</button>
                            </div>
                        </form>
                    </div>


                </div>
            </div>

            <div class="row" style="height:70%;">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-body" style="height:90%;overflow-y:scroll;">

                            <table class="table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th style="">
                                        <div class="th-inner ">序号</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">爬虫名称</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">起始URL</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">分页URL</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">列表连接Xpath</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">标题Xpath</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">内容Xpath</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">操作</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${spiderList}" var="spider" varStatus="status">>
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td>${spider.name}</td>
                                        <td>${spider.startUrl}</td>
                                        <td>${spider.pageUrlXpath}</td>
                                        <td>${spider.fetchUrlXpath}</td>
                                        <td>${spider.titleXpath}</td>
                                        <td>${spider.contentXpath}</td>
                                        <td><a href="${pageContext.request.contextPath}/spider/toEditSpiderPage.do?id=${spider.id}">
                                            <button class="btn btn-primary">编辑</button>
                                        </a>&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/spider/deleteSpider.do?id=${spider.id}">
                                            <button class="btn btn-primary">删除</button>
                                        </a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
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

<script type="text/javascript"
        src="${pageContext.request.contextPath}/jqPaginator/dist/1.2.0/jqPaginator.min.js"></script>
</body>

</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>