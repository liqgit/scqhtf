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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="keywords"
          content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>首页</title>

    <!--dashboard calendar-->
    <link href="${pageContext.request.contextPath}/adminex/css/clndr.css" rel="stylesheet">


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
                <div class="col-md-12">
                    <!--statistics start-->
                    <div class="row state-overview">
                        <div class="col-md-4 col-xs-12 col-sm-4">
                            <div class="panel purple">
                                <div class="symbol">
                                    <i class="fa fa-gavel"></i>
                                </div>
                                <a href="${pageContext.request.contextPath}/epidemic/epidemicListPage.do?flag=0"
                                   style="color: white">
                                    <div class="state-value">
                                        <div class="value">${newEpidemicCount}</div>
                                        <div class="title">全球新增疫情总数
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-4 col-xs-12 col-sm-4">
                            <div class="panel red">
                                <div class="symbol">
                                    <i class="fa fa-tags"></i>
                                </div>
                                <a href="${pageContext.request.contextPath}/epidemic/epidemicListPage.do?flag=0"
                                   style="color:white;">
                                    <div class="state-value">
                                        <div class="value">${newLocalEpidemicCount}</div>
                                        <div class="title">我国新增疫情总数</div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-4 col-xs-12 col-sm-4">
                            <div class="panel green">
                                <div class="symbol">
                                    <i class="fa fa-eye"></i>
                                </div>
                                <a href="${pageContext.request.contextPath}/epidemic/epidemicListPage.do"
                                   style="color: white">
                                    <div class="state-value">
                                        <div class="value">${epidemicCount}</div>
                                        <div class="title">已知疫情总数</div>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <!--statistics end-->
                </div>

            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            疫情TOP概览
                        </div>
                        <div class="panel-body">
                            <div class="row revenue-states">
                                <div class="col-md-12">
                                    <div class="btn-group">
                                        <button id="yqpie" class="btn btn-sm btn-primary" onclick="yqTop10()">
                                            全球疫情TOP10
                                        </button>
                                        <button class="btn btn-sm btn-primary" onclick="yqLocalTop10()">我国疫情TOP10
                                        </button>
                                    </div>

                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div id="timeLine" style="height:400px;"></div>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div id="top10Pie" style="height:400px"></div>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div id="top10Bar" style="height:400px"></div>
                                </div>
                            </div>
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
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>
<%--<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>--%>

<!--common scripts for all pages-->

<script src="${pageContext.request.contextPath}/echarts/build/dist/echarts-all.js"></script>

<script src="${pageContext.request.contextPath}/angularjs/angular.min.js"></script>

<script src="${pageContext.request.contextPath}/echarts/timeline.js"></script>

<script>
    function yqTop10Bar(id, regionName, title) {
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['出现次数']
            },
            toolbox: {
                show: true,
                feature: {
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    splitLine: {
                        show: false
                    },
                    data: []
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '出现次数',
                    type: 'bar',
                    data: [],
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        };

        $.post("${pageContext.request.contextPath}/index/epidemicTopTenBar.do", {regionName: regionName},
                function (data) {
                    var json = data;
                    option.xAxis[0].data = json['epidemicLocalTopTenNames'];
                    option.series[0].data = json['epidemicLocalTopTenValues'];
                    var myChart = echarts.init(document.getElementById(id));
                    myChart.setOption(option);
                },
                "json");
    }


    function yqTop10Pie(id, regionName, title) {
        var option = {
            title: {
                text: title,
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: []
            },
            toolbox: {
                show: true,
                feature: {
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left'
                            }
                        }
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: '全球疫情',
                    type: 'pie',
                    radius: '40%',
                    center: ['50%', '60%'],
                    data: []
                }
            ]
        };
        $.post("${pageContext.request.contextPath}/index/epidemicTopTenPie.do", {regionName: regionName},
                function (data) {
                    var json = data;
                    option.legend.data = json['epidemicNames'];
                    option.series[0].data = json['epidemicTop'];
                    var myChart = echarts.init(document.getElementById(id));
                    myChart.setOption(option);
                },
                "json");

    }

    function timeLine(type) {

        $.post("${pageContext.request.contextPath}/index/epidemicTimeline.do",{"type":type},
            function (data) {
                var json = data;
                data.push(
                    {"date":2016,"name":"中东呼吸综合征冠状病毒","value":"0"},
                    {"date":2016,"name":"脊髓灰质炎","value":"0"},
                    {"date":2007,"name":"中东呼吸综合征冠状病毒","value":"0"},
                    {"date":2007,"name":"脊髓灰质炎","value":"0"},
                    {"date":2007,"name":"手足口病","value":"0"},
                    {"date":2007,"name":"黄热病","value":"0"},
                    {"date":2008,"name":"中东呼吸综合征冠状病毒","value":"0"},
                    {"date":2009,"name":"中东呼吸综合征冠状病毒","value":"0"},
                    {"date":2009,"name":"黄热病","value":"0"},
                    {"date":2009,"name":"手足口病","value":"0"},
                    {"date":2010,"name":"中东呼吸综合征冠状病毒","value":"0"},
                    {"date":2010,"name":"脊髓灰质炎","value":"0"},
                    {"date":2010,"name":"手足口病","value":"0"},
                    {"date":2010,"name":"黄热病","value":"0"},
                    {"date":2011,"name":"中东呼吸综合征冠状病毒","value":"0"},
                    {"date":2011,"name":"脊髓灰质炎","value":"0"},
                    {"date":2011,"name":"手足口病","value":"0"},
                    {"date":2011,"name":"黄热病","value":"0"}
                    );
                var yearlist={"2016":[],"2015":[],"2014":[],"2013":[],"2012":[],"2011":[],"2010":[],"2009":[],"2008":[],"2007":[]};
                var diseaseList = {"2016":[],"2015":[],"2014":[],"2013":[],"2012":[],"2011":[],"2010":[],"2009":[],"2008":[],"2007":[]};
                for(var i=0;i<data.length;i++){
                    var year = data[i]['date'];
                    if (yearlist[year].length<10){
                        yearlist[year].push(data[i]);
                        diseaseList[year].push(data[i]['name']);
                    }

                }
                var option = {
                    timeline: {
                        data: [
                            '2007-01-01', '2008-01-01', '2009-01-01', '2010-01-01',
                            '2011-01-01', '2012-01-01', '2013-01-01', '2014-01-01', '2015-01-01','2016-01-01'
                        ],
                        label: {
                            formatter: function (s) {
                                return s.slice(0, 4);
                            }
                        },
                        autoPlay: true,
                        playInterval: 1000
                    },
                    options: [
                        {
                            title: {
                                'text': '2007疫情变化图示',
                                'subtext': '数据来自全球卫生组织'
                            },
                            tooltip: {'trigger': 'axis'},
                            legend: {
                                x: 'right',
                                'data': [' ', '全球']
                            },
                            toolbox: {
                                'show': true,
                                orient: 'vertical',
                                x: 'right',
                                y: 'center',
                                'feature': {
                                    'mark': {'show': true},
                                    'dataView': {'show': true, 'readOnly': false},
                                    'magicType': {'show': true, 'type': ['line', 'bar', 'stack', 'tiled']},
                                    'restore': {'show': true},
                                    'saveAsImage': {'show': true}
                                }
                            },
                            calculable: true,
                            grid: {'y': 80, 'y2': 100},
                            xAxis: [{
                                'type': 'category',
                                'axisLabel': {'interval': 0},
                                'data':diseaseList['2007']
                            }],
                            yAxis: [
                                {
                                    'type': 'value',
                                    'name': '疫情总次数'
                                },
                                {
                                    'type': 'value',
                                    'name': '平均次数'
                                }
                            ],
                            series: [
                                {
                                    'name': '全球',
                                    'type': 'bar',
                                    'data': yearlist['2007']
                                }
                            ]
                        },
                        {
                            title: {'text': '2008疫情变化图示'},
                            series: [
                                {'data': yearlist['2008']},
                            ],
                            xAxis: [{
                            'type': 'category',
                            'axisLabel': {'interval': 0},
                            'data': diseaseList['2008']

                        }],

                        },
                        {
                            title: {'text': '2009疫情变化图示'},
                            series: [
                                {'data': yearlist['2009']}
                            ],
                            xAxis: [{
                            'type': 'category',
                            'axisLabel': {'interval': 0},
                            'data': diseaseList['2009']

                        }],
                        },
                        {
                            title: {'text': '2010疫情变化图示'},
                            series: [
                                {'data': yearlist['2010']}],
                            xAxis: [{
                                'type': 'category',
                                'axisLabel': {'interval': 0},
                                'data': diseaseList['2010']

                            }]
                        },
                        {
                            title: {'text': '2011疫情变化图示'},
                            series: [
                                {'data': yearlist['2011']}
                            ],
                            xAxis: [{
                                'type': 'category',
                                'axisLabel': {'interval': 0},
                                'data': diseaseList['2011']

                            }]
                        },
                        {
                            title: {'text': '2012疫情变化图示'},
                            series: [
                                {'data': yearlist['2012']}
                            ],
                            xAxis: [{
                                'type': 'category',
                                'axisLabel': {'interval': 0},
                                'data': diseaseList['2012']

                            }]
                        },
                        {
                            title: {'text': '2013疫情变化图示'},
                            series: [
                                {'data': yearlist['2013']}
                            ],
                            xAxis: [{
                                'type': 'category',
                                'axisLabel': {'interval': 0},
                                'data': diseaseList['2013']

                            }]
                        },
                        {
                            title: {'text': '2014疫情变化图示'},
                            series: [
                                {'data': yearlist['2014']}
                            ],
                            xAxis: [{
                                'type': 'category',
                                'axisLabel': {'interval': 0},
                                'data': diseaseList['2014']

                            }]
                        },
                        {
                            title: {'text': '2015疫情变化图示'},
                            series: [
                                {'data': yearlist['2015']}
                            ],
                            xAxis: [{
                                'type': 'category',
                                'axisLabel': {'interval': 0},
                                'data': diseaseList['2015']

                            }]
                        },
                        {
                            title: {'text': '2016疫情变化图示'},
                            series: [
                                {'data': yearlist['2016']}
                            ],
                            xAxis: [{
                                'type': 'category',
                                'axisLabel': {'interval': 0},
                                'data': diseaseList['2016']

                            }]
                        }
                    ]
                };
                var myChart = echarts.init(document.getElementById('timeLine'));
                myChart.setOption(option);

            },
            "json");
    }


    function yqTop10() {
        yqTop10Pie('top10Pie', '', '全球疫情TOP10');
        yqTop10Bar('top10Bar', '');
        timeLine('world');
    }

    function yqLocalTop10() {
        yqTop10Pie('top10Pie', '中国', '我国疫情TOP10');
        yqTop10Bar('top10Bar', '中国');
        timeLine('china');
    }

    $(document).ready(function () {
        $("#yqpie").click();
        timeLine();
    });


</script>
</body>

</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
