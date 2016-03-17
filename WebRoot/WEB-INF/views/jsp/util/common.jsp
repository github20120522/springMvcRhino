<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--css--%>
<link rel="stylesheet" type="text/css" href="${basePath}static/js/lib/bootstrap/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}static/js/lib/datatables/DataTables-1.10.10/css/custom.dataTables.css"/>
<style type="text/css">
    .dtCenter {
        text-align: center;
    }
    .dtNoWrap {
        white-space: nowrap;
    }
    td.details-control {
        background: url('${basePath}static/js/lib/datatables/DataTables-1.10.10/images/details_open.png') no-repeat center center;
        cursor: pointer;
    }
    tr.shown td.details-control {
        background: url('${basePath}static/js/lib/datatables/DataTables-1.10.10/images/details_close.png') no-repeat center center;
    }
    .dataTables_length {
        margin-top: 10px;
        margin-left: 10px;
    }
    .modal {
        text-align: center;
        padding-top: 200px;
        display: none;
        position: fixed;
        z-index: 9000;
        top: 0;
        left: 0;
        height: 100%;
        width: 100%;
        background: rgba(255, 255, 255, .3);
    }
    body.loading {
        overflow: hidden;
    }
    body.loading .modal {
        display: block;
    }
</style>
<%--javascript--%>
<script type="text/javascript" src="${basePath}static/js/lib/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${basePath}static/js/lib/datatables/datatables.min.js"></script>
<script type="text/javascript" src="${basePath}static/js/lib/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript">
    $.extend($.fn.dataTable.defaults, {
        "displayLength": 20,
        "lengthMenu": [[20, 50, 100], [20, 50, 100]],
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

    function commonDataTable(url, elementStr, exConfig, ajaxParamsFn, columns, columnDefs, singleSelectFlag) {

        var config = {
            "dom": '<"toolbar"rtip><"bottom"l>',
            // scrollY: 400,
            scrollX: true,
            ajax: {
                url: url,
                type: "POST",
                data: ajaxParamsFn
            },
            "columns": columns,
            "columnDefs": columnDefs
        };
        for (var key in exConfig) {
            config[key] = exConfig[key];
        }
        var table = $('#' + elementStr).DataTable(config);
        table.on('processing.dt', function (e, settings, processing) {
            $('#processingIndicator').css('display', processing ? 'block' : 'none');
        });

        if (!singleSelectFlag) {
            // 全选/取消工具栏
            var toolbar = '<p>';
            toolbar += '<input id="selectAllBtn" type="button" class="btn btn-primary btn-xs" value="全选"/>';
            toolbar += '<input id="deSelectAllBtn" type="button" class="btn btn-default btn-xs" value="取消全选">';
            toolbar += '</p>';
            $("div.sBar").html(toolbar);

            $("#selectAllBtn").click(function () {
                var trs = $("#" + elementStr + " > tbody > tr");
                for (var i=0; i<trs.length; i++) {
                    if (!$(trs[i]).hasClass('selected')) {
                        $(trs[i]).addClass('selected');
                    }
                }
            });

            $("#deSelectAllBtn").click(function () {
                var trs = $("#" + elementStr + " > tbody > tr");
                for (var i=0; i<trs.length; i++) {
                    if ($(trs[i]).hasClass('selected')) {
                        $(trs[i]).removeClass('selected');
                    }
                }
            });

            // 多选
            $('#' + elementStr + ' tbody').on('click', 'tr', function () {
                $(this).toggleClass('selected');
            });
        } else {
            // 单选
            $('#' + elementStr + ' tbody').on('click', 'tr', function () {
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
</script>
<div id="processingIndicator" class="modal">
    <span style="color: #7e7bff">正在处理，请稍等。。。</span>
</div>
