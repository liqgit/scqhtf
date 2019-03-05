<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>用户管理</title>
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminex/js/data-tables/DT_bootstrap.css" />

</head>
<body class="sticky-header">


<section>
    <%@include file="menu.jsp" %>
    <!-- main content start-->
    <div class="main-content">
        <%@include file="header.jsp" %>
        <div class="wrapper">
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading">
                            用户列表
                        </header>
                        <div class="panel-body">
                            <div class="adv-table editable-table ">
                                <div class="clearfix">
                                    <div class="btn-group">
                                        <button id="editable-sample_new" class="btn btn-primary">
                                            添加新用户 <i class="fa fa-plus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="space15"></div>
                                <table class="table table-striped table-hover table-bordered" id="editable">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>用户名</th>
                                        <th>密码</th>
                                        <th>邮箱</th>
                                        <th>部门</th>
                                        <th>权限</th>
                                        <th>编辑</th>
                                        <th>删除</th>
                                        <th style="display: none"></th>
                                        <th style="display: none"></th>
                                    </tr>
                                    </thead>
                                    <tbody id="table1">
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </section>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- main content end-->


<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.isotope.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/data-tables/jquery.dataTables.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/data-tables/DT_bootstrap.js"></script>

<script src="${pageContext.request.contextPath}/adminex/js/editable-table.js"></script>

</body>
</html>

<script>
    function findUserList() {
        $.post('${pageContext.request.contextPath}/userManager/findUserList.do',null,function (data) {
            if(data!==null){
                $('#table1').empty();
                for(var i=0;i<data.length;i++){
                    var psswd = data[i]['passWd'];
                    var transpwd = psswd.substr(0,1)+"***"+psswd.substr(psswd.length-1);
                    var authority = data[i].authority;
                    var email = data[i]['email'];
                    var department = data[i]['department'];
                    var tdstr = "<td>"+($("#table1")[0].childElementCount+1)+"</td><td>"+data[i].name+"</td><td>"+transpwd+"</td>"+
                        '<td>'+email+'</a></td>' +
                        '<td>'+department+'</a></td>' +
                        '<td>'+authority+'</td>'+
                        '<td><a class="edit" href="javascript:;">Edit</a></td>' +
                        '<td><a class="delete" href="javascript:;">Delete</a></td>'+
                        "<td style='display: none'>"+psswd+"</td><td style='display: none'>"+data[i].id+"</td>";
                    $('#table1').append("<tr>"+tdstr+"</tr>");
                }
                EditableTable.init();

            }
        },'json');
    }
    jQuery(document).ready(function() {
        findUserList();

    });
</script>