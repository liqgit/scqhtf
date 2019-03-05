<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>系统菜单</title>

    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminex/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/sysMenu/css/flexslider.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/sysMenu/css/jquery.fancybox.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/sysMenu/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/sysMenu/css/responsive.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/sysMenu/css/animate.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/sysMenu/css/font-icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminex/fonts/css/font-awesome.min.css">
    <script type="text/javascript">
        function gc(name){
            var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
            if(arr) return unescape(arr[2]); return null;
        }
        var ssid = '<%=session.getId()%>';
        var au = '<%=session.getAttribute("authority")%>';
        var csid = gc('userSID');
        if(ssid!==csid||!au||au===""){
            window.location.href='${pageContext.request.contextPath}/system/login.do';
        }


    </script>
</head>
<body class="login-body">
<section id="hero" class="section ">
    <div class="container">
        <div class="row">
            <div class="col-md-7 col-sm-6 hero">
                <div class="hero-content">

                </div>
            </div>
        </div>
    </div>
</section>
<section id="portfolio">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <hr class="section">
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6 portfolio-item" id="ml1"> <a href="${pageContext.request.contextPath}/index/indexPage.do" class="portfolio-link">
                <div class="caption">
                    <div class="caption-content">
                        <h2>WHO疫情指数分析功能</h2>
                        <h4>WHO epidemic analysis function</h4>
                    </div>
                </div>
                <img src="../../images/customFile/who_menu.jpg" class="img-responsive" alt=""> </a> </div>
            <div class="col-sm-6 portfolio-item" id="ml2"> <a href="${pageContext.request.contextPath}/oieDashboard/toOIEDashboardPage.do" class="portfolio-link">
                <div class="caption">
                    <div class="caption-content">
                        <h2>动物疫情监测与预警系统</h2>
                        <h4>OIE epidemic analysis and warning system</h4>
                    </div>
                </div>
                <img src="../../images/customFile/oie_menu.jpg" class="img-responsive" alt=""> </a> </div>
            <div class="col-sm-6 portfolio-item" id="ml3"> <a href="${pageContext.request.contextPath}/dataWarehouse/toDatawarehousePage.do"" class="portfolio-link">
                <div class="caption">
                    <div class="caption-content">
                        <h2>数据中心</h2>
                        <h4>data center</h4>
                    </div>
                </div>
                <img src="../../images/customFile/dataCenter_menu.png" class="img-responsive" alt=""> </a> </div>
            <div class="col-sm-6 portfolio-item" id="ml4"> <a href="${pageContext.request.contextPath}/words/toWordsPage.do" class="portfolio-link">
                <div class="caption">
                    <div class="caption-content">
                        <h2>控制面板</h2>
                        <h4>control panel</h4>
                    </div>
                </div>
                <img src="../../images/customFile/controlPanel_menu.png" class="img-responsive" alt=""> </a> </div>
        </div>
    </div>
</section>


<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/sysMenu/js/jquery.fancybox.pack.js"></script>
<script src="${pageContext.request.contextPath}/sysMenu/js/retina.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>
<script src="${pageContext.request.contextPath}/sysMenu/js/main.js"></script>

<script type="text/javascript">
    var eles = au.split(";");
    for(var i=0;i<eles.length;i++){
        $("#"+eles[i]).remove();
    }
</script>
<script type="application/javascript">

</script>
</body>
</html>
