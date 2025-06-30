function toggleStar(imgElement) {
	const emptyStar = '/images/icons/empty_star.png';
	const filledStar = '/images/icons/star.png';

	const isAdding = imgElement.getAttribute('src').includes('empty_star');

	const bookmarkType = imgElement.dataset.type;
	const bookmarkId = imgElement.dataset.id;

	const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
	const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

	fetch("/workspace/bookmark/toggle", {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			[header]: token
		},
		body: JSON.stringify({
			type: bookmarkType,
			targetId: bookmarkId,
			action: isAdding ? 'ADD' : 'REMOVE'
		})
	})
		.then(res => res.json())
		.then(data => {
			if (!data.success) {
				alert("즐겨찾기 저장 중 문제가 발생했습니다.");
			} else {
				updateAllStars(bookmarkType, bookmarkId, isAdding ? filledStar : emptyStar);
			}
		})
		.catch(err => {
			console.error(err);
			alert('에러 발생');
		});
}

function updateAllStars(type, id, newSrc) {
	console.log("호출");
	const selector = `.bookmark_star_${type}_${id}`;
	console.log("사용된 selector:", selector);
	const allStars = document.querySelectorAll(selector);
	console.log("찾은 star 개수:", allStars.length);
	allStars.forEach(star => {
		console.log("변경 전 src:", star.getAttribute('src'));
		star.setAttribute('src', newSrc);
		console.log("변경 후 src:", star.getAttribute('src'));
	});
}

