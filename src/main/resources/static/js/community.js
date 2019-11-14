$(document).ready(function(){
    /*提交问题回复*/
   $("#replay").click(function(){
       var questionId = $("#question-id").val();
       var context = $("#commentText").val();
       replayToTarget(questionId,1,context);
   })

    /*
    提交二级评论
     */

    $("#replay2").click(function(){
        var id = this.getAttribute("data-id");
        var context = $("#input-"+id).val();
        replayToTarget(id,2,context);
    })

    /*
    展开二级评论
     */
    toogleComment = function (e){
        var id = e.getAttribute("data-id");
        var comments = $("#comment-" +id);
        var replays = $("#replay-" + id);
        if(comments.hasClass("in")){
           comments.removeClass("in");
            replays.removeClass("active");

        }else {
            console.log("add class");
            var subCommentContainer = $("#comment-" + id);
            console.log(subCommentContainer.children().length);
            if (subCommentContainer.children().length != 1) {
                //展开二级评论
                comments.addClass("in");
                replays.addClass("active");
            } else {
                $.getJSON("http://localhost:8080/comment/" + id, function (data) {
                    $.each(data.data.reverse(), function (index, comment) {
                        var mediaLeftElement = $("<div/>", {
                            "class": "media-left"
                        }).append($("<img/>", {
                            "class": "media-object img-rounded",
                            "src": comment.user.avatarUrl
                        }));

                        var mediaBodyElement = $("<div/>", {
                            "class": "media-body"
                        }).append($("<h6/>", {
                            "class": "media-heading",
                            "html": comment.user.name
                        })).append($("<div/>", {
                            "html": comment.content
                        })).append($("<div/>", {
                            "class": "menu"
                        }).append($("<span/>", {
                            "class": "pull-right time-style",
                            "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                        })));

                        var mediaElement = $("<div/>", {
                            "class": "media"
                        }).append(mediaLeftElement).append(mediaBodyElement);

                        var commentElement = $("<div/>", {
                            "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 ",
                            "id":"comment-content",
                        }).append(mediaElement);

                        subCommentContainer.prepend(commentElement);
                    });
                });
                comments.addClass("in");
                replays.addClass("active");
            }

        }
    }
    /*
    提交到目标的方法
     */
   function replayToTarget(targetId,type,context) {
        if (!context) {
            alert("评论内容不能为空----来自前端的提醒");
            return;
        }
        var data = {
            "parentId": targetId,
            "content": context,
            "type": type
        }
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/comment",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (data) {
                if (data.code == 200) {
                    window.location.reload();
                    $("#comment_section").hide();
                } else {
                    if (data.code == 2003) {
                        var isAccepted = confirm(data.message);
                        if (isAccepted) {
                            window.open("https://github.com/login/oauth/authorize?client_id=6119f2bfd354c366ccdc&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                            window.localStorage.setItem("closable", true);
                        }
                    } else {
                        alert(data.message);
                    }
                }
            },
            dataType: "json"
        });
    }

    /*
     选择标签
     */
    selectTag = function(e) {
        var value = e.getAttribute("data-tag");
     //   console.log(value);
        var previous = $("#tag").val();
     //  console.log(previous);
        if (previous.indexOf(value) == -1) {
            if (previous) {
                $("#tag").val(previous + "," + value);
            } else {
                $("#tag").val(value);
            }
        }
    }

    /*
    展示標籤頁
     */
    $("#tag").click(function(){
        $("#select-tag").show();
    })


});
