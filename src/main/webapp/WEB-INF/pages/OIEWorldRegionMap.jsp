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

    <title>疫情地域分布</title>

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
    <!-- main content start-->
    <div class="main-content">
        <%@include file="header.jsp" %>
        <!--body wrapper start-->
        <div class="wrapper">
            <div class="row">
                <div class="col-sm-12">
                    <div class="panel">
                        <div class="panel-heading">
                            <div class="row">
                                <table style="text-align: center;">
                                    <tr>
                                        <td><label>&nbsp;&nbsp;&nbsp;时间段:&nbsp;</label></td>
                                        <td>
                                            <div data-date-minviewmode="months" data-date-viewmode="years"
                                                 class="input-group input-large custom-date-range " data-date="102/2012"
                                                 data-date-format="yyyy-mm-dd">
                                                <input type="text" id="startDate" class="form-control dpd1 AQForm tc"
                                                       name="from" placeholder="From">
                                                <span class="input-group-addon">到</span>
                                                <input type="text" id="endDate" class="form-control dpd2 AQForm tc"
                                                       name="to" placeholder="To">
                                            </div>
                                        </td>
                                        <td>&nbsp;或&nbsp;</td>
                                        <td>
                                            <input type="text" id="interval" class="form-control AQForm"
                                                   placeholder="间隔天数">
                                        </td>
                                        <td><label>&nbsp;&nbsp;疫病名称:&nbsp;&nbsp;</label></td>
                                        <td>
                                            <input type="text" id='epidemicName' class="form-control AQForm" placeholder="疫病名称">
                                        </td>


                                        <td>
                                            &nbsp;&nbsp;&nbsp;
                                            <button class="btn btn-primary" onclick="initAdvancedQueryInput()">重置条件
                                            </button>
                                        </td>
                                        <td>
                                            &nbsp;&nbsp;
                                            <button class="btn btn-info" onclick="search()">查询</button>
                                        </td>
                                        <td>
                                            &nbsp;&nbsp;
                                            <input id="selectall" type="button" class="btn btn-info" value="全不选" flag="1"/>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="panel-body" style="display: block;">

                            <div class="row">
                                <div id="main" style="height:800px"></div>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/typeahead/bootstrap3-typeahead.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>
<!--pickers plugins-->
<script type="text/javascript"
        src="${pageContext.request.contextPath}/adminex/js/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/adminex/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/adminex/js/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/adminex/js/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/adminex/js/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/adminex/js/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
<!--pickers initialization-->
<script src="${pageContext.request.contextPath}/adminex/js/pickers-init.js"></script>
<script src="${pageContext.request.contextPath}/echarts/build/dist/echarts-all.js"></script>


<script>
    $('.tc').focus(function () {
        $('#interval').val("")
    });
    $('#interval').focus(function () {
        $('.tc').val("")
    });
    var nameMap ={'Afghanistan':'阿富汗','Angola':'安哥拉','Albania':'阿尔巴尼亚','United Arab Emirates':'阿联酋','Argentina':'阿根廷','Armenia':'亚美尼亚','French Southern and Antarctic Lands':'法属南半球和南极领地','Australia':'澳大利亚','Austria':'奥地利','Azerbaijan':'阿塞拜疆','Burundi':'布隆迪','Belgium':'比利时','Benin':'贝宁','Burkina Faso':'布基纳法索','Bangladesh':'孟加拉国','Bulgaria':'保加利亚','The Bahamas':'巴哈马','Bosnia and Herzegovina':'波斯尼亚和黑塞哥维那','Belarus':'白俄罗斯','Belize':'伯利兹','Bermuda':'百慕大','Bolivia':'玻利维亚','Brazil':'巴西','Brunei':'文莱','Bhutan':'不丹','Botswana':'博茨瓦纳','Central African Republic':'中非共和国','Canada':'加拿大','Switzerland':'瑞士','Chile':'智利','China':'中国','Ivory Coast':'象牙海岸','Cameroon':'喀麦隆','Democratic Republic of the Congo':'刚果民主共和国','Republic of the Congo':'刚果共和国','Colombia':'哥伦比亚','Costa Rica':'哥斯达黎加','Cuba':'古巴','Northern Cyprus':'北塞浦路斯','Cyprus':'塞浦路斯','Czech Republic':'捷克共和国','Germany':'德国','Djibouti':'吉布提','Denmark':'丹麦','Dominican Republic':'多明尼加共和国','Algeria':'阿尔及利亚','Ecuador':'厄瓜多尔','Egypt':'埃及','Eritrea':'厄立特里亚','Spain':'西班牙','Estonia':'爱沙尼亚','Ethiopia':'埃塞俄比亚','Finland':'芬兰','Fiji':'斐济','Falkland Islands':'福克兰群岛','France':'法国','Gabon':'加蓬','United Kingdom':'英国','Georgia':'格鲁吉亚','Ghana':'加纳','Guinea':'几内亚','Gambia':'冈比亚','Guinea Bissau':'几内亚比绍','Equatorial Guinea':'赤道几内亚','Greece':'希腊','Greenland':'格陵兰','Guatemala':'危地马拉','French Guiana':'法属圭亚那','Guyana':'圭亚那','Honduras':'洪都拉斯','Croatia':'克罗地亚','Haiti':'海地','Hungary':'匈牙利','Indonesia':'印尼','India':'印度','Ireland':'爱尔兰','Iran':'伊朗','Iraq':'伊拉克','Iceland':'冰岛','Israel':'以色列','Italy':'意大利','Jamaica':'牙买加','Jordan':'约旦','Japan':'日本','Kazakhstan':'哈萨克斯坦','Kenya':'肯尼亚','Kyrgyzstan':'吉尔吉斯斯坦','Cambodia':'柬埔寨','South Korea':'韩国','Kosovo':'科索沃','Kuwait':'科威特','Laos':'老挝','Lebanon':'黎巴嫩','Liberia':'利比里亚','Libya':'利比亚','Sri Lanka':'斯里兰卡','Lesotho':'莱索托','Lithuania':'立陶宛','Luxembourg':'卢森堡','Latvia':'拉脱维亚','Morocco':'摩洛哥','Moldova':'摩尔多瓦','Madagascar':'马达加斯加','Mexico':'墨西哥','Macedonia':'马其顿','Mali':'马里','Myanmar':'缅甸','Montenegro':'黑山','Mongolia':'蒙古','Mozambique':'莫桑比克','Mauritania':'毛里塔尼亚','Malawi':'马拉维','Malaysia':'马来西亚','Namibia':'纳米比亚','New Caledonia':'新喀里多尼亚','Niger':'尼日尔','Nigeria':'尼日利亚','Nicaragua':'尼加拉瓜','Netherlands':'荷兰','Norway':'挪威','Nepal':'尼泊尔','New Zealand':'新西兰','Oman':'阿曼','Pakistan':'巴基斯坦','Panama':'巴拿马','Peru':'秘鲁','Philippines':'菲律宾','Papua New Guinea':'巴布亚新几内亚','Poland':'波兰','Puerto Rico':'波多黎各','North Korea':'北朝鲜','Portugal':'葡萄牙','Paraguay':'巴拉圭','Qatar':'卡塔尔','Romania':'罗马尼亚','Russia':'俄罗斯','Rwanda':'卢旺达','Western Sahara':'西撒哈拉','Saudi Arabia':'沙特阿拉伯','Sudan':'苏丹','South Sudan':'南苏丹','Senegal':'塞内加尔','Solomon Islands':'所罗门群岛','Sierra Leone':'塞拉利昂','El Salvador':'萨尔瓦多','Somaliland':'索马里兰','Somalia':'索马里','Republic of Serbia':'塞尔维亚共和国','Suriname':'苏里南','Slovakia':'斯洛伐克','Slovenia':'斯洛文尼亚','Sweden':'瑞典','Swaziland':'斯威士兰','Syria':'叙利亚','Chad':'乍得','Togo':'多哥','Thailand':'泰国','Tajikistan':'塔吉克斯坦','Turkmenistan':'土库曼斯坦','East Timor':'东帝汶','Trinidad and Tobago':'特里尼达和多巴哥','Tunisia':'突尼斯','Turkey':'土耳其','United Republic of Tanzania':'坦桑尼亚联合共和国','Uganda':'乌干达','Ukraine':'乌克兰','Uruguay':'乌拉圭','United States of America':'美国','Uzbekistan':'乌兹别克斯坦','Venezuela':'委内瑞拉','Vietnam':'越南','Vanuatu':'瓦努阿图','West Bank':'西岸','Yemen':'也门','South Africa':'南非','Zambia':'赞比亚','Zimbabwe':'津巴布韦'};
    var geoCoord ={'Afghanistan':[67.709953,33.93911],'Angola':[17.873887,-11.202692],'Albania':[20.168331,41.153332],'United Arab Emirates':[53.847818,23.424076],'Argentina':[-63.61667199999999,-38.416097],'Armenia':[45.038189,40.069099],'French Southern and Antarctic Lands':[69.348557,-49.280366],'Australia':[133.775136,-25.274398],'Austria':[14.550072,47.516231],'Azerbaijan':[47.576927,40.143105],'Burundi':[29.918886,-3.373056],'Belgium':[4.469936,50.503887],'Benin':[2.315834,9.30769],'Burkina Faso':[-1.561593,12.238333],'Bangladesh':[90.356331,23.684994],'Bulgaria':[25.48583,42.733883],'The Bahamas':[-77.39627999999999,25.03428],'Bosnia and Herzegovina':[17.679076,43.915886],'Belarus':[27.953389,53.709807],'Belize':[-88.49765,17.189877],'Bermuda':[-64.7505,32.3078],'Bolivia':[-63.58865299999999,-16.290154],'Brazil':[-51.92528,-14.235004],'Brunei':[114.727669,4.535277],'Bhutan':[90.433601,27.514162],'Botswana':[24.684866,-22.328474],'Central African Republic':[20.939444,6.611110999999999],'Canada':[-106.346771,56.130366],'Switzerland':[8.227511999999999,46.818188],'Chile':[-71.542969,-35.675147],'China':[104.195397,35.86166],'Ivory Coast':[-5.547079999999999,7.539988999999999],'Cameroon':[12.354722,7.369721999999999],'Democratic Republic of the Congo':[21.758664,-4.038333],'Republic of the Congo':[15.827659,-0.228021],'Colombia':[-74.297333,4.570868],'Costa Rica':[-83.753428,9.748916999999999],'Cuba':[-77.781167,21.521757],'Northern Cyprus':[33.429859,35.126413],'Cyprus':[33.429859,35.126413],'Czech Republic':[15.472962,49.81749199999999],'Germany':[10.451526,51.165691],'Djibouti':[42.590275,11.825138],'Denmark':[9.501785,56.26392],'Dominican Republic':[-70.162651,18.735693],'Algeria':[1.659626,28.033886],'Ecuador':[-78.18340599999999,-1.831239],'Egypt':[30.802498,26.820553],'Eritrea':[39.782334,15.179384],'Spain':[-3.74922,40.46366700000001],'Estonia':[25.013607,58.595272],'Ethiopia':[40.489673,9.145000000000001],'Finland':[25.748151,61.92410999999999],'Fiji':[178.065032,-17.713371],'Falkland Islands':[-59.523613,-51.796253],'France':[2.213749,46.227638],'Gabon':[11.609444,-0.803689],'United Kingdom':[-3.435973,55.378051],'Georgia':[43.356892,42.315407],'Ghana':[-1.023194,7.946527],'Guinea':[-9.696645,9.945587],'Gambia':[-15.310139,13.443182],'Guinea Bissau':[-15.180413,11.803749],'Equatorial Guinea':[10.267895,1.650801],'Greece':[21.824312,39.074208],'Greenland':[-42.604303,71.706936],'Guatemala':[-90.23075899999999,15.783471],'French Guiana':[-53.125782,3.933889],'Guyana':[-58.93018,4.860416],'Honduras':[-86.241905,15.199999],'Croatia':[15.2,45.1],'Haiti':[-72.285215,18.971187],'Hungary':[19.503304,47.162494],'Indonesia':[113.921327,-0.789275],'India':[78.96288,20.593684],'Ireland':[-8.24389,53.41291],'Iran':[53.688046,32.427908],'Iraq':[43.679291,33.223191],'Iceland':[-19.020835,64.963051],'Israel':[34.851612,31.046051],'Italy':[12.56738,41.87194],'Jamaica':[-77.297508,18.109581],'Jordan':[36.238414,30.585164],'Japan':[138.252924,36.204824],'Kazakhstan':[66.923684,48.019573],'Kenya':[37.906193,-0.023559],'Kyrgyzstan':[74.766098,41.20438],'Cambodia':[104.990963,12.565679],'South Korea':[127.766922,35.907757],'Kosovo':[20.902977,42.6026359],'Kuwait':[47.481766,29.31166],'Laos':[102.495496,19.85627],'Lebanon':[35.862285,33.854721],'Liberia':[-9.429499000000002,6.428055],'Libya':[17.228331,26.3351],'Sri Lanka':[80.77179699999999,7.873053999999999],'Lesotho':[28.233608,-29.609988],'Lithuania':[23.881275,55.169438],'Luxembourg':[6.129582999999999,49.815273],'Latvia':[24.603189,56.879635],'Morocco':[-7.092619999999999,31.791702],'Moldova':[28.369885,47.411631],'Madagascar':[46.869107,-18.766947],'Mexico':[-102.552784,23.634501],'Macedonia':[21.745275,41.608635],'Mali':[-3.996166,17.570692],'Myanmar':[95.956223,21.913965],'Montenegro':[19.37439,42.708678],'Mongolia':[103.846656,46.862496],'Mozambique':[35.529562,-18.665695],'Mauritania':[-10.940835,21.00789],'Malawi':[34.301525,-13.254308],'Malaysia':[101.975766,4.210484],'Namibia':[18.49041,-22.95764],'New Caledonia':[165.618042,-20.904305],'Niger':[8.081666,17.607789],'Nigeria':[8.675277,9.081999],'Nicaragua':[-85.207229,12.865416],'Netherlands':[5.291265999999999,52.132633],'Norway':[8.468945999999999,60.47202399999999],'Nepal':[84.12400799999999,28.394857],'New Zealand':[174.885971,-40.900557],'Oman':[55.923255,21.512583],'Pakistan':[69.34511599999999,30.375321],'Panama':[-80.782127,8.537981],'Peru':[-75.015152,-9.189967],'Philippines':[121.774017,12.879721],'Papua New Guinea':[143.95555,-6.314992999999999],'Poland':[19.145136,51.919438],'Puerto Rico':[-66.590149,18.220833],'North Korea':[127.510093,40.339852],'Portugal':[-8.224454,39.39987199999999],'Paraguay':[-58.443832,-23.442503],'Qatar':[51.183884,25.354826],'Romania':[24.96676,45.943161],'Russia':[105.318756,61.52401],'Rwanda':[29.873888,-1.940278],'Western Sahara':[-12.885834,24.215527],'Saudi Arabia':[45.079162,23.885942],'Sudan':[30.217636,12.862807],'South Sudan':[31.3069788,6.876991899999999],'Senegal':[-14.452362,14.497401],'Solomon Islands':[160.156194,-9.64571],'Sierra Leone':[-11.779889,8.460555],'El Salvador':[-88.89653,13.794185],'Somaliland':[46.8252838,9.411743399999999],'Somalia':[46.199616,5.152149],'Republic of Serbia':[21.005859,44.016521],'Suriname':[-56.027783,3.919305],'Slovakia':[19.699024,48.669026],'Slovenia':[14.995463,46.151241],'Sweden':[18.643501,60.12816100000001],'Swaziland':[31.465866,-26.522503],'Syria':[38.996815,34.80207499999999],'Chad':[18.732207,15.454166],'Togo':[0.824782,8.619543],'Thailand':[100.992541,15.870032],'Tajikistan':[71.276093,38.861034],'Turkmenistan':[59.556278,38.969719],'East Timor':[125.727539,-8.874217],'Trinidad and Tobago':[-61.222503,10.691803],'Tunisia':[9.537499,33.886917],'Turkey':[35.243322,38.963745],'United Republic of Tanzania':[34.888822,-6.369028],'Uganda':[32.290275,1.373333],'Ukraine':[31.16558,48.379433],'Uruguay':[-55.765835,-32.522779],'United States of America':[-95.712891,37.09024],'Uzbekistan':[64.585262,41.377491],'Venezuela':[-66.58973,6.42375],'Vietnam':[108.277199,14.058324],'Vanuatu':[166.959158,-15.376706],'West Bank':[35.3027226,31.9465703],'Yemen':[48.516388,15.552727],'South Africa':[22.937506,-30.559482],'Zambia':[27.849332,-13.133897],'Zimbabwe':[29.154857,-19.015438]};
    var mapChart;

    var alertInterval = 30;
    var mapDataIntervalCondition = 150;
    var startDateCondition = "";
    var endDateCondition = "";
    var epidemicNameCondition = '';

    function initAdvancedQueryInput() {
        $(".AQForm").val("");
    }

    function initConditions() {
        startDateCondition = '';
        endDateCondition = '';
        mapDataIntervalCondition = 150;
        epidemicNameCondition= '';
    }

    function search() {
        initConditions();
        startDateCondition = $('#startDate').val();
        endDateCondition = $('#endDate').val();
        var intervalVal = $('#interval').val();
        mapDataIntervalCondition = (intervalVal!=null&&intervalVal!=="")?intervalVal:150;
        epidemicNameCondition = $('#epidemicName').val();
        findWorldMap();
    }







    function findWorldMap() {
        $.post('${pageContext.request.contextPath}/oieWorldRegion/epidemicRecentOutbreakRegion.do', {
            'alertInterval': alertInterval,
            'mapDataInterval': mapDataIntervalCondition,
            'startDate': startDateCondition,
            'endDate': endDateCondition,
            'epidemicName':epidemicNameCondition
        }, function (data) {
            var option = {
                backgroundColor: '#1b1b1b',
                title: {
                    text: '疫情地域分布',
                    x: 'center',
                    textStyle: {
                        color: '#fff'
                    }
                },
                color: ['#fff','#f70','#ff0',
                    '#8f0', '#0f0','#0f8',
                    '#08f', '#00f','#80f','#888'
                ],
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    y: 'bottom',
                    data: [
                        '近30天疫情提示','第一次在该国发生','紧急发病','流行病学发生改变','新的宿主','新的致病源',
                        '该国发生某种疫病新的血清型','再次发生','发生预料之外的变化及增加','不常见的宿主'
                    ],
                    selectedMode: 'multiple',
                    textStyle: {color: '#fff'}
                },
                tooltip: {
                    trigger: 'item',
                    showDelay: 50,
                    textStyle:{
                        fontSize:10
                    },

                    formatter: function (params) {
                        var dataMap = params.data.data;
                        var value = "";
                        if(typeof(dataMap)!=="string"){
                            for (var key in dataMap) {
                                if (parseInt(key) <= params.data.value) {
                                    var dataList = dataMap[key];
                                    for(var i=0;i<dataList.length;i++){
                                        if(value===""){
                                            value=dataList[i];
                                        }else {
                                            value += "</br>"+dataList[i];
                                        }
                                    }
                                }
                            }

                        }else {
                            value=dataMap;
                        }
                        if (value) {
                            return value;
                        } else {
                            return "";
                        }


                    }
                },

                toolbox: {
                    show: true,
                    orient: 'vertical',
                    x: 'right',
                    y: 'center',
                    feature: {
                        mark: {show: true},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                dataRange: {
                    show: false,
                    splitList: [
                        {start: 512, color: '#f00'},
                        {start: 256, end: 511, color: '#f70'},
                        {start: 128, end: 255, color: '#ff0'},
                        {start: 64, end: 127, color: '#8f0'},
                        {start: 32, end: 63, color: '#0f0'},
                        {start: 16, end: 31, color: '#0f8'},
                        {start: 8, end: 15, color: '#0ff'},
                        {start: 4, end: 7, color: '#08f'},
                        {start: 2, end: 3, color: '#00f'},
                        {start: 1, end: 1, color: '#80f'},
                        {start: 0, end: 0, color: '#888'}
                    ],
                    textStyle: {color: '#fff'},
                    realtime: true,
                    calculable: false
                },
                series: []
            };

            var mapMarkPoint = {
                dataRange: {},
                symbol: 'emptyCircle',
                symbolSize: function (v) {
                    var num = 10 + v/5;
                    if (num > 30) {
                        num = 30
                    }
                    return num
                },
                effect: {
                    show: true,
                    shadowBlur: 10,
                    color: "#fff"
                },
                itemStyle: {
                    normal: {
                        label: {show: false}
                    },
                    emphasis: {
                        label: {show: false}
                    }
                },
                data: []
            };


            if (data !== null) {
                var mapDataList = data.mapDataList;

                for (var key in mapDataList) {
                    var mapSeries={name:'',type:'map',mapType:'world',roam:true,mapLocation:{y:60},itemStyle:{normal:{borderColor:'rgba(100,149,237,1)',borderWidth:1.5,areaStyle:{color:'#1b1b1b'}}},showLegendSymbol:false,data:[],nameMap:nameMap,geoCoord:geoCoord,markPoint:""};
                    mapSeries.name=transformReasonIdToName(key);
                    option.series.push(mapSeries);
                    option.series[option.series.length-1].data=mapDataList[key];
                }
                var mapSeries={name:'',type:'map',mapType:'world',roam:true,mapLocation:{y:60},itemStyle:{normal:{borderColor:'rgba(100,149,237,1)',borderWidth:1.5,areaStyle:{color:'#1b1b1b'}}},showLegendSymbol:false,data:[],nameMap:nameMap,geoCoord:geoCoord,markPoint:""};
                mapSeries.name="近期疫情提示";
                option.series.push(mapSeries);
                option.series[option.series.length-1].markPoint=mapMarkPoint;
                option.series[option.series.length-1].markPoint.data = data.alertList;
            }
            if(mapChart!==undefined&&mapChart!==null){
                mapChart.clear();
            }
            mapChart = echarts.init(document.getElementById('main'));
            mapChart.setOption(option);
            mapChart.on(echarts.config.EVENT.CLICK, function (param) {
                var regionName = param.name;
                if (regionName !== null && regionName !== "") {
                    window.location.href = "${pageContext.request.contextPath}/oieEpidemicSearch/toOIEEpidemicSearchPage.do?from=map&name=" + encodeURI(regionName);
                }
            });

            // 使用刚指定的配置项和数据显示图表。
            var selectArr = option.legend.data;
            $('#selectall').click(function(){
                var flag = $(this).attr('flag');
                if(flag == 1){
                    var val = false;
                    $(this).attr('flag',0);
                    $(this).val('全选中');
                }else{
                    var val = true;
                    $(this).attr('flag',1);
                    $(this).val('全不选');
                }
                var obj = {};
                for(var key in selectArr){
                    obj[selectArr[key]] = val;
                }
                option.legend.selected = obj;
                if(mapChart!==undefined&&mapChart!==null){
                    mapChart.clear();
                }
                mapChart.setOption(option);
            });
        }, 'json');
    }

    function transformReasonIdToName(id) {
        switch (id) {
            case "512":
                return '第一次发生';
            case "256":
                return '第一次在该国发生';
            case "128":
                return '紧急发病';
            case "64":
                return '流行病学发生改变';
            case "32":
                return '新的宿主';
            case "16":
                return '新的致病源';
            case "8":
                return '新的血清型';
            case "4":
                return '该国发生某种疫病新的血清型';
            case "2":
                return '再次发生';
            case "1":
                return '发生预料之外的变化及增加';
            case "0":
                return '不常见的宿主';
        }
    }

    $(document).ready(function () {
        findWorldMap();

        $.post('${pageContext.request.contextPath}/oieEpidemicSearch/epidemicNameList.do', null, function (data) {
            $('#epidemicName').typeahead({source: data})
        }, 'json');
    });
</script>


</body>
</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
