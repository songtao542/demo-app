window.jQuery || document.write('<script src="/js/vendor/jquery-slim.min.js"><\/script>')

function commit(obj) {
    console.log("obj---------->" + obj.value);
    var inputs = obj.parentNode.getElementsByTagName('INPUT');
    console.log("ccccc--->" + inputs.length);
    var data = [];
    var token;
    var k = 0;
    for (var i = 0; i < inputs.length; i++) {
        console.log("inputs[i]--->" + inputs[i]);
        var input = inputs[i];
        if (input.name == '_csrf') {
            token = input.value;
            console.log("token---------->" + token);
            continue;
        }

        var id = input.value;
        console.log("id---------->" + id);
        var isChecked = input.checked;
        console.log("isChecked--->" + isChecked);
        data[k] = {};
        data[k].userId = id;
        data[k].granted = isChecked;
        k++;
    }

    var param = {
        "url": obj.value,
        "permission": JSON.stringify(data)
    };

    // window.parent.postMessage({
    //     url: "/url/access",
    //     data: param,
    //     // data: JSON.stringify(param),
    // }, '*');
    console.log("param--->" + JSON.stringify(param));
    $.ajax({
        headers: {
            "_csrf": token,
            "X-CSRF-TOKEN": token
        },
        type: "POST",
        url: "/url/access",
        data: param,
        success: function (msg) {
            console.log("post success--->" + JSON.stringify(msg));
        }
    });

    return true;
}

function submit() {
    console.log("submit submit---------->")
    $.ajax({
        type: "POST",
        url: "post.go",
        data: "",
        success: function (msg) {
        }
    });
    return false;
}