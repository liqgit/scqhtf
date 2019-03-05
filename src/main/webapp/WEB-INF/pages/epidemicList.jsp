<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 16/7/14
  Time: 下午12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>综合查询列表</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="keywords"
          content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>AdminX</title>


    <!--common-->
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/fonts/css/font-awesome.min.css" rel="stylesheet">


    <!--pickers css-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/css/datepicker-custom.css"/>


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
                            疫情综合查询
                        </div>

                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-4 form-group">
                                    <label class="control-label">疫情名称</label>
                                    <input type="text" id='epidemicName' class="form-control" placeholder="疫情名称">
                                </div>
                                <div class="col-lg-4 form-group">
                                    <label class="control-label">地域名称</label>
                                    <input type="text" id="regionCn" class="form-control" placeholder="地域名称">
                                </div>
                                <div class="col-lg-4 form-group">
                                    <label class="control-label">时间段</label>
                                    <div data-date-minviewmode="months" data-date-viewmode="years"
                                         class="input-group input-large custom-date-range " data-date="102/2012"
                                         data-date-format="yyyy-mm-dd">
                                        <input type="text" id="startDate" class="form-control dpd1" name="from">
                                        <span class="input-group-addon">到</span>
                                        <input type="text" id="endDate" class="form-control dpd2" name="to">
                                    </div>

                                </div>
                            </div>

                        </div>
                        <div class="panel-footer">
                            <button class="btn btn-primary" onclick="search();">查询</button>
                        </div>
                    </div>

                </div>
            </div>

            <%--</div>--%>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-body">

                            <table class="table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th style="">
                                        <div class="th-inner ">序号</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">疫情名称</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">地区</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">数量</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">爆发时间</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">操作</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="epidmicData">
                                </tbody>
                                <tfoot>
                                <tr>
                                    <div class="dataTables_paginate paging_bootstrap pagination">
                                        <ul id="demo1"></ul>
                                    </div>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!--body wrapper end-->
        </div>
    </div>
    <!-- main content end-->
</section>

<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/typeahead/bootstrap3-typeahead.min.js"/>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
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

<script type="text/javascript"
        src="${pageContext.request.contextPath}/jqPaginator/dist/1.2.0/jqPaginator.min.js"></script>




<script type="text/javascript">
    var conditionEpidemicName = '';
    var conditionRegionCn = '';
    var conditionStartDate = '';
    var conditionEndDate = '';

    function pageUtils() {
        $.post('${pageContext.request.contextPath}/epidemic/epidemicList.do', {
            'epidemicName': conditionEpidemicName,
            'regionCn': conditionRegionCn,
            'startDate': conditionStartDate,
            'endDate': conditionEndDate
        }, function (data) {
            $("#demo1").jqPaginator({
                totalPages: Math.ceil(data.epidemicAppearListCount / 10),
                visiblePages: 10,
                currentPage: 1,
                first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
                prev: '<li class="prev"><a href="javascript:void(0);">上一页<\/a><\/li>',
                next: '<li class="next"><a href="javascript:void(0);">下一页<\/a><\/li>',
                last: '<li class="last"><a href="javascript:void(0);">尾页<\/a><\/li>',
                page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
                onPageChange: function (n) {
                    $("#epidmicData").empty();
                    $.post('${pageContext.request.contextPath}/epidemic/epidemicList.do', {
                        'pageNo': n - 1,
                        'epidemicName': conditionEpidemicName,
                        'regionCn': conditionRegionCn,
                        'startDate': conditionStartDate,
                        'endDate': conditionEndDate
                    }, function (data) {
                        var epidemicAppearList = data.epidemicAppearList;
                        for (var i = 0; i < epidemicAppearList.length; i++) {
                            $("#epidmicData").append("<tr><td style=''>" + (i + 1) + "</td>" + "<td style=''>" + epidemicAppearList[i].epidemic.epidemicName + "</td>" + "<td style=''>" + epidemicAppearList[i].region.regionCn + "</td>" + "<td style=''>" + epidemicAppearList[i].appearTimes + "</td>" + "<td style=''>" + epidemicAppearList[i].appearDate + "</td>" + "<td style=''>" + "<a href='${pageContext.request.contextPath}/epidemic/epidemicDetail.do?epidemicAppearId=" + epidemicAppearList[i].id + "'><button class='btn btn-primary'>详细信息</button></a>" + "</td>" + "</tr>");
                        }
                    }, 'json');
                }
            });
        }, 'json');
    }

    function initConditions() {
        conditionEpidemicName = '';
        conditionRegionCn = '';
        conditionStartDate = '';
        conditionEndDate = '';
    }

    function search() {
        initConditions();
        conditionEndDate = $('#endDate').val();
        conditionEpidemicName = $('#epidemicName').val();
        conditionRegionCn = $('#regionCn').val();
        conditionStartDate = $('#startDate').val();
        pageUtils();
    }

    $(document).ready(function () {
        var flag = '${flag}';
        if (flag != null && flag != '') {
            $.post('${pageContext.request.contextPath}/epidemic/epidemicListOverRide.do', {'flag': flag}, function (data) {
                $("#demo1").jqPaginator({
                    totalPages: Math.ceil(data.epidemicAppearListCount / 10),
                    visiblePages: 10,
                    currentPage: 1,
                    first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
                    prev: '<li class="prev"><a href="javascript:void(0);">上一页<\/a><\/li>',
                    next: '<li class="next"><a href="javascript:void(0);">下一页<\/a><\/li>',
                    last: '<li class="last"><a href="javascript:void(0);">尾页<\/a><\/li>',
                    page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
                    onPageChange: function (n) {
                        $("#epidmicData").empty();
                        $.post('${pageContext.request.contextPath}/epidemic/epidemicList.do', {
                            'pageNo': n - 1,
                            'flag': flag
                        }, function (data) {
                            var epidemicAppearList = data.epidemicAppearList;
                            for (var i = 0; i < epidemicAppearList.length; i++) {
                                $("#epidmicData").append("<tr><td style=''>" + (i + 1) + "</td>" + "<td style=''>" + epidemicAppearList[i].epidemic.epidemicName + "</td>" + "<td style=''>" + epidemicAppearList[i].region.regionCn + "</td>" + "<td style=''>" + epidemicAppearList[i].appearTimes + "</td>" + "<td style=''>" + epidemicAppearList[i].appearDate + "</td>" + "<td style=''>" + "<a href='${pageContext.request.contextPath}/epidemic/epidemicDetail.do?epidemicAppearId=" + epidemicAppearList[i].id + "'><button class='btn btn-primary'>详细信息</button></a>" + "</td>" + "</tr>");
                            }
                        }, 'json');
                    }
                });
            }, 'json');
        } else {
            pageUtils();
        }

        $.post('${pageContext.request.contextPath}/common/regionList.do', null, function (data) {
            $('#regionCn').typeahead({source: data})
        }, 'json');

        $.post('${pageContext.request.contextPath}/common/epidemicNameList.do', null, function (data) {
            $('#epidemicName').typeahead({source: data})
        }, 'json');


    });
</script>
</body>
</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>