$(function() {
	// // 0. security認証
	// var token = $("meta[name='_csrf']").attr('content');
	// var header = $("meta[name='_csrf_header']").attr('content');
	// $(document).ajaxSend(function(e, xhr, options) {
	// 	xhr.setRequestHeader(header, token);
	// });

	// 親カテゴリのセットアップ
  setUpParentCategory();

  // 子カテゴリのセットアップ
  $('.parent-category').change(function(){
    $('.child-category > option').remove();
    $('.grandChild-category > option').remove();
    $('.child-category').append($('<option>').html("- childCategory -"));
    $('.grandChild-category').append($('<option>').html("- grandChild -"));
    setUpChildCategory();
  });

  // 孫カテゴリのセットアップ
  $('.child-category').change(function(){
    $('.grandChild-category > option').remove();
    $('.grandChild-category').append($('<option>').html("- grandChild -"));
    setUpGrandChildCategory();
  });



	//// 親カテゴリを自動生成するメソッド
	function setUpParentCategory() {
		$.ajax({
			type: 'POST',
			url: '/ajax/setUpParentCategory',
			dataType: 'json'
		})
			.done(function(parentList) {
				const target = $('.parent-category');
				for (const parent of parentList) {
					target.append($('<option>').html(parent).val(parent));
				}
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown) {
				alert('リクエストに失敗' + textStatus + ':\n' + errorThrown);
			});
  }
  
  //// 子カテゴリを自動生成するメソッド
    function setUpChildCategory() {
      const parent = $('.parent-category').val();
      $.ajax({
        type: 'POST',
        url: '/ajax/setUpChildCategory',
        data: {
          parent: parent
        },
        dataType: 'json'
      })
        .done(function(childList) {
          const target = $('.child-category');
          for (const child of childList) {
            target.append($('<option>').html(child).val(child));
          }
        })
        .fail(function(XMLHttpRequest, textStatus, errorThrown) {
          alert('リクエストに失敗' + textStatus + ':\n' + errorThrown);
        });
    }
  //// 孫カテゴリを自動生成するメソッド
    function setUpGrandChildCategory() {
      const parent = $('.parent-category').val();
      const child = $('.child-category').val();
      $.ajax({
        type: 'POST',
        url: '/ajax/setUpGrandChildCategory',
        data: {
          parent: parent,
          child: child
        },
        dataType: 'json'
      })
        .done(function(grandChildList) {
          const target = $('.grandChild-category');
          for (const grandChild of grandChildList) {
            target.append($('<option>').html(grandChild).val(grandChild));
          }
        })
        .fail(function(XMLHttpRequest, textStatus, errorThrown) {
          alert('リクエストに失敗' + textStatus + ':\n' + errorThrown);
        });
    }
});
