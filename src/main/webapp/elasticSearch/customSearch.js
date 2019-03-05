var client = new $.es.Client({hosts: 'http://sandbox.hortonworks.com:9201',log: 'trace'});
var size = 10;
var from = 0;

function getQueryRule(type,fromNum){
    from = fromNum?fromNum:0;
    var basicQueryRule = {
        "query": {"bool": {"must": [], "must_not": [], "should": [], "minimum_should_match": 1}},
        "size": size,
        "from": from
    };
    basicQueryRule.query.bool.must_not.push({"match": {"title": "null"}});
    var queryRule = basicQueryRule;
    if(type=='simpleQuery'){
        var searchInputVal = $('#words').val();
        var s1;
        if (searchInputVal != null && searchInputVal != ""){
            s1 = searchInputVal.split(";")
        }else {
            queryRule.query.bool.must.push({"match_all": {}});
        }
        if (s1 != null && s1.length == 1) {
            queryRule = handleSimpleQueryRules(queryRule,s1[0],1);
        }
        if(s1 != null && s1.length > 1){
            for (var i = 0; i < s1.length; i++) {
                var extraRule = { "bool": { "must": [], "must_not": [], "should": [], "minimum_should_match": 1 } };
                queryRule.query.bool.should.push(extraRule);
                var num = i
                queryRule = handleSimpleQueryRules(queryRule,s1[i], s1.length,num);
            }
        }
    }else {
        queryRule = handleAdvancedQueryRules(queryRule);
    }
    return queryRule;
}

function handleAdvancedQueryRules(queryRule){
    var mustStr = $('#mustWords').val() ? $('#mustWords').val().trim : "";
    var shouldStr = $('#shouldWords').val()?$('#shouldWords').val().trim:"";
    var mustNotStr = $('#mustNotWords').val()?$('#mustNotWords').val().trim:"";
    var domainStr = $('#domainRules').val()?$('#domainRules').val().trim:"";
    if(mustStr!=""){
        var mustPrams = mustStr.split(/\s+/);
        for (var i = 0; i < mustPrams.length; i++) {
            var pram = '{ "range": { "wordsMap.' + mustPrams[i].trim() + '":{} } }';
            queryRule.query.bool.must.push(JSON.parse(pram));
        }
    }
    if(mustNotStr!=""){
        var mustNotPrams = mustNotStr.split(/\s+/);
        for (var i = 0; i < mustPrams.length; i++) {
            var pram = '{ "range": { "wordsMap.' + mustNotPrams[i].trim() + '":{} } }';
            queryRule.query.bool.must.push(JSON.parse(pram));
        }
    }
    if(shouldStr!=""){
        var shouldPrams = shouldStr.split(/\s+/);
        for (var i = 0; i < shouldPrams.length; i++) {
            var pram = '{ "range": { "wordsMap.' + mustNotPrams[i].trim() + '":{} } }';
            queryRule.query.bool.must.push(JSON.parse(pram));
        }
    }
    if(domainStr!=""){
        var domainPrams = domainStr.split(/\s+/);
        for (var i = 0; i < domainPrams.length; i++) {
            var pram = '{ "range": { "wordsMap.' + mustNotPrams[i].trim() + '":{} } }';
            queryRule.query.bool.must.push(JSON.parse(pram));
        }
    }
    return queryRule;


}


function handleSimpleQueryRules(queryRule,str,len,num) {
    var mustReg = /must\([^\)]*\)/
    var mustNotReg = /not\([^\)]*\)/
    var shouldReg = /should\([^\)]*\)/
    var mustStr = str.match(mustReg);
    var noRuleFlag = 0;
    if(mustStr != null && mustStr.length>0){
        noRuleFlag += 1;
        var mustPrams = mustStr[0].replace(/must\(/, "").replace(/\)/, "").split(",");
        var tmpRule = []
        for (var i = 0; i < mustPrams.length; i++) {
            var pram = '{ "range": { "wordsMap.' + mustPrams[i].trim() + '":{} } }';
            tmpRule=JSON.parse(pram);
            if(len ==1){
                queryRule.query.bool.must.push(tmpRule);
            } else {
                queryRule.query.bool.should[num].bool.must.push(tmpRule);
            }
        }
    return queryRule;

    }
    var mustNotStr = str.match(mustNotReg);
    if (mustNotStr != null && mustNotStr.length > 0) {
        noRuleFlag += 1;
        var mustNotPrams = mustNotStr[0].replace(/not\(/, "").replace(/\)/, "").split(",");
        var tmpRule = []
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
        var tmpRule = []
        for (var i = 0; i < shouldPrams.length; i++) {
            var pram = '{ "range": { "wordsMap.' + shouldPrams[i].trim() + '":{} } }';
            console.log(pram);
            tmpRule = JSON.parse(pram);
            queryRule.query.bool.should.push(tmpRule);
        }

    }
}

function changePageContent(type) {
    var queryRule = "";
    queryRule = getQueryRule(type);
    var total=0;
    client.search({
        index: 'words',
        type: 'wordline',
        body: queryRule
    }).then(function (data) {
        total = data.hits.total;
        $("#pageNum").jqPaginator({
            totalPages: Math.ceil(total / 10),
            visiblePages: 10,
            currentPage: 1,
            first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
            prev: '<li class="prev"><a href="javascript:void(0);">上一页<\/a><\/li>',
            next: '<li class="next"><a href="javascript:void(0);">下一页<\/a><\/li>',
            last: '<li class="last"><a href="javascript:void(0);">尾页<\/a><\/li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
            onPageChange: function (n) {
                $("#content").empty();
                queryRule = getQueryRule(type,(n-1)*10);
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
                    console.log('error:');
                    console.log(err);
                });


            }

        });
    }, function (err) {
        console.log('error:');
        console.log(err);
    });

}