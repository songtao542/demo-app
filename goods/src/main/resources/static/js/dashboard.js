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

var showing = {
    frame: document.getElementById("i_frame_welcome"),
    tab: document.getElementById("tab_welcome")
};

function onMenuClicked(obj) {
    var url = obj.getAttribute("data")
    var id = obj.getAttribute("id")
    if (showing.frame) {
        showing.frame.setAttribute("style", "display:none");
        showing.tab.setAttribute("class", "nav-gray-link");
    }
    var div = findFrame('i_frame_' + id);
    var tab = findTab('tab_' + id);
    if (div) {
        div.setAttribute("style", "display:block");
        tab.setAttribute("class", "nav-gray-link active");
    } else {
        tab = addTab(id, obj.text);
        div = addFrame(id, url);
    }
    showing.frame = div;
    showing.tab = tab;
}

function addFrame(id, url) {
    var div = document.createElement("div");
    div.setAttribute('class', 'full-m-p-0');
    div.id = 'i_frame_' + id;
    var frame = document.createElement("iframe");
    frame.src = url;
    frame.setAttribute('style', "width:100%;height:100%;border:0")
    // frm.onload = function () {
    //     callback();
    // }
    frame.onerror = function () {
    }
    div.appendChild(frame);
    document.getElementById('frame_container').appendChild(div);
    return div;
}

function addTab(id, name) {
    var ul = document.getElementById("tabs");
    var li = document.createElement("li");
    ul.appendChild(li);
    li.setAttribute("class", "nav-item");

    var a = document.createElement("a");
    li.appendChild(a);
    a.setAttribute("id", "tab_" + id);
    a.setAttribute("class", "nav-gray-link active");
    a.setAttribute("onclick", "clickTab(this)");
    a.setAttribute("href", "javascript:void(0);");
    a.text = name;

    var btn = document.createElement("button");
    a.appendChild(btn);
    btn.setAttribute("class", "close closeTab");
    btn.setAttribute("type", "button");
    btn.setAttribute("onclick", "closeTab(this)");
    btn.innerHtml = "×";
    btn.innerText = "×";
    btn.value = "×";

    return a;
}

function clickTab(obj) {
    var id = obj.getAttribute("id");
    showing.frame.setAttribute("style", "display:none");
    showing.tab.setAttribute("class", "nav-gray-link");
    var tab = findTab(id);
    var frame = findFrame("i_frame_" + id.substr(4));
    tab.setAttribute("class", "nav-gray-link active");
    frame.setAttribute("style", "display:block");
    showing.frame = frame;
    showing.tab = tab;
}

function closeTab(obj) {
    var id = obj.parentNode.getAttribute("id");
    if (id == showing.tab.getAttribute("id")) {
        findSibling();

        obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
        var frameId = "i_frame_" + id.substr(4);
        var frame = findFrame(frameId);
        frame.parentNode.removeChild(frame);

        showing.frame.setAttribute("style", "display:block");
        showing.tab.setAttribute("class", "nav-gray-link active");
    } else {
        obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
        var frameId = "i_frame_" + id.substr(4);
        var frame = findFrame(frameId);
        frame.parentNode.removeChild(frame);
    }
    window.event ? window.event.cancelBubble = true : event.stopPropagation();
    return false;
}

function findSibling() {
    var children = document.getElementById('frame_container').children;
    var id = showing.frame.getAttribute("id");
    var i;
    for (i = 0; i < children.length; i++) {
        if (children[i].id == id) {
            break;
        }
    }

    if (i !== children.length - 1) {
        var next = children[i + 1];
        id = next.getAttribute("id");
        showing.frame = document.getElementById(id);
        showing.tab = document.getElementById("tab_" + id.substr(8));
    } else {
        var prev = children[i - 1];
        id = prev.getAttribute("id");
        showing.frame = document.getElementById(id);
        showing.tab = document.getElementById("tab_" + id.substr(8));
    }
}


function findFrame(id) {
    var children = document.getElementById('frame_container').children;
    for (var i = 0; i < children.length; i++) {
        if (children[i].id == id) {
            return children[i];
        }
    }
}

function findTab(id) {
    var children = document.getElementById('tabs').children;
    for (var i = 0; i < children.length; i++) {
        if (children[i].firstElementChild.id == id) {
            return children[i].firstElementChild;
        }
    }
}

// function logout() {
//     $.ajax({
//         type: "POST",
//         url: "/logout",
//         success: function (msg) {
//             console.log("post success--->" + JSON.stringify(msg));
//         }
//     });
// }
//
// window.addEventListener("message", function (e) {
//     console.debug("frame message e.data=" + e.data);
//     console.debug("frame message e.data.url=" + e.data.url);
//     console.debug("frame message e.data.param=" + JSON.stringify(e.data.data));
//
//     $.ajax({
//         type: "POST",
//         url: e.data.url,
//         data: e.data.data,
//         success: function (msg) {
//             console.log("post success--->" + JSON.stringify(msg));
//         }
//     });
// });