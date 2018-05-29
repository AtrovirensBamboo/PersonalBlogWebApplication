function typeVerify(element) {
    var sortPattern = new RegExp("^\\d{1,5}$");
    var namePattern = new RegExp("^[a-zA-Z]{1,15}$");
    var id = element.id;
    var value = element.value;

    if (id === "sort"){
        if (sortPattern.test(value)){
            return true;
        }
        alert("排列顺序必须为正整数");
        return false;
    }else if (id === "name"){
        if (namePattern.test(value)){
            return true;
        }
        alert("文章分类必须是字母");
        return false;
    }
}
//校验所有sort和name的input标签的值
function transDataVerify(){
    var sortArray = $("input#sort");
    var nameArray = $("input#name");
    var sortLength = sortArray.length;
    var nameLength = nameArray.length;
    var signal = false;

    //校验排列顺序的内容
    for (var i = 0; i < sortLength; i++){
        //如果通过验证，将signal的值设置为true
        if(typeVerify(sortArray[i])){
            if (signal !== true){
                signal = true;
            }
        }else {
            //如果未通过校验，则将相应input标签的值清空，并将signal设置为false
            sortArray[i].value = "";
            if (signal !== false) {
                signal = false;
            }
        }
    }
    //校验文章分类的内容
    for (var j = 0; j < nameLength; j++){
        //如果通过验证，将signal的值设置为true
        if (typeVerify(nameArray[j])){
            if (signal !== true){
                signal = true;
            }
        }else {
            //如果未通过验证，将相应input标签的值清空
            nameArray[i].value = "";
            if (signal !== false){
                signal = false;
            }
        }
    }
    return signal;
}
$(document).ready(function () {
    //动态添加文章分类
    $("button#add-type").click(function () {
        var newType = '';
        newType += '<tr>';
        newType += '<td><label for="select"> </label><input id="select" type="checkbox" value=""/></td>';
        newType += '<td><label for="sort"> </label>' +
            '<input id="sort" class="verify" type="text" value=""/>' +
            '</td>';
        newType += '<td><label for="name"> </label>' +
            '<input id="name" class="verify" type="text" value=""/>' +
            '</td>';
        newType += '</tr>';

        $("tbody#type-list").append(newType);
        //将所有的新增的表格行的checkbox的checked的值设置为true
        var inputSelectArray = $("input#select");
        for (var i = 0; i < inputSelectArray.length; i++){
            if (inputSelectArray[i].value === "") {
                inputSelectArray[i].checked = true;
            }
        }
    });
    //父选框触发点击事件
    $("input#select-all").click(function () {
        //!!!!!请注意inputParent和inputChildren获取checked状态的差别
        //全选框的选择状态
        var allChecked = $("input#select-all")[0].checked;
        //所有的子选框
        var inputChildren = $("input#select");
        //自选框的数量
        var length = inputChildren.length;

        //判断子选框的状态，如果与父选框的状态不同，则更改自选框的状态
        for (var i = 0; i < length; i++){
            if ( inputChildren[i].checked !== allChecked){
                inputChildren[i].checked = allChecked;
            }
        }
    });
    //当需要对动态添加的标签绑定事件时，通过对父标签使用on方法可以解决后添加的标签不触发时间的问题，或者在标签中添加on*属性
    //子选框触发点击事件
    $("tbody#type-list").on("click","input",function () {
        //父选框元素
        var inputParent = $("input#select-all")[0];
        //子选框元素
        var inputChildren = $("input#select");
        //子选框数量
        var length = inputChildren.length;

        for (var i = 0; i < length; i++){
            //判断子选框状态，如果是false
            if (inputChildren[i].checked === false){
                //判断父选框状态，如果是true
                if (inputParent.checked === true) {
                    //设置父选框的状态为false
                    inputParent.checked = false;
                }
                return;
            }
        }
        //循环结束，所有子选框状态为true，若父选框状态为false，更改父选框的状态为true
        if (inputParent.checked === false){
            inputParent.checked = true;
        }
    });
    //当输入内容变化时校验更改后的内容
    $("tbody#type-list").on("change","input.verify",function () {
        var sortPattern = new RegExp("^\\d{1,5}$");
        var namePattern = new RegExp("^[a-zA-Z]{1,15}$");
        var id = $(this).prop("id");
        var value = $(this).val();

        if (id === "sort"){
            if (!sortPattern.test(value)){
                alert("排列顺序必须为正整数");
            }
        }else if (id === "name"){
            if (!namePattern.test(value)){
                alert("文章分类必须是字母");
            }
        }
    });
    //设置保存按钮点击事件
    $("button#save-type").click(function () {
        //如果所有input标签的值都通过验证，则发送ajax请求给服务器
        var idArray = $("input#select");
        var sortArray = $("input#sort");
        var nameArray = $("input#name");
        var dataLength = idArray.length;
        var idValueArray = new Array();
        var sortValueArray = new Array();
        var nameValueArray = new Array();

        for (var i = 0; i < dataLength; i++){
            if (idArray[i].checked) {
                idValueArray.push(idArray[i].value);
                sortValueArray.push(sortArray[i].value);
                nameValueArray.push(nameArray[i].value);
            }
        }
        if (transDataVerify()){
            $.ajax({
                url: "/userinfo/typechange.json",
                type: "POST",
                datatype: "json",
                traditional:true,
                data : {"idValueArray":idValueArray,
                        "sortValueArray":sortValueArray,
                        "nameValueArray":nameValueArray},
                success: function (data) {
                    var resultTypeChange = data;
                    if (resultTypeChange["status"] === "success"){
                        window.location.reload(true);
                    }else if(data["status"] === "failed"){
                        alert(resultTypeChange["message"]);
                    }
                }
            });
        }else {
            //否则显示通知信息
            alert("请重新输入值为空的内容");
        }
    });
});
