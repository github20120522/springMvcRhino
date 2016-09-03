<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--css--%>
<link rel="stylesheet" type="text/css" href="${basePath}static/js/lib/bootstrap/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}static/js/lib/datatables/DataTables-1.10.10/css/custom.dataTables.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}static/css/common.css"/>
<%--javascript--%>
<script type="text/javascript" src="${basePath}static/js/lib/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${basePath}static/js/lib/datatables/datatables.min.js"></script>
<script type="text/javascript" src="${basePath}static/js/lib/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${basePath}static/js/common.js"></script>
<div id="processingIndicator" class="modalTab">
    <span style="color: #ffffff; font-size: 12px; top: 95%; right: 0; position: fixed;">loading...</span>
</div>
<div id="maskProcess" class="modalMask">
    <span style="color: #ff1a13; font-size: 20px;">正在处理，请稍等。。。</span>
</div>
