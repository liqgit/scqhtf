<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<title>动物疫情周报导出</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="keywords"
	content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
<meta name="description" content="">
<meta name="author" content="ThemeBucket">
<link rel="shortcut icon" href="#" type="image/png">

<title>AdminX</title>
<link href="${pageContext.request.contextPath}/adminex/css/style.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/adminex/css/style-responsive.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/adminex/fonts/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/css/datepicker-custom.css" />


</head>
<body class="sticky-header">
	<section>
		<%@include file="menu.jsp"%>
		<div class="main-content">
			<%@include file="header.jsp"%>
			<div class="wrapper">
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading">动物疫情周报</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-md-3 form-group">
										<label class="control-label">报告内容计算时间</label>
										<div data-date-minviewmode="months" data-date-viewmode="years"
											class="input-group input-large custom-date-range "
											data-date="102/2012" data-date-format="yyyy-mm-dd">
											<input id="startDate" class="form-control dpd1 AQForm"
												name="from" placeholder="选择该周内任意一天">
											<div class="input-group-btn">
												<button class="btn btn-primary"
													onclick="initAdvancedQueryInput()">X</button>
												<button class="btn btn-info" onclick="exportHighcharts();">导出报告</button>
											</div>
										</div>
									</div>
								</div>
								<div id="processDiv"></div>

							</div>

						</div>


					</div>
				</div>
				<div class="wrapper" style="display: none">
					<div id="container"
						style="width: 700px; height: 400px; margin: 0 auto"></div>
					<div id="container_pie"
						style="min-width: 310px; height: 400px; margin: 0 auto"></div>
					<div id="container_bar"
						style="min-width: 310px; height: 400px; margin: 0 auto"></div>
					<div id="container_rr_pie"
						style="min-width: 310px; height: 400px; margin: 0 auto"></div>
					<form style="display: none" id="uploadData">
						<input name="startDateCondition" value="" id="in1"> <input
							name="svgString" value="" id="in2"> <input
							name="svgDiseaseDetailBar" value="" id="in3"> <input
							name="svgDiseaseHistoryBar" value="" id="in4"> <input
							name="svgReportReasonPie" value="" id="in5">
					</form>
				</div>


				<div class="panel panel-default">
				</div>


			</div>
		</div>
	</section>
	<script
		src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/typeahead/bootstrap3-typeahead.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
	<script
		src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>
	<!--common scripts for all pages -->
	<script
		src="${pageContext.request.contextPath}/echarts/build/dist/echarts-all.js"></script>
	<script
		src="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/js/bootstrap-datepicker.js?v=1"></script>
	<%--<!--pickers initialization-->--%>
	<script
		src="${pageContext.request.contextPath}/adminex/js/pickers-init.js"></script>

	<script src="${pageContext.request.contextPath}/hcharts/highcharts.js"></script>
	<script
		src="${pageContext.request.contextPath}/hcharts/highcharts-3d.js"></script>
	<script
		src="${pageContext.request.contextPath}/hcharts/modules/exporting.js"></script>
	<script type="application/javascript">
		
		
    function initAdvancedQueryInput(){
        $(".AQForm").val("");
    }




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
		var processDiv = $('#processDiv');

		var diseaseClassDataFlag = false;
		var diseaseDetailDataFlag = false;
		var continentDiseaseHistoryDataFlag = false;
		var reportReasonDataFlag = false;

		function initFlag() {
			diseaseClassDataFlag = false;
			diseaseDetailDataFlag = false;
			continentDiseaseHistoryDataFlag = false;
			reportReasonDataFlag = false;
		}

		function uploadSvgString() {

			var chart_pie = $("#container_pie").highcharts();
			var svg_diseaseClass_pie = chart_pie != null ? chart_pie.getSVG()
					: '';
			var chart_diseaseDetail_bar = $("#container").highcharts();
			var svg_diseaseDetail_bar = chart_diseaseDetail_bar != null ? chart_diseaseDetail_bar
					.getSVG()
					: '';
			var chart_bar = $("#container_bar").highcharts();
			var svg_bar = chart_bar != null ? chart_bar.getSVG() : '';
			var rr_pie = $("#container_rr_pie").highcharts();
			var svg_rr_pie = rr_pie != null ? rr_pie.getSVG() : '';
			var form = $("#uploadData");
			form.attr("method", "post");
			form.attr("action",
					'${pageContext.request.contextPath}/weeklyReportExport/importSvgString.post?'
							+ Date.parse(new Date()));
			var input1 = $("#in1");
			input1.attr("value", startDateCondition);
			var input2 = $("#in2");
			input2.attr("value", svg_diseaseClass_pie);
			var input3 = $("#in3");
			input3.attr("value", svg_diseaseDetail_bar);
			var input4 = $("#in4");
			input4.attr("value", svg_bar);
			var input5 = $("#in5");
			input5.attr("value", svg_rr_pie);
			form.submit();
			window.setTimeout(function() {
				changeProcessBar(100);
			}, 2000);
		}

		function checkSvgDataStatus() {
			return diseaseClassDataFlag === true
					&& diseaseDetailDataFlag === true
					&& continentDiseaseHistoryDataFlag === true
					&& reportReasonDataFlag === true;
		}

		function changeProcessBar(percent) {
			processDiv.empty();
			processDiv
					.append(''
							+ '<div class="progress progress-striped active progress-sm">'
							+ '<div id ="processBar" style="width: '
							+ percent
							+ '%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="45" role="progressbar" class="progress-bar progress-bar-success">'
							+ '<span class="sr-only">0% Complete</span>'
							+ '</div>' + '</div>');
		}

		function exportHighcharts() {
			initFlag();
			changeProcessBar(0);
			startDateCondition = $('#startDate').val();
			findDiseaseClassData(startDateCondition);
			findDiseaseDetailData(startDateCondition);
			findContinentDiseaseHistoryData(startDateCondition);
			findReportReasonData(startDateCondition);
			var flag = checkSvgDataStatus();
			window.setTimeout(function() {
				flag = checkSvgDataStatus();
				if (flag === true) {
					changeProcessBar(50);
					uploadSvgString();
				} else {
					var tv = window.setInterval(function() {
						flag = checkSvgDataStatus();
						if (flag === true) {
							changeProcessBar(50);
							uploadSvgString();
							window.clearInterval(tv);
						}
						console.info("notReady")
					}, 500)
				}
			}, 1000);

		}

		function findDiseaseClassData(startDateCondition) {
			$
					.post(
							'${pageContext.request.contextPath}/weeklyReportExport/findDiseaseClassData.do',
							{
								'startDateCondition' : startDateCondition
							},
							function(data) {
								if (data && data.length > 0) {
									var dataList = [];
									for (var i = 0; i < data.length; i++) {
										var param = [];
										param.push(data[i].diseaseClass);
										param.push(data[i].ttc);
										dataList.push(param)
									}
									diseaseClassPieOption.series[0].data = dataList;
									$('#container_pie').highcharts(
											diseaseClassPieOption);
									diseaseClassDataFlag = true
								}
							}, 'json');
		}

		function findDiseaseDetailData(startDateCondition) {
			$
					.post(
							'${pageContext.request.contextPath}/weeklyReportExport/findDiseaseDetailData.do',
							{
								'startDateCondition' : startDateCondition
							}, function(data) {
								if (data && data.length > 0) {
									var seriesList = [];
									for (var i = 0; i < data.length; i++) {
										seriesList.push(data[i])
									}
									diseaseOutbreakChars.series = seriesList;
									$('#container').highcharts(
											diseaseOutbreakChars);
									diseaseDetailDataFlag = true;
								}

							}, 'json');
		}

		function findContinentDiseaseHistoryData(startDateCondition) {
			$
					.post(
							'${pageContext.request.contextPath}/weeklyReportExport/findContinentDiseaseHistoryData.do',
							{
								'startDateCondition' : startDateCondition
							},
							function(data) {
								if (data && data.length > 0) {
									var dataList = [];
									for (var i = 0; i < data.length; i++) {
										var param = [];
										var name = data[i][0] + "/"
												+ data[i][1];
										var value = parseInt(data[i][2]);
										param.push(name);
										param.push(value);
										dataList.push(param)
									}
									diseaseHistoryCharOption.series[0].data = dataList;
									$('#container_bar').highcharts(
											diseaseHistoryCharOption);
									continentDiseaseHistoryDataFlag = true;
								}

							}, 'json');
		}

		function findReportReasonData(startDateCondition) {
			$
					.post(
							'${pageContext.request.contextPath}/weeklyReportExport/findReportReasonData.do',
							{
								'startDateCondition' : startDateCondition
							},
							function(data) {
								if (data && data.length > 0) {
									var dataList = [];
									for (var i = 0; i < data.length; i++) {
										var param = [];
										param.push(data[i].reason);
										param.push(data[i].num);
										dataList.push(param)
									}
									reportReasonPieOption.series[0].data = dataList;
									$('#container_rr_pie').highcharts(
											reportReasonPieOption);
									reportReasonDataFlag = true;
								}

							}, 'json');
		}
	</script>
</body>
</html>
<script
	src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
