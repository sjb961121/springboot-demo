function post() {
     var questionId = $("#question_id").val();
    var content = $("#commentcontent").val();
    if (!content){
        alert("评论内容不能为空")
        return;
    }
    // console.log(questionId);
    // console.log(content);
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify(
            {
                "parentId": questionId,
                "content":content,
                "type":1
            } ),
        success: function (response) {
            if (response.code==200){
                window.location.reload();
                // $("#commentsection").hide();
            }
            else{
                if (response.code==2001){
                    var conf = confirm(response.message);
                    if (conf){
                        window.open("https://github.com/login/oauth/authorize?client_id=f713d1e4d8e394d02c5d&http://redirect_uri=localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);
                    }
                }
            }

        },
        error:function(){
                alert("错误");
        },
        dataType: "json"
    });
}

function collapseComment(e) {
    // var data = document.getElementById('secondcomment');
    // data.getAttribute('data-id');
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    comments.toggleClass("in");
    $("#" + id).toggleClass("active");
    // console.log(id);
    // console.log(comments);
    $.getJSON( "/comment/"+id, function( data ) {
        // console.log(data.t);
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length==2) {
            $.each(data.t, function (index, comment) {
                console.log(comment.parentId)
                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<img/>", {
                    style:"width: 20px;height: 20px",
                    "class": "media-object",
                    "src": comment.user.avatarUrl
                }));

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "media-heading",
                    "html": comment.user.name
                })).append($("<div/>", {
                    "html": comment.content
                })).append($("<div/>", {
                    "class": "menus"
                }).append($("<span/>", {
                    "class": "pull-right",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                })));

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                // var commentElement = $("<div/>", {
                //     "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                // }).append(mediaElement);

                subCommentContainer.prepend(mediaElement);
            });
        }
    });
}

function subcomment(e) {
    var id = e.getAttribute("data-id");
    var content = $("#input-"+id).val();
    if (!content){
        alert("评论内容不能为空")
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify(
            {
                "parentId": id,
                "content":content,
                "type":2
            } ),
        success: function (response) {
            if (response.code==200){
                window.location.reload();
                // $("#commentsection").hide();
            }
            else{
                if (response.code==2001){
                    var conf = confirm(response.message);
                    if (conf){
                        window.open("https://github.com/login/oauth/authorize?client_id=f713d1e4d8e394d02c5d&http://redirect_uri=localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);
                    }
                }
            }

        },
        error:function(){
            alert("错误");
        },
        dataType: "json"
    });
}

function selectTag(tag) {
    var taginput = $("#tag").val();
    if (taginput.indexOf(tag)==-1){
        if (taginput){
            $("#tag").val(taginput+','+tag);
        }
        else {
            $("#tag").val(tag);
        }
    }

}

function showselectTag() {
    $("#select_tag").show();
}