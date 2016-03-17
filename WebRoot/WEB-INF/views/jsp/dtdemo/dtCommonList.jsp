<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>用户管理</title>
    <%@ include file="../util/common.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {

            var ajaxParamsFn = function (d) {
                d.name = $("#name").val();
            };
            var columns = [
                { "data": "id" },
                {
                    "data": null,
                    "className": 'details-control',
                    "defaultContent": ''
                },
                { "data": "name", className: "dtCenter" },
                { "data": "realName", className: "dtCenter dtNoWrap" },
                { "data": "isActivity", className: "dtCenter" },
                { "data": "op", className: "dtCenter" }
            ];
            var columnDefs = [
                { "visible": false,  "targets": [ 0 ] },
                {
                    "render": function ( data, type, row ) {
                        return data +' ('+ row.isActivity+')';
                    },
                    "targets": 2
                },
                {
                    "targets": -1,
                    "data": null,
                    "defaultContent": "<button>点点点</button>"
                }
            ];
            var table = new commonDataTable("${basePath}dt/dtListJson", "example", {scrollY: 300}, ajaxParamsFn, columns, columnDefs, false);

            // 自定义
            $('#example tbody').on('click', 'button', function () {
                var data = table.row($(this).parents('tr')).data();
                alert(data.name + data.realName);
            });

            // 获取选中元素的值
            $('#button').click(function () {
                console.log(table.rows('.selected').data());
            });

            // 重新查询
            $("#reloadBtn").click(function () {
                table.ajax.reload();
            });

            // 格式化数据
            function format ( d ) {
                var table = '<table cellpadding="5" cellspacing="0" border="0" style="margin-left: 50px; width: 500px;">';
                table += '<tbody style="padding-left: 50px;">';
                table += '<tr>';
                table += '<td>帐号:</td>';
                table += '<td>' + d.name + '</td>';
                table += '</tr>';
                table += '<td>真名:</td>';
                table += '<td>' + d.realName + '</td>';
                table += '</tr>';
                table += '<tr>';
                table += '<td>状态:</td>';
                table += '<td>' + d.isActivity + '</td>';
                table += '</tr>';
                table += '</tbody>';
                table += '</table>';
                return table;
            }

            $('#example tbody').on('click', 'td.details-control', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                if (row.child.isShown()) {
                    // This row is already open - close it
                    row.child.hide();
                    tr.removeClass('shown');
                } else {
                    // Open this row
                    row.child(format(row.data())).show();
                    tr.addClass('shown');
                }
            });

            $("#name").bind("keypress", function (event) {
                if (event.keyCode == 13) {
                    table.ajax.reload();
                }
            });

        });
    </script>
</head>
<body>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">用户列表</h3>
    </div>
    <div class="panel-body">
        <div class="form-group form-inline">
            <input id="button" class="btn btn-success" type="button" value="获取选中数据">
            <label>姓名</label><input id="name" class="form-control" type="text" placeholder="名字" />
            <input id="reloadBtn" class="btn btn-default" type="button" value="查询">
        </div>
        <div class="sBar"></div>
        <table id="example" class="dataTable compact display cell-border hover order-column row-border stripe" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>ID</th>
                <th></th>
                <th>姓名</th>
                <th>真实姓名</th>
                <th>是否有效</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
