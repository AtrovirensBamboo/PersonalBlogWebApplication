
function submitForm() {
    if (submitVerify()) {
        $.ajax({
            url: "/submit.json",
            type: "POST",
            dataType: "json",
            async: true,
            data: $("#submitform").serialize(),
            success: function (data) {
                var answer = data;
                if (answer["status"] === "success" ) {
                    window.location.href = "/userInfo.action";
                } else {
                    //输出用户名相关错误信息
                    if (answer.message != null) {
                        window.location.reload(true);
                    }
                }
            }
        });
    }
}
function submitVerify() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var usernamePattern = new RegExp("[0-9a-zA-Z]{1,20}");
    var passwordPattern = new RegExp("\\S{1,20}");
    var message = new Array();

    //校验用户名
    if(!usernamePattern.test(username)){
        message.push("用户名长度必须小于20，且只能包含字母和数字!");
    }
    //校验密码
    if(!passwordPattern.test(password)){
        message.push("密码长度必须小于20，可以是任何非空字符!");
    }
    if (message.length === 0){
        return true;
    }
    alert(message.join());
    return false;
}
