var dataMap = {};
function dataFormatter(obj) {
    var pList = ['中东呼吸综合征冠状病毒',
        '脊髓灰质炎',
        '流感',
        '传染性非典型肺炎',
        '肺炭疽',
        '新型冠状病毒病',
        '埃博拉病毒',
        '拉沙热',
        '马尔堡热',
        '西尼罗热'];
    var temp;
    var max = 0;
    for (var year = 2006; year <= 2015; year++) {
        temp = obj[year];
        for (var i = 0, l = temp.length; i < l; i++) {
            max = Math.max(max, temp[i]);
            obj[year][i] = {
                name: pList[i],
                value: temp[i]
            }
        }
        obj[year + 'max'] = Math.floor(max / 100) * 100;
    }
    return obj;
}

function dataMix(list) {
    var mixData = {};
    for (var i = 0, l = list.length; i < l; i++) {
        for (var key in list[i]) {
            if (list[i][key] instanceof Array) {
                mixData[key] = mixData[key] || [];
                for (var j = 0, k = list[i][key].length; j < k; j++) {
                    mixData[key][j] = mixData[key][j]
                        || {name: list[i][key][j].name, value: []};
                    mixData[key][j].value.push(list[i][key][j].value);
                }
            }
        }
    }
    return mixData;
}

dataMap.dataGDP = dataFormatter({
    "2015": [49, 92, 0, 136, 87, 15, 19, 60, 0, 6],
    "2014": [7, 44, 22, 0, 0, 750, 0, 50, 8, 0],
    "2013": [21, 47, 165, 34, 0, 0, 0, 110, 32, 0],
    "2012": [35, 2, 308, 34, 0, 180, 19, 0, 40, 0],
    "2011": [7, 2, 341, 68, 58, 15, 0, 50, 32, 0],
    "2010": [42, 4, 231, 0, 0, 0, 0, 80, 40, 0],
    "2009": [21, 4, 792, 102, 15, 38, 40, 48, 0],
    "2008": [42, 2, 429, 34, 0, 30, 19, 30, 88, 0],
    "2007": [28, 2, 759, 170, 0, 45, 0, 30, 24, 0],
    "2006": [77, 2, 891, 170, 0, 0, 0, 50, 16, 0]
});

dataMap.dataFinancial = dataFormatter({
    "2015": [0, 17, 0, 0, 0, 0, 0, 0, 0, 0],
    "2014": [0, 2, 0, 0, 0, 0, 0, 0, 0, 0],
    "2013": [0, 2, 154, 0, 0, 0, 0, 0, 0, 0],
    "2012": [0, 0, 22, 0, 0, 0, 0, 0, 0, 0],
    "2011": [0, 0, 0, 0, 0, 0, 0, 10, 0, 0],
    "2010": [0, 0, 22, 0, 0, 0, 0, 0, 0, 0],
    "2009": [0, 0, 44, 0, 0, 0, 0, 0, 0, 0],
    "2008": [0, 0, 44, 0, 0, 0, 0, 20, 0, 0],
    "2007": [0, 0, 77, 0, 0, 0, 0, 0, 0, 0],
    "2006": [0, 0, 165, 0, 0, 0, 0, 0, 0, 0]
});

dataMap.dataGDP_Estate = dataMix([dataMap.dataEstate, dataMap.dataGDP]);

