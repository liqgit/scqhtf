var EditableTable = function () {

    return {

        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[1].innerHTML = '<input type="text" class="form-control small" value="' + aData[1] + '">';
                jqTds[2].innerHTML = '<input type="text" class="form-control small" value="' + aData[8] + '">';
                jqTds[3].innerHTML = '<input type="text" class="form-control small" value="' + aData[3] + '">';
                jqTds[4].innerHTML = '<input type="text" class="form-control small" value="' + aData[4] + '">';
                jqTds[5].innerHTML = '<input type="text" class="form-control small" value="' + aData[5] + '">';
                jqTds[6].innerHTML = '<a class="edit" href="">Save</a>';
                jqTds[7].innerHTML = '<a class="cancel" href="">Cancel</a>';
            }

            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                var userName = jqInputs[0].value;
                var aData = oTable.fnGetData(nRow);
                if (userName==null||userName===""){
                    alert("用户名不能为空!");
                    return
                }
                var passwd = jqInputs[1].value;
                var transpwd="";
                if (passwd==null||passwd.trim()===""||passwd.length<6){
                    alert("密码不能为空并且不能小于6位!");
                    return
                }else {
                    transpwd = passwd.substr(0,1)+"***"+passwd.substr(passwd.length-1);
                }
                var email = jqInputs[2].value;
                var department = jqInputs[3].value;
                var authority = jqInputs[4].value;

                if (authority==null||authority===""){
                    alert("用户权限不能为空");
                    return
                }
                var uid = aData[9];
                var basePath = '<%=basePath%>';
                swal({
                        title: "是否确认提交修改？",
                        type: "info",
                        showCancelButton: true,
                        closeOnConfirm: false,
                        showLoaderOnConfirm: true
                    },
                    function(){
                        $.post('/userManager/updateUserInfo.do',{
                            "id":uid,
                            "passWd":passwd,
                            "authority":authority,
                            "userName":userName,
                            "email":email,
                            "department":department
                        },function (data) {
                            setTimeout(function(){
                                if(data!=null&&data.result!=null&&data.result===true){
                                    oTable.fnClearTable();
                                    oTable.fnDestroy();
                                    findUserList();
                                    nEditing=null;
                                    swal("修改成功!");
                                }else{
                                    editRow(oTable, nRow);
                                    swal("修改失败，请重新提交");
                                }
                            }, 1000);

                        },'json').error(function() { restoreRow(oTable, nRow);swal("网络异常"); })

                    });

            }

            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
                oTable.fnUpdate(jqInputs[5].value, nRow, 5, false);
                oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 6, false);
                oTable.fnDraw();
            }
            var tableBody=$("#table1")[0];
            var oTable = $('#editable').dataTable({
                'autoWidth':true,
                'searching':false,
                "aLengthMenu": [
                    [5, 10, 20, -1],
                    [5, 10, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 5,
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "每页 _MENU_条记录",
                    "oPaginate": {
                        "sPrevious": "上一页",
                        "sNext": "下一页"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0]
                    }
                ]
            });

            jQuery('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
            jQuery('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

            var nEditing = null;

            $('#editable-sample_new').click(function (e) {
                e.preventDefault();
                var aiNew = oTable.fnAddData([0, '', '', '','','visitor',
                        '<a class="edit" href="">Edit</a>', '<a class="cancel" data-mode="new" href="">Cancel</a>','',''
                ]);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                editRow(oTable, nRow);
                nEditing = nRow;
            });

            $('#editable a.delete').live('click', function (e) {
                e.preventDefault();
                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                var userName = aData[1];
                var uid = aData[9];
                swal({
                        title: "确定"+userName+"用户吗？",
                        text: "你将无法恢复该用户！",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定删除",
                        closeOnConfirm: false,
                        showLoaderOnConfirm: true
                    },
                    function(isConfirm){
                        if (isConfirm) {
                            $.post('/userManager/deleteUser.do',{
                                "id":uid
                            },function (data) {
                                setTimeout(function(){
                                    if(data!=null&&data.result!=null&&data.result===true){
                                        oTable.fnDeleteRow(nRow);
                                        swal("删除成功", userName+"用户已经被删除。", "success");
                                    }else{
                                        swal("修改失败，请重新提交");
                                    }
                                }, 1000);

                            },'json').error(function() { restoreRow(oTable, nRow);swal("网络异常,删除失败"); })
                        } else {
                            swal("取消！", "取消删除"+userName+"用户吗",
                                "error");
                        }


                    });

            });

            $('#editable a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#editable a.edit').live('click', function (e) {
                e.preventDefault();
                var nRow = $(this).parents('tr')[0];
                if (nEditing !== null && nEditing != nRow) {
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    saveRow(oTable, nEditing);
                } else {
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });
        }

    };

}();