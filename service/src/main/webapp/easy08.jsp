<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic Panel - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.2/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.2/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.2/demo/demo.css">
    <script type="text/javascript" src="jquery-easyui-1.5.2/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
    <script>


    </script>
    <script>
        $(function () {
            var flag; //判断方法
            $('#t_student').datagrid({
                idField: 'id',
                title: '学生列表',
                width: "100%",
//                fit: true,
                height: 400,
                url: '/rest/student/form',
                method: 'get',
//                striped: true,
                fitColumns: true,
                rownumbers: true,
                loadMsg: "数据加载中,请稍后",
//                singleSelect: true,
                rowStyler: function (index, record) {
                    if (record.time > 1494385869000) {
                        return "background:pink";
                    }
                },
                frozenColumns: [[
                    {
                        field: 'ck',
                        width: 50,
                        checkbox: true
                    }
                ]],
                columns: [[
                    {
                        field: 'id',
                        title: "编号",
                        width: 80
                    }, {
                        field: 'name',
                        title: "姓名",
                        width: 120,
                        align: "center"
                    }, {
                        field: 'del',
                        title: "是否删除",
                        width: 40
                    }, {
                        field: 'time',
                        title: "创建时间",
                        width: 80,
                        formatter: function (value, row, index) {
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.toLocaleString();
                        }
                    }, {
                        field: 'last',
                        title: "最后修改时间",
                        width: 120,
                        formatter: function (value, row, index) {
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.toLocaleString();
                        }
                    }
                ]],
                pagination: true,
                pageSize: 5,
                pageList: [5, 10, 15, 20, 25, 30],
                toolbar: [
                    {
                        text: '新增学生',
                        iconCls: 'icon-add',
                        handler: function () {
                            flag = "add";
                            $('#mydialog').dialog({
                                title: '新增学生'
                            });
                            $('#mydialog').dialog('open');
                        }
                    }, {
                        text: '修改学生',
                        iconCls: 'icon-edit',
                        handler: function () {
                            flag = "edit";
                            var arr = $('#t_student').datagrid('getSelections');
                            if (arr.length != 1) {
                                $.messager.show({
                                    title: '提示信息!',
                                    msg: '只能选择一行记录进行修改'
                                });
                            } else {
                                $('#mydialog').dialog({
                                    title: '修改学生'
                                });
                                $('#mydialog').dialog('open');
                                $('#myform').get(0).reset();
                                $('#myform').form('load', {
                                    id: arr[0].id,
                                    name: arr[0].name
                                });
                            }
                        }
                    }, {
                        text: '删除学生',
                        iconCls: 'icon-remove',
                        handler: function () {
                            var arr = $('#t_student').datagrid('getSelections');
                            if (arr.length <= 0) {
                                $.messager.show({
                                    title: '提示信息!',
                                    msg: '请至少选择一条记录进行删除'
                                });
                            } else {
                                $.messager.confirm('提示信息', '确认删除?', function (r) {
                                    if (r) {
                                        var ids = '';
                                        for (var i = 0; i < arr.length; i++) {
                                            ids += arr[i].id + ",";
                                        }
                                        ids = ids.substring(0, ids.length - 1);
                                        $.post('/rest/student/del', {ids: ids}, function (result) {
                                            $('#t_student').datagrid('reload');
//                                            $('#t_student').datagrid('unselectAll');
                                            $.messager.show({
                                                title: '提示信息',
                                                msg: '删除成功'
                                            });
                                        });
                                    } else {
                                        return;
                                    }
                                });

                            }
                        }
                    }, {
                        text: '查询学生',
                        iconCls: 'icon-search',
                        handler: function () {
                            $('#lay').layout('expand', 'north');
                        }
                    }
                ]

            });
            $('#btn2').click(function () {
                $('#mydialog').dialog('close');
            });
            $('#btn1').click(function () {
                if ($('#myform').form('validate')) {
                    $.ajax({
                        type: 'post',
                        url: '/rest/student/' + flag,
                        cache: false,
                        data: $('#myform').serialize(),
                        dataType: 'json',
                        success: function (result) {
                            $('#mydialog').dialog('close');
                            $.messager.show({
                                title: "状态",
                                msg: "操作成功"
                            });
                        }
                    });
                } else {
                    $.messager.show({
                        title: '提示信息',
                        msg: '数据验证不通过,不能保存'
                    });
                }
            });
            $('#searchbtn').click(function () {
                if ($('#mysearch').form('validate')) {
                    $('#t_student').datagrid('load', serializeForm($('#mysearch')));
                } else {
                    $.messager.show({
                        title: '提示信息',
                        msg: '数据验证不通过,不能保存'
                    });
                }

            });
            $('#clearbtn').click(function () {
                $('#mysearch').form('clear');
                $('#t_student').datagrid('load', {});
            });

        });
        function serializeForm(form) {
            var obj = {};
            $.each(form.serializeArray(), function (index) {
                if (obj[this['name']]) {
                    obj[this['name']] = obj[this['name']] + ',' + this['value'];
                } else {
                    obj[this['name']] = this['value'];
                }
            });
            return obj;
        }

    </script>
</head>
<body>
<div id="lay" class="easyui-layout" style="width:100%;height: 100%">
    <div region="north" title="学生查询" collapsed="true" style="height: 100px;">
        <form id="mysearch" method="post">
            学生姓名<input name="username" class="easyui-validatebox" required="true" missingMessage="学生名必须填写"
                       value=""><br/>
            开始日期(根据创建时间)<input name="startTime" class="easyui-datetimebox" editable="false" style="width:160px"
                               value="">
            结束日期(根据创建时间)<input name="endTime" class="easyui-datetimebox" editable="false" style="width:160px" value="">
            <a id="searchbtn" class="easyui-linkbutton">查询</a>
            <a id="clearbtn" class="easyui-linkbutton">清空</a>

        </form>

    </div>
    <div region="center">
        <table id="t_student"></table>
    </div>

</div>

<div id="mydialog" model="true" draggable="false" class="easyui-dialog" closed="true" style="width:260px;">
    <form id="myform" action="" method="post">
        <input type="hidden" name="id" value=""/>
        学生姓名<input name="name" type="text"/>
        <a id="btn1" class="easyui-linkbutton">确定</a>
        <a id="btn2" class="easyui-linkbutton">关闭</a>

    </form>

</div>

<%--<table class="easyui-datagrid" title="Basic DataGrid" style="width:700px;height:auto"--%>
<%--data-options="singleSelect:true,collapsible:true,url:'/rest/student/form',method:'get'">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th data-options="field:'id',width:100">编号</th>--%>
<%--<th data-options="field:'name',width:100">姓名</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--</table>--%>

</body>
</html>