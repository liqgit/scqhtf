<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 16/8/10
  Time: 下午2:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据详情</title>

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
            <div class="row blog">
                <div class="col-md-12">
                    <div class="panel">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <h4>信息来源</h4>
                                </div>
                                <div class="col-md-12">
                                    <h1 class=""><a id="title" href="#"></a></h1>
                                    <p id="time2" class=" auth-row">
                                    </p>

                                    <p id="content">
                                    </p>

                                    <a data-toggle="modal" data-target="#myModal1" class="more">查看来源</a>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="panel">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <h4>信息分词</h4>
                                </div>
                                <div class="col-md-12" id="wordsBtn">

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                        &times;
                                    </button>
                                    <h4 class="modal-title" id="myModalLabel">
                                        原文链接二维码
                                    </h4>
                                </div>
                                <div id="baikeUrlDiv" class="modal-body" style="text-align: center;">
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                                    </button>
                                </div>
                            </div><!-- /.modal-content -->
                        </div><!-- /.modal -->
                    </div>

                </div>

            </div>
        </div>
    </div>
    <!--body wrapper end-->
</section>

<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>

<script src="${pageContext.request.contextPath}/jqqrcode/jquery-qrcode-0.14.0.min.js"></script>


<script>

    var reg = new RegExp("(^|&)rowKey=(.*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    console.info(r);
    var rowkey=(r!==null)?r[2]:null;
    console.info(rowkey);
    $(document).ready(function () {
        $.post('${pageContext.request.contextPath}/dataWarehouse/datawarehouseDetail.do', {'rowKey': rowkey}, function (data) {
            var json = data;
            $("#baikeUrlDiv").qrcode({
                render: "table", //table方式
                width: 200, //宽度
                height: 200, //高度
                text: json.contentUrl //任意内容
            });
            $("#epidemicName").text(json.epidemicName);
            $("#time").text(json.time);
            $("#time2").text(json.time);
			var a_regx = /(<a[^>]*>)|(<\/a>)|(<img[^>]*>)/g;
			var content_html = json.content?json.content.replace(a_regx,""):"";
            $("#content").html(content_html);

        }, 'json');


        $.post('${pageContext.request.contextPath}/dataWarehouse/datawarehouseWords.do', {'rowKey': '${rowKey}'}, function (data) {
            var json = data;
            for (var key in json) {
			var regx = /^[0-9][0-9]{0,10}$/;
                if(!key.match(regx)){
                    $("#wordsBtn").append("<button class='btn btn-default' type='button'>" + key + "*" + json[key] + "</button>");
                }

            }
        }, 'json');
    });
</script>


</body>
</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>