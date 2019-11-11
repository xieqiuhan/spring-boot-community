$(document).ready(function(){
   $("#replay").click(function(){
       var questionId = $("#question-id").val();
       var context = $("#commentText").val();
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
                   $("#comment_section").hide();
               }else{
                   alert(data.message);
               }

               console.log(data);
           },
           dataType: "json"
       });

   })
});