
var connectImgURL  = "http://roborio-1389-frc.local:5801/?action=snapshot";
var imageStreamURL = "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcR0vgTiljth-6P1AeiPO-5ta_ByA7_6K0oNn9M7z8XSf-1JAst1";
	imageStreamURL = "http://roborio-1389-frc.local:5801/?action=stream";
var sendMessageURL = "/servlet/armPosition";
var canvasSize = {
		width: 640,
		height: 480
};
var keyMessages = {
	"a" : makeMessageSend("Yolo "),
	"b" : makeMessageSend("quack"),
	"h" : makeMessageSend("wut tho")
};

function phiFromR(r){
	var scaled = r/2 + .5;
	var coefs = [
		-70.68, 11.1, 79.3, 67.09, -21.95, -106.1, -74.28, 87.32, 176, -94.8
	];


	var total = 0;
	for (var i = 0; i < coefs.length; i += 1){
		var index = coefs.length - i - 1;
		total += coefs[index] * Math.pow(scaled, i);
	}
	
	var rad = degToRad(total);
	return Math.PI/2 - rad;
}

function thetaFromXY(x, y){
	return Math.atan(y / x);
}

function radToDeg(rad){
	return rad / Math.PI * 180;
}

function degToRad(deg){
	return deg * Math.PI / 180;
}

function calculateAlpha(theta, phi){
	return Math.atan((Math.cos(phi) * Math.cos(theta)) / Math.sin(phi));
}

function calculateD(h, theta, phi){
	return h * Math.sin(theta) * Math.tan(phi);
}

function calculateTowerPos(h, x, y){
	var r = Math.sqrt(x*x + y*y);
	var phi = phiFromR(r);
	var theta = thetaFromXY(x, y);
	var d = calculateD(h, theta, phi);
	var alpha = calculateAlpha(theta, phi);
	return {
		alpha: alpha,
		distance: d
	}
}

addEventListener("click", function() {
    var
          el = document.documentElement
        , rfs =
               el.requestFullScreen
            || el.webkitRequestFullScreen
            || el.mozRequestFullScreen
    ;
    rfs.call(el);
});

document.addEventListener("DOMContentLoaded", function() {

	//var canvas = makeCanvas(cameraStream);
	var image = only.html(
		{
			img: "",
			src: imageStreamURL,
			css: {
				transform: "rotate(-90deg)",
				width: canvasSize.width,
				height: canvasSize.height
				
			}
		});
	
	var lastSentCmd = makeMessageSend.sendMessage;
	
	var distance = only.html({p: "click"});
	
	only.setHtml([
		
		{div: [
		       image
		       ],
			css: {
				'margin-top': "" + (canvasSize.width - canvasSize.height)/2 + "px",
				width: canvasSize.height,
				height: canvasSize.width
			}},
		{p : "this message exists for debugging purposes"},
		distance,
		{h3: "Last Cmd Sent: "}
	]);

	image.addEventListener("mousedown", function(e){
		var res = getClickCoords(image, e);
		console.log(res.x + " " + res.y);
		distance.innerHTML = calculateDistance(res.y);
	});

//	initDragElement(image);
	setupKeys(keyMessages);
})

function getClickCoords(element, event){
	var rect = element.getBoundingClientRect();
	console.log(rect.height);
	var left = event.pageX - rect.left + document.body.scrollLeft;
	var top = event.pageY - rect.top + document.body.scrollTop;
	var xPre = left / (rect.width);
	var yPre = top / (rect.height);
	var x = xPre * 2 - 1;
	var y = (1 - yPre) * 2 - 1;
	return {x: x, y: y};
}

function makeMessageSend(msg){
	function sendMessage(){
		console.log("sending: " + msg);
		var msgObj = {
				msg: msg
		}
		httpPostAsync(sendMessageURL, JSON.stringify(msgObj), function(msg){
			console.log(msg);
		})
	}
	return sendMessage;
}

function initDragElement(element){
	var drag = false;
	var div = only.html({div: "",
		css: {
			border: "4px solid red",
			height: 0,
			width: 0,
			left: 0,
			top: 0,
			position: "absolute",
			display: "inline",
			visibility: "hidden"
		}})
	var divInfo = {
		x: 0,
		y: 0
	}
	element.appendChild(div);

	element.addEventListener("mousedown", function(e){
		drag = true;
		var x = e.pageX;
		var y = e.pageY;
		div.style.left = x;
		div.style.top = y;
		divInfo.x = x;
		divInfo.y = y;
		div.style.width = 0;
		div.style.height = 0;
		element.appendChild(div);
		div.style.visibility = "initial";
	})
	element.addEventListener("mousemove", function(e){
		if(drag){
			var x = e.pageX;
			var y = e.pageY;
			div.style.width = x - divInfo.x;
			div.style.height = y - divInfo.y;
		}
	});
	element.addEventListener("mouseup", function(e){
		if(drag){
			element.removeChild(div);
			drag = false;
			div.style.visibility = "hidden";
			var rectangle = {
				x : divInfo.x,
				y : divInfo.y,
				width : e.pageX - divInfo.x,
				height : e.pageY - divInfo.y
			};
			console.log(rectangle.x + " " + rectangle.y + " " + rectangle.width + " " + rectangle.height)
		}
	})
}

function setupDrawOutlines(canvas, callback){
	var rect = {
			x1: 0, y1: 0, x1: 0, y2: 0
	}
	canvas.addEventListener("mousedown", mousedown, false);
	canvas.addEventListener("mouseup", mouseup, false);
	canvas.addEventListener("mousemove", mousemove, false);
	
	function mousedown(){
		
	}
	function mouseup(){
		
	}
	function mousemove(){
		
	}
	
	canvas.save();
}

function makeCanvas(drawFrame){
	var canvas = only.html({
		canvas: "",
		css: {
			width: canvasSize.width,
			height: canvasSize.height
		}
	})
	canvas.width = canvasSize.width;
	canvas.height = canvasSize.height;

	function doTick(){
		drawFrame(canvas);
	}

	window.setInterval(doTick, 1);
	return canvas;
}
function setupKeys(keyCallbacks){
	for (var key in keyCallbacks){
		var callback = keyCallbacks[key];
	}
	document.addEventListener("keypress", function(e){
		var char = String.fromCharCode(e.which || e.keyCode);
		var callback = keyCallbacks[char];
		if (callback){
			callback();
		}
	})
}

function httpPostAsync(theUrl, data, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText),
            document.body.style.backgroundColor = "green"
    else
    	throw "Error Sending Message"
        document.body.style.backgroundColor = "red";
    }
    xmlHttp.open("POST", theUrl, true); // true for asynchronous
    xmlHttp.send(data);
}
