var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8090/admin");
    ws.onopen = function (event) {

    }
    ws.onmessage = function (event) {
        var $textarea = document.getElementById("messages");
        $textarea.value = $textarea.value + event.data + "\n";
        console.log("onmessage: " + event.data)
    }
    ws.onclose = function (event) {

    }
};

function sendMessage() {
    var messageField = document.getElementById("message");
    var userNameField = document.getElementById("username");
    var message = userNameField.value + ":" + messageField.value;
    ws.send(message);
    messageField.value = '';
}