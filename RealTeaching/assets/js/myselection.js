function gEBC(className){
	return document.getElementsByClassName(className);
}
function cleanSelection(){
	rangy.init();
	var sel = rangy.getSelection();
	if(sel.rangeCount > 0){
		sel.removeAllRanges();
	}
	window.jscrosssearch.jsLog("called clean selection!");
}
function setCrossSearchInfo(sel){
	var selectStr = sel.toString();
	var normalHtml = document.body.outerHTML;
		
	var range = sel.getRangeAt(0);
		
	var selectionEnd = $("<endsel class=\"EndSel\"></endsel>");
	var selectionStart = $("<startsel class=\"StartSel\"></startsel>");
		
	var startRange = document.createRange();
	startRange.setStart(range.startContainer, range.startOffset);
	startRange.insertNode(selectionStart[0]);
	    
	var endRange = document.createRange();
	endRange.setStart(range.endContainer, range.endOffset);
	endRange.insertNode(selectionEnd[0]);
	    
	var selectHtml = document.body.outerHTML;
	    
	var left = selectionStart.offset().left;
	var top = selectionStart.offset().top;
	var right = selectionEnd.offset().left;
	var bottom = selectionEnd.offset().top;	    
	    
	window.jscrosssearch.setSelectionRect(left, top, right, bottom);
	window.jscrosssearch.setSelectionInfo(normalHtml, selectHtml, selectStr);
	    
	selectionStart.remove();
	selectionEnd.remove();
	
	window.jscrosssearch.jsLog("called set CrossSearchInfo!");
}
function reportSelectionTextDirect(){
	var sel = window.getSelection();
	if(!sel){
		return;
	}
	setCrossSearchInfo(sel);	
	window.jscrosssearch.DirectCrossSearchWin();
	cleanSelection();
	window.jscrosssearch.jsLog("called reportSelectionTextDirect!");
}
function reportSelectionText() {
	var sel = window.getSelection();
	if(!sel){
		return;
	}
	setCrossSearchInfo(sel);
	window.jscrosssearch.CrossSearchWin();
	cleanSelection();
	window.jscrosssearch.jsLog("called reportSelectionText!");
}


