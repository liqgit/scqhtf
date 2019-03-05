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
    <title>数据中心</title>
    <link href="${pageContext.request.contextPath}/adminex/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/adminex/css/style-responsive.css" rel="stylesheet">

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
                    <section class="panel">
                        <header class="panel-heading">
                            查询条件
                        </header>
                        <div class="panel-body">
                            <table style="text-align: center;">
                                <tr>
                                    <td><label>分词:&nbsp;&nbsp;&nbsp;</label></td>
                                    <td>
                                        <div class="input-group">
                                            <input id="words" class="form-control SQForm" style="width: 200px"/>
                                        </div>
                                    </td>
                                    <td>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <button class="btn btn-primary" onclick="initSimpleQueryInput()">重置条件</button>
                                    </td>
                                    <td>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <button class="btn btn-info" onclick="simpleQuery()">查询</button>
                                    </td>
                                </tr>

                            </table>

                        </div>

                    </section>
                    <section class="panel">
                        <header class="panel-heading">
                            高级查询
                            <span class="tools pull-right">
                                <a class="fa fa-chevron-down" href="javascript:;"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="col-md-6">
                                <form role="form"  style="margin: auto">
                                    <div class="form-group">
                                        <label>包含以下全部关键词：</label>
                                        <input class="form-control AQForm"  type="text" id="mustWords">
                                    </div>
                                    <div class="form-group">
                                        <label>包含以下任意关键词：</label>
                                        <input class="form-control AQForm"  type="text" id="shouldWords">
                                    </div>
                                    <div class="form-group">
                                        <label>不包含以下关键词：</label>
                                        <input class="form-control AQForm"  type="text" id="mustNotWords">
                                    </div>
                                    <div class="form-group">
                                        <label>只查询以下域名：</label>
                                        <input class="form-control AQForm"  type="text" id="domainRule">
                                    </div>


                                </form>
                                <div class="col-lg-offset-5">
                                    <button class="btn btn-primary" onclick="initAdvancedQueryInput()">重置条件</button>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button class="btn btn-info" onclick="advancedQuery()">查询</button>
                                </div>
                            </div>


                        </div>

                    </section>

                </div>

            </div>

            <div class="row">
                <div class="col-md-12" id="content"></div>

            </div>
            <div class="row">
                <div class="dataTables_paginate paging_bootstrap pagination">
                    <ul id="pageNum"></ul>
                </div>
            </div>
        </div>
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
<script src="${pageContext.request.contextPath}/adminex/js/pickers-init.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/jqPaginator/dist/1.2.0/jqPaginator.min.js"></script>

<!--common scripts for all pages-->
<script src="${pageContext.request.contextPath}/adminex/js/scripts.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/elasticSearch/elasticsearch.jquery.js"></script>

<script type="text/javascript">

    function setCookie(name,value) {
        document.cookie = name + "="+ escape (value);
    }

    function getCookie(name) {
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }

    $('.panel .tools .fa').click(function () {
        var el = $(this).parents(".panel").children(".panel-body");
        if ($(this).hasClass("fa-chevron-down")) {
            $(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
            el.slideUp(200);
        } else {
            $(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
            el.slideDown(200); }
    });

    var client = new $.es.Client({hosts: 'http://dwyq.tj.ciq:9200',log: 'trace'});
    //sandbox.hortonworks.com
    var size = 10;
    var from = 0;
    var queryRule;
    var queryType;
    var cp = 1;

    $(document).ready(function () {
        queryType = "load";
        changePageContent();
        queryType = getCookie("queryType");
    });

    function simpleQuery() {
        console.info("simpleQuery");
        queryType = "simpleQuery";
        changePageContent();
    }
    function advancedQuery(){
        queryType = "advancedQuery";
        changePageContent();
    }

    function initSimpleQueryInput() {
        $(".SQForm").val("");
    }
    function initAdvancedQueryInput(){
        $(".AQForm").val("");
    }


    function getQueryRule(fromNum){
        from = fromNum?fromNum:0;
        queryRule = { "query": { "bool": { "must": [], "must_not": [], "should": [], "minimum_should_match": 1 } }, "size": size,"from":from };
        queryRule.query.bool.must_not.push({"match": {"title": "null"}});
        if(queryType == "advancedQuery"){
            cp =1;
            handleAdvanceQueryRules();
            setCookie("queryRule",JSON.stringify(queryRule));
            setCookie("queryType","advancedQuery");
        }else if(queryType == "load"){
            console.info(getCookie("queryRule"));
            cp = getCookie("currentPage")?parseInt(getCookie("currentPage")):1;
            queryRule = (getCookie("queryRule") != null && getCookie("queryRule")!="")?JSON.parse(getCookie("queryRule")):queryRule;
        } else {
            cp =1;
            var searchInputVal = $('#words').val();
            var s1;
            if (searchInputVal != null && searchInputVal != ""){
                s1 = searchInputVal.split(";")
            }else {
                queryRule.query.bool.must.push({"match_all": {}});
            }
            if (s1 != null && s1.length == 1) {
                handleQueryRules(s1[0],1);
            }
            if(s1 != null && s1.length > 1){
                for (var i = 0; i < s1.length; i++) {
                    var extraRule = { "bool": { "must": [], "must_not": [], "should": [], "minimum_should_match": 1 } };
                    queryRule.query.bool.should.push(extraRule);
                    handleQueryRules(s1[i], s1.length,i);
                }
            }
            setCookie("queryRule",JSON.stringify(queryRule));
            setCookie("queryType","simpleQuery");
        }

    }

    function handleAdvanceQueryRules(){
        console.info("AdvanceQuery");
        var mustInputVal = $('#mustWords').val().trim();
        var shouldInputVal = $('#shouldWords').val().trim();
        var mustNotInputVal = $('#mustNotWords').val().trim();
        var domainInputVal = $('#domainRule').val().trim();
        if(mustInputVal && mustInputVal!= ""){
            var mustPrams = mustInputVal.split(/\s+/);
            var tmpRule = [];
            for (var i = 0; i < mustPrams.length; i++) {
                var pram = '{ "range": { "wordsMap.' + mustPrams[i].trim() + '":{} } }';
                tmpRule=JSON.parse(pram);
                queryRule.query.bool.must.push(tmpRule);
            }
        }
        if(shouldInputVal && shouldInputVal!= ""){
            var shouldPrams = shouldInputVal.split(/\s+/);
            var tmpRule = [];
            for (var i = 0; i < shouldPrams.length; i++) {
                var pram = '{ "range": { "wordsMap.' + shouldPrams[i].trim() + '":{} } }';
                tmpRule=JSON.parse(pram);
                queryRule.query.bool.should.push(tmpRule);

            }
        }
        if(mustNotInputVal && mustNotInputVal!= ""){
            var mustNotPrams = mustNotInputVal.split(/\s+/);
            var tmpRule = [];
            for (var i = 0; i < mustNotPrams.length; i++) {
                var pram = '{ "range": { "wordsMap.' + mustNotPrams[i].trim() + '":{} } }';
                tmpRule=JSON.parse(pram);
                queryRule.query.bool.must_not.push(tmpRule);
            }
        }
        if(domainInputVal && domainInputVal!= ""){
            var domainPrams = domainInputVal.split(/\s+/);
            var tmpRule = [];
            for (var i = 0; i < domainPrams.length; i++) {
                var pram = '{ "term": { "rowKey":"'+domainPrams[i].trim()+'" } }';
                tmpRule=JSON.parse(pram);
                queryRule.query.bool.must.push(tmpRule);
            }
        }
    }


    function handleQueryRules(str,len,num) {
        var mustReg = /must\([^\)]*\)/;
        var mustNotReg = /not\([^\)]*\)/;
        var shouldReg = /should\([^\)]*\)/;
        var mustStr = str.match(mustReg);
        var noRuleFlag = 0;
        if(mustStr != null && mustStr.length>0){
            noRuleFlag += 1;
            var mustPrams = mustStr[0].replace(/must\(/, "").replace(/\)/, "").split(",");
            var tmpRule = [];
            for (var i = 0; i < mustPrams.length; i++) {
                var pram = '{ "range": { "wordsMap.' + mustPrams[i].trim() + '":{} } }';
                tmpRule=JSON.parse(pram);
                if(len ==1){
                    queryRule.query.bool.must.push(tmpRule);
                } else {
                    queryRule.query.bool.should[num].bool.must.push(tmpRule);
                }
            }
        }
        var mustNotStr = str.match(mustNotReg);
        if (mustNotStr != null && mustNotStr.length > 0) {
            noRuleFlag += 1;
            var mustNotPrams = mustNotStr[0].replace(/not\(/, "").replace(/\)/, "").split(",");
            var tmpRule = [];
            for (var i = 0; i < mustNotPrams.length; i++) {
                var pram = '{ "range": { "wordsMap.' + mustNotPrams[i].trim() + '":{} } }';
                tmpRule = JSON.parse(pram);
                if (len == 1) {
                    queryRule.query.bool.must_not.push(tmpRule);
                } else {
                    queryRule.query.bool.should[num].bool.must_not.push(tmpRule);
                }
            }

        }
        var shouldStr = str.match(shouldReg);
        if (shouldStr !=null && shouldStr.length > 0) {
            noRuleFlag += 1;
            var shouldPrams = shouldStr[0].replace(/should\(/, "").replace(/\)/, "").split(",");
            var tmpRule = []
            for (var i = 0; i < shouldPrams.length; i++) {
                var pram = '{ "range": { "wordsMap.' + shouldPrams[i].trim() + '":{} } }';
                console.log(pram);
                tmpRule = JSON.parse(pram);
                if (len == 1) {
                    queryRule.query.bool.should.push(tmpRule);
                } else {
                    queryRule.query.bool.should[num].bool.should.push(tmpRule);
                }
            }

        }
        if(len ==1 && noRuleFlag ==0){
            var shouldPrams = str.split(/\s+/);
            console.log(shouldPrams);
            var tmpRule = [];
            for (var i = 0; i < shouldPrams.length; i++) {
                var pram = '{ "range": { "wordsMap.' + shouldPrams[i].trim() + '":{} } }';
                console.log(pram);
                tmpRule = JSON.parse(pram);
                queryRule.query.bool.should.push(tmpRule);
            }

        }
    }

    function changePageContent(){
        getQueryRule();
        var total=0;
        client.search({
            index: 'words',
            type: 'wordline',
            body: queryRule
        }).then(function (data) {
            total = data.hits.total;
            if(total==0){
                $("#content").empty();
                return 0;
            }
            $("#pageNum").jqPaginator({
                totalPages: Math.ceil(total / 10),
                visiblePages: 10,
                currentPage: cp,
                first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
                prev: '<li class="prev"><a href="javascript:void(0);">上一页<\/a><\/li>',
                next: '<li class="next"><a href="javascript:void(0);">下一页<\/a><\/li>',
                last: '<li class="last"><a href="javascript:void(0);">尾页<\/a><\/li>',
                page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
                onPageChange: function (n) {
                    $("#content").empty();
                    getQueryRule((n-1)*10);
                    setCookie("currentPage",n);
                    client.search({
                        index: 'words',
                        type: 'wordline',
                        body: queryRule
                    }).then(function (data) {
                        if(data != null){
                            var os = data.hits.hits;
                            total = data.hits.total;
                            for (var i = 0; i < os.length; i++) {
                                var o = os[i];
                                var title = o._source.title;
                                var rowKey = o._source.rowKey;
                                if(title != null && title != ""&& title != "null"&& rowKey != null && rowKey != "" && rowKey != "null"){
                                    var htmlTag = "<div class='panel'> <div class='panel-body text-center'><h3 class=''><a href='${pageContext.request.contextPath}/dataWarehouse/datawarehouseDetailPage.do?rowKey=" + rowKey + "'>" + title + "</a></h3> </div> </div>";
                                    $("#content").append(htmlTag);
                                }
                            }
                        }
                    }, function (err) {
                        $("#content").empty();
                        console.log('error:');
                        console.log(err);
                    });
                }
            });
        }, function (err) {
            $("#content").empty();
            console.log('error:');
            console.log(err);
        });

    }



</script>
</body>

</html>
<script src="${pageContext.request.contextPath}/adminex/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/menuScript.js"></script>
