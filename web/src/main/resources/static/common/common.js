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
    }
});