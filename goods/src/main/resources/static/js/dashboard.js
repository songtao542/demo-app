// window.jQuery || document.write('<script src="/js/vendor/jquery-slim.min.js"><\/script>')

$(function () {
    $('.nav li a').click(function () {
        var url = $(this).href;
        console.log("url============" + url)
        // $('#i_frame').src = "/view/access/manage"
        createIFrame("/view/access/manage");

    });
});

function createIFrame(src) {
    var frm = document.createElement("iframe");

    frm.src = src;
    frm.id = 'i_frame';
    frm.setAttribute('style', "width:100%;height:100%;border:0")
    // frm.onload = function () {
    //     callback();
    // }
    frm.onerror = function () {

    }

    var div = document.createElement("div");
    div.setAttribute('class', 'full-m-p-0');
    div.appendChild(frm);

    // $.ajax({
    //     type: "POST",
    //     url: "",
    //     data: "",
    //     success: function (msg) {
    //         console.log("post success--->" + msg);
    //     }
    // });

    return document.getElementById('frame_container').appendChild(div);
}

window.addEventListener("message", function (e) {
    console.debug("frame message e.data=" + e.data);
    console.debug("frame message e.data.url=" + e.data.url);
    console.debug("frame message e.data.param=" + JSON.stringify( e.data.data));

    $.ajax({
        type: "POST",
        url: e.data.url,
        data: e.data.data,
        success: function (msg) {
            console.log("post success--->" + JSON.stringify(msg));
        }
    });
});