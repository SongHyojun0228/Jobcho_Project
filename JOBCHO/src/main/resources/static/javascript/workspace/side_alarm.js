console.log(Number('[[${workspaceId}]]'));

function fetchAlarms() {
	console.log("알람 패치");
	fetch("/" + Number('[[${workspaceId}]]') + "/alarm/list")
		.then(response => response.text())
		.then(html => {
			const container = document.querySelector(".bottom_alarm_container");
			container.innerHTML = html;
		})
		.catch(error => console.error("알람 가져오기 실패:", error));
}
setInterval(fetchAlarms, 5000);
fetchAlarms();