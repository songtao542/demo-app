// window.jQuery || document.write('<script src="/js/vendor/jquery-slim.min.js"><\/script>')

// $(function () {
//     $('.nav li a').click(function () {
//         var url = $(this).href;
//         console.log("url============" + url)
//         // $('#i_frame').src = "/view/access/manage"
//         createIFrame("/view/access/manage");
//
//     });
// });

var show;

function onMenuClicked(obj) {
    var url = obj.getAttribute("data")
    var id = 'i_frame_' + obj.getAttribute("id")
    console.log("url-->" + url);
    console.log("show-->" + show);
    if (show) {
        show.setAttribute("style", "display:none");
    }
    var div = findFrame(id);
    console.log("div-->" + div);
    if (div) {
        div.setAttribute("style", "display:block");
    } else {
        div = document.createElement("div");
        div.setAttribute('class', 'full-m-p-0');
        div.id = id;
        var frame = document.createElement("iframe");
        frame.src = url.substr(1);
        frame.setAttribute('style', "width:100%;height:100%;border:0")
        // frm.onload = function () {
        //     callback();
        // }
        frame.onerror = function () {

        }
        div.appendChild(frame);
        document.getElementById('frame_container').appendChild(div);
    }
    show = div;
}

function findFrame(id) {
    var children = document.getElementById('frame_container').children;
    for (var i = 0; i < children.length; i++) {
        console.log("children[i].id-->" + children[i].id);
        console.log("------------id-->" + id);
        if (children[i].id == id) {
            return children[i];
        }
    }
}

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
    return document.getElementById('frame_container').appendChild(div);
}

window.addEventListener("message", function (e) {
    console.debug("frame message e.data=" + e.data);
    console.debug("frame message e.data.url=" + e.data.url);
    console.debug("frame message e.data.param=" + JSON.stringify(e.data.data));

    $.ajax({
        type: "POST",
        url: e.data.url,
        data: e.data.data,
        success: function (msg) {
            console.log("post success--->" + JSON.stringify(msg));
        }
    });
});