<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 2017/9/19
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>动物疫病名录</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="keywords"
          content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">
    <!--common-->
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/fonts/css/font-awesome.min.css" rel="stylesheet">


    <!--pickers css-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/css/datepicker-custom.css"/>

    <link href="${pageContext.request.contextPath}/adminex/js/data-tables/DT_bootstrap.css" rel="stylesheet">
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
                        <%--<div class="panel-heading">
                            中华人民共和国进境动物检疫疫病名录
                        </div>--%>
                        <div class="panel-heading">
                            动物疫病名录
                        </div>
                        <%--@elvariable id="chineseStandardAnimalEpidemicItem" type="cn.com.eship.model.ChineseStandardAnimalEpidemicItem"--%>
                        <form:form commandName="chineseStandardAnimalEpidemicItem"
                                   action="${pageContext.request.contextPath}/epidemicItem/epidemicItemListPage.do">
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-lg-3 form-group">
                                        <label class="control-label">疫病名称</label>
                                        <form:input cssClass="form-control AQForm" path="diseaseNameCn" typeahead="" id="epidemicName"/>
                                    </div>
                                    <div class="col-lg-3 form-group">
                                        <label class="control-label" for="epidemicClass">类目</label>
                                        <form:select id='epidemicClass' class="form-control AQForm" path="diseaseClass">
                                            <form:option value="" label="--全部--"/>
                                            <form:option value="一类" >一类</form:option>
                                            <form:option value="二类">二类</form:option>
                                            <form:option value="其他类">其他类</form:option>
                                        </form:select>
                                    </div>
                                </div>
                                <div>
                                    <button type="submit" class="btn btn-primary">查询</button>
                                </div>
                            </div>
                        </form:form>

                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <section class="panel">
                                <header class="panel-heading">
                                    查询结果
                                    <span class="tools pull-right">
                                <%--<button class="btn btn-primary" onclick="exExcel()">--%>
                                <%--导出excel--%>
                                <%--</button>--%>
                            </span>
                                </header>
                                <div class="panel-body" style="display: block;">
                                    <table id="example" class="table table-bordered table-striped table-condensed"
                                           cellspacing="0" width="100%">
                                        <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>疫病中文名称</th>
                                            <th>疫病英文名称</th>
                                            <th>所属分类</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${epidemicItemList}" var="chineseStandardAnimalEpidemicItem" varStatus="status">
                                            <tr>
                                                <td>${status.index + 1}</td>
                                                <td>${chineseStandardAnimalEpidemicItem.diseaseNameCn}</td>
                                                <td>${chineseStandardAnimalEpidemicItem.diseaseNameEng}</td>
                                                <td>${chineseStandardAnimalEpidemicItem.diseaseClass}</td>
                                            </tr>
                                        </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </section>
                        </div>
                    </div>

                </div>
            </div>

            <%--</div>--%>


            <!--body wrapper end-->
        </div>
    </div>
    <!-- main content end-->
</section>
<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/typeahead/bootstrap3-typeahead.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>
<!--common scripts for all pages -->
<%--<script src="${pageContext.request.contextPath}/adminex/js/scripts.js"></script>--%>
<script src="${pageContext.request.contextPath}/echarts/build/dist/echarts-all.js"></script>
<script src="${pageContext.request.contextPath}/angularjs/angular.min.js"></script>

<%--<!--pickers plugins-->--%>
<%--<script type="text/javascript"--%>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/js/bootstrap-datepicker.js?v=1"></script>

<%--<!--pickers initialization-->--%>
<script src="${pageContext.request.contextPath}/adminex/js/pickers-init.js"></script>

<script src="${pageContext.request.contextPath}/jqPaginator/dist/1.2.0/jqPaginator.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/data-tables/jquery.dataTables.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/data-tables/DT_bootstrap.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#example').DataTable({
            "bFilter": false,
            "bInfo": false,
            "bLengthChange":false,
            "oLanguage": {
                "sZeroRecords": "抱歉， 没有找到",
                "sInfoEmpty": "没有数据",
                //"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前一页",
                    "sNext": "后一页",
                    "sLast": "尾页"
                }
            }
        });
        $.post('${pageContext.request.contextPath}/oieEpidemicSearch/epidemicNameList.do', null, function (data) {
            $('#epidemicName').typeahead({source: data})
        }, 'json');

    });
</script>
</body>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
</html>
