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
    <title>疫情详情</title>

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
                <div class="col-md-4">
                    <div class="panel">
                        <div class="panel-body">
                            <ul class="p-info">
                                <li>
                                    <div class="title">疫情名称</div>
                                    <div class="desk">${epidemicAppear.epidemic.epidemicName}</div>
                                </li>
                                <li>
                                    <div class="title">地域</div>
                                    <div class="desk">${epidemicAppear.region.regionCn}</div>
                                </li>
                                <li>
                                    <div class="title">发生次数</div>
                                    <div class="desk">${epidemicAppear.appearTimes}</div>
                                </li>
                                <li>
                                    <div class="title">爆发时间</div>
                                    <div class="desk">${epidemicAppear.appearDate}</div>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div class="panel">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <h4>疫情百科</h4>
                                </div>
                                <div class="col-md-12">
                                    <div class="blog-img-sm" >
                                        <img id="epidemicBaiKeImg" src="#"
                                             alt="" style="height:auto;width:auto">
                                    </div>
                                </div>
                                <div class="col-md-12" style="height:80%;overflow-y:scroll;">
                                    <p id="baikeSummary">

                                    </p>
                                    <a id="baikeContentUrl" data-toggle="modal" data-target="#myModal" href="#"
                                       class="more">查看原文</a>
                                </div>
                            </div>
                        </div>
                    </div>


                </div>

                <div class="col-md-8">
                    <div class="panel">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <h4>信息来源</h4>
                                </div>
                                <div class="col-md-12">
                                    <h1 class=""><a id="epidemicSourceTitle" href="#"></a></h1>
                                    <p id="epidemicSourceTime" class=" auth-row">
                                    </p>

                                    <p id="epidemicSourceContent">
                                    </p>
                                    <a data-toggle="modal" data-target="#myModal1" class="more">查看来源</a>
                                </div>
                            </div>
                        </div>
                    </div>



                </div>

            </div>
        </div>
    </div>
    <!--body wrapper end-->

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        疫情百科原文链接二维码
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

    <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel1">
                        疫情抓取原文链接二维码
                    </h4>
                </div>
                <div id="epidemicSourceUrlDiv" class="modal-body" style="text-align: center;">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</section>

<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>
<script src="${pageContext.request.contextPath}/jqqrcode/jquery-qrcode-0.14.0.min.js"></script>
<script src="${pageContext.request.contextPath}/echarts/build/dist/echarts-all.js"></script>
<script>

    var itemStyles = ['label-default', 'label-primary', 'label-success', 'label-info', 'label-warning', 'label-danger']

    $(document).ready(function () {
        $("#epidemicSourceUrlDiv").qrcode({
            render: "table", //table方式
            width: 200, //宽度
            height: 200, //高度
            text: '${epidemicAppear.rowKey}' //任意内容
        });
        $.post('${pageContext.request.contextPath}/hbaseController/epidemicBaike.do', {'rowKey': 'http://baike.baidu.com/item/${epidemicAppear.epidemic.epidemicName}'}, function (data) {
            var json = data;
            $("#epidemicBaiKeImg").attr("src", 'http://reptile3.tj.ciq:8080/img/${epidemicAppear.epidemic.epidemicName}.jpg');
            var summary = json.summary;
			var a_regx = /(<a[^>]*>)|(<\/a>)|(<img[^>]*>)|(<em[^>]*>[\s\S]*?<\/em>[\s\S]{0,10}[编辑|锁定]+)/g;
            summary = summary.replace(a_regx,"");
	    $("#baikeSummary").append(summary.replace('main-content',''));
	    $(".lemmaWgt-lemmaCatalog").remove();
	    $(".top-tool").remove();
	    $(".lemma-picture").remove();
            $("#baikeUrlDiv").qrcode({
                render: "table", //table方式
                width: 200, //宽度
                height: 200, //高度
                text: 'http://baike.baidu.com/item/${epidemicAppear.epidemic.epidemicName}' //任意内容
            });
        }, 'json');


        $.post('${pageContext.request.contextPath}/hbaseController/epidemicSource.do', {'rowKey': '${epidemicAppear.rowKey}'}, function (data) {
            var json = data;
            $("#epidemicSourceTitle").text(json.epidemicName);
            $("#epidemicSourceTime").text(json.time);
            $("#epidemicSourceContent").append(json.content);
        }, 'json');


        <%--$.post('${pageContext.request.contextPath}/epidemic/epidemicWords.do', {'rowKey': '${epidemicAppear.rowKey}'}, function (data) {--%>
            <%--var json = data;--%>
            <%--if (json == null || json.length <= 0) {--%>
                <%--$("#wordsBtn").append("<span class='label " + itemStyles[parseInt(itemStyles.length * Math.random())] + "'>" + "无分词" + "</span>&nbsp;&nbsp;");--%>
            <%--} else {--%>
                <%--for (var key in json) {--%>
                    <%--$("#wordsBtn").append("<button class='btn btn-default' type='button'>" + key + "*" + json[key] + "</button>");--%>
                <%--}--%>
            <%--}--%>

        <%--}, 'json');--%>
        //findWordsCloud();
    });


    function findWordsCloud() {
        //设置数据
        var option = {
            tooltip: {
                show: true
            },
            series: [{
                type: 'wordCloud',
                size: ['80%', '80%'],
                textRotation: [0, 45, 90, -45],
                textPadding: 0,
                autoSize: {
                    enable: true,
                    minSize: 14
                },
                data: []
            }]
        };
        $.post('${pageContext.request.contextPath}/dataWarehouse/datawarehouseWords.do', {'rowKey': '${epidemicAppear.rowKey}'}, function (data) {
            var json = data;
            for (var key in json) {
                var regx = /^[0-9][0-9]{0,10}$/;
                if(!key.match(regx)){
                    option.series[0].data.unshift({name:key,value:json[key],'itemStyle':createRandomItemStyle()});
                }
            }
            var myChart = echarts.init(document.getElementById('wordsBtn'));
            myChart.setOption(option);
        }, 'json');
    }

    function createRandomItemStyle() {
        return {
            normal: {
                color: 'rgb(' + [
                    Math.round(Math.random() * 160),
                    Math.round(Math.random() * 160),
                    Math.round(Math.random() * 160)
                ].join(',') + ')'
            }
        };
    }
</script>


</body>
</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>