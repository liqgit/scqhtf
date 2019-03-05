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
    <title>分词列表</title>
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">
    <!--dropzone css-->
    <link href="${pageContext.request.contextPath}/adminex/js/dropzone/css/dropzone.css" rel="stylesheet"/>

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
                            分词查询
                        </div>

                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-4 form-group">
                                    <label class="control-label">字典分类</label>
                                    <input type="text" id='kindName' class="form-control" placeholder="字典分类">
                                </div>
                                <div class="col-lg-4 form-group">
                                    <label class="control-label">字典名称</label>
                                    <input type="text" id='words' class="form-control" placeholder="字典名称">
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer">
                            <button class="btn btn-primary" onclick="search();">查询</button>
                            <button class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                                新增分词分类
                            </button>
                            <button class="btn btn-primary" data-toggle="modal" data-target="#myModal1">
                                新增分词
                            </button>
                            <button class="btn btn-primary" onclick="exExcel()">
                                导出excel
                            </button>
                        </div>
                    </div>

                </div>
            </div>

            <div class="row">
                <div class="col-md-4">
                    <div class="panel">
                        <div class="panel-heading">
                            字典分类
                        </div>
                        <div class="panel-body blog-post">
                            <ul id="dic_category"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="panel">
                        <div class="panel-heading">
                            字典详情
                        </div>
                        <div class="panel-body">
                            <table class="table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th style="">
                                        <div class="th-inner ">序号</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">字典分类</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">分类等级</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">字典名称</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="">
                                        <div class="th-inner ">操作</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="wordsData"></tbody>
                                <tfoot></tfoot>
                            </table>
                            <div class="dataTables_paginate paging_bootstrap pagination">
                                <ul id="demo1"></ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- main content end-->

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">×
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        新增分词分类
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="addKindDicKindName">分类名称</label>
                        <input type="text" class="form-control" id="addKindDicKindName" name="kindName"
                               placeholder="字典名称">
                    </div>
                    <div class="form-group">
                        <label for="level">分类等级</label>
                        <input type="text" class="form-control" id="level" name="level"
                               placeholder="分类等级">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" id="addKindDicBtn" onclick="addKindDicBtn()" class="btn btn-primary">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->


    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">×
                    </button>
                    <h4 class="modal-title" id="myModalLabel1">
                        新增分词
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="addWordsKindDic">字典分类</label>
                        <select id="addWordsKindDic" class="form-control m-bot15">
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="addWordsWord">字典名称</label>
                        <input type="text" class="form-control" id="addWordsWord"
                               placeholder="字典名称">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" id="addWordsBtn" onclick="addWordsBtn();" class="btn btn-primary">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->








</section>


<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/adminex/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/adminex/js/modernizr.min.js"></script>

<script src="${pageContext.request.contextPath}/adminex/js/jquery.isotope.js"></script>

<script src="${pageContext.request.contextPath}/adminex/js/scripts.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/jqPaginator/dist/1.2.0/jqPaginator.min.js"></script>

<script type="text/javascript">

    var kindNameCondition = '';
    var wordsCondition = '';
    var level = '';


    function initVal() {
        kindNameCondition = $("#kindName").val();
        wordsCondition = $("#words").val();

    }

    function addKindDicBtn() {
        var addKindDicKindNameVal = $('#addKindDicKindName').val();
        var level = $("#level").val();
        $.post('${pageContext.request.contextPath}/words/addKindDic.do', {'kindName': addKindDicKindNameVal,'level':level}, function (data) {
            if (data == 1) {
                alert('添加分词分类成功')
                window.location.reload();
            } else {
                alert('添加分词分类失败')
                window.location.reload();
            }
        }, 'json');
    }

    function addWordsBtn() {
        var addWordsWordVal = $('#addWordsWord').val();
        var addWordsKindDicVal = $('#addWordsKindDic option:selected').val();
        $.post('${pageContext.request.contextPath}/words/addWords2.do', {
            'kindId': addWordsKindDicVal,
            'word': addWordsWordVal
        }, function (data) {
            if (data == 1) {
                alert('添加分词成功')
                window.location.reload();
            } else {
                alert('添加分词失败')
                window.location.reload();
            }
        }, 'json');

    }

    $(document).ready(function () {
        $.post('${pageContext.request.contextPath}/common/kindNameList.do', null, function (data) {
            var json = data;
            for (var i = 0; i < json.length; i++) {
                $("#addWordsKindDic").append("<option selected='selected' value='" + json[i].id + "'>" + json[i].kindName + "</option>");
                $("#dic_category").append('<li><a class="dic_category" style="font-weight:bold" href="javascript:;" onclick="findWordsByCategory(this)"><i class="  fa fa-angle-right"></i>'+json[i].kindName+'</a></li>');
            }

        }, 'json');
        search()

    });
    function search() {

        initVal();
        $.post('${pageContext.request.contextPath}/words/wordsList.do', {
            'kindName': kindNameCondition,
            'words': wordsCondition
        }, function (data) {
            if(data.wordsListCount == 0){
                $("#wordsData").empty();
                return;
            }
            $("#demo1").jqPaginator({
                totalPages: Math.ceil(data.wordsListCount / 10),
                visiblePages: 10,
                currentPage: 1,
                first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
                prev: '<li class="prev"><a href="javascript:void(0);">上一页<\/a><\/li>',
                next: '<li class="next"><a href="javascript:void(0);">下一页<\/a><\/li>',
                last: '<li class="last"><a href="javascript:void(0);">尾页<\/a><\/li>',
                page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
                onPageChange: function (n) {
                    $("#wordsData").empty();
                    $.post('${pageContext.request.contextPath}/words/wordsList.do', {
                        'pageNo': n - 1,
                        'kindName': kindNameCondition,
                        'words': wordsCondition
                    }, function (data) {
                        var wordsList = data.wordsList;
                        for (var i = 0; i < wordsList.length; i++) {
                            $("#wordsData").append("<tr><td style=''>" + (i + 1) + "</td>" + "<td style=''>" + wordsList[i].kindDic.kindName + "</td>" + "<td style=''>" + wordsList[i].kindDic.level + "</td>" + "<td style=''>" + wordsList[i].word + "</td>" + "<td style=''>" + "<a href='${pageContext.request.contextPath}/words/editWords.do?id=" + wordsList[i].id + "'><button class='btn btn-primary'>编辑</button></a>&nbsp&nbsp&nbsp<a href='${pageContext.request.contextPath}/words/deleteWords.do?id=" + wordsList[i].id + "'><button class='btn btn-primary'>删除</button></a>" + "</tr>");
                        }
                    }, 'json');
                }
            });
        }, 'json');
    }

    function uploadWords() {
        $.post('${pageContext.request.contextPath}/words/uploadWords.do', null, function (data) {
            if (data == 0) {
                alert("更新分词失败");
            } else {
                alert("更新分词成功");
            }
        }, 'json');

    }
    function exExcel() {
        var url = '${pageContext.request.contextPath}/words/exExcel.do';
        $('<form method="post" action="' + url + '"></form>').appendTo('body').submit().remove();

    }

    function findWordsByCategory(obj){
        if(obj && obj.text){
            $("#kindName").val(obj.text);
            search();
        }
    }
</script>
</body>

</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>