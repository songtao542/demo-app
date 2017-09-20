LEC.seaConfig6 = {
    base: $$c.rootUrl,
    alias: {
        "mCustomScrollbar": $$c.rootUrl + '/lejs/lib/3/jquery.mCustomScrollbar.js',
    }
}
seajs.config(LEC.seaConfig6);
seajs.use(['dot', 'Go', 'zTree','mCustomScrollbar'], function(dot, Go) {
    var D = [];
    var page = {
        resizeTimer: null,
        _container: $('#j_header'),
        _init: function() {
            page._resize();
            $("#j_sidebar").mCustomScrollbar({
                theme:"minimal",
                axis:"yx"
            });
            page.bindChange();
            page.showTip();
        },
        _resize: function() {

            $(window).on('resize', function() {
                clearTimeout(page.resizeTimer);
                page.resizeTimer = setTimeout(function() {

                    page.resizeCall()
                }, 500);
            })
        },
        resizeCall : function(){
            $le.listener.trigger('index.page', 'resize');
        },
        bindChange: function() {
            var me = this;
            me._container.on('change', '[data-op]', function(){
                me.setLang($(this));
            });
        },
        setLang: function(dom) {
            var me = this;
            var area,lang;
            var host = window.location.host;

            if(dom.data('op') == 'changeLang') {
                area = $('#j_selarea').val();
                lang = dom.val();
            }
            if(dom.data('op') == 'changeArea') {
                area = dom.val();
                lang = dom.find('option:selected').data('lang');
            }
            window.location.href = 'http://'+host+'?lang='+lang+'&area='+area;
        },
        showTip: function(){
            if(!$Le.browser.chrome){
                $('.header_center').after('<div id="j_browTip" class="browser_tip">å»ºè®®ä½¿ç”¨Chromeæµè§ˆå™¨ä½“éªŒæ›´ä½³<i></i></div>');
                $('#j_browTip').fadeIn('slow');

                $('#j_browTip i').on('click', function(){
                    $('#j_browTip').fadeOut('slow');
                })
            }
        }
    }

    page._init();


    var mTree = {
        clickTreeNode: function(treeNode) {
            if(treeNode.children){
                return
            }
            if(treeNode.furl === ''){
                alert('è¿˜æ²¡æœ‰ç»‘å®š,è¯·è”ç³»ç³»ç»Ÿç®¡ç†å‘˜!')
                return
            }

            //ç‚¹å‡»æ ‘æ´¾å‘ tab.click
            $le.listener.trigger('index.tree', 'click', treeNode);
        }
    }

    var sideToggle = function(){
        this._init();
    };
    sideToggle.prototype = {
        _init:  function(){
            var self = this;
            $("#j_sidebar").after("<div class='toggle_box' id='toggle_box'><div id='toggle_btn' style='z-index:999999;' class='toggle_btn'></div></div>");
            $("#toggle_btn").on("click", function(){
                self._sideToggle($(this));
                if($(this).hasClass('hover')){
                    $(this).removeClass('hover')
                }else{
                    $(this).addClass('hover')
                }
            }).on('mouseenter',function(){
                $(this).addClass('hover')
            }).on('mouseleave',function(){
                $(this).removeClass('hover')
            });

            $("#toggle_box").on('mouseenter',function(){
                $(this).css("cursor","ew-resize");
            }).on("mousedown",function(e){
                var me = this;
                var ol = $("#toggle_box");
                var lw = $("#j_sidebar");
                var rw = $("#j_wrap");

                var mL = $(this).offset().left;
                var mX = (e || window.event).pageX;

                var div = document.createElement("div");
                div.style.height="100%";
                div.style.width = "100%";
                div.style.position="absolute";
                div.style.left="0";
                div.style.top="0";
                div.style.zIndex="99999";
                rw.css("position","relative");
                rw.append(div);

                document.onmousemove = function(e) {
                    $("#toggle_btn").parent().removeClass("slide_in");
                    var mX1 = (e || window.event).pageX;//èŽ·å–ç§»åŠ¨åŽXåæ ‡
                    var l = mL + (mX1 - mX);//é¼ æ ‡ç§»åŠ¨åŽè·æ–‡æ¡£å·¦ä¾§è·ç¦»
                    l < 180 && (l = 180);
                    l > 400 && (l = 400);
                    $(me).css("left",l + "px");
                    lw.css({"width":l + "px"});
                    rw.css({"margin-left":l + $(me).width() + "px"});
                };
                document.onmouseup = function(e) {
                    document.onmousemove = null;
                    document.onmouseup = null;
                    $(div).remove();
                    ol.releaseCapture && ol.releaseCapture();
                };
                ol.setCapture && ol.setCapture();
                return false
            });
        },
        _sideToggle: function(w){
            if(w.parent().hasClass("slide_in")){
                this._slide(180, 190)
            }else{
                this._slide(0, 0)
            }


        },
        _slide: function(n, m){
            $("#toggle_btn").parent().toggleClass("slide_in");

            $("#toggle_box").stop().animate({"left": n}, 200);
            $("#j_sidebar").stop().animate({"width": n}, 200);
            $("#j_wrap").stop().animate({"margin-left": m}, 200);
        }
    }
    //å®žä¾‹åŒ–
    var slideToggle = new sideToggle();




    var mTab = {
        _curTid: 'home',
        _init: function() {
            this.listener();
            this.bind();
        },
        listener: function() {
            //ç‚¹å‡»æŽ¥æ”¶ æ ‘å¾—.click
            $le.listener.on('index.tree', 'click', function(fun, opt) {
                mTab.addTab(opt);
            });
            $le.listener.on('index.tab', 'add', function(fun, opt) {
                mTab.setTabWidth('add');
            });
            $le.listener.on('index.tab', 'remove', function(fun, opt) {
                mTab.setTabWidth('remove');
            });
            $le.listener.on('index.tab', 'select', function(fun, opt) {
                mTab.setTabWidth('select');
            });
            $le.listener.on('index.page', 'resize',function() {
                // alert(1)
                mTab.setTabWidth('resize');
            });
        },
        setTabWidth: function(type) {

            var tabBox = $('#j_tab_box');
            var rbox = $('#j_wrap');
            var tabBox_w = tabBox.outerWidth();
            var rbox_w = rbox.width();
            var li_w = 0; //è¡¨ç¤ºé™¤äº†é€‰ä¸­çš„liçš„w
            var set = function() {
                var lis = tabBox.find('li').not('.selected').find('i');
                li_w = rbox_w - tabBox.find('li.selected').outerWidth() - 20;
                var len = lis.length; //æ‰€æœ‰litab ï¼‹ home tag
                // alert(li_w)

                lis.width(li_w / len - 21);
            }
            var reset = function() {
                var lis = tabBox.find('li').find('i');

                lis.width('auto')
            }
            if (type == 'add') {
                if (tabBox_w > rbox_w) {
                    set();
                }
            }
            if (type == 'remove') {
                reset();
                tabBox_w = tabBox.outerWidth();
                rbox_w = rbox.width();
                if (tabBox_w > rbox_w) {
                    set();
                }

            }
            if (type == 'select' || type == 'resize') {
                //console.log(1)
                reset();
                tabBox_w = tabBox.outerWidth();
                rbox_w = rbox.width();
                if (tabBox_w > rbox_w) {
                    // dd(1)
                    set();
                }

            }

        },
        reloadIframe: function(dom) {
            var fname = 'name_' + dom.data('fname');
            window.frames[fname].location.reload();
        },
        //æ·»åŠ ä¸€ä¸ªtab åˆ°tabåŒºåŸŸ
        addTab: function(opt) {
            //è¡¨ç¤ºæœ‰è¢«é€‰ä¸­çš„
            // if ($('#j_tab_box').find('li.selected').length) {
            //     mTab._curTid = $('#j_tab_box').find('li.selected').data('tid');

            // }

            if ($('#mTab_' + opt.id).length) {
                if (opt.id != mTab._curTid) {
                    mTab.select(opt.id);
                }

                return;
            }
            if (mTab._curTid) {

                $('#mTab_' + mTab._curTid).removeClass('selected');
                $('#mIframe_' + mTab._curTid).hide();
                $('#mTab_' + mTab._curTid).find('b').hide();
            }
            mTab._curTid = opt.id;
            mTab.add(opt);
        },
        select: function(id) {
            if (mTab._curTid) {
                $('#mTab_' + mTab._curTid).removeClass('selected');
                $('#mIframe_' + mTab._curTid).hide();
                $('#mTab_' + mTab._curTid).find('b').hide();
                $('#mTab_' + id).addClass('selected');
                $('#mIframe_' + id).show();
                mTab._curTid = id;
                var tit = $('#mTab_' + mTab._curTid).data('name');
                document.title = tit || 'æˆ‘çš„ä¸»é¡µ ';
                $('#mTab_' + id).find('b').show();
                $le.listener.trigger('index.tab', 'select');
            }
        },
        add: function(opt) {
            var reload = '<b data-fname={{=it.id}} style="display:none"></b>';
            var _tmp = '<li id="mTab_{{=it.id}}" class="selected"\
             data-tid="{{=it.id}}" data-name="{{=it.name}}" title="{{=it.name}}" data-furl="{{=it.furl}}"><i>{{=it.name}}</i>\
             <span></span>' + reload + '</li>';
            var tabHtml = $le.tplRender(dot, _tmp, opt);
            $('#j_tab_box').append(tabHtml);
            document.title = $('#mTab_' + mTab._curTid).data('name');

            $('#mTab_' + mTab._curTid).find('b').show();
            //æ·»åŠ å®Œæˆæ´¾å‘ addäº‹ä»¶
            $le.listener.trigger('index.tab', 'add', opt);

        },
        remove: function(dom) {
            var li = dom.parent();
            var id = li.data('tid');
            var tid = li.prev().data('tid') || li.next().data('tid');


            // if (tid == undefined) {

            //     return false
            // } else {
            if (li.hasClass('selected')) {
                mTab.select(tid);
            }
            // }
            $('#mTab_' + id).remove();
            $('#mIframe_' + id).remove();

            $le.listener.trigger('index.tab', 'remove');

        },
        bind: function() {
            $('#j_tab').on('click', 'li', function(e) {
                var myTid = $(this).data('tid');
                if (mTab._curTid == myTid) {
                    return;
                } else {
                    mTab.select(myTid);
                }
            })
            $('#j_tab').on('click', 'span', function(e) {
                e.stopPropagation();
                mTab.remove($(this));
            })
            $('#j_tab').on('click', 'b', function(e) {
                e.stopPropagation();
                mTab.reloadIframe($(this));
            })
        }
    }
    var mIframe = {
        listener: function() {
            $le.listener.on('index.tab', 'add', function(fun, opt) {
                mIframe.addIframe(opt);
            });
        },
        addIframe: function(opt) {
            // console.log(window.location.origin+opt.furl)
            var id = opt.id,
                furl = opt.furl;
            var _tmp = '<div class="j_iframe_item" id="mIframe_{{=it.id}}" ><iframe frameborder="0"  src="{{=it.furl}}"  name="name_{{=it.id}}"></iframe></div>';
            var frameHtml = $le.tplRender(dot, _tmp, opt);
            $('#j_frame').append(frameHtml);
        }
    }
    var P = function() {
        var me = this;
        this.treeData = D;
        this.treeSetting = {
            view: {
                dblClickExpand: false,
                addDiyDom: this.addDiyDom
            },
            callback: {
                onClick: function(event, treeId, treeNode, clickFlag) {
                    $('#j_tree_index').find('li').removeClass('curColor');
                    var treeNodeObj = $("#" + treeNode.tId);

                    var zTree = $.fn.zTree.getZTreeObj(treeId);
                    zTree.expandNode(treeNode);
                    mTree.clickTreeNode(treeNode);
                    if(!treeNode.children) {
                        if(treeNodeObj.find('.curSelectedNode').length) {
                            treeNodeObj.addClass('curColor');
                        }
                    }
                }
            }
        };
        this._init();
    }

    P.prototype = {
        setHeight: function() {
            var h = $('body').height() - $('#j_header').height();
            $('#j_sidebar').height(h);
            $('#toggle_box').height(h);
            $('#j_wrap').height(h);
            $('#j_sidebar').height(h);
            $('#j_frame').height(h - 32);
            // $('#j_frame .j_iframe_item').height(h - 32);
        },
        bindResize: function() {
            var me = this;
            $(window).resize(function() {
                setTimeout(function() {
                    me.setHeight();
                }, 200);
            });
        },
        setTree: function(data) {
            $.fn.zTree.init($("#j_tree_index"), this.treeSetting, data);
        },
        _init: function() {
            var me = this;
            this.setHeight();
            this.bindResize();
            $('#mIframe_home h2').show();
            new Go({
                url: '/site/showtree',
                success: function(json) {

                    if (json && json.errno === 10000 && json.data) {
                        me.setTree(json.data);

                        mTab._init();
                        mIframe.listener()
                    } else {
                        $('#mIframe_home').append('<p style="color: red; line-height:30px;">æ‚¨å½“å‰æ²¡æœ‰ç³»ç»Ÿä½¿ç”¨æƒé™ï¼Œè¯·è”ç³»ç®¡ç†å‘˜ä¸ºæ‚¨å¼€é€šæ‰€éœ€æƒé™ï¼Œç®¡ç†å‘˜é‚®ç®±ï¼šcloud@letv.com</p>');
                        $("#j_tree_index").html('');
                    }
                },
                beforeSend: function() {
                    var loader = document.createElement("div");
                    loader.setAttribute('class','ui-loader-container');
                    $(loader).html('<div class="loader"></div>');
                    $(loader).find(".loader").css('margin-top', ($('body').height()-$('#j_header').height())/2);
                    $("#j_tree_index").append(loader)
                },
                error: function(){
                    $("#j_tree_index").html('<p class="ui-msg" style="color:#fff;margin-top:10px">' + $Le.lang1.go.errorMsg + '</p>');
                },
                complete: function(XMLHttpRequest, status) {
                    if (status == 'timeout') {
                        $("#j_tree_index").html('<p class="ui-msg" style="color:#fff;margin-top:10px">'+$Le.g1("go.timeOut")+'</p>');
                    }
                },
                timeout:10000,
                dataType: 'json'
            });
        },
        addDiyDom: function(treeId, treeNode) {
            var spaceWidth = 10;
            var switchObj = $("#" + treeNode.tId + "_switch");
            // icoObj = $("#" + treeNode.tId + "_ico");
            // switchObj.remove();
            // icoObj.before(switchObj);

            if ((treeNode.level > 0 && treeNode.children) || treeNode.level > 1) {
                var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
                switchObj.before(spaceStr);
            }
        }
    };
    new P();
})