/*链接*/
var server_ip = "172.20.50.217";
var rest_server_prefix = "http://"+server_ip+":8081";
var quark_login_api = rest_server_prefix+"/user/login";
var quark_getUser_api = rest_server_prefix+"/user/message/";
var quark_logout_api = rest_server_prefix+"/user/logout";
var quark_register_api = rest_server_prefix+"/user";
var quark_upload_api = rest_server_prefix+"/upload/image";
var quark_upload_icon_api = rest_server_prefix+"/upload/usericon/";
var quark_posts_add_api = rest_server_prefix+"/posts";
var quark_posts_get_api = rest_server_prefix+"/posts";
var quark_posts_detail_api = rest_server_prefix+"/posts/detail/";
var quark_reply_add_api = rest_server_prefix+"/reply";
var quark_label_getall_api = rest_server_prefix+"/label";
var quark_rank_posts_api=rest_server_prefix+"/rank/topPosts";
var quark_rank_users_api = rest_server_prefix+"/rank/newUsers";
var quark_user_detail_api = rest_server_prefix+"/user/detail/";
var quark_user_update_api = rest_server_prefix+"/user/";
var quark_user_update_psd_api = rest_server_prefix+"/user/password/";
var quark_label_posts_api = rest_server_prefix+"/posts/label/";
var quark_webSocket_api = rest_server_prefix+"/quarkServer";
var quark_notification_api= rest_server_prefix+"/notification/";
var quark_chat_webSocket_api = "ws://"+server_ip+":8083/websocket";
function setCookie(data) {
    var expiresDate= new Date();
    expiresDate.setTime(expiresDate.getTime() + (60 * 60 * 1000));
    $.cookie("QUARK_TOKEN",data,{
        path: '/',
        expires : 1
    });
}

function getCookie() {
    return $.cookie('QUARK_TOKEN');
}

function deleteCookie() {
    $.cookie("QUARK_TOKEN",null,{
        path: '/'
    });
}

(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

function applyHeader() {
    var htm;
    $.get(quark_getUser_api+ getCookie(),function (data) {
        if (data.status == 200){
            htm =  "<a class='avatar' href='/user/setting'>"+
                "<img src='"+data.data.icon+"'>"+
                "<cite>"+data.data.username+"</cite>"+
                "</a>"+
                "<div class='nav'>"+
                "<a href='/user/setting'><i class='iconfont icon-shezhi'></i>设置</a>"+
                "<a href='' onclick='logout()'><i class='iconfont icon-tuichu' style='top: 0; font-size: 22px;'></i>退了</a>"+
                "</div>";
        }else{
            htm ="<a class='unlogin' href='#'><i class='iconfont icon-touxiang'></i></a>"+
                "<span><a href='/user/login'>登入</a><a href='/user/register'>注册</a></span>";
        }

        $(".nav-user").append(htm);
    });
}

function logout() {

    $.post(quark_logout_api,{
        token: getCookie()
    },function (obj) {
        if (obj.status == 200){
            deleteCookie();
            location.href = "/index";
        }else{
            layer.msg(obj.error,{icon:5});
        }
    });

}