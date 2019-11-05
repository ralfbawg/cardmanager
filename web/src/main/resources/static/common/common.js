/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * 项目自定义的公共JavaScript，可覆盖jeesite.js里的方法
 */
$.extend({
    TransferUsdToRmb: function (curr, usd) {
        if (curr == undefined || usd == undefined) {
            return "未知";
        }
        var result = (curr * usd) / 10000;
        console.log(result);
        return result;
    },
    accDiv: function (arg1, arg2) {
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
    },
    CurrentLongToDouble: function (a) {
        console.log(a)
        return a / 100;
    },
    GetUser: function (usercode) {
        if ($.userCache.users[usercode] != undefined && new Date().getTime() - $.userCache.lastTime[usercode] < 60 * 1000) {
            return $.userCache.users[usercode];
        }
        var user;
        $.ajax({
            type: "POST",
            url: "/system/getUser",
            data: {usercode: usercode},
            dataType: "json",
            async: false,
            success: function (data) {
                user = data;
                $.userCache.users[user.usercode] = user;
                $.userCache.lastTime[user.usercode] = new Date().getTime();
            },
            error: function (e) {
                console.log(e);
            }
        });
        return user;
    },
    GetBudget: function (budgetId) {
        if ($.budgetCache.budgetWithId[budgetId] != undefined && new Date().getTime() - $.budgetCache.lastTime[budgetId] < 60 * 1000) {
            return $.budgetCache.budgetWithId[budgetId];
        }
        var budget;
        $.ajax({
            type: "POST",
            url: "/system/getBudget",
            data: {budgetId: budgetId},
            dataType: "json",
            async: false,
            success: function (data) {
                budget = data;
                var now = new Date().getTime();
                $.budgetCache.budgets[budget.ownerUsercode] = budget;
                $.budgetCache.lastTime[budget.ownerUsercode] = now;
                $.budgetCache.IdLastTime[budget.id] =  now;
                $.budgetCache.budgetWithId[budget.id] = budget;
            },
            error: function (e) {
                console.log(e);
            }
        });
        return budget;
    },
    GetBudgetByUsercode: function (usercode) {
        if ($.budgetCache.budgets[usercode] != undefined && new Date().getTime() - $.budgetCache.lastTime[usercode] < 60 * 1000) {
            return $.budgetCache.budgets[usercode];
        }
        var budget;
        $.ajax({
            type: "POST",
            url: "/system/getBudgetByUsercode",
            data: {usercode: usercode},
            dataType: "json",
            async: false,
            success: function (data) {
                budget = data;
                var now = new Date().getTime();
                $.budgetCache.budgets[budget.ownerUsercode] = budget;
                $.budgetCache.lastTime[budget.ownerUsercode] = now;
                $.budgetCache.IdLastTime[budget.id] =  now;
                $.budgetCache.budgetWithId[budget.id] = budget;
            },
            error: function (e) {
                console.log(e);
            }
        });
        return budget;
    },
    GetBudgetCardCount: function (budgetId) {
        if ($.budgetCountCache.budgets[budgetId] != undefined && new Date().getTime() - $.budgetCountCache.lastTime[budgetId] < 60 * 1000) {
            return $.budgetCountCache[budgetId];
        }
        var count;
        $.ajax({
            type: "POST",
            url: "/system/getBudgetCardCount",
            data: {budgetId: budgetId},
            dataType: "json",
            async: false,
            success: function (data) {
                count = data;
                $.budgetCountCache.budgets[budgetId] = count;
                $.budgetCountCache.lastTime[budgetId] = new Date().getTime();
            },
            error: function (e) {
                console.log(e);
            }
        });
        return count;
    },
    userCache: {users: [], lastTime: []},
    budgetCache: {budgets: [], budgetWithId:[],lastTime: [],IdLastTime: []},
    budgetCountCache: {budgets: [], lastTime: []}
});

function addRow(id, obj) {
    // 选中行rowid
    var rowId = $("#" + id).dataGrid('getGridParam', 'selrow');

    // 选中行实际表示的位置
    var ind = $("#" + id).getInd(rowId);
    // 新插入行的位置
    var newInd = ind + 1;
    $("#" + id).addRowData(rowId + 1, obj, newInd);
}

var budgets = new Array();
var cardBudgets = new Array();

function getBudget(id, refresh) {
    if (budgets[id] == undefined || refresh) {
        budgets[id] = $.GetBudget(id);
    }
    return budgets[id];
}