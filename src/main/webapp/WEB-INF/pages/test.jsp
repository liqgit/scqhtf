<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="keywords"
          content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">
    <title>导出word</title>

</head>
<body>

    <div>
        <input type="hidden" name="svg" id="svg" />
        <input id="btn_word" type="button" value="导出word" onclick="exportHighcharts('word');"/>
        <input id="btn_pdf" type="button" value="导出pdf" onclick="exportHighcharts('pdf');"/>
    </div>


    <div id="container" style="width:700px; height: 400px; margin: 0 auto"></div>
    <div id="container_pie" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
    <div id="container_bar" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
    <div id="container_rr_pie" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/hcharts/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/hcharts/highcharts-3d.js"></script>
<script src="${pageContext.request.contextPath}/hcharts/modules/exporting.js"></script>
<script type="text/javascript">

    Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
        return {
            radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
            stops: [
                [0, color],
                [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
            ]
        };
    });

    $(function() {
        Highcharts.wrap(Highcharts.Chart.prototype, 'getSVG', function (proceed) {
            return proceed.call(this)
                .replace(
                    /(fill|stroke)="rgba([0−9]+,[0−9]+,[0−9]+),([0−9.]+)"/g,
                    '$1="rgb($2)" $1-opacity="$3"'
                );
        });
    });

    var diseaseOutbreakChars = {
        chart: {
            colorCount:20,
            type: 'column',
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 25
            }
        },
        credits: {
            enabled: false
        },
        title: {
            text: '疫情爆发情况'
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            min: 0,
            title: {
                text: '爆发次数'
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'top',
            y: 25,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                depth: 25
            },
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    style: {
                        textShadow: '0 0 3px black'
                    }
                }
            }

        },
        series:[]
    };


    var diseaseClassPieOption = {

        chart : {
            plotBackgroundColor : null,
            plotBorderWidth : null,
            plotShadow : false
        },
        credits: {
            enabled: false
        },
        title : {
            text : '疫情类别占比'
        },
        plotOptions : {
            pie : {
                allowPointSelect : true,
                cursor : 'pointer',
                dataLabels : {
                    enabled : true,
                    color : '#000000',
                    connectorColor : '#000000',
                    format : '{point.percentage:.0f} %'
                },
                showInLegend:true
            }

        },
        series : [ {
            type : 'pie',
            name : 'Browser share',
            data : []
        } ]
    };

    var reportReasonPieOption = {

        chart : {
            plotBackgroundColor : null,
            plotBorderWidth : null,
            plotShadow : false
        },
        credits: {
            enabled: false
        },
        title : {
            text : '疫情公布理由占比'
        },
        plotOptions : {
            pie : {
                allowPointSelect : true,
                cursor : 'pointer',
                dataLabels : {
                    enabled : true,
                    color : '#000000',
                    connectorColor : '#000000',
                    format : '{point.percentage:.0f} %'
                },
                showInLegend:true
            }

        },
        series : [ {
            type : 'pie',
            name : 'Browser share',
            data : []
        } ]
    };

    var diseaseHistoryCharOption={
        chart: {
            type: 'column'
        },
        credits: {
            enabled: false
        },
        title: {
            text: '疫病在该国累计爆发次数'
        },
        xAxis: {
            type: 'category',
            labels: {
                staggerLines:2
            }

        },
        yAxis: {
            title: {
                text: '爆发次数'
            }
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true
                }
            }
        },
        series: [{
            colorByPoint: true,
            data: [
            ]
        }]
    }
</script>
<script type="text/javascript">
    var DiseaseClassData = false;
    function exportHighcharts(){
        var chart_pie = $("#container_pie").highcharts();
        var svg_diseaseClass_pie = chart_pie.getSVG();
        var chart_diseaseDetail_bar = $("#container").highcharts();
        var svg_diseaseDetail_bar = chart_diseaseDetail_bar.getSVG();
        var chart_bar = $("#container_bar").highcharts();
        var svg_bar = chart_bar.getSVG();
        var rr_pie = $("#container_rr_pie").highcharts();
        var svg_rr_pie = rr_pie.getSVG();
        $.post('${pageContext.request.contextPath}/test/importSvgString.do', {
            'svgString': svg_diseaseClass_pie,
            'svgDiseaseDetailBar':svg_diseaseDetail_bar,
            'svgDiseaseHistoryBar':svg_bar,
            "svgReportReasonPie":svg_rr_pie
        }, function (data) {

        }, 'json');
    }

    function findDiseaseClassData() {
        $.post('${pageContext.request.contextPath}/test/findDiseaseClassData.do', null,
            function (data) {
            var dataList = [];
            for (var i=0;i<data.length;i++){
                var param = [];
                param.push(data[i].diseaseClass);
                param.push(data[i].ttc);
                dataList.push(param)
            }
            diseaseClassPieOption.series[0].data=dataList;
            $('#container_pie').highcharts(diseaseClassPieOption);
            }, 'json');
    }

    function findDiseaseDetailData() {
        $.post('${pageContext.request.contextPath}/test/findDiseaseDetailData.do', null,
            function (data) {
                var seriesList = [];
                for (var i=0;i<data.length;i++){
                    seriesList.push(data[i])
                }
                console.info(seriesList);
                diseaseOutbreakChars.series=seriesList;
                $('#container').highcharts(diseaseOutbreakChars);
            }, 'json');
    }

    function findContinentDiseaseHistoryData() {
        $.post('${pageContext.request.contextPath}/test/findContinentDiseaseHistoryData.do', null,
            function (data) {
                var dataList = [];
                for (var i=0;i<data.length;i++){
                    var param = [];
                    var name = data[i][0]+"/"+data[i][1];
                    var value = parseInt(data[i][2]);
                    param.push(name);
                    param.push(value);
                    dataList.push(param)
                }
                console.info(dataList);
                diseaseHistoryCharOption.series[0].data=dataList;
                $('#container_bar').highcharts(diseaseHistoryCharOption);
            }, 'json');
    }

    function findReportReasonData() {
        $.post('${pageContext.request.contextPath}/test/findReportReasonData.do', null,
            function (data) {
                var dataList = [];
                for (var i=0;i<data.length;i++){
                    var param = [];
                    param.push(data[i].reason);
                    param.push(data[i].num);
                    dataList.push(param)
                }
                reportReasonPieOption.series[0].data=dataList;
                $('#container_rr_pie').highcharts(reportReasonPieOption);
            }, 'json');

    }

    $(document).ready(function () {
        findDiseaseClassData();
        findDiseaseDetailData();
        findContinentDiseaseHistoryData();
        findReportReasonData();
    });
</script>

</html>