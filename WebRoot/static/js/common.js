/* common dataTable begin */
$.extend($.fn.dataTable.defaults, {
    "displayLength": 20,
    "lengthMenu": [[20, 50, 100, 500], [20, 50, 100, 500]],
    searching: false,
    ordering: false,
    processing: false,
    serverSide: true,
    "language": {
        "sProcessing":   "处理中...",
        "sLengthMenu":   "显示 _MENU_ 项结果",
        "sZeroRecords":  "没有匹配结果",
        "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    }
});

function commonDataTable(paramsConfig) {

    // paramsConfig : {url, tabElement, exConfig, ajaxParamsFn, columns, columnDefs, singleSelectFlag}
    var config = {
        "dom": '<"toolbar"rtip><"bottom"l>',
        // scrollY: 400,
        scrollX: true,
        ajax: {
            url: paramsConfig.url,
            type: "POST",
            data: function(d) {
                var result = $.extend({}, paramsConfig.ajaxParamsFn());
                result.draw = d.draw;
                result.length = d.length;
                result.start = d.start;
                return result;
            }
        },
        "columns": paramsConfig.columns,
        "columnDefs": paramsConfig.columnDefs
    };
    $.extend(config, paramsConfig.exConfig);
    var table = $(paramsConfig.tabElement).DataTable(config);
    table.on('processing.dt', function (e, settings, processing) {
        $('#processingIndicator').css('display', processing ? 'block' : 'none');
    });

    if (!paramsConfig.singleSelectFlag) {
        // 全选/取消工具栏
        var toolbar = '<div style="position: absolute; top: 1px; z-index: 9527;">';
        toolbar += '<input id="selectAllBtn" type="button" class="btn btn-info btn-xs" value="全"/>';
        toolbar += '<input id="deSelectAllBtn" type="button" class="btn btn-default btn-xs" value="空">';
        toolbar += '</div>';
        $(toolbar).prependTo($(paramsConfig.tabElement + "_wrapper"));

        $("#selectAllBtn").click(function () {
            var trs = $(paramsConfig.tabElement + " > tbody > tr");
            for (var i=0; i<trs.length; i++) {
                if (!$(trs[i]).hasClass('selected')) {
                    $(trs[i]).addClass('selected');
                }
            }
        });

        $("#deSelectAllBtn").click(function () {
            var trs = $(paramsConfig.tabElement + " > tbody > tr");
            for (var i=0; i<trs.length; i++) {
                if ($(trs[i]).hasClass('selected')) {
                    $(trs[i]).removeClass('selected');
                }
            }
        });

        // 多选
        $(paramsConfig.tabElement + ' tbody').on('click', 'tr', function () {
            $(this).toggleClass('selected');
        });
    } else {
        // 单选
        $(paramsConfig.tabElement + ' tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            } else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
        });
    }

    return table;
}

// 格式化数据
function rowDetailFormat (dataList) {
    var table = '<table cellpadding="5" cellspacing="0" border="0" style="margin-left: 50px; width: 800px;">';
    table += '<tbody style="padding-left: 50px;">';
    for (var i=0; i<dataList.length; i++) {
        var data = dataList[i];
        table += '<tr>';
        table += '<td width="100px;">' + data.label + ':</td>';
        table += '<td>' + data.value + '</td>';
        table += '</tr>';
    }
    table += '</tbody>';
    table += '</table>';
    return table;
}

// 显示记录明细数据
function bindRowDetailShow(table, tabElement, keyList) {

    // table: dataTable 对象，tabElement: 具体的表格元素，keyList: 明细数据的label和key
    $(tabElement + ' tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row
            var rowData = row.data();
            var dataList = [];
            for (var i=0; i<keyList.length; i++) {
                var data = {
                    label: keyList[i].label,
                    value: rowData[keyList[i].key]
                };
                dataList.push(data);
            }
            row.child(rowDetailFormat(dataList)).show();
            tr.addClass('shown');
        }
    });
}
/* common dataTable end */

// 遮罩层
function maskTrigger(flag) {
    if (flag) {
        $("#maskProcess").show();
    } else {
        $("#maskProcess").hide();
    }
}
