/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * 项目自定义的公共JavaScript，可覆盖jeesite.js里的方法
 */
$.extend({
    TransferUsdToRmb:function(curr,usd){
        if (curr==undefined||usd==undefined){
            return "未知";
        }
        var result = (curr*usd)/10000;
        console.log(result);
        return result;
    },
    accDiv:function(arg1,arg2) {
        var t1 = 0, t2 = 0, r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length
        } catch (e) {
        }
        try {
            t2 = arg2.toString().split(".")[1].length
        } catch (e) {
        }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""))
            r2 = Number(arg2.toString().replace(".", ""))
            return (r1 / r2) * pow(10, t2 - t1);
        }
    }
});
function addRow(id,obj) {
    // 选中行rowid
    var rowId = $("#"+id).dataGrid('getGridParam', 'selrow');

    // 选中行实际表示的位置
    var ind = $("#"+id).getInd(rowId);
    // 新插入行的位置
    var newInd = ind + 1;
    $("#"+id).addRowData(rowId + 1, obj, newInd);
}