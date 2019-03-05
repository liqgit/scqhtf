
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="keywords"
          content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>OIE疫情概览</title>

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
        <div class="page-heading"></div>
        <!--body wrapper start-->
        <div class="wrapper">
            <div class="row">
                <div class="col-md-6">
                    <!--statistics start-->
                    <div class="row state-overview">
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <div class="panel purple">
                                <div class="symbol">
                                    <i class="fa fa-gavel"></i>
                                </div>
                                <div class="state-value">
                                    <div class="value" id="odo">0</div>
                                    <div class="title">昨日疫情爆发次数</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <div class="panel red">
                                <div class="symbol">
                                    <i class="fa fa-tags"></i>
                                </div>
                                <div class="state-value">
                                    <div class="value" id="odr">0</div>
                                    <div class="title">昨日新增报告数</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row state-overview">
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <div class="panel blue">
                                <div class="symbol">
                                    <i class="fa fa-money"></i>
                                </div>
                                <div class="state-value">
                                    <div class="value" id="sdo">0</div>
                                    <div class="title">7日内累计爆发次数</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-xs-12 col-sm-6">
                            <div class="panel green">
                                <div class="symbol">
                                    <i class="fa fa-eye"></i>
                                </div>
                                <div class="state-value" >
                                    <div class="value" id="tdo">0</div>
                                    <div class="title">30日内累计爆发次数</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--statistics end-->
                </div>

                <div class="col-md-6"  >
                    <div class="panel" id="diseaseClassPie" style="height:260px"></div>
                </div>
            </div>
            <!--row end-->

            <div class="row">
                <div class="col-md-12" >
                    <div class="panel panel-default" >
                        <div class="panel-body" >
                            <div id="calendarHeatMap" style="height:650px;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12" >
                    <div class="panel" >
                        <div class="panel-body" >
                            <div id="diseaseScatter" style="height:650px"></div>
                            <div id="sliders" >
                                <table class="col-lg-12">
                                    <tr class="col-lg-4">
                                        <td><b>α 角（内旋转角）</b></td>
                                        <td><label for="alpha"></label><input id="alpha" type="range" min="0" max="90" value="0"/> <span id="alpha-value" class="value"></span></td>



                                    </tr>
                                    <tr class="col-lg-4">
                                        <td><b>β 角（外旋转角）</b></td>
                                        <td><label for="beta"></label><input id="beta" type="range" min="-90" max="90" value="0"/> <span id="beta-value" class="value"></span></td>
                                    </tr>
                                    <tr class="col-lg-4">
                                        <td><button class="btn btn-info " type="button" id="resetRange"><i class="fa fa-refresh"></i> 重置视角</button></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" >
                    <div class="panel panel-default" >
                        <div class="panel-body" >
                            <div id="containers"></div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="modal fade col-lg-12" id="detailMod" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
            <div class="modal-dialog" style="width:1000px">
                <div class="modal-content">
                    <div class="modal-header" style="background-color: #2a6496">
                        <button type="button" class="close" data-dismiss="modal">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel">
                            疫情列表
                        </h4>
                    </div>
                    <div>
                        <section id="unseen">
                            <table class="table table-bordered table-striped table-condensed">
                                <thead>
                                <tr>
                                    <th>疫病名称</th>
                                    <th>疫病分类</th>
                                    <th>国家名称</th>
                                    <th class="numeric">公布时间</th>
                                    <th class="numeric">公布理由</th>
                                    <th class="numeric">爆发次数</th>
                                    <th class="numeric">解决时间</th>
                                    <th class="numeric">操作</th>
                                </tr>
                                </thead>
                                <tbody align="center" id="epidemicData">
                                </tbody>
                                <tfoot>
                                <tr>
                                    <div class="dataTables_paginate paging_bootstrap pagination">
                                        <ul id="demo1"></ul>
                                    </div>
                                </tr>
                                </tfoot>
                            </table>
                        </section>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>


        <!--body wrapper end-->
    </div>
    <!-- main content end-->
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

    var dateIntervalCondition = 30;

    function openDetailMod(startDate,endDate,epidemicClass) {
        getEpidemicEventList(startDate,endDate,epidemicClass);
    }


    var diseasePieChart = null;
    var diseaseClassPieOption={chart:{type:'pie',plotBackgroundColor:'#49586e',plotBorderWidth:null,plotShadow:false,
        spacing:[0,0,0,0],options3d:{enabled:true},height:260},credits:{enabled:false},legend:{verticalAlign:'middle',
        backgroundColor:'#49586e',layout:"vertical",floating:true,itemStyle:{color:'#ffffff',fontWeight:'normal',
            fontSize:'18px'},itemDistance:50,x:200},title:{floating:true,align:'center',y:1000,text:'上周各类疫情爆发占比',
        style:{color:'#ffffff',fontSize:'20px',fontWeight:"bold"},widthAdjust:-1000},tooltip:{pointFormat:'占比 <b>' +
    '{point.percentage:.1f}%</b>'},plotOptions:{pie:{innerSize:"70%",allowPointSelect:true,cursor:'pointer',dataLabels:
        {enabled:false},showInLegend:true,point:{events:{mouseOver:function(e){diseasePieChart.setTitle({text:e.target.name+
    '疫情共爆发'+e.target.y+' 起\n',style:{color:'#ffffff',fontSize:'20px',fontWeight:"bold"},widthAdjust:-300})},
        click:function(e){openDetailMod(null,null,e.point.name)}}}}},series:[{type:'pie',data:[]}]};

    var myDate = new Date();
    var calendarHeatMapOption={title: {
        text: '疫情日历',
        left: 'center'
    },tooltip:{position:'top',formatter:function(params){return params.marker+params.value[0]+
        ' 共爆发 '+params.value[1]+' 例疫情 '}},visualMap:{min:0,max:500,calculable:true,orient:'horizontal',left:'center'
        ,top:'bottom'},calendar:[{range:myDate.getFullYear()-2,cellSize:setCalendarSize(),dayLabel:{firstDay:1,nameMap:'cn'},monthLabel:
        {nameMap:'cn'}},{top:260,range:myDate.getFullYear()-1,cellSize:setCalendarSize(),dayLabel:{firstDay:1,nameMap:'cn'},monthLabel:
        {nameMap:'cn'}},{top:450,range:myDate.getFullYear(),cellSize:setCalendarSize(),dayLabel:{firstDay:1,nameMap:'cn'},monthLabel:
        {nameMap:'cn'}}],series:[]};

    var ele = "";

    var diseaseScatterOption = {
        chart: {
            margin: 70,
            type: 'scatter',
            options3d: {
                enabled: true,
                alpha: 0,
                beta: 0,
                depth: 250,
                viewDistance: 5,
                frame: {
                    bottom: { size: 1, color: 'rgba(0,0,0,0.2)' },
                    back: { size: 1, color: 'rgba(0,0,0,0.05)' },
                    side: { size: 5, color: 'rgba(0,0,0,0.15)' }
                }
            },
            events: {
                click: function (evt) {
                    ele = evt;
                    var obj =evt.target;
                    if (!obj) return;
                    obj = (obj.nodeName && obj.nodeName==='g')?obj:(obj.parentNode&&obj.parentNode.nodeName &&
                        obj.parentNode.nodeName==='g')?obj.parentNode:obj.parentNode.parentNode;
                    if (obj&&obj.nodeName && obj.nodeName==='g'){
                        console.info(obj.className)
                    }
                }
            },
            zoomType: 'xy'
        },
        credits:{enabled:false},
        title: {
            text: '<b>疫情历史爆发情况分布</b>'
        },
        tooltip: {
            formatter: function () {
                return this.key+'<br/> 共发生: <b>' + this.x +
                    '</b>次<br/> 被报告: <b>' + this.y + '</b>次<br/>' +
                    '共涉及: <b>'+this.point.z+'个国家</b>';
            }
        },
        colors:$.map(Highcharts.getOptions().colors, function (color) {
            return {
                radialGradient: {
                    cx: 0.4,
                    cy: 0.3,
                    r: 0.5
                },
                stops: [
                    [0, color],
                    [1, Highcharts.Color(color).brighten(-0.2).get('rgb')]
                ]
            };
        }),

        plotOptions: {
            scatter: {
                width: 10,
                height: 10,
                depth: 10
            }
        },
        yAxis: {

            min: 0,
            title:{
                text:'<b>报告次数</b>'
            },

        },
        xAxis: {
            tickPixelInterval: 10,
            breaks: [],
            min: 0,
            title:{
                text:'<b>爆发次数</b>'
            },
            visible:true

        },
        zAxis: {
            min: 0,
            title:{
                text:'<b>影响国家</b>',
                x:0
            },
            offset:-20
        },
        legend: {
            enabled: false
        },
        series: [{
            colorByPoint: true,
            data:[]
        },{
            marker:{symbol:'square'},
            colorByPoint: true,
            data:[]
        },{
            marker:{symbol:'triangle'},
            colorByPoint: true,
            data:[]
        }]
    };


    function setCalendarSize() {
        var wn = document.body.clientWidth;
        if(wn>=1450){
            return [20,20]
        }else if (1450>wn&&wn>=1200){
            return ['auto',15]
        }else {
            return ['auto',10]
        }
    }





    function findDiseaseClassData() {
        $.post('${pageContext.request.contextPath}/oieDashboard/getDiseaseClassPieData.do', {
                'dateInterval': dateIntervalCondition
            },
            function (data) {
                if (data){
                    if(data.length<=0){diseaseClassPieOption.title.text="上周无新增疫情爆发报告"}

                    var dataList = [];
                    for (var i=0;i<data.length;i++){
                        var param = [];
                        var diseaseName = data[i]['diseaseClass'];
                        param.push(diseaseName);
                        param.push(data[i]['ttc']);
                        dataList.push(param)
                    }
                    diseaseClassPieOption.series[0].data=dataList;
                    $('#diseaseClassPie').highcharts(diseaseClassPieOption,function(c) {
                        var centerY = c.series[0].center[1],
                            titleHeight = parseInt(c.title.styles.fontSize);
                        c.setTitle({
                            y:centerY + titleHeight/2
                        });
                        diseasePieChart = c;
                    });
                }
            }, 'json');
    }

    function findCalendarHeatMapData() {
        $.post('${pageContext.request.contextPath}/oieDashboard/getCalendarHeatMapData.do',
            null,
            function (data) {
                if (data){
                    for (var key in data){
                        var yearDataList = data[key];
                        var CHSeriesOption = {
                            type: 'heatmap',
                            coordinateSystem: 'calendar',
                            calendarIndex: 0,
                            data: []
                        };
                        CHSeriesOption.data=yearDataList;
                        CHSeriesOption.calendarIndex=calendarHeatMapOption.series.length;
                        calendarHeatMapOption.series.push(CHSeriesOption);
                    }
                }
                var calendarHeatMapChart = echarts.init(document.getElementById('calendarHeatMap'));
                calendarHeatMapChart.setOption(calendarHeatMapOption);
                calendarHeatMapChart.on('click', function (params) {
                    openDetailMod(params.value[0],params.value[0]);
                });

            }, 'json');
    }


    function getEpidemicEventList(startDate,endDate,epidemicClass) {
        $.post('${pageContext.request.contextPath}/oieDashboard/getDiseaseEventListData.do', {
            'startDate': startDate,
            'endDate': endDate,
            'epidemicClass':epidemicClass
        }, function (data) {
            $("#demo1").jqPaginator({
                totalPages: Math.ceil(data['epidemicEventListCount'] / 10),
                visiblePages: 10,
                currentPage: 1,
                first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
                prev: '<li class="prev"><a href="javascript:void(0);">上一页<\/a><\/li>',
                next: '<li class="next"><a href="javascript:void(0);">下一页<\/a><\/li>',
                last: '<li class="last"><a href="javascript:void(0);">尾页<\/a><\/li>',
                page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
                onPageChange: function (n) {
                    $("#epidemicData").empty();
                    $.post('${pageContext.request.contextPath}/oieDashboard/getDiseaseEventListData.do', {
                        'startDate': startDate,
                        'endDate': endDate,
                        'epidemicClass':epidemicClass,
                        'pageNo': n - 1
                    }, function (data) {
                        var epidemicAppearList = data['epidemicEventList'];
                        for (var i = 0; i < epidemicAppearList.length; i++) {
                            var tr="";
                            var td = "<td style=''></td>";
                            if(epidemicAppearList[i]['epidemicNameCn']&&epidemicAppearList[i]['epidemicNameCn'] !==""){
                                td = "<td style=''><p>"+epidemicAppearList[i]['epidemicNameCn'] + "</p><p>" + epidemicAppearList[i]['epidemicNameEng']+"</p></td>";
                                tr = tr+td;
                            }else {
                                td= "<td style=''><p>" + epidemicAppearList[i].disease + "</p></td>";
                                tr = tr+td;
                            }
                            tr = tr+"<td style=''><p>" + epidemicAppearList[i]['diseaseClass'] + "</p></td>";
                            if (epidemicAppearList[i]['regionNameCn']){
                                td = "<td style=''><p>" + epidemicAppearList[i]['regionNameCn'] + "</p><p>" + epidemicAppearList[i]['regionNameEng'] +"</p></td>";
                                tr = tr+td;
                            }else {
                                td= "<td style=''><p>" + epidemicAppearList[i]['country'] + "</p></td>";
                                tr = tr+td;
                            }
                            tr = tr+"<td style=''><p>" + epidemicAppearList[i]['date'] + "</p></td>" +
                                "<td style=''><p>" + epidemicAppearList[i]['reason'] + "</p></td>" +
                                "<td style=''><p>" + epidemicAppearList[i]['outbreaks'] + "</p></td>" +
                                "<td style=''><p>" + epidemicAppearList[i]['dateRes'] + "</p></td>" +
                                "<td style=''>" + "<a href='${pageContext.request.contextPath}/oieEpidemicSearch/toOIEDetailPage.do?rowKey=" + epidemicAppearList[i]['report'] + "'><button class='btn btn-primary'>详细信息</button></a>" + "</td>" + "</tr>";
                            $("#epidemicData").append("<tr>"+tr+"</tr>");
                            $("td").css("vertical-align","middle");
                            $('#detailMod').modal('show').css({
                                width: 'auto'
                            })
                        }
                    }, 'json');
                }
            });


        }, 'json');
    }



    function findDiseaseScatterData() {
        $.post('${pageContext.request.contextPath}/oieDashboard/getDiseaseScatterData.do', {
                'dateInterval': dateIntervalCondition
            },
            function (data) {
                if (data){
                    diseaseScatterOption.series[0].data=data['aClass'];
                    diseaseScatterOption.series[1].data=data['bClass'];
                    diseaseScatterOption.series[2].data=data['cClass'];
                    var top10d = data['top10'];
                    for(var i=0;i<9;i++){
                        if(top10d[i]-top10d[i+1]>700){
                            diseaseScatterOption.xAxis.breaks.push({from:top10d[i+1]+50,to:top10d[i]-50})
                        }
                    }
                    var diseaseScatterChart = new Highcharts.Chart("diseaseScatter",diseaseScatterOption);
                    $('#sliders').find('input').on('input change', function () {
                        diseaseScatterChart.options.chart.options3d[this.id] = this.value;
                        showValues(diseaseScatterChart);
                        diseaseScatterChart.redraw(false);
                    });
                    $('#resetRange').on('click',function () {
                        $('#alpha').val(0);
                        $('#beta').val(0);
                        diseaseScatterChart.options.chart.options3d['alpha'] = 0;
                        diseaseScatterChart.options.chart.options3d['beta'] = 0;
                        showValues(diseaseScatterChart);
                        diseaseScatterChart.redraw(false);
                    });
                    showValues(diseaseScatterChart);
                }
            }, 'json');
    }

    function showValues(sChart) {
        $('#alpha-value').html(sChart.options.chart.options3d.alpha);
        $('#beta-value').html(sChart.options.chart.options3d.beta);
    }


    function findTotalOutbreaksAndReportOfDays() {
        $.post('${pageContext.request.contextPath}/oieDashboard/findGeneralFormData.do', null,
            function (data) {
                if (data){
                    var odo = data['oneDay'][0]?data['oneDay'][0]:0;
                    var odr = data['oneDay'][1]?data['oneDay'][1]:0;
                    var sdo = data['sevenDay'][0]?data['sevenDay'][0]:0;
                    var tdo = data['thirtyDay'][0]?data['thirtyDay'][0]:0;
                    $('#odo').text(odo);
                    $('#odr').text(odr);
                    $('#sdo').text(sdo);
                    $('#tdo').text(tdo);
                }
            }, 'json');
    }


    $(document).ready(function () {
        findTotalOutbreaksAndReportOfDays();
        findDiseaseClassData();
        findCalendarHeatMapData();
        findDiseaseScatterData();

    });

    $(function () {
        var aar =[];
        <c:forEach items="${diseases}" var="ss">
        aar.push('${ss}');
        </c:forEach>
        var arr = [];
        <c:forEach items="${outbreaks}" var="rr">
        arr.push(parseInt('${rr}'));
        </c:forEach>
        // Set up the chart
        new Highcharts.Chart({
            chart: {
                renderTo: 'containers',
                type: 'column',
                options3d: {
                    enabled: true,
                    alpha: 0,
                    beta: 0,
                    depth: 50,
                    viewDistance: 25
                }
            },
            colors:$.map(Highcharts.getOptions().colors, function (color) {
                return {
                    radialGradient: {
                        cx: 0.4,
                        cy: 0.3,
                        r: 0.5
                    },
                    stops: [
                        [0, color],
                        [1, Highcharts.Color(color).brighten(-0.2).get('rgb')]
                    ]
                };
            }),
            credits:{enabled:false},
            title: {
                text: '<b>三十天内疫情爆发次数TOP10</b>'
            },
            plotOptions: {
                column: {
                    depth: 25
                }
            },
            xAxis: {
                categories:aar
            },
            yAxis:{
              title:{
                  text:'爆发次数'
              }
            },
            legend: {
                enabled: false
            },
            series: [{
                colorByPoint: true,
                name:'疫情爆发次数',
                data: arr
            }]
        });
    });

</script>
</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
