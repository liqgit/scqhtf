<%--
  Created by IntelliJ IDEA.
  User: ISK
  Date: 2018/12/7
  Time: 22:41
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

    <title>病虫害时间趋势分析</title>

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
                        <div id="zbzjzb" style="height:650px;"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12" >
                <div class="panel panel-default" >
                    <div class="panel-body" >
                        <div id="hcqs" style="height:650px;"></div>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-md-12" >
                <div class="panel panel-default" >
                    <div class="panel-body" >
                        <div id="fzqs" style="height:650px;"></div>
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
<script type="application/javascript">
    function getVirtulData(year) {
        year = year || '2019';
        var date = +echarts.number.parseDate(year + '-01-01');
        var end = +echarts.number.parseDate((+year + 1) + '-01-01');
        var dayTime = 3600 * 24 * 1000;
        var data = [];
        for (var time = date; time < end; time += dayTime) {
            data.push([
                echarts.format.formatTime('yyyy-MM-dd', time),
                Math.floor(Math.random() * (4-1)+2)
            ]);
        }
        return data;
    }
   function zb() {
     var data = getVirtulData(2018);
     var  zboption = {
         backgroundColor: '#404a59',

         title: {
             top: 30,
             text: '植保植检周报',
             subtext: '非真实数据',
             left: 'center',
             textStyle: {
                 color: '#fff'
             }
         },
         tooltip : {
             trigger: 'item'
         },
         legend: {
             top: '30',
             left: '100',
             data:['面积', 'Top 3'],
             textStyle: {
                 color: '#fff'
             }
         },
         calendar: [{
             top: 100,
             left: 'center',
             range: ['2018-01-01', '2018-06-30'],
             splitLine: {
                 show: true,
                 lineStyle: {
                     color: '#000',
                     width: 4,
                     type: 'solid'
                 }
             },
             yearLabel: {
                 formatter: '{start}',
                 textStyle: {
                     color: '#fff'
                 }
             },
             itemStyle: {
                 normal: {
                     color: '#323c48',
                     borderWidth: 1,
                     borderColor: '#111'
                 }
             }
         }, {
             top: 340,
             left: 'center',
             range: ['2018-07-01', '2018-12-31'],
             splitLine: {
                 show: true,
                 lineStyle: {
                     color: '#000',
                     width: 4,
                     type: 'solid'
                 }
             },
             yearLabel: {
                 formatter: '{start}',
                 textStyle: {
                     color: '#fff'
                 }
             },
             itemStyle: {
                 normal: {
                     color: '#323c48',
                     borderWidth: 1,
                     borderColor: '#111'
                 }
             }
         }],
         series : [
             {
                 name: '面积',
                 type: 'scatter',
                 coordinateSystem: 'calendar',
                 data: data,
                 symbolSize: function (val) {
                     return val[1]*2;
                 },
                 itemStyle: {
                     normal: {
                         color: '#ddb926'
                     }
                 }
             },
             {
                 name: '面积',
                 type: 'scatter',
                 coordinateSystem: 'calendar',
                 calendarIndex: 1,
                 data: data,
                 symbolSize: function (val) {
                     return val[1]*2;
                 },
                 itemStyle: {
                     normal: {
                         color: '#ddb926'
                     }
                 }
             },
             {
                 name: 'Top 3',
                 type: 'effectScatter',
                 coordinateSystem: 'calendar',
                 calendarIndex: 1,
                 data: data.sort(function (a, b) {
                     return b[1] - a[1];
                 }).slice(0, 12),
                 symbolSize: function (val) {
                     return val[1]*2;
                 },
                 showEffectOn: 'render',
                 rippleEffect: {
                     brushType: 'stroke'
                 },
                 hoverAnimation: true,
                 itemStyle: {
                     normal: {
                         color: '#f4e925',
                         shadowBlur: 10,
                         shadowColor: '#333'
                     }
                 },
                 zlevel: 1
             },
             {
                 name: 'Top 3',
                 type: 'effectScatter',
                 coordinateSystem: 'calendar',
                 data: data.sort(function (a, b) {
                     return b[1] - a[1];
                 }).slice(0, 12),
                 symbolSize: function (val) {
                     return val[1]*2;
                 },
                 showEffectOn: 'render',
                 rippleEffect: {
                     brushType: 'stroke'
                 },
                 hoverAnimation: true,
                 itemStyle: {
                     normal: {
                         color: '#f4e925',
                         shadowBlur: 10,
                         shadowColor: '#333'
                     }
                 },
                 zlevel: 1
             }
         ]
     };
     var calendarHeatMapChart = echarts.init(document.getElementById('zbzjzb'));
     calendarHeatMapChart.setOption(zboption);
     calendarHeatMapChart.on('click', function (params) {
         openDetailMod(params.value[0],params.value[0]);
     });
 }


   function hc() {
       var  data1 = [["2018-06-05",11],["2018-06-06",9],["2018-06-07",5],["2018-06-08",6],["2018-06-09",7],["2018-06-10",5],["2018-06-11",7],["2018-06-12",8],["2018-06-13",9],["2018-06-14",13],["2018-06-15",5],["2018-06-16",13],["2018-06-17",15],["2018-06-18",10],["2018-06-19",9],["2018-06-20",6],["2018-06-21",7],["2018-06-22",8],["2018-06-23",5],["2018-06-24",4],["2018-06-25",7],["2018-06-26",6],["2018-06-27",4],["2018-06-28",3],["2018-06-29",5],["2018-06-30",3],["2018-07-01",3],["2018-07-02",5],["2018-07-03",7],["2018-07-04",8],["2018-07-05",4],["2018-07-06",2],["2018-07-07",6],["2018-07-08",7],["2018-07-09",6],["2018-07-10",9],["2018-07-11",9],["2018-07-12",3],["2018-07-13",7],["2018-07-14",3],["2018-07-15",3],["2018-07-16",4],["2018-07-17",6],["2018-07-18",8],["2018-07-19",7],["2018-07-20",3],["2018-07-21",1],["2018-07-22",5],["2018-07-23",5],["2018-07-24",6]];

       var dateList = data1.map(function (item) {
           return item[0];
       });
       var valueList = data1.map(function (item) {
           return item[1];
       });

       var  hcoption = {

           visualMap: [{
               show: false,
               type: 'continuous',
               seriesIndex: 0,
               min: 0,
               max: 400
           }, {
               show: false,
               type: 'continuous',
               seriesIndex: 1,
               dimension: 0,
               min: 0,
               max: dateList.length - 1
           }],


           title: [{
               left: 'center',
               text: '病虫害发生种类趋势'
           }, {
               top: '55%',
               left: 'center',
               text: '当前发生面积累计趋势'
           }],
           tooltip: {
               trigger: 'axis'
           },
           xAxis: [{
               data: dateList
           }, {
               data: dateList,
               gridIndex: 1
           }],
           yAxis: [{
               splitLine: {show: false}
           }, {
               splitLine: {show: false},
               gridIndex: 1
           }],
           grid: [{
               bottom: '60%'
           }, {
               top: '60%'
           }],
           series: [{
               type: 'line',
               showSymbol: false,
               data: valueList
           }, {
               type: 'line',
               showSymbol: false,
               data: valueList,
               xAxisIndex: 1,
               yAxisIndex: 1
           }]
       };
       var calendarHeatMapChart = echarts.init(document.getElementById('hcqs'));
       calendarHeatMapChart.setOption(hcoption);
       calendarHeatMapChart.on('click', function (params) {
           openDetailMod(params.value[0],params.value[0]);
       });
   }

    function fz() {
        var  data1 = [["2018-06-05",11],["2018-06-06",9],["2018-06-07",5],["2018-06-08",6],["2018-06-09",7],["2018-06-10",5],["2018-06-11",7],["2018-06-12",8],["2018-06-13",9],["2018-06-14",13],["2018-06-15",5],["2018-06-16",13],["2018-06-17",15],["2018-06-18",10],["2018-06-19",9],["2018-06-20",6],["2018-06-21",7],["2018-06-22",8],["2018-06-23",5],["2018-06-24",4],["2018-06-25",7],["2018-06-26",6],["2018-06-27",4],["2018-06-28",3],["2018-06-29",5],["2018-06-30",3],["2018-07-01",3],["2018-07-02",5],["2018-07-03",7],["2018-07-04",8],["2018-07-05",4],["2018-07-06",2],["2018-07-07",6],["2018-07-08",7],["2018-07-09",6],["2018-07-10",9],["2018-07-11",9],["2018-07-12",3],["2018-07-13",7],["2018-07-14",3],["2018-07-15",3],["2018-07-16",4],["2018-07-17",6],["2018-07-18",8],["2018-07-19",7],["2018-07-20",3],["2018-07-21",1],["2018-07-22",5],["2018-07-23",5],["2018-07-24",6]];

        var dateList = data1.map(function (item) {
            return item[0];
        });
        var valueList = data1.map(function (item) {
            return item[1];
        });

        var  fzoption = {

            visualMap: [{
                show: false,
                type: 'continuous',
                seriesIndex: 0,
                min: 0,
                max: 400
            }, {
                show: false,
                type: 'continuous',
                seriesIndex: 1,
                dimension: 0,
                min: 0,
                max: dateList.length - 1
            }],


            title: [{
                left: 'center',
                text: '防治效果均值趋势'
            }, {
                top: '55%',
                left: 'center',
                text: '发生程度趋势'
            }],
            tooltip: {
                trigger: 'axis'
            },
            xAxis: [{
                data: dateList
            }, {
                data: dateList,
                gridIndex: 1
            }],
            yAxis: [{
                splitLine: {show: false}
            }, {
                splitLine: {show: false},
                gridIndex: 1
            }],
            grid: [{
                bottom: '60%'
            }, {
                top: '60%'
            }],
            series: [{
                type: 'line',
                showSymbol: false,
                data: valueList
            }, {
                type: 'line',
                showSymbol: false,
                data: valueList,
                xAxisIndex: 1,
                yAxisIndex: 1
            }]
        };
        var calendarHeatMapChart = echarts.init(document.getElementById('fzqs'));
        calendarHeatMapChart.setOption(fzoption);
        calendarHeatMapChart.on('click', function (params) {
            openDetailMod(params.value[0],params.value[0]);
        });
    }

    $(document).ready(function () {
        zb();
        hc();
        fz();
    });
</script>
</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
