<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 2017/9/19
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>动物疫情传播路径</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="keywords"
          content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>AdminX</title>


    <!--common-->
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/fonts/css/font-awesome.min.css" rel="stylesheet">


    <!--pickers css-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/css/datepicker-custom.css"/>
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
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            动物疫情传播路径
                        </div>

                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-3 form-group">
                                    <label class="control-label">疫病名称</label>
                                    <input type="text" id='epidemicName' class="form-control AQForm" placeholder="疫病名称">
                                </div>
                                <div class="col-lg-3 form-group">
                                    <label class="control-label">时间段</label>
                                    <div data-date-minviewmode="months" data-date-viewmode="years"
                                         class="input-group input-large custom-date-range " data-date="102/2012"
                                         data-date-format="yyyy-mm-dd">
                                        <input type="text" id="startDate" class="form-control dpd1 AQForm" name="from">
                                        <span class="input-group-addon">到</span>
                                        <input type="text" id="endDate" class="form-control dpd2 AQForm" name="to">
                                    </div>

                                </div>
                            </div>
                            <div>
                                <button class="btn btn-primary" onclick="search();">查询</button>
                            </div>


                        </div>

                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <section class="panel">
                                <header class="panel-heading">
                                    查询结果
                                    <span class="tools pull-right">
                                <%--<button class="btn btn-primary" onclick="exExcel()">--%>
                                <%--导出excel--%>
                                <%--</button>--%>
                            </span>
                                </header>
                                <div class="panel-body" style="display: block;">
                                    <div id="oiePathChart" style="width: 100%;height: 600px;"></div>
                                </div>
                            </section>
                        </div>
                    </div>

                </div>
            </div>

            <%--</div>--%>


            <!--body wrapper end-->
        </div>
    </div>
    <!-- main content end-->
</section>
<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/typeahead/bootstrap3-typeahead.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>
<!--common scripts for all pages -->
<%--<script src="${pageContext.request.contextPath}/adminex/js/scripts.js"></script>--%>
<script src="${pageContext.request.contextPath}/echarts/build/dist/echarts-all.js"></script>
<script src="${pageContext.request.contextPath}/angularjs/angular.min.js"></script>

<%--<!--pickers plugins-->--%>
<%--<script type="text/javascript"--%>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/js/bootstrap-datepicker.js?v=1"></script>

<%--<!--pickers initialization-->--%>
<script src="${pageContext.request.contextPath}/adminex/js/pickers-init.js"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/jqPaginator/dist/1.2.0/jqPaginator.min.js"></script>


<script type="text/javascript">

    function getCookie(name){
        var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
        if(arr) return unescape(arr[2]); return null;
    }

    function setCookie(name, value, expire){
        var exp = new Date();
        expire = expire ? expire : 30;
        exp.setTime(exp.getTime() + expire*24*60*60*1000);
        document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
    }

 	var myChart = null;
    function getGeo() {
        var nameMap ={'Afghanistan':'阿富汗','Angola':'安哥拉','Albania':'阿尔巴尼亚','United Arab Emirates':'阿联酋','Argentina':'阿根廷','Armenia':'亚美尼亚','French Southern and Antarctic Lands':'法属南半球和南极领地','Australia':'澳大利亚','Austria':'奥地利','Azerbaijan':'阿塞拜疆','Burundi':'布隆迪','Belgium':'比利时','Benin':'贝宁','Burkina Faso':'布基纳法索','Bangladesh':'孟加拉国','Bulgaria':'保加利亚','The Bahamas':'巴哈马','Bosnia and Herzegovina':'波斯尼亚和黑塞哥维那','Belarus':'白俄罗斯','Belize':'伯利兹','Bermuda':'百慕大','Bolivia':'玻利维亚','Brazil':'巴西','Brunei':'文莱','Bhutan':'不丹','Botswana':'博茨瓦纳','Central African Republic':'中非共和国','Canada':'加拿大','Switzerland':'瑞士','Chile':'智利','China':'中国','Ivory Coast':'象牙海岸','Cameroon':'喀麦隆','Democratic Republic of the Congo':'刚果民主共和国','Republic of the Congo':'刚果共和国','Colombia':'哥伦比亚','Costa Rica':'哥斯达黎加','Cuba':'古巴','Northern Cyprus':'北塞浦路斯','Cyprus':'塞浦路斯','Czech Republic':'捷克共和国','Germany':'德国','Djibouti':'吉布提','Denmark':'丹麦','Dominican Republic':'多明尼加共和国','Algeria':'阿尔及利亚','Ecuador':'厄瓜多尔','Egypt':'埃及','Eritrea':'厄立特里亚','Spain':'西班牙','Estonia':'爱沙尼亚','Ethiopia':'埃塞俄比亚','Finland':'芬兰','Fiji':'斐济','Falkland Islands':'福克兰群岛','France':'法国','Gabon':'加蓬','United Kingdom':'英国','Georgia':'格鲁吉亚','Ghana':'加纳','Guinea':'几内亚','Gambia':'冈比亚','Guinea Bissau':'几内亚比绍','Equatorial Guinea':'赤道几内亚','Greece':'希腊','Greenland':'格陵兰','Guatemala':'危地马拉','French Guiana':'法属圭亚那','Guyana':'圭亚那','Honduras':'洪都拉斯','Croatia':'克罗地亚','Haiti':'海地','Hungary':'匈牙利','Indonesia':'印尼','India':'印度','Ireland':'爱尔兰','Iran':'伊朗','Iraq':'伊拉克','Iceland':'冰岛','Israel':'以色列','Italy':'意大利','Jamaica':'牙买加','Jordan':'约旦','Japan':'日本','Kazakhstan':'哈萨克斯坦','Kenya':'肯尼亚','Kyrgyzstan':'吉尔吉斯斯坦','Cambodia':'柬埔寨','South Korea':'韩国','Kosovo':'科索沃','Kuwait':'科威特','Laos':'老挝','Lebanon':'黎巴嫩','Liberia':'利比里亚','Libya':'利比亚','Sri Lanka':'斯里兰卡','Lesotho':'莱索托','Lithuania':'立陶宛','Luxembourg':'卢森堡','Latvia':'拉脱维亚','Morocco':'摩洛哥','Moldova':'摩尔多瓦','Madagascar':'马达加斯加','Mexico':'墨西哥','Macedonia':'马其顿','Mali':'马里','Myanmar':'缅甸','Montenegro':'黑山','Mongolia':'蒙古','Mozambique':'莫桑比克','Mauritania':'毛里塔尼亚','Malawi':'马拉维','Malaysia':'马来西亚','Namibia':'纳米比亚','New Caledonia':'新喀里多尼亚','Niger':'尼日尔','Nigeria':'尼日利亚','Nicaragua':'尼加拉瓜','Netherlands':'荷兰','Norway':'挪威','Nepal':'尼泊尔','New Zealand':'新西兰','Oman':'阿曼','Pakistan':'巴基斯坦','Panama':'巴拿马','Peru':'秘鲁','Philippines':'菲律宾','Papua New Guinea':'巴布亚新几内亚','Poland':'波兰','Puerto Rico':'波多黎各','North Korea':'北朝鲜','Portugal':'葡萄牙','Paraguay':'巴拉圭','Qatar':'卡塔尔','Romania':'罗马尼亚','Russia':'俄罗斯','Rwanda':'卢旺达','Western Sahara':'西撒哈拉','Saudi Arabia':'沙特阿拉伯','Sudan':'苏丹','South Sudan':'南苏丹','Senegal':'塞内加尔','Solomon Islands':'所罗门群岛','Sierra Leone':'塞拉利昂','El Salvador':'萨尔瓦多','Somaliland':'索马里兰','Somalia':'索马里','Republic of Serbia':'塞尔维亚共和国','Suriname':'苏里南','Slovakia':'斯洛伐克','Slovenia':'斯洛文尼亚','Sweden':'瑞典','Swaziland':'斯威士兰','Syria':'叙利亚','Chad':'乍得','Togo':'多哥','Thailand':'泰国','Tajikistan':'塔吉克斯坦','Turkmenistan':'土库曼斯坦','East Timor':'东帝汶','Trinidad and Tobago':'特里尼达和多巴哥','Tunisia':'突尼斯','Turkey':'土耳其','United Republic of Tanzania':'坦桑尼亚联合共和国','Uganda':'乌干达','Ukraine':'乌克兰','Uruguay':'乌拉圭','United States of America':'美国','Uzbekistan':'乌兹别克斯坦','Venezuela':'委内瑞拉','Vietnam':'越南','Vanuatu':'瓦努阿图','West Bank':'西岸','Yemen':'也门','South Africa':'南非','Zambia':'赞比亚','Zimbabwe':'津巴布韦'};
        var geoCoord ={'Afghanistan':[67.709953,33.93911],'Angola':[17.873887,-11.202692],'Albania':[20.168331,41.153332],'United Arab Emirates':[53.847818,23.424076],'Argentina':[-63.61667199999999,-38.416097],'Armenia':[45.038189,40.069099],'French Southern and Antarctic Lands':[69.348557,-49.280366],'Australia':[133.775136,-25.274398],'Austria':[14.550072,47.516231],'Azerbaijan':[47.576927,40.143105],'Burundi':[29.918886,-3.373056],'Belgium':[4.469936,50.503887],'Benin':[2.315834,9.30769],'Burkina Faso':[-1.561593,12.238333],'Bangladesh':[90.356331,23.684994],'Bulgaria':[25.48583,42.733883],'The Bahamas':[-77.39627999999999,25.03428],'Bosnia and Herzegovina':[17.679076,43.915886],'Belarus':[27.953389,53.709807],'Belize':[-88.49765,17.189877],'Bermuda':[-64.7505,32.3078],'Bolivia':[-63.58865299999999,-16.290154],'Brazil':[-51.92528,-14.235004],'Brunei':[114.727669,4.535277],'Bhutan':[90.433601,27.514162],'Botswana':[24.684866,-22.328474],'Central African Republic':[20.939444,6.611110999999999],'Canada':[-106.346771,56.130366],'Switzerland':[8.227511999999999,46.818188],'Chile':[-71.542969,-35.675147],'China':[104.195397,35.86166],'Ivory Coast':[-5.547079999999999,7.539988999999999],'Cameroon':[12.354722,7.369721999999999],'Democratic Republic of the Congo':[21.758664,-4.038333],'Republic of the Congo':[15.827659,-0.228021],'Colombia':[-74.297333,4.570868],'Costa Rica':[-83.753428,9.748916999999999],'Cuba':[-77.781167,21.521757],'Northern Cyprus':[33.429859,35.126413],'Cyprus':[33.429859,35.126413],'Czech Republic':[15.472962,49.81749199999999],'Germany':[10.451526,51.165691],'Djibouti':[42.590275,11.825138],'Denmark':[9.501785,56.26392],'Dominican Republic':[-70.162651,18.735693],'Algeria':[1.659626,28.033886],'Ecuador':[-78.18340599999999,-1.831239],'Egypt':[30.802498,26.820553],'Eritrea':[39.782334,15.179384],'Spain':[-3.74922,40.46366700000001],'Estonia':[25.013607,58.595272],'Ethiopia':[40.489673,9.145000000000001],'Finland':[25.748151,61.92410999999999],'Fiji':[178.065032,-17.713371],'Falkland Islands':[-59.523613,-51.796253],'France':[2.213749,46.227638],'Gabon':[11.609444,-0.803689],'United Kingdom':[-3.435973,55.378051],'Georgia':[43.356892,42.315407],'Ghana':[-1.023194,7.946527],'Guinea':[-9.696645,9.945587],'Gambia':[-15.310139,13.443182],'Guinea Bissau':[-15.180413,11.803749],'Equatorial Guinea':[10.267895,1.650801],'Greece':[21.824312,39.074208],'Greenland':[-42.604303,71.706936],'Guatemala':[-90.23075899999999,15.783471],'French Guiana':[-53.125782,3.933889],'Guyana':[-58.93018,4.860416],'Honduras':[-86.241905,15.199999],'Croatia':[15.2,45.1],'Haiti':[-72.285215,18.971187],'Hungary':[19.503304,47.162494],'Indonesia':[113.921327,-0.789275],'India':[78.96288,20.593684],'Ireland':[-8.24389,53.41291],'Iran':[53.688046,32.427908],'Iraq':[43.679291,33.223191],'Iceland':[-19.020835,64.963051],'Israel':[34.851612,31.046051],'Italy':[12.56738,41.87194],'Jamaica':[-77.297508,18.109581],'Jordan':[36.238414,30.585164],'Japan':[138.252924,36.204824],'Kazakhstan':[66.923684,48.019573],'Kenya':[37.906193,-0.023559],'Kyrgyzstan':[74.766098,41.20438],'Cambodia':[104.990963,12.565679],'South Korea':[127.766922,35.907757],'Kosovo':[20.902977,42.6026359],'Kuwait':[47.481766,29.31166],'Laos':[102.495496,19.85627],'Lebanon':[35.862285,33.854721],'Liberia':[-9.429499000000002,6.428055],'Libya':[17.228331,26.3351],'Sri Lanka':[80.77179699999999,7.873053999999999],'Lesotho':[28.233608,-29.609988],'Lithuania':[23.881275,55.169438],'Luxembourg':[6.129582999999999,49.815273],'Latvia':[24.603189,56.879635],'Morocco':[-7.092619999999999,31.791702],'Moldova':[28.369885,47.411631],'Madagascar':[46.869107,-18.766947],'Mexico':[-102.552784,23.634501],'Macedonia':[21.745275,41.608635],'Mali':[-3.996166,17.570692],'Myanmar':[95.956223,21.913965],'Montenegro':[19.37439,42.708678],'Mongolia':[103.846656,46.862496],'Mozambique':[35.529562,-18.665695],'Mauritania':[-10.940835,21.00789],'Malawi':[34.301525,-13.254308],'Malaysia':[101.975766,4.210484],'Namibia':[18.49041,-22.95764],'New Caledonia':[165.618042,-20.904305],'Niger':[8.081666,17.607789],'Nigeria':[8.675277,9.081999],'Nicaragua':[-85.207229,12.865416],'Netherlands':[5.291265999999999,52.132633],'Norway':[8.468945999999999,60.47202399999999],'Nepal':[84.12400799999999,28.394857],'New Zealand':[174.885971,-40.900557],'Oman':[55.923255,21.512583],'Pakistan':[69.34511599999999,30.375321],'Panama':[-80.782127,8.537981],'Peru':[-75.015152,-9.189967],'Philippines':[121.774017,12.879721],'Papua New Guinea':[143.95555,-6.314992999999999],'Poland':[19.145136,51.919438],'Puerto Rico':[-66.590149,18.220833],'North Korea':[127.510093,40.339852],'Portugal':[-8.224454,39.39987199999999],'Paraguay':[-58.443832,-23.442503],'Qatar':[51.183884,25.354826],'Romania':[24.96676,45.943161],'Russia':[105.318756,61.52401],'Rwanda':[29.873888,-1.940278],'Western Sahara':[-12.885834,24.215527],'Saudi Arabia':[45.079162,23.885942],'Sudan':[30.217636,12.862807],'South Sudan':[31.3069788,6.876991899999999],'Senegal':[-14.452362,14.497401],'Solomon Islands':[160.156194,-9.64571],'Sierra Leone':[-11.779889,8.460555],'El Salvador':[-88.89653,13.794185],'Somaliland':[46.8252838,9.411743399999999],'Somalia':[46.199616,5.152149],'Republic of Serbia':[21.005859,44.016521],'Suriname':[-56.027783,3.919305],'Slovakia':[19.699024,48.669026],'Slovenia':[14.995463,46.151241],'Sweden':[18.643501,60.12816100000001],'Swaziland':[31.465866,-26.522503],'Syria':[38.996815,34.80207499999999],'Chad':[18.732207,15.454166],'Togo':[0.824782,8.619543],'Thailand':[100.992541,15.870032],'Tajikistan':[71.276093,38.861034],'Turkmenistan':[59.556278,38.969719],'East Timor':[125.727539,-8.874217],'Trinidad and Tobago':[-61.222503,10.691803],'Tunisia':[9.537499,33.886917],'Turkey':[35.243322,38.963745],'United Republic of Tanzania':[34.888822,-6.369028],'Uganda':[32.290275,1.373333],'Ukraine':[31.16558,48.379433],'Uruguay':[-55.765835,-32.522779],'United States of America':[-95.712891,37.09024],'Uzbekistan':[64.585262,41.377491],'Venezuela':[-66.58973,6.42375],'Vietnam':[108.277199,14.058324],'Vanuatu':[166.959158,-15.376706],'West Bank':[35.3027226,31.9465703],'Yemen':[48.516388,15.552727],'South Africa':[22.937506,-30.559482],'Zambia':[27.849332,-13.133897],'Zimbabwe':[29.154857,-19.015438]};

        var geoObj = {};
        for (var key in nameMap) {
            geoObj[nameMap[key]] = geoCoord[key];
        }
        return geoObj;
    }

    function initChart(data1, epidemicName,selectObj) {
        var pathAndValueResultLength = data1['pathAndValueResult'].length;
        var firstLocationName = data1['pathAndValueResult'][0][0]['name'];
        var firstLocationValue = data1['pathAndValueResult'][0][1]['value'];
        var firstLocation = {name: firstLocationName, value: firstLocationValue};
        var lastLocation = data1['pathAndValueResult'][pathAndValueResultLength - 1][1];
        	myChart = echarts.init(document.getElementById('oiePathChart'));
        var option = {
            backgroundColor: '#1b1b1b',
            color: ['gold', 'aqua', 'lime'],
            title: {
                text: epidemicName,
                subtext: '',
                x: 'center',
                textStyle: {
                    color: '#fff'
                }
            },
            tooltip: {
                trigger: 'item',
                //formatter: '{b}'
                formatter: function (params,ticket,callback){
                    var locationStr = params[1];
                    if(locationStr&&locationStr.indexOf(">")>0){
                        var startLocation = locationStr.split(">")[0].trim();
                        var endLocation = locationStr.split(">")[1].trim();
                        return startLocation + '（爆发时间:' + params['data']['startDate'] + '）>>> '+endLocation+"（爆发时间:" + params['data']['endDate'] + '），时间间隔：' + params['data']['day'] + '天';
                    }else {
                        return "";
                    }

//                    return params[1] + ',起始时间:' + params['data']['startDate'] + ',结束时间:' + params['data']['endDate'] + ',时间间隔:' + params['data']['day'] + '天';
                }
            },
//            legend: {
//                orient: 'vertical',
//                x: 'left',
//                data: [$("#epidemicNameSelect option:selected").text()],
//                textStyle: {
//                    color: '#fff'
//                }
//            },
            toolbox: {
                show: true,
                orient: 'vertical',
                x: 'right',
                y: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            dataRange: {
                min: 0,
                max: 100,
                calculable: true,
                color: ['#ff3333', 'orange', 'yellow', 'lime', 'aqua'],
                textStyle: {
                    color: '#fff'
                }
            },
            series: [
                {
                    name: '世界',
                    type: 'map',
                    roam: true,
                    hoverable: false,
                    mapType: 'world',
                    itemStyle: {
                        normal: {
                            borderColor: 'rgba(100,149,237,1)',
                            borderWidth: 0.5,
                            areaStyle: {
                                color: '#1b1b1b'
                            }
                        }
                    },
                    data: [],
                    markLine: {
                        smooth: true,
                        symbol: ['none', 'circle'],
                        symbolSize: 1,
                        itemStyle: {
                            normal: {
                                color: '#fff',
                                borderWidth: 1,
                                borderColor: 'rgba(30,144,255,0.5)'
                            }
                        },
                        data: data1['pathList'],
                    },
                    geoCoord: getGeo()
                },
                {
                    name: epidemicName,
                    type: 'map',
                    mapType: 'world',
                    data: [],
                    markLine: {
                         smooth: true,
                        effect: {
                            show: true,
                            scaleSize: 1,
                            period: 30,
                            color: '#fff',
                            shadowBlur: 10
                        },
                        itemStyle: {
                            normal: {
                                borderWidth: 1,
                                lineStyle: {
                                    type: 'solid',
                                    shadowBlur: 10
                                }
                            }
                        },
                        data: data1['pathAndValueResult']
                    },
                    markPoint: {
                        symbol: 'emptyCircle',
                        symbolSize: function (v) {
                            return 10 + v / 10
                        },
                        effect: {
                            show: true,
                            shadowBlur: 0
                        },
                        itemStyle: {
                            normal: {
                                label: {show: false}
                            },
                            emphasis: {
                                label: {position: 'top'}
                            }
                        },
                        data: [
                            firstLocation,
                            lastLocation
                        ]
                    }
                }
            ]
        };
        myChart.setOption(option);
    }

    function formartDate(date) {
        var dateArray = date.split('/');
        var result = dateArray[2] + '-' + dateArray[0] + '-' + dateArray[1];
        return result;
    }

    function search() {
        var pMap = {};
        var epidemicName = setSearchKeyWord();
        pMap['disease'] = epidemicName;
        var startDate = $("#startDate").val()
        if (startDate != null && startDate != '') {
            pMap['startDate'] = formartDate(startDate);
        }

        var endDate = $("#endDate").val()
        if (endDate != null && endDate != '') {
            pMap['endDate'] = formartDate(endDate);
        }

        $.post('${pageContext.request.contextPath}/oIEEpidemicPathController/oieEpidemicEventPathResult.do', pMap, function (data) {
        		initChart(data,epidemicName);
        }, 'json');

    }
    
    function anmPath(data){
    		var pathAndValueResultLength = data['pathAndValueResult'].length;
    		var dataResult = {'pathAndValueResult':null,'pathList':null};
    		for(var i = 0;i < pathAndValueResultLength;i++){
    			dataResult.pathAndValueResult = data['pathAndValueResult'].slice(0,i + 1);
    			dataResult.pathList = data['pathList'].slice(0,i + 1);
    			initChart(dataResult);
    		}
    }
    function sleep(numberMillis) {
        var now = new Date();
        var exitTime = now.getTime() + numberMillis;
        while (true) {
            now = new Date();
            if (now.getTime() > exitTime)
                return;
        }
    }

    $(document).ready(function () {
        $.post('${pageContext.request.contextPath}/oieEpidemicSearch/epidemicNameList.do', null, function (data) {
            $('#epidemicName').typeahead({source: data})
        }, 'json');
        search();
    });

    function setSearchKeyWord(){
        var defaultName = "口蹄疫";
        var searchKeyword = $("#epidemicName").val();
        var cookieSK = getCookie("oieEPSK");
        if (searchKeyword&&searchKeyword.trim()!==""){
            setCookie("oieEPSK",searchKeyword);
            return searchKeyword;
        }else if(cookieSK&&cookieSK.trim()!==""){
            $("#epidemicName").val(cookieSK);
            return cookieSK;
        }else {
            $("#epidemicName").val(defaultName);
            return defaultName;
        }
    }
</script>
</body>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
</html>
