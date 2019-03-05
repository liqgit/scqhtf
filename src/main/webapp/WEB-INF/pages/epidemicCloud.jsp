<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 16/7/14
  Time: 下午8:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>字符云</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="keywords"
          content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>AdminX</title>


    <link href="${pageContext.request.contextPath}/adminex/css/bootstrap.min.css" rel="stylesheet">

    <!--pickers css-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/css/datepicker-custom.css"/>
    <!--common-->
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/fonts/css/font-awesome.min.css" rel="stylesheet">
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
                <div class="col-sm-12">
                    <div class="panel">
                        <header class="panel-heading">
                            查询
                        </header>
                        <div class="panel-body" style="display: block;">
                            <table style="text-align: center;">
                                <tr>
                                    <td><label>地域:&nbsp;&nbsp;&nbsp;</label></td>
                                    <td>
                                        <div class="input-group">
                                            <input id="region" class="form-control" style="width: 150px"/>
                                        </div>
                                    </td>
                                    <td><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间段:&nbsp;&nbsp;&nbsp;</label></td>
                                    <td>
                                        <div data-date-minviewmode="months" data-date-viewmode="years"
                                             class="input-group input-large custom-date-range " data-date="102/2012"
                                             data-date-format="yyyy-mm-dd">
                                            <input type="text" id="startDate" class="form-control dpd1" name="from">
                                            <span class="input-group-addon">到</span>
                                            <input type="text" id="endDate" class="form-control dpd2" name="to">
                                        </div>
                                    </td>
                                    <td>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <button class="btn btn-primary" onclick="clearAll()">重置条件</button>
                                    </td>
                                    <td>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <button class="btn btn-info" onclick="findYQChartsCloud()">查询</button>
                                    </td>
                                </tr>

                            </table>

                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <div class="panel">
                        <header class="panel-heading">
                            疫情字符云
                                    <span class="tools pull-right">
                                        <a href="javascript:;" class="fa fa-chevron-down"></a>
                                     </span>
                        </header>
                        <div class="panel-body" style="display: block;">
                            <div id="main" style="height:400px"></div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!--body wrapper end-->
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

<!--common scripts for all pages-->
<%--<script src="${pageContext.request.contextPath}/adminex/js/scripts.js"></script>--%>


<%--<!--pickers plugins-->--%>
<%--<script type="text/javascript"--%>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/js/bootstrap-datepicker.js?v=1"></script>

<%--<!--pickers initialization-->--%>
<script src="${pageContext.request.contextPath}/adminex/js/pickers-init.js"></script>

<%--<script src="${pageContext.request.contextPath}/bootstrap/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>--%>

<script src="${pageContext.request.contextPath}/echarts/build/dist/echarts-all.js"></script>


<script>

    function boundRegionList() {
        $.post('${pageContext.request.contextPath}/common/regionList.do', null, function (data) {
            $('#region').typeahead({source: data})
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

    function clearAll() {
        $("input").attr("value", "");
        $("#region").attr("value", "");
        findYQChartsCloud()

    }


    function findYQChartsCloud() {
        //设置数据
        var option = {
            tooltip: {
                show: true
            },
            series: [{
                name: '疫情字符云',
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
        $.post("${pageContext.request.contextPath}/epidemicCloud/epidemicCloud.do", {
                    'region': $('#region').val(),
                    'startDate': $('#startDate').val(),
                    'endDate': $('#endDate').val()
                },
                function (data) {
                    var json = data;
                    option.series[0].data = [];
                    for (var i = 0; i < json.length; i++) {
                        json[i]['itemStyle'] = createRandomItemStyle()
                        option.series[0].data=json;
                    }
                    var myChart = echarts.init(document.getElementById('main'));
                    myChart.setOption(option);
                },
                "json");
    }


    $(document).ready(function () {
        boundRegionList();
        findYQChartsCloud();
    });


</script>

</body>
</html >
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
