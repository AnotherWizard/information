function getCookie(name) {
    var r = document.cookie.match("\\b" + name + "=([^;]*)\\b");
    return r ? r[1] : undefined;
}


$(function () {
    $(".pass_info").submit(function (e) {
        e.preventDefault();

        // 修改密码
        var params = {};
        // 取到当前表单所需要提交的参数，name 有值的input标签
        // x: {name:"old_password", value: "123456788"}
        $(this).serializeArray().map(function (x) {
            params[x.name] = x.value;
        });
        // 取到两次密码进行判断
        var new_password = params["new_password"];
        var new_password2 = params["new_password2"];

        if (new_password != new_password2) {
            alert('两次密码输入不一致')
            return
        }

        $.ajax({
            url: "/user/pass_info",
            type: "post",
            contentType: "application/json",
            headers: {
                "X-CSRFToken": getCookie("csrf_token")
            },
            data: JSON.stringify(params),
            success: function (resp) {
                if (resp.errno == "0") {
                    // 修改成功
                    alert("修改成功")
                    window.location.reload()
                }else {
                    alert(resp.errmsg)
                }
            }
        })
    })
})