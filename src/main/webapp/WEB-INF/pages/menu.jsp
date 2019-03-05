<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 16/7/14
  Time: 下午12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- left side start-->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
    function gc(name){
        var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
        if(arr) return unescape(arr[2]); return null;
    }
    var ssid = '<%=session.getId()%>';
    var au = '<%=session.getAttribute("authority")%>';
    var csid = gc('userSID');
    if(ssid!==csid||au==null||au==""){
        window.location.href='${pageContext.request.contextPath}/system/login.do';
    }


</script>
<div class="left-side sticky-left-side" style="overflow: hidden;">

    <!--logo and iconic logo start-->
   <%-- <div class="logo">
        <a href="${pageContext.request.contextPath}/oieDashboard/toOIEDashboardPage.do"><h4>疫情监测与预警平台</h4></a>
    </div>--%>

    <div class="logo-icon text-center">
        <a href="${pageContext.request.contextPath}/oieDashboard/toOIEDashboardPage.do"><img src="${pageContext.request.contextPath}/adminex/images/logo_icon.png" alt=""></a>
    </div>
    <!--logo and iconic logo end-->

    <div class="left-side-inner">

        <!-- visible to small devices only -->
        <div class="visible-xs hidden-sm hidden-md hidden-lg">
            <div class="media logged-user">
                <img alt="" src="${pageContext.request.contextPath}/adminex/images/photos/user-avatar.png"
                     class="media-object">
                <div class="media-body">
                    <h4><a href="#">John Doe</a></h4>
                    <span>"Hello There..."</span>
                </div>
            </div>

            <h5 class="left-nav-title">Account Information</h5>
            <ul class="nav nav-pills nav-stacked custom-nav">
                <li><a href="#"><i class="fa fa-user"></i> <span>Profile</span></a></li>
                <li><a href="#"><i class="fa fa-cog"></i> <span>Settings</span></a></li>
                <li><a href="#"><i class="fa fa-sign-out"></i> <span>Sign Out</span></a></li>
            </ul>
        </div>

        <!--sidebar nav start-->
        <ul class="nav nav-pills nav-stacked custom-nav">
           <%-- <li class="menu-list" id="ml1"><a href="#"><i class="fa fa-bar-chart-o"></i> <span>WHO疫情</span></a>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi1" href="${pageContext.request.contextPath}/index/indexPage.do">仪表盘</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi2" href="${pageContext.request.contextPath}/epidemicCloud/epidemicCloudPage.do">疫情字符云</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi3" href="${pageContext.request.contextPath}/wordRegion/wordRegionPage.do">疫情地域分布</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi4" href="${pageContext.request.contextPath}/epidemicBaike/epidemicBaikePage.do">疫情百科</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi5" href="${pageContext.request.contextPath}/epidemic/epidemicListPage.do">疫情综合查询</a></li>
                </ul>
            </li>--%>

            <%--四川--%>
            <li class="menu-list" id="ml2"><a href="#"><i class="fa fa-bar-chart-o"></i> <span>动物疫情</span></a>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi2_1" href="${pageContext.request.contextPath}/oieDashboard/toOIEDashboardPage.do">动物疫情概览</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi2_2" href="${pageContext.request.contextPath}/oieEpidemicSearch/toOIEEpidemicSearchPage.do">动物疫情查询</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi2_3" href="${pageContext.request.contextPath}/oieWorldRegion/toOIEWorldRegionPage.do">动物疫情地域分布</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi2_4" href="${pageContext.request.contextPath}/oIEEpidemicPathController/oieEpidemicPathPage.do">动物疫情爆发时间路径</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi2_5" href="${pageContext.request.contextPath}/weeklyReportExport/toReportPage.do">动物疫情周报导出</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi2_6"
                           href="${pageContext.request.contextPath}/epidemicItem/epidemicItemListPage.do">动物疫病名录</a>
                    </li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi2_7"
                           href="${pageContext.request.contextPath}/timeTrend/timeTrendPage.do">病虫害时间趋势分析</a>
                    </li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi2_8"
                           href="${pageContext.request.contextPath}/occurrenceTrend/occurrenceTrendPage.do">病虫害发生趋势分析</a>
                    </li>
                </ul>

            </li>


                <%--天津--%>
               <%--<li class="menu-list" id="ml2"><a href="#"><i class="fa fa-bar-chart-o"></i> <span>OIE疫情</span></a>
                   <ul class="sub-menu-list">
                       <li><a class="menu-item" id="mi2_1" href="${pageContext.request.contextPath}/oieDashboard/toOIEDashboardPage.do">OIE疫情概览</a></li>
                   </ul>
                   <ul class="sub-menu-list">
                       <li><a class="menu-item" id="mi2_2" href="${pageContext.request.contextPath}/oieEpidemicSearch/toOIEEpidemicSearchPage.do">OIE疫情查询</a></li>
                   </ul>
                   <ul class="sub-menu-list">
                       <li><a class="menu-item" id="mi2_3" href="${pageContext.request.contextPath}/oieWorldRegion/toOIEWorldRegionPage.do">OIE疫情地域分布</a></li>
                   </ul>
              &lt;%&ndash;     <ul class="sub-menu-list">
                       <li><a class="menu-item" id="mi2_4" href="${pageContext.request.contextPath}/oIEEpidemicPathController/oieEpidemicPathPage.do">OIE疫情爆发时间路径</a></li>
                   </ul>&ndash;%&gt;
                   <ul class="sub-menu-list">
                       <li><a class="menu-item" id="mi2_5" href="${pageContext.request.contextPath}/weeklyReportExport/toReportPage.do">OIE疫情周报导出</a></li>
                   </ul>
                   <ul class="sub-menu-list">
                       <li><a class="menu-item" id="mi2_6"
                              href="${pageContext.request.contextPath}/epidemicItem/epidemicItemListPage.do">中华人民共和国进境动物检疫疫病名录</a>
                       </li>
                   </ul>

               </li>--%>

        <%--    <li class="menu-list" id="ml2"><a href="#"><i class="fa fa-bar-chart-o"></i> <span>国内动物疫情</span></a></li>
            <li class="menu-list" id="ml2"><a href="#"><i class="fa fa-bar-chart-o"></i> <span>进境动物检疫业务动态</span></a>
                <ul class="sub-menu-list">
                    <li><a class="menu-item"  href="#">中国海关进境动物检疫统计</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item"   href="#">国家指定隔离场动态分布图</a></li>
                </ul>
            </li>
            <li class="menu-list" id="ml2"><a href="#"><i class="fa fa-bar-chart-o"></i> <span>中国海关进境动物检疫</span></a>
                <ul class="sub-menu-list">
                    <li><a class="menu-item"  href="#">进境动物检疫法律法规</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item"  href="#">进境动物检疫规范性文件</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item"  href="#">禁止进境动物疫情名录</a></li>
                </ul>
               <ul class="sub-menu-list">
                     <li><a class="menu-item" href="#">禁止及预警通报</a></li>
               </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item"  href="#">检疫准入及检疫许可程序流程图</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" href="#">进境动物国外官方证书库</a></li>
                </ul>
            </li>
            <li class="menu-list" id="ml2"><a href="#"><i class="fa fa-bar-chart-o"></i> <span>动物疫病知识库</span></a></li>
--%>
            <li class="menu-list" id="ml3"><a href="#"><i class="fa fa-tasks"></i> <span>数据中心</span></a>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi8" href="${pageContext.request.contextPath}/dataWarehouse/toDatawarehousePage.do">数据中心</a></li>
                </ul>
            </li>
            <li class="menu-list" id="ml4"><a href="#"><i class="fa fa-cogs"></i> <span>控制面板</span></a>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi9" href="${pageContext.request.contextPath}/words/toWordsPage.do">分词器</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi10" href="${pageContext.request.contextPath}/spider/toSpiderListPage.do">爬虫列表</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi11" href="${pageContext.request.contextPath}/spider/toCreateSpiderPage.do">增加爬虫实例</a></li>
                </ul>
                <ul class="sub-menu-list">
                    <li><a class="menu-item" id="mi12" href="${pageContext.request.contextPath}/userManager/toUserManagePage.do">用户管理</a></li>
                </ul>
            </li>
            <%--<li class="menu-list" id="testList"><a href="#"><i class="fa fa-bar-chart-o"></i> <span>Test</span></a>--%>
                <%--<ul class="sub-menu-list">--%>
                    <%--<li><a class="menu-item" id="test1" href="${pageContext.request.contextPath}/test/toTestPage.do">test1</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>
        </ul>
        <!--sidebar nav end-->

    </div>
</div>

<script type="text/javascript">
    var eles = au.split(";")
    var eleCount = 0;
    for(var i=0;i<eles.length;i++){
        var ele = $("#"+eles[i]);
        ele.remove();
    }
</script>

<!-- left side end-->
