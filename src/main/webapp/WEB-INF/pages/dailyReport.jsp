
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DailyReport</title>
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">

</head>
<body class="sticky-header">


<section>
    <%@include file="menu.jsp" %>
    <!-- main content start-->
    <div class="main-content">
        <%@include file="header.jsp" %>
        <div class="wrapper">
            <div class="row">
                <section class="panel">
                    <header class="panel-heading">
                        查询条件
                    </header>
                    <div class="panel-body">
                        <table style="text-align: center;">
                            <tr>
                                <td><label>时间:&nbsp;&nbsp;&nbsp;</label></td>
                                <td>
                                    <div class="input-group">
                                        <input id="queryTime" class="form-control SQForm" style="width: 200px"/>
                                    </div>
                                </td>
                                <td>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button class="btn btn-info" onclick="simpleQuery()">查询</button>
                                </td>
                            </tr>

                        </table>

                    </div>

                </section>
                <div class="col-md-12" id="content">




                </div>

            </div>
        </div>


            <div class="row">
                <div class="dataTables_paginate paging_bootstrap pagination">
                    <ul id="pageNum"></ul>
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
<script src="${pageContext.request.contextPath}/adminex/js/pickers-init.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/jqPaginator/dist/1.2.0/jqPaginator.min.js"></script>

<!--common scripts for all pages-->
<script src="${pageContext.request.contextPath}/adminex/js/scripts.js"></script>
<script src="${pageContext.request.contextPath}/js/dailyReport.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/elasticSearch/elasticsearch.jquery.js"></script>
<script type="text/javascript">

    function simpleQuery() {
        $("#content").children().remove();
        var time = $("#queryTime").val();
        console.info(time);
        var drd=window.dailyReportData;
        var datas=drd.hits;
        var list=[];
        for(var i=0;i<datas.length;i++){
            if(datas[i]._source && datas[i]._source.time && datas[i]._source.time==time){
                list.push(datas[i]);
            }
        }
        for(var i=0;i<list.length;i++){
            var title = list[i]._source.title;
            var time = list[i]._source.time;
            var content = list[i]._source.content.replace(/(\|-\|)/g,"\"");
            var source = list[i]._source.from;
            var htmlTag = "<section class='panel'><header class='panel-heading'>"+title+"&nbsp&nbsp&nbsp&nbsp&nbsp"+time+"&nbsp&nbsp&nbsp&nbsp&nbsp From: "+source+"<span class='tools pull-right'><a class='fa fa-chevron-down' href='javascript:;'></a></span></header><div class='panel-body'style='display: none;'>"+content+"</div></section>";
            $("#content").append(htmlTag);

        }
        $('.panel .tools .fa').unbind();
        $('.panel .tools .fa').click(function () {
            var el = $(this).parents(".panel").children(".panel-body");
            if ($(this).hasClass("fa-chevron-down")) {
                $(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
                el.slideDown(200);
            } else {
                $(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
                el.slideUp(200); }
        });
        $('.link_media').parent().parent().remove();
        $('.list_dash').remove();
    }




</script>

</body>
</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
