var connectImgURL  = "http://roborio-1389-frc.local:5801/?action=snapshot";
var imageStreamURL = "http://roborio-1389-frc.local:5801/?action=stream";
var canvasSize = {
		width: 640,
		height: 480
}

document.addEventListener("DOMContentLoaded", function() {

	//var canvas = makeCanvas(cameraStream);
	var imageDiv = only.html({div: [
		{
			img: "",
			src: imageStreamURL,
			css: canvasSize
		}
	]});
	
	only.setHtml([
		{p : "this message exists for debugging purposes"},
		{center: [
				imageDiv
			]}
	]);

	initDragElement(imageDiv);
	
})

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
			visible: "hidden"
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
