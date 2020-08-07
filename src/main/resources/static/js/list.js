$(function() {
	// // 0. security認証
	// var token = $("meta[name='_csrf']").attr('content');
	// var header = $("meta[name='_csrf_header']").attr('content');
	// $(document).ajaxSend(function(e, xhr, options) {
	// 	xhr.setRequestHeader(header, token);
	// });

	//// 画面描画直後に親カテゴリのセレクトボックスを自動生成
	// 親カテゴリ
	setUpParentCategory();

	//// 動的にカテゴリのセレクトボックスを変化させる機能
	// 子カテゴリのセットアップ
	$('.parent-category').change(function() {
		$('.child-category > option').remove();
		$('.grandChild-category > option').remove();
		$('.child-category').append($('<option>').html('- childCategory -').val(''));
		$('.grandChild-category').append($('<option>').html('- grandChild -').val(''));
		setUpChildCategory();
	});

	// 孫カテゴリのセットアップ
	$('.child-category').change(function() {
		$('.grandChild-category > option').remove();
		$('.grandChild-category').append($('<option>').html('- grandChild -').val(''));
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
				const parentSelected = $('input:hidden[name="parentCategory"]').val();
				const childSelected = $('input:hidden[name="childCategory"]').val();
				const grandChildSelected = $('input:hidden[name="grandChildCategory"]').val();
				for (const parent of parentList) {
					if (parent == parentSelected) {
						// 検索結果を表示させてる場合はセレクトボックスにカテゴリを表示
						target.append($('<option>').html(parent).val(parent).prop('selected',true));
						setUpChildCategoryS(parentSelected, childSelected);
					} else {
						target.append($('<option>').html(parent).val(parent));
					}
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
	//// 子カテゴリを自動生成するメソッド（検索結果を表示している場合）
	function setUpChildCategoryS(parentSelected, childSelected) {
		const parent = parentSelected;
		const grandChildSelected = $('input:hidden[name="grandChildCategory"]').val();
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
					if (child == childSelected) {
						// 検索結果を表示させてる場合はセレクトボックスにカテゴリを表示
						target.append($('<option>').html(child).val(child).prop('selected',true));
						setUpGrandChildCategoryS(parentSelected, childSelected, grandChildSelected);
					} else {
						target.append($('<option>').html(child).val(child));
					}
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
	//// 孫カテゴリを自動生成するメソッド（検索結果を表示している場合）
	function setUpGrandChildCategoryS(parentSelected, childSelected, grandChildSelected) {
		const parent = parentSelected;
		const child = childSelected;
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
					if (grandChild == grandChildSelected) {
						// 検索結果を表示させてる場合はセレクトボックスにカテゴリを表示
						target.append($('<option>').html(grandChild).val(grandChild).prop('selected',true));
					} else {
						target.append($('<option>').html(grandChild).val(grandChild));
					}
				}
			})
			.fail(function(XMLHttpRequest, textStatus, errorThrown) {
				alert('リクエストに失敗' + textStatus + ':\n' + errorThrown);
			});
	}
});
