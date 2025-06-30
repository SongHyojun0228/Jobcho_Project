document.getElementById("chat_input").addEventListener("keydown", function(event) {
	if (event.key === "Enter" && !event.shiftKey) {
		event.preventDefault();
		sendMessage();
	}
});