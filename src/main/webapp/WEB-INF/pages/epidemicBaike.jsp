<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 16/7/14
  Time: 下午12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>疫情百科</title>
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">

</head>
<body class="sticky-header">
<script>
function aa(){
$(this).attr('src','http://reptile3.cn:8080/img/liugan.jpg');
}

</script>

<section>
    <%@include file="menu.jsp" %>
    <!-- main content start-->
    <div class="main-content">
        <%@include file="header.jsp" %>

        <!--body wrapper start-->
        <div class="wrapper">
            <div class="row">
                <div class="col-md-12">
                    <section class="panel">
                        <header class="panel-heading">
                            疫情百科
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                                <a href="javascript:;" class="fa fa-times"></a>
                             </span>
                        </header>
                        <div class="panel-body">

                            <div id="gallery" class="media-gal">
                                <c:forEach items="${epidemicBaikeList}" var="baike">
                                    <div class="images item ">
                                        <a onclick="dj('${baike.rowKey}','${baike.epidemicName}');">
                                            <img src="${pageContext.request.contextPath}/img/${baike.epidemicName}.jpg" onerror="javascript:this.src='http://reptile3.tj.ciq:8080/img/liugan.jpg'" alt=""/>
                                        </a>
                                        <p>${baike.epidemicName}</p>
                                    </div>
                                </c:forEach>
                            </div>


                            <!-- Modal -->
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
                                 aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-hidden="true">&times;</button>
                                            <h4 id="title" class="modal-title"></h4>
                                        </div>

                                        <div class="modal-body row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label> 简介</label>
                                                    <p id="jj"></p>
                                                </div>
                                            </div>

                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>


                    </section>
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

<script src="${pageContext.request.contextPath}/adminex/js/jquery.isotope.js"></script>

<!--common scripts for all pages-->
<%-- <script src="${pageContext.request.contextPath}/adminex/js/scripts.js"></script> --%>

<script type="text/javascript">
    function dj(id, name) {
        $.post("${pageContext.request.contextPath}/epidemicBaike/fetchBaikeInfo.do", {'rowKey': id},
                function (data) {
                    var json = data;
                    $("#title").html(name);
					var a_regx = /(<a[^>]*>)|(<\/a>)|(<img[^>]*>)|(<em[^>]*>[\s\S]*?<\/em>[\s\S]{0,10}[编辑|锁定]+)/g;
					var main_content = json.replace(a_regx,"");
                    $("#jj").html(main_content.replace('main-content',''));
            $(".lemmaWgt-lemmaCatalog").remove();
            $(".top-tool").remove();
            $(".lemma-picture").remove();
                    $('#myModal').modal('show');
                },
                "text");
    }
</script>
</body>


</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>