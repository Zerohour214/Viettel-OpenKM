<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 10/14/2021
  Time: 3:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Organization</title>
    <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="../js/jstree.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css"/>
    <link rel="stylesheet" href="../css/font-awesome/font-awesome.min.css"/>
</head>
<body>
<div class="container-fluid pt-4">


    <div id="wrapper-org" class="row">
        <div class="col-md-4">
            <div class="card mt-3">
                <div class="card-header">
                    <button type="button" class="btn btn-success" id="tree-open-all">
                        <i class="fa fa-folder-open-o"></i>
                    </button>
                    <button type="button" class="btn btn-secondary" data-bs-toggle="modal"
                            data-bs-target="#warning-delete-org">
                        <i class="fa fa-trash-o"></i>
                    </button>
                    <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                            data-bs-target="#importOrgModal" id="btn-import_org">
                        <i class="fa fa-upload"></i>
                    </button>
                </div>
                <div class="card-body">
                    <div id="jstree_demo_div">
                    </div>
                </div>
            </div>

            <!-- The Modal import org-->
            <div class="modal fade" id="importOrgModal">
                <div class="modal-dialog modal-dialog-scrollable modal-lg ">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>

                        <div class="modal-body">
                            <form id="form-import-org" enctype="multipart/form-data">
                                <div class="input-group mb-3">
                                    <input type="file" class="form-control" name="userFile" id="input-file-org">
                                    <button class="btn btn-success" type="button" id="import-org-submit"
                                            data-bs-dismiss="modal">Import
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div>
                            <a
                               href="/kms/services/rest/organization/downloadTemplateImportOrg?type=ORG">Download template</a>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="col-md-4">
            <div class="card mt-3">
                <div class="card-header">
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addUserModal">
                        <i class="fa fa-user-plus"></i>
                    </button>
                    <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                            data-bs-target="#importUserModal">
                        <i class="fa fa-upload"></i>
                    </button>

                </div>
                <div class="card-body">
                    <table class="table" id="tbl-user-by-org">
                        <thead>
                        <tr>
                            <th>Mã nhân viên</th>
                            <th>Họ tên</th>
                            <th>Email</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
            <!-- The Modal add user-->
            <div class="modal fade" id="addUserModal">
                <div class="modal-dialog modal-dialog-scrollable modal-lg ">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>

                        <div class="modal-body">
                            <form id="form-search-user">
                                <div class="input-group mb-3">
                                    <input type="text" class="form-control"
                                           placeholder="Mã nhân viên, họ tên, email,..." name="userSearch">
                                    <button class="btn btn-success" type="button" id="userSearchSubmitBtn">Tìm kiếm
                                    </button>
                                </div>
                            </form>
                            <table class="table" id="table-user-search">
                                <thead>
                                <tr>
                                    <th>Mã nhân viên</th>
                                    <th>Họ tên</th>
                                    <th>Email</th>
                                    <th>Chọn</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            <div class="d-flex flex-row-reverse">
                                <button type="button" class="btn btn-success" id="add-user-org" data-bs-dismiss="modal">
                                    Thêm
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- The Modal import user-->
            <div class="modal fade" id="importUserModal">
                <div class="modal-dialog modal-dialog-scrollable modal-lg ">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>

                        <div class="modal-body">
                            <form id="form-import-user" enctype="multipart/form-data">
                                <div class="input-group mb-3">
                                    <input type="file" class="form-control" name="userFile" id="input-file-user">
                                    <button class="btn btn-success" type="button" id="import-user-org"
                                            data-bs-dismiss="modal">Import
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div>
                            <a
                               href="/kms/services/rest/organization/downloadTemplateImportOrg?type=USER_ORG">Download template</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card mt-3">
                <div class="card-body">
                    <form id="form-create-org" class="form-horizontal">
                        <div class="mt-3">
                            <label for="orgName">Tên đơn vị :</label>
                            <input type="text" class="form-control" id="orgName" placeholder="Nhập tên đơn vị"
                                   name="orgName">
                        </div>

                        <div class="mt-3">
                            <label for="orgCode">Mã đơn vị :</label>
                            <input type="text" class="form-control" id="orgCode" placeholder="Nhập mã đơn vị"
                                   name="orgCode">
                        </div>

                        <div class="mt-3 form-group">
                            <label for="myModal" class="form-label">Đơn vị cha:</label>
                            <div class="input-group">
                                <input class="form-control" name="orgParent" id="orgParent" placeholder="Tìm đơn vị cha"
                                       type="text" autocomplete="off" data-bs-toggle="modal" data-bs-target="#myModal">

                                    <button class="btn btn-danger" type="button" onclick="$('#orgParent').val(''); $('#orgParentId').val('');">
                                        <i class="fa fa-remove"></i>
                                    </button>


                            </div>
                                <input class="form-control" name="orgParentId" id="orgParentId"
                                       type="hidden"/>

                        </div>

                        <div class="d-flex justify-content-between">
                            <button type="button" class="btn btn-success mt-3" id="orgCreateSubmitBtn">Thêm mới</button>
                            <button type="button" class="btn btn-info mt-3" id="orgUpdateSubmitBtn">Cập nhật</button>
                        </div>
                    </form>
                </div>

                <div class="modal fade" id="myModal">
                    <div class="modal-dialog modal-dialog-scrollable modal-lg ">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <form id="form-search-org" class="row" style="width: 100%;">

                                    <div class="col-md-5">
                                        <input type="text" class="form-control" placeholder="Tên đơn vị"
                                               name="orgName">
                                    </div>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" placeholder="Mã đơn vị"
                                               name="orgCode">
                                    </div>
                                    <div class="col-md-2">
                                        <button type="submit" class="btn btn-success" id="orgSearchSubmitBtn">
                                            Tìm kiếm
                                        </button>
                                    </div>
                                    <div class="col-md-1 d-flex align-content-center align-items-center">
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>

                                </form>
                            </div>

                            <!-- Modal body -->
                            <div class="modal-body">

                                <div class="row">
                                    <table class="table table-hover table-bordered" id="table-org-search">
                                        <thead>
                                        <tr>
                                            <th>Mã đơn vị</th>
                                            <th>Tên đơn vị</th>
                                            <th>Chọn</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- The Modal Remove User From Org-->
        <div class="modal fade" id="warning-remove-user">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Modal body -->
                    <div class="modal-body" id="warning-remove-content">
                        Xoá người dùng khỏi đơn vị?
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-bs-dismiss="modal" id="approve-remove-user">
                            Xoá
                        </button>
                        <button type="button" class="btn" data-bs-dismiss="modal">Thoát</button>
                    </div>

                </div>
            </div>
        </div>

        <!-- The Modal Delete Org-->
        <div class="modal fade" id="warning-delete-org">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Modal body -->
                    <div class="modal-body">
                        Xoá đơn vị này?
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-bs-dismiss="modal" id="approve-delete-org">
                            Xoá
                        </button>
                        <button type="button" class="btn" data-bs-dismiss="modal">Thoát</button>
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>
</body>
<script>
    $(document).ready(function () {
            var treeDataGlobal;
            var orgChoosed;
            var userRemoved;
            let userChecked = [];
            let userCurrentOrg = [];

            getAllOrgRoot();

            function renderOrgTree(treeData) {
                $('#jstree_demo_div').jstree("destroy").empty();
                $('#jstree_demo_div')
                    /*.on('ready.jstree', function() {
                        $("#jstree_demo_div").jstree("open_all");
                    })*/
                    .on('changed.jstree', function (e, data) {
                        let nodeSelected = data.node.original;

                        $('#orgName').val(nodeSelected.name);
                        $('#orgCode').val(nodeSelected.code);
                        $('#orgParentId').val(nodeSelected.parent);
                        $('#orgParent').val(nodeSelected.parentName);

                        getUsersByOrgId(nodeSelected.id);

                        orgChoosed = nodeSelected.id;
                        $('#import-user-orgId-input').val(orgChoosed);
                        userChecked = [];
                    })
                    .jstree({
                        'core': {
                            'data': treeData
                        }
                    });
            }

            $("#tree-open-all").click(e => {
                $("#jstree_demo_div").jstree("open_all");
            })

            function getUsersByOrgId(orgId) {
                $.ajax({
                    url: `/kms/services/rest/organization/findUsersbyOrg?orgId=` + orgId,
                    type: 'GET',
                    processData: false,
                    contentType: 'application/json',
                    success: (res) => {
                        $('#tbl-user-by-org tbody').empty();
                        res.forEach(u => {
                            let tr = document.createElement('tr');
                            let tdName = document.createElement('td'), tdCode = document.createElement('td'),
                                tdEmail = document.createElement('td'), tdRemove = document.createElement('td')
                            tdName.innerText = u[1];
                            tdCode.innerText = u[0];
                            tdEmail.innerText = u[2];

                            userCurrentOrg.push(u[0]);

                            let divTmp = document.createElement('div');
                            let buttonRemove = '<button type="button" class="btn btn-danger btn-sm" id="btn-remove-user" data-bs-toggle="modal" data-bs-target="#warning-remove-user"><i class="fa fa-trash"></i></button>'
                            divTmp.innerHTML = buttonRemove;
                            divTmp.onclick = () => {
                                userRemoved = u[0];
                            }
                            tdRemove.appendChild(divTmp);

                            tr.appendChild(tdCode);
                            tr.appendChild(tdName);
                            tr.appendChild(tdEmail);
                            tr.appendChild(tdRemove);
                            $('#tbl-user-by-org tbody').append(tr);

                            buttonRemove.onclick = (e) => {
                                userRemoved = u[0];
                            }
                        })
                    }
                })
            }

            function getAllOrgRoot() {
                $.ajax({
                    url: '/kms/services/rest/organization/search',
                    type: 'GET',
                    processData: false,
                    contentType: 'application/json',
                    success: (res) => {
                        treeDataGlobal = res;
                        let treeData = res.map(org => {
                            let obj = {};
                            obj.id = org.id;
                            obj.parent = org.parent !== -1 ? org.parent : "#";
                            obj.text = org.name;

                            obj.code = org.code;
                            obj.name = org.name;
                            obj.parentName = res.filter(o => o.id === org.parent)[0] ? res.filter(o => o.id === org.parent)[0].name : null
                            return obj;
                        })
                        renderOrgTree(treeData)
                    }
                })
            }

            $("#orgCreateSubmitBtn").click((e) => {
                e.preventDefault();
                $.ajax({
                    url: '/kms/services/rest/organization/create',
                    type: 'POST',
                    processData: false,
                    contentType: 'application/x-www-form-urlencoded',
                    data: $("#form-create-org").serialize(),
                    success: (res) => {
                        getAllOrgRoot();
                    }
                });
            })

            $("#orgUpdateSubmitBtn").click((e) => {
                e.preventDefault();
                $.ajax({
                    url: '/kms/services/rest/organization/update?orgId=' + orgChoosed,
                    type: 'POST',
                    processData: false,
                    contentType: 'application/x-www-form-urlencoded',
                    data: $("#form-create-org").serialize(),
                    success: (res) => {
                        getAllOrgRoot();
                    }
                });
            })

            $('#orgSearchSubmitBtn').click((e) => {
                e.preventDefault();
                $('#table-org-search tbody').empty()
                $.ajax({
                    url: '/kms/services/rest/organization/search',
                    type: 'POST',
                    processData: false,
                    contentType: 'application/x-www-form-urlencoded',
                    data: $("#form-search-org").serialize(),
                    success: (response) => {
                        loadOrgSearchParent(response)
                    }
                });
            })

            $('#userSearchSubmitBtn').click((e) => {
                $.ajax({
                    url: `/kms/services/rest/user/getAllUser?notInOrg=1`,
                    type: 'POST',
                    processData: false,
                    contentType: 'application/x-www-form-urlencoded',
                    data: $("#form-search-user").serialize(),
                    success: (res) => {
                        console.log(res)
                        loadUserSearch(res);
                    }
                })
            })

            $('#approve-remove-user').click((e) => {
                $.ajax({
                    url: `/kms/services/rest/organization/removeUserOrg?` + $.param({
                        "orgId": orgChoosed,
                        "userId": userRemoved
                    }),
                    type: 'DELETE',
                    processData: false,
                    contentType: 'application/json',
                    success: (res) => {
                        console.log(res)
                        getUsersByOrgId(orgChoosed)
                    }
                })
            })

            $('#approve-delete-org').click((e) => {
                if (!orgChoosed) alert("Bạn cần chọn 1 đơn vị!")
                $.ajax({
                    url: `/kms/services/rest/organization/deleteOrg?` + $.param({"orgId": orgChoosed}),
                    type: 'DELETE',
                    processData: false,
                    contentType: 'application/json',
                    success: (res) => {
                        console.log(res)
                        getAllOrgRoot();
                    }
                })
            })


            function loadOrgSearchParent(jsonData) {
                jsonData.forEach(org => {
                    let tr = document.createElement('tr');
                    let tdName = document.createElement('td'), tdCode = document.createElement('td'),
                        tdChoose = document.createElement('td');
                    tdName.innerText = org.name;
                    tdCode.innerText = org.code;

                    let iconChoose = document.createElement('span');
                    iconChoose.setAttribute('class', "fa fa-check-circle")
                    iconChoose.setAttribute('data-target', org.id)

                    iconChoose.onclick = function () {
                        $('.fade').css('display', 'none');
                        $('#orgParentId').val(org.id)
                        $('#orgParent').val(org.name);
                    };

                    tdChoose.append(iconChoose)

                    tr.appendChild(tdCode);
                    tr.appendChild(tdName);
                    tr.appendChild(tdChoose);
                    $('#table-org-search tbody').append(tr);
                })

            }

            function loadUserSearch(data) {
                userChecked = [];
                $('#table-user-search tbody').empty()
                console.log(userCurrentOrg)
                data.forEach(user => {
                    let tr = document.createElement('tr');
                    let tdName = document.createElement('td'),
                        tdCode = document.createElement('td'),
                        tdMail = document.createElement('td'),
                        tdCheckbox = document.createElement('td');


                    tdName.innerText = user[0];
                    tdCode.innerText = user[1];
                    tdCode.setAttribute('id', user[1].toString());
                    tdMail.innerText = user[2];

                    let inputCheckbox = document.createElement('input');
                    inputCheckbox.setAttribute('type', 'checkbox');
                    inputCheckbox.onchange = (e) => {
                        if (inputCheckbox.checked) {
                            userChecked.push({
                                userId: user[1],
                                orgId: orgChoosed
                            });
                        } else {
                            userChecked = userChecked.filter(u => u.userId !== user[1])
                        }

                    }
                    tdCheckbox.appendChild(inputCheckbox)

                    tr.appendChild(tdCode);
                    tr.appendChild(tdName);
                    tr.appendChild(tdMail);
                    tr.appendChild(tdCheckbox);
                    $('#table-user-search tbody').append(tr);

                })


            }

            $('#add-user-org').click(e => {
                if (orgChoosed) {
                    console.log(userChecked)

                    $.ajax({
                        url: '/kms/services/rest/organization/addUserToOrg',
                        type: 'POST',
                        processData: false,
                        contentType: 'application/x-www-form-urlencoded',
                        data: "bodyData=" + JSON.stringify(userChecked),
                        success: () => {
                            getUsersByOrgId(orgChoosed);
                        }
                    })
                } else {
                    alert("Chưa chọn đơn vị")
                }

            })

            $('#import-user-org').click(e => {
                    let files = document.getElementById('input-file-user').files;
                    let formData = new FormData();
                    if (files.length > 0) {
                        formData.append("file", files[0]);

                        $.ajax({
                            url: '/kms/services/rest/organization/importUserToOrg',
                            type: 'POST',
                            enctype: 'multipart/form-data',
                            cache: false,
                            contentType: false,
                            processData: false,
                            data: formData,
                            complete: function(jqXHR) {
                                if(jqXHR.readyState === 4) {
                                    let warnList = jqXHR.responseText.split(",");
                                    let warnStr = "";
                                    if(warnList[0] === "") warnList.shift()
                                    for(let i=0; i<warnList.length-1; i+=2) {

                                        warnStr += warnList[i] + " - " + warnList[i+1] + "\n";
                                    }
                                    alert(warnStr);
                                    getUsersByOrgId(orgChoosed);
                                }
                            }
                        })
                    }
            })

            $('#import-org-submit').click(e => {
                let files = document.getElementById('input-file-org').files;
                let formData = new FormData();
                if (files.length > 0) {
                    formData.append("file", files[0]);

                    $.ajax({
                        url: '/kms/services/rest/organization/importOrg',
                        type: 'POST',
                        enctype: 'multipart/form-data',
                        cache: false,
                        contentType: false,
                        processData: false,
                        data: formData,
                        success: () => {
                            getAllOrgRoot();
                        }
                    })
                } else {
                    alert("Bạn chưa chọn file!")
                }

            })


            $('#addUserModal').on('show.bs.modal', function (e) {
                userChecked = [];
                $('#table-user-search tbody').empty()
                $.ajax({
                    url: `/kms/services/rest/user/getAllUser?notInOrg=1`,
                    type: 'POST',
                    processData: false,
                    contentType: 'application/x-www-form-urlencoded',
                    data: $("#form-search-user").serialize(),
                    success: (res) => {
                        console.log(res)
                        loadUserSearch(res);
                    }
                })
            })


        }
    )
</script>
</html>
