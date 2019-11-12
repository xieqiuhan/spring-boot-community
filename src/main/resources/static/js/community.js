$(document).ready(function(){
   $("#replay").click(function(){
       var questionId = $("#question-id").val();
       var context = $("#commentText").val();
       if(!context){
           alert("评论内容不能为空----来自前端的提醒");
           return;
       }
      var data = {
           "parentId":questionId,
           "content":context,
           "type":1

       }
       $.ajax({
           type: "POST",
           url: "http://localhost:8080/comment",
           contentType:"application/json",
           data: JSON.stringify(data),
           success: function(data){
               if(data.code == 200){
                   window.location.reload();
                   $("#comment_section").hide();
               }else{
                   if(data.code == 2003){
                       var isAccepted = confirm(data.message);
                       if(isAccepted){
                           window.open("https://github.com/login/oauth/authorize?client_id=6119f2bfd354c366ccdc&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                           window.localStorage.setItem("closable", true);
                       }

                   }else{
                       alert(data.message);
                   }

               }

               console.log(data);
           },
           dataType: "json"
       });

   })
});