//<![CDATA[
var chaptArray = new Array(
    "ch0","ch1","ch2","ch3","ch4","ch5","ch6","ch7","ch8",
    "ch9","ch10","ch11","app");

function toggleSection(divId,imgId) {
 div = document.getElementById(divId)
 img = document.getElementById(imgId)
 if (div.style.display == "block") {
   div.style.display = "none";
   img.src="images/collapsed.gif"
  } else {
   div.style.display = "block";
   img.src="images/expanded.gif"
  }
}

function expandAll() {
    for(i=0; i<chaptArray.length; i++){
	document.getElementById(chaptArray[i]).style.display="block";
	document.getElementById(chaptArray[i]+"-img").src="images/expanded.gif";
   }
}

function collapseAll() {
    for(i=0; i<chaptArray.length; i++){
	document.getElementById(chaptArray[i]).style.display="none";
	document.getElementById(chaptArray[i]+"-img").src="images/collapsed.gif";
   }
}

function SearchGuidelines() {
   var term = escape(document.getElementById('searchfield').value); 
   window.location.href = 'http://xdesign.sfbay.sun.com/search/index.jsp?search=1&si=1&ns=10&st=relevance&c=SWAED-3_0&qt=' + term; } 

//]]>
