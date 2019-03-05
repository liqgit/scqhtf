<%--
  Created by IntelliJ IDEA.
  User: ISK
  Date: 2018/12/7
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="keywords"
          content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>病虫害发生趋势分析</title>

    <!--dashboard calendar-->
    <link href="${pageContext.request.contextPath}/adminex/css/clndr.css" rel="stylesheet">

    <!--pickers css-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/css/datepicker-custom.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/adminex/js/bootstrap-timepicker/css/timepicker.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/adminex/js/bootstrap-colorpicker/css/colorpicker.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/adminex/js/bootstrap-daterangepicker/daterangepicker-bs3.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/adminex/js/bootstrap-datetimepicker/css/datetimepicker-custom.css"/>


    <!--common-->
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/fonts/css/font-awesome.min.css" rel="stylesheet">
</head>
<body class="sticky-header">
<section>
    <%@include file="menu.jsp" %>
    <div class="main-content">
        <%@include file="header.jsp" %>

        <div class="row">
            <div class="col-md-12" >
                <div class="panel panel-default" >
                    <div class="panel-body" >
                        <div id="fscd" style="min-width:500px;height:500px"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12" >
                <div class="panel panel-default" >
                    <div class="panel-body" >
                        <div id="container" style="min-width:500px;height:500px"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12" >
                <div class="panel panel-default" >
                    <div class="panel-body" >
                        <div id="leiji" style="min-width:500px;height:500px"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12" >
                <div class="panel panel-default" >
                    <div class="panel-body" >
                        <div id="fz" style="min-width:500px;height:500px"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12" >
                <div class="panel panel-default" >
                    <div class="panel-body" >
                        <div id="ljfz" style="min-width:500px;height:500px"></div>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-md-12" >
                <div class="panel panel-default" >
                    <div class="panel-body" >
                        <div id="md" style="min-width:500px;height:500px"></div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</section>

<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/typeahead/bootstrap3-typeahead.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>

<script src="${pageContext.request.contextPath}/echarts/build/dist/echarts3-7-1.min.js"></script>

<script src="${pageContext.request.contextPath}/hcharts/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/hcharts/highcharts-3d.js"></script>
<script src="${pageContext.request.contextPath}/hcharts/modules/broken-axis.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/jqPaginator/dist/1.2.0/jqPaginator.min.js"></script>


</body>

<script>

    var chart = Highcharts.chart('container',{
        chart: {
            type: 'column'
        },
        title: {
            text: '当前发生面积分析'
        },
        xAxis: {
            categories: [
                '一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            // head + 每个 point + footer 拼接成完整的 table
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<br>{series.name}:{point.y:.1f}</td>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                borderWidth: 0
            }
        },
        series: [{
            name: '累计发生面积',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        }, {
            name: '当前发生面积',
            data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]
        }, {
            name: '当前需防治面积',
            data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]
        }, {
            name: '累计防治面积',
            data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]
        }]
    });

    var chart = Highcharts.chart('leiji',{
        chart: {
            type: 'column'
        },
        title: {
            text: '累计发生面积分析'
        },
        xAxis: {
            categories: [
                '一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            // head + 每个 point + footer 拼接成完整的 table
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<br>{series.name}:{point.y:.1f} ',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                borderWidth: 0
            }
        },
        series: [{
            name: '累计发生面积',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        }, {
            name: '当前发生面积',
            data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]
        }, {
            name: '当前需防治面积',
            data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]
        }, {
            name: '累计防治面积',
            data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]
        }]
    });


    var chart = Highcharts.chart('fscd',{
        chart: {
            type: 'column'
        },
        title: {
            text: '发生程度趋势'
        },
        xAxis: {
            categories: [
                '一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            // head + 每个 point + footer 拼接成完整的 table
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<br>{series.name}:{point.y:.1f} ',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                borderWidth: 0
            }
        },
        series: [{
            name: '小春',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        }]
    });

    var chart = Highcharts.chart('fz',{
        chart: {
            type: 'column'
        },
        title: {
            text: '当前需防治面积'
        },
        xAxis: {
            categories: [
                '一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            // head + 每个 point + footer 拼接成完整的 table
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<br>{series.name}:{point.y:.1f} ',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                borderWidth: 0
            }
        },
        series: [{
            name: '累计发生面积',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        }, {
            name: '当前发生面积',
            data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]
        }, {
            name: '当前需防治面积',
            data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]
        }, {
            name: '累计防治面积',
            data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]
        }]
    });

    var chart = Highcharts.chart('ljfz', {
        chart: {
            type: 'column'
        },
        title: {
            text: '累计防治面积'
        },
        xAxis: {
            categories: ['一月', '二月', '三月', '四月', '五月','六月', '七月', '八月', '九月', '十月','十一月','十二月']
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>' +
                '({point.percentage:.0f}%)<br/>',
            //:.0f 表示保留 0 位小数，详见教程：https://www.hcharts.cn/docs/basic-labels-string-formatting
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'percent'
            }
        },
        series: [{
            name: '累计发生面积',
            data: [5, 3, 4, 7, 2,5, 3, 4, 7, 2,6,8]
        }, {
            name: '当前发生面积',
            data: [2, 2, 3, 2, 1,5, 3, 4, 7, 2,4,3]
        }, {
            name: '当前需防治面积',
            data: [3, 4, 4, 2, 5,5, 3, 4, 7, 2,5,2]
        },{
            name: '累计防治面积',
            data: [3, 4, 4, 2, 5,5, 3, 4, 7, 2,5,2]
        }]
    });

    function md() {
      var  mdoption = {
            title: {
                left: 'center',
                text: '一般密度及最高密度',
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter: function (params) {
                    var tar = params[1];
                    return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type : 'category',
                splitLine: {show:false},
                data : ['一月', '二月', '三月', '四月', '五月','六月', '七月', '八月', '九月', '十月','十一月','十二月']
            },
            yAxis: {
                type : 'value'
            },
            series: [
                {
                    name: '辅助',
                    type: 'bar',
                    stack:  '总量',
                    itemStyle: {
                        normal: {
                            barBorderColor: 'rgba(0,0,0,0)',
                            color: 'rgba(0,0,0,0)'
                        },
                        emphasis: {
                            barBorderColor: 'rgba(0,0,0,0)',
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    data: [5, 3, 4, 7, 2,5, 3, 4, 7, 2,6,8]
                },
                {
                    name: '密度',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    },
                    data:[5, 3, 4, 7, 2,5, 3, 4, 7, 2,6,8]
                }
            ]
        };

        var calendarHeatMapChart = echarts.init(document.getElementById('md'));
        calendarHeatMapChart.setOption(mdoption);
        calendarHeatMapChart.on('click', function (params) {
            openDetailMod(params.value[0],params.value[0]);
        });
    }
    $(document).ready(function () {
        md();
    });
</script>
</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>