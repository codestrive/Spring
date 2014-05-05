 $(function(){

    $(".dropdown-menu li a").click(function(){

      $(this).closest(".dropdown-menu").prev().html($(this).text()+' <span class="caret"></span>');

   });

});