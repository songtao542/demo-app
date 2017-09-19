window.jQuery || document.write('<script src="/js/vendor/jquery-slim.min.js"><\/script>')

function commit(obj) {
    console.log("obj---------->" + obj.value);
    var inputs = obj.parentNode.getElementsByTagName('INPUT');
    console.log("ccccc--->" + inputs.length);
    var data = [];
    for (var i = 0; i < inputs.length; i++) {
        console.log("value--->" + inputs[i]);
        var input = inputs[i];
        var id = input.value;
        console.log("id---------->" + id);

        var isChecked = input.checked;
        console.log("isChecked--->" + isChecked);
        data[i] = {};
        data[i].userId = id;
        data[i].granted = isChecked;
    }

    var param = {
        "url": obj.value,
        "permission": JSON.stringify(data)
    };

    window.parent.postMessage({
        url: "/url/access",
        data: param,
        // data: JSON.stringify(param),
    }, '*');

    // $.ajax({
    //     type: "POST",
    //     url: "/url/access",
    //     data: param,
    //     success: function (msg) {
    //         console.log("post success--->" + msg);
    //     }
    // });

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