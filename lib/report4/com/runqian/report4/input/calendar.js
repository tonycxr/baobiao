
            //���� ���ʻ����滻���� Ĭ������
            var __time = "ʱ��";
            var __year = "��";
            var __month = "��";
            var __date = "��";
            var __hour = "ʱ";
            var __minute = "��";
            var __second = "��";
            var __prevYear = "��ǰ��1��";
            var __nextYear = "���1��";
            var __prevMonth = "��ǰ��1��";
            var __nextMonth = "���1��";
            var __prevHour = "��ǰ��1��Сʱ";
            var __nextHour = "���1��Сʱ";
            var __prevMinutes = "��ǰ��1����";
            var __nextMinutes = "���1����";
            var __prevSeconds = "��ǰ��1��";
            var __nextSeconds = "���1��";
            var __funMonthSelect = "����˴�ѡ���·�";
            var __Sunday = "����";
            var __Monday = "��һ";
            var __Tuesday = "�ܶ�";
            var __Wednesday = "����";
            var __Thursday = "����";
            var __Friday = "����";
            var __Saturday = "����";
            var __clear = "���";
            var __close = "�ر�";
            var __fontFamily = "����";
            var _Jan = "1��";
            var _Feb = "2��";
            var _Mar = "3��";
            var _Apr = "4��";
            var _May = "5��";
            var _Jun = "6��";
            var _Jul = "7��";
            var _Aug = "8��";
            var _Sept = "9��";
            var _Oct = "10��";
            var _Nov = "11��";
            var _Dec = "12��";
            // over

function _createRunqianCalendar() {
    var obj = document.getElementById("_runqianCalendar");
    if( obj == null ) {
		document.body.insertAdjacentHTML( 'afterBegin', "<div id=_runqianCalendar style='position: absolute; z-index: 7777; width: 145px; height: 175px; display: none'>" +
				"</div>" );
		var _rc = _runqianCalendar;
    	_rc.iframe = window.createPopup();
	    _rc.daysMonth  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	    _rc.day        = new Array(38);
	    _rc.dayObj     = new Array(38);
	    _rc.objExport  = null;
	    _rc.eventSrc   = null;
	    _rc.inputDate  = null;
	    _rc.thisYear   = new Date().getFullYear();
	    _rc.thisMonth  = new Date().getMonth()+ 1;
	    _rc.thisDay    = new Date().getDate();
	    _rc.thisHour   = new Date().getHours();
	    _rc.thisMinutes= new Date().getMinutes();
	    _rc.thisSeconds= new Date().getSeconds();
      	_rc.dateFormat = "yyyy-MM-dd HH:mm:ss";
		_rc.emptyAsNow = false;

	    _rc.today      = _rc.thisDay +"/"+ _rc.thisMonth +"/"+ _rc.thisYear;
	    _rc.darkColor  = "#fa8072";      //����屳����ɫ
	    try{ _rc.darkColor = _calendarMainBackColor; }catch(e){}
	    _rc.lightColor = "#FFFFFF";      //���߿�����ɫ
	    _rc.wordColor  = "#000040";      //����������ɫ
	    try{ _rc.wordColor = _calendarDayColor; }catch(e){}
	    _rc.wordDark   = "#DCDCDC";      //�Ǳ�������������ɫ
	    _rc.dayBgColor = "#ffe4e1";      //������屳��ɫ
	    try{ _rc.dayBgColor = _calendarDayBackColor; }catch(e){}
	    _rc.todayColor = "#4682b4";      //��������������ɫ
	    _rc.DarkBorder = "#a9a9a9";      //���ڰ��߿�����ɫ
	    _rc.weekColor  = "#FFFFFF";      //��������ɫ
	    try{ _rc.weekColor = _calendarWeekColor; }catch(e){}
	    _rc.type = "day";//07-7-17������������ XXXX-XX-XX XX:XX:XX;
	    _rc.separator = "-";//ͣ��
           // _initCalJsVar();

    	    _createIframeSyntax();
    }
}

function _createIframeSyntax() {
	var _rc = _runqianCalendar;
	var sheet = _rc.iframe.document.createStyleSheet(); 
	sheet.addRule( "*","font-size: 12px; font-family:" + __fontFamily );
    sheet.addRule( ".bg", "color: "+ _rc.lightColor +"; cursor: default; background-color: "+ _rc.darkColor );
    sheet.addRule( "table#tableWeek td", "color: "+ _rc.weekColor + ";border-top: 1px solid #FFFFFF; border-left: 1px solid #FFFFFF;"+
    	"border-bottom: 1px solid "+ _rc.DarkBorder +"; border-right: 1px solid "+ _rc.DarkBorder );
    sheet.addRule( "table#tableDay  td", "font-weight: bold" );
    sheet.addRule( ".over", "text-align: center; border-top: 1px solid "+ _rc.DarkBorder +"; border-left: 1px solid "+ _rc.DarkBorder +";"+
    	"border-right: 1px solid "+ _rc.lightColor +"; border-bottom: 1px solid "+ _rc.lightColor );
    sheet.addRule( ".out", "text-align: center; border-top: 1px solid #FFFFFF; border-left: 1px solid #FFFFFF;"+
    	"border-bottom: 1px solid "+ _rc.DarkBorder +"; border-right: 1px solid "+ _rc.DarkBorder );

    var str = "<meta http-equiv='Content-Type' content='text/html; charset=gb2312'><table id=tableMain class=bg border=0 cellspacing=2 cellpadding=0 onmousedown='if( event.srcElement != tmpMonthSelect ) parent._hiddenSelect(tmpMonthSelect,false)'>"+
    "<tr><td height=18 bgcolor='"+ _rc.lightColor +"'>"+
    "    <table id=tableHead width=224 border=0 cellspacing=1 cellpadding=0><tr align=center>"+
    "    <td width=13 height=18 class=bg title=" + __prevYear + " onselectstart='return false' style='cursor: hand' onclick='parent._prevYear()'><b>&lt;</b></td>"+
    "    <td width=50 align=right><INPUT id=yearInput onkeydown='parent._changeYear(event.keyCode)' onclick='this.select()' maxLength=4 oldValue='' style='width:50px;height:18px;line-height:15px;text-align:center;border:none;background-color:white'></td>"+
    "    <td width=25 align=left onselectstart='return false' style='color:black'>" + __year + "</td>"+
    "    <td width=13 class=bg title=" + __nextYear + " onselectstart='return false' onclick='parent._nextYear()' style='cursor: hand'><b>&gt;</b></td>"+
    "    <td width=13 class=bg title=" + __prevMonth + " onselectstart='return false' style='cursor: hand' onclick='parent._prevMonth()'><b>&lt;</b></td>"+
    "    <td width=80 id=runqianYearMonth title=" + __funMonthSelect + " align=center onselectstart='return false'><span onselectstart='return false' onclick='parent._funMonthSelect()' style='color:black'></span>" +
    "<select id=tmpMonthSelect style='width:70px;display:none'"+
    " onchange='parent._runqianCalendar.thisMonth=this.value;parent._hiddenSelect(this,true);'>" +
    "<option value=1>"+ _Jan +"</option><option value=2>"+ _Feb +"</option><option value=3>"+ _Mar +"</option><option value=4>"+ _Apr +"</option><option value=5>"+ _May +"</option><option value=6>"+ _Jun +"</option>" +
    "<option value=7>"+ _Jul +"</option><option value=8>"+ _Aug +"</option><option value=9>"+ _Sept +"</option><option value=10>"+ _Oct +"</option><option value=11>"+ _Nov +"</option><option value=12>"+ _Dec +"</option>" +
    "</select></td>"+
    "    <td width=13 class=bg title=" + __nextMonth + " onselectstart='return false' onclick='parent._nextMonth()' style='cursor: hand'><b>&gt;</b></td></tr></table>"+
    "</td></tr><tr><td height=20><table id=tableWeek onselectstart='return false' border=0 width=224 cellpadding=0 cellspacing=0>"+
    "    <tr align=center><td height=20 style='width:32px'>" + __Sunday + "</td><td style='width:32px'>" + __Monday + "</td><td style='width:32px'>" + __Tuesday + "</td><td style='width:32px'>" + __Wednesday + "</td><td style='width:32px'>" + __Thursday + "</td><td style='width:32px'>" + __Friday + "</td><td style='width:32px'>" + __Saturday + "</td></tr></table>"+
    "</td></tr><tr><td valign=top width=224 bgcolor='"+ _rc.lightColor +"'>"+
    "    <table id=tableDay onselectstart='return false' height=120 width=224 border=1 cellspacing=1 cellpadding=0>";
         for(var x=0; x<5; x++){ str += "<tr height=18>";
         for(var y=0; y<7; y++)  str += "<td class=out style='width:32px' id='runqianDay"+ (x*7+y) +"'></td>"; str += "</tr>";}
         str += "<tr>";
         for(var x=35; x<38; x++) str += "<td class=out id='runqianDay"+ x +"'></td>";
         str +="<td colspan=2 class=out ><input style=' background-color: "+
         _rc.dayBgColor +";cursor: hand; padding-top: 4px; width: 100%; height: 100%; border: 0' onfocus='this.blur()'"+
         " type=button value=" + __clear + " onclick=\"parent._runqianCalendar.separator='';parent._returnDate('//')\"></td>";
         str +="<td colspan=2 class=out ><input style=' background-color: "+
         _rc.dayBgColor +";cursor: hand; padding-top: 4px; width: 100%; height: 100%; border: 0' onfocus='this.blur()'"+
         " type=button value=" + __close + " onclick='parent._hiddenCalendar()'></td></tr></table>"+
    "</td></tr>";

    	str += "<tr id=timeTr><td id=timeTd style='height:20px' bgcolor='"+ _rc.lightColor +"'><div id=timeDiv style='display:none' ><table id=timeTable width=221 border=0 border=0 cellspacing=1 cellpadding=0><tr>"+
			         "<td style='height:18px;align:right' class=bg align=left>" + __time + ":</td>"+
			           "<td style='cursor: hand' title=" + __prevHour + " onselectstart='return false' onclick='parent._prevHour()' width=12 height=18 class=bg><b>&lt;</b></td>"+
			           "<td><input type=text id=hour  onkeydown=\"parent._changeTime(event.keyCode,'hour')\" onclick='this.select()' style='width:22px;height:15px;border:none'>" + __hour + " </td>"+
			           "<td style='cursor: hand' title=" + __nextHour + " onselectstart='return false' onclick='parent._nextHour()'width=12 height=18 class=bg><b>&gt;</b></td>"+

			           "<td style='cursor: hand' title=" + __prevMinutes + " onselectstart='return false'  onclick='parent._prevMinutes()' width=12 height=18 class=bg><b>&lt;</b></td>"+
			           "<td><input type=text id=minute onkeydown=\"parent._changeTime(event.keyCode,'minute')\" onclick='this.select()' style='width:22px;height:15px;border:none'>" + __minute + "</td>"+
			           "<td style='cursor: hand' title=" + __nextMinutes + " onselectstart='return false'  onclick='parent._nextMinutes()' width=12 height=18 class=bg><b>&gt;</b></td>"+

			           "<td style='cursor: hand' title=" + __prevSeconds + " onselectstart='return false'    onclick='parent._prevSeconds()' width=12 height=18 class=bg><b>&lt;</b></td>"+
			           "<td><input type=text id=second onkeydown=\"parent._changeTime(event.keyCode,'second')\" onclick='this.select()' style='width:22px;height:15px;border:none'>" + __second + "</td>"+
			           "<td style='cursor: hand' title=" + __nextSeconds + " onselectstart='return false'    onclick='parent._nextSeconds()' width=12 height=18 class=bg><b>&gt;</b></td>"+
					    "</tr></table></div></td></tr>";

    str += "</table>";
    //_rc.iframe.document.writeln(str);
    var oBody = _rc.iframe.document.body;
    oBody.innerHTML = str;
	oBody.style.border = "none";
	oBody.style.overflowX = "hidden";
	oBody.style.overflowY = "hidden";
	oBody.style.margin = "0";
    oBody.oncontextmenu = function(){ return false;};
    for(var i=0; i<38; i++)
    {
        _rc.dayObj[i] = _rc.iframe.document.getElementById("runqianDay"+ i);
        _rc.dayObj[i].onmouseover = _dayMouseOver;
        _rc.dayObj[i].onmouseout  = _dayMouseOut;
        _rc.dayObj[i].onclick     = _returnDate;
    }
}

function _showCalendar() {
	var _rc = _runqianCalendar;
	_rc.iframe.document.getElementById( "tmpMonthSelect" ).style.display = "none";
	if ( _rc.type == 'date' || _rc.type == 'time' ){
	 	_rc.iframe.document.getElementById("timeDiv").style.display = "";
	}
	else _rc.iframe.document.getElementById("timeDiv").style.display = "none";

    var e = window.event.srcElement;
    _rc.eventSrc = e;
	if (arguments.length == 0) _rc.objExport = e;
    else _rc.objExport = eval(arguments[0]);
    if( e.tagName == "INPUT" ) {
    	if( !e.readOnly ) {
    		if( e.clicktime == null ) e.clicktime = 0;
    		e.clicktime++;
    		if( e.clicktime % 2 == 0 ) {
    			_hiddenCalendar();
    			return;
    		}
    	}
    }
	if ( _rc.type == 'date' || _rc.type == 'time' ){
		_rc.iframe.show( 0, e.offsetHeight, 228, 195, e );
	}
	else{
	    _rc.iframe.show( 0, e.offsetHeight, 228, 172, e );
	}

    var value = _rc.objExport.value;
    if( _rc.objExport.tagName == "TD" ) value = _rc.objExport.innerText;
    value = _trimString( value );
    _rc.oldValue = value;
    if ( value != ""){
    	if ( _rc.dateFormat.length == value.length ){
		    if ( _rc.dateFormat .indexOf( 'yyyy' ) >= 0 ){
		    	 var fl = _rc.dateFormat.indexOf( 'y' );
		    	 var ll = _rc.dateFormat.lastIndexOf( 'y' );
		    	 _rc.thisYear = value.substring( fl , ll + 1 );
		    }
		    if ( _rc.dateFormat .indexOf( 'MM' ) >= 0 ){
		    	 var fl = _rc.dateFormat.indexOf( 'M' );
		    	 var ll = _rc.dateFormat.lastIndexOf( 'M' );
		    	 _rc.thisMonth = value.substring( fl , ll + 1 );
		    }
		    if ( _rc.dateFormat .indexOf( 'dd' ) >= 0 ){
		    	 var fl = _rc.dateFormat.indexOf( 'd' );
		    	 var ll = _rc.dateFormat.lastIndexOf( 'd' );
		    	 _rc.thisDay = value.substring( fl , ll + 1 );
		    }
		    if ( _rc.dateFormat .indexOf( 'HH' ) >= 0 ){
		    	 var fl = _rc.dateFormat.indexOf( 'H' );
		    	 var ll = _rc.dateFormat.lastIndexOf( 'H' );
		    	 _rc.thisHour = value.substring( fl , ll + 1 );
		    }
		    if ( _rc.dateFormat .indexOf( 'mm' ) >= 0 ){
		    	 var fl = _rc.dateFormat.indexOf( 'm' );
		    	 var ll = _rc.dateFormat.lastIndexOf( 'm' );
		    	 _rc.thisMinutes = value.substring( fl , ll + 1 );
		    }
		    if ( _rc.dateFormat .indexOf( 'ss' ) >= 0 ){
		    	 var fl = _rc.dateFormat.indexOf( 's' );
		    	 var ll = _rc.dateFormat.lastIndexOf( 's' );
		    	 _rc.thisSeconds = value.substring( fl , ll + 1 );
		    }
		}
		var y = _rc.thisYear;
		var m = _rc.thisMonth;
		var d = _rc.thisDay;
		var h = _rc.thisHour;
		var mi= _rc.thisMinutes;
		var s = _rc.thisSeconds;
        if( !isNaN( y ) && y > 1970 && y < 2050) _rc.thisYear   = parseInt( y, 10 );
        else _rc.thisYear = new Date().getFullYear();
        if( !isNaN( m ) && m >= 1 && m <= 12 ) _rc.thisMonth  = parseInt( m, 10 );
        else _rc.thisMonth = new Date().getMonth()+ 1;
        if( !isNaN( d ) ) _rc.thisDay  = parseInt( d, 10 );
        if( !isNaN( h ) && h >= 0 && h <= 23) _rc.thisHour    = parseInt( h, 10 );
        else _rc.thisHour = new Date().getHours();
        if( !isNaN( mi ) && mi >= 0 && mi <= 59 ) _rc.thisMinutes    = parseInt( mi, 10 );
        else _rc.thisMinutes = new Date().getMinutes();
        if( !isNaN( s ) && s >= 0 && s <= 59 ) _rc.thisSeconds    = parseInt( s, 10 );
	    else _rc.thisSeconds = new Date().getSeconds();

        _rc.inputDate  = parseInt(_rc.thisDay, 10) +"/"+ parseInt(_rc.thisMonth, 10) +"/"+
            parseInt(_rc.thisYear, 10);
    }
	else if( _rc.emptyAsNow ) {
	    _rc.thisYear   = new Date().getFullYear();
	    _rc.thisMonth  = new Date().getMonth()+ 1;
	    _rc.thisDay    = new Date().getDate();
	    _rc.thisHour   = new Date().getHours();
	    _rc.thisMinutes= new Date().getMinutes();
	    _rc.thisSeconds= new Date().getSeconds();
	}
    else _rc.inputDate = "";
    _writeCalendar();
    if( _rc.objExport.tagName == "INPUT" ) _rc.objExport.blur();
}

function _funMonthSelect() {
    var m = isNaN(parseInt(_runqianCalendar.thisMonth, 10)) ? new Date().getMonth() + 1 : parseInt(_runqianCalendar.thisMonth);
    var e = _runqianCalendar.iframe.document.getElementById( "tmpMonthSelect" );
    e.parentElement.childNodes[0].innerText = "";
    e.style.display = ""; e.value = m;
}

function _prevHour(){
	  _runqianCalendar.thisDay = 1;
	  if (_runqianCalendar.thisHour==0){
	  	_runqianCalendar.thisHour=23;
	  	_runqianCalendar.hour=23;
	  }
	  else {
		  _runqianCalendar.thisHour--;
		  _runqianCalendar.hour--;
	  }
	  _writeCalendar();
}

function _nextHour(){
	  _runqianCalendar.thisDay = 1;
	  if (_runqianCalendar.thisHour==23){
	  	_runqianCalendar.thisHour=0;
	  	_runqianCalendar.hour=0;
	  }
	  else {
		  _runqianCalendar.thisHour++;
		  _runqianCalendar.hour++;
	  }
	  _writeCalendar();
}

function _prevMinutes(){
	  _runqianCalendar.thisDay = 1;
	  if (_runqianCalendar.thisMinutes==0){
	  	_runqianCalendar.thisMinutes=59;
	  	_runqianCalendar.minute=59;
	  }
	  else {
		  _runqianCalendar.thisMinutes--;
		  _runqianCalendar.minute--;
	  }
	  _writeCalendar();
}

function _nextMinutes(){
	  _runqianCalendar.thisDay = 1;
	  if (_runqianCalendar.thisMinutes==59){
	  	_runqianCalendar.thisMinutes=0;
	  	_runqianCalendar.minute=0;
	  }
	  else {
		  _runqianCalendar.thisMinutes++;
		  _runqianCalendar.minute++;
	  }
	  _writeCalendar();
}

function _prevSeconds(){
	  _runqianCalendar.thisDay = 1;
	  if (_runqianCalendar.thisSeconds==0){
	  	_runqianCalendar.thisSeconds=59;
	  	_runqianCalendar.hour=59;
	  }
	  else {
		  _runqianCalendar.thisSeconds--;
		  _runqianCalendar.hour--;
	  }
	  _writeCalendar();
}

function _nextSeconds(){
	  _runqianCalendar.thisDay = 1;
	  if (_runqianCalendar.thisSeconds==59){
	  	_runqianCalendar.thisSeconds=0;
	  	_runqianCalendar.second=0;
	  }
	  else {
		  _runqianCalendar.thisSeconds++;
		  _runqianCalendar.second++;
	  }
	  _writeCalendar();
}

function _prevMonth() {
    _runqianCalendar.thisDay = 1;
    if (_runqianCalendar.thisMonth==1)
    {
        _runqianCalendar.thisYear--;
        _runqianCalendar.thisMonth=13;
    }
    _runqianCalendar.thisMonth--; _writeCalendar();
}

function _nextMonth() {
    _runqianCalendar.thisDay = 1;
    if (_runqianCalendar.thisMonth==12)
    {
        _runqianCalendar.thisYear++;
        _runqianCalendar.thisMonth=0;
    }
    _runqianCalendar.thisMonth++; _writeCalendar();
}

function _prevYear(){
	_runqianCalendar.thisDay = 1;
	_runqianCalendar.thisYear--;
	_writeCalendar();
}

function _nextYear(){
	_runqianCalendar.thisDay = 1;
	_runqianCalendar.thisYear++;
	_writeCalendar();
}

function _hiddenSelect(e,reshow){
	e.style.display="none";
	if( reshow ) {
		_hiddenCalendar();
		var obj = _runqianCalendar.objExport;
    	if ( _runqianCalendar.type == 'date' || _runqianCalendar.type == 'time' ){
    		_runqianCalendar.iframe.show( 0, obj.offsetHeight, 228, 195, obj );
    	}
    	else{
            _runqianCalendar.iframe.show( 0, obj.offsetHeight, 228, 172, obj );
    	}
	}
	_writeCalendar();
}

function _hiddenCalendar(){
	_runqianCalendar.iframe.hide();
	var o = _runqianCalendar.objExport;
	if( o.tagName == "INPUT" && !o.readOnly ) {
		o.clicktime = 0;
	}
}

function _appendZero(n){
	if( n == "" ) return n;
	return(("00"+ n).substr(("00"+ n).length-2));
}

function _trimString( s ) {
	return s.replace(/(^\s*)|(\s*$)/g,"");
}

function _dayMouseOver() {
    this.className = "over";
    this.style.backgroundColor = _runqianCalendar.darkColor;
    if(_runqianCalendar.day[this.id.substr(10)].split("/")[1] == _runqianCalendar.thisMonth)
    this.style.color = _runqianCalendar.lightColor;
}

function _dayMouseOut() {
    this.className = "out"; var d = _runqianCalendar.day[this.id.substr(10)], a = d.split("/");
    this.style.removeAttribute('backgroundColor');
    if(a[1] == _runqianCalendar.thisMonth && d != _runqianCalendar.today)
    {
        this.style.color = _runqianCalendar.wordColor;
    }
}

function _writeCalendar() {
	var _rc = _runqianCalendar;
    var y = _rc.thisYear;
    var m = _rc.thisMonth;
    var d = _rc.thisDay;
	var h = _rc.thisHour;
	var mi= _rc.thisMinutes;
	var s = _rc.thisSeconds;
	var _doc = _rc.iframe.document;
    _doc.getElementById( "yearInput" ).value = y;
    var yue = parseInt( m, 10 );
    var yueDisp = "";
    switch( yue ) {
    	case 1: yueDisp = _Jan; break;
    	case 2: yueDisp = _Feb; break;
    	case 3: yueDisp = _Mar; break;
    	case 4: yueDisp = _Apr; break;
    	case 5: yueDisp = _May; break;
    	case 6: yueDisp = _Jun; break;
    	case 7: yueDisp = _Jul; break;
    	case 8: yueDisp = _Aug; break;
    	case 9: yueDisp = _Sept; break;
    	case 10: yueDisp = _Oct; break;
    	case 11: yueDisp = _Nov; break;
    	case 12: yueDisp = _Dec; break;
    }
    var yueSpan = _doc.getElementById( "runqianYearMonth" ).childNodes[0];
    yueSpan.innerText = yueDisp;
    _rc.daysMonth[1] = (0==y%4 && (y%100!=0 || y%400==0)) ? 29 : 28;
    if(_rc.type == 'date' || _rc.type == 'time'){
		_doc.getElementById( "hour" ).value = h;
		_doc.getElementById( "minute" ).value = mi;
		_doc.getElementById( "second" ).value = s;
    }
    var w = new Date(y, m-1, 1).getDay();
    var prevDays = m==1  ? _rc.daysMonth[11] : _rc.daysMonth[m-2];
    for( var i=(w-1); i>=0; i--) {
        _rc.day[i] = prevDays +"/"+ (parseInt(m, 10)-1) +"/"+ y +"/" +h +"/"+mi +"/"+s;
        if(m==1) _rc.day[i] = prevDays +"/"+ 12 +"/"+ (parseInt(y, 10)-1) +"/" +h +"/"+mi +"/"+s;
        prevDays--;
    }
    for(var i=1; i<=_rc.daysMonth[m-1]; i++) _rc.day[i+w-1] = i +"/"+ m +"/"+ y +"/" +h +"/"+mi +"/"+s;
    for(var i=1; i<38-w-_rc.daysMonth[m-1]+1; i++) {
        _rc.day[_rc.daysMonth[m-1]+w-1+i] = i +"/"+ (parseInt(m, 10)+1) +"/"+ y +"/" +h +"/"+mi +"/"+s;
        if(m==12) _rc.day[_rc.daysMonth[m-1]+w-1+i] = i +"/"+ 1 +"/"+ (parseInt(y, 10)+1) +"/" +h +"/"+mi +"/"+s;
    }
    for(var i=0; i<38; i++) {
        var a = _rc.day[i].split("/");
        _rc.dayObj[i].innerText    = a[0];
        _rc.dayObj[i].title        = _getDateWithFormat( _rc.dateFormat , a[2] , _appendZero(a[1]) , _appendZero(a[0]) , _appendZero(a[3]) , _appendZero(a[4]) , _appendZero(a[5]) );//a[2] +_runqianCalendar.separator+ _appendZero(a[1]) +_runqianCalendar.separator+ _appendZero(a[0])+" "+_appendZero(a[3])+":"+_appendZero(a[4])+":"+_appendZero(a[5]);
        _rc.dayObj[i].bgColor      = _rc.dayBgColor;
        _rc.dayObj[i].style.color  = _rc.wordColor;
        _rc.dayObj[i].className = "out";
    	_rc.dayObj[i].style.removeAttribute('backgroundColor');
        if ((i<10 && parseInt(_rc.day[i], 10)>20) || (i>27 && parseInt(_rc.day[i], 10)<12))
            _rc.dayObj[i].style.color = _rc.wordDark;
        if ( _rc.day[i].indexOf( _rc.inputDate + "/" ) == 0 ) {
        	_rc.dayObj[i].bgColor = _rc.darkColor;
        	_rc.dayObj[i].style.color = _rc.lightColor;
        }
        if (_rc.day[i].indexOf( _rc.today + "/" ) == 0 ) {
        	_rc.dayObj[i].bgColor = _rc.todayColor;
        	_rc.dayObj[i].style.color = _rc.lightColor;
        }
    }
}

function _returnDate() {
	var _rc = _runqianCalendar;
    if( _rc.objExport ) {
        var returnValue = "";
        //�������ʱ��IE9��ǰarguments����Ϊ0, IE9���䳤��Ϊ1������Ϊ����¼�
        var a = (arguments.length==0 || typeof(arguments[0]) != "string" ) ? _rc.day[this.id.substr(10)].split("/") : arguments[0].split("/");
        if (a == ",,"){
                returnValue = "";
        }
        if( _rc.type == "day"  && a !=",," ){
                   returnValue = _getDateWithFormat( _rc.dateFormat , a[2] , _appendZero(a[1]) , _appendZero(a[0]));
        }
        else if( _rc.type == "month"  && a !=",," ) {
        	   returnValue = _getDateWithFormat( _rc.dateFormat , a[2] , _appendZero(a[1]) );
        }
        else if( _rc.type == "year" ) returnValue = a[2];
        else if( _rc.type == "date" && a !=",,"){
        	   returnValue = _getDateWithFormat( _rc.dateFormat , a[2] , _appendZero(a[1]) , _appendZero(a[0]) , _appendZero(a[3]) , _appendZero(a[4]) , _appendZero(a[5]) );
        	}

        else if( _rc.type == "time" && a !=",,"){
        	   returnValue = _getDateWithFormat( _rc.dateFormat , a[2] , _appendZero(a[1]) , _appendZero(a[0]) ,_appendZero(a[3]) , _appendZero(a[4]) , _appendZero(a[5]) );
        	}

        var valueChanged = _rc.oldValue != returnValue;
       	_rc.objExport.setAttribute( "value", returnValue );
        if( _rc.objExport.tagName == "TD" ) {
        	_rc.objExport.innerText = returnValue;
			var table = _rc.objExport;
			while( table.tagName != "TABLE" ) table = table.parentElement;
        	var dataValid = _rc.objExport.getAttribute( "dataValid" );
        	if( dataValid != null && dataValid.length > 0 ) {
				if( table.getAttribute( "isli" ) ) {
					li_currTbl = table;
					li_currCell = _rc.objExport;
	        		if( ! eval( dataValid ) ) {
	        			_rc.objExport.setAttribute( "value", "" );
	        			_rc.objExport.innerText = "";
	        			return;
	        		}
				}
				else {
	        		if( ! eval( dataValid ) ) {
	        			_rc.objExport.setAttribute( "value", "" );
	        			_rc.objExport.innerText = "";
	        			return;
	        		}
	        	}
        	}
        	if( valueChanged ) {
				table.changed = true;
				if( !autoCalcOnlyOnSubmit ) {  //�Զ�����
					var cellName = _rc.objExport.id;
					var pos = cellName.lastIndexOf( "_" );
					var tableId = cellName.substring( 0, pos );
					cellName = cellName.substring( pos + 1 );
					if( !table.getAttribute( "isli" ) ) {
						eval( _getReportName( document.getElementById( tableId ) ) + "_autoCalc( '" + cellName + "' )" );
					}
				}
				if( table.getAttribute( "isli" ) ) {
					var editingRow = _rc.objExport.parentElement;
					if( editingRow.getAttribute( "status" ) == "0" ) editingRow.setAttribute( "status", "1" );
					else if( editingRow.getAttribute( "status" ) == "2" ) editingRow.setAttribute( "status", "3" );
					_calcTbl( table, _rc.objExport );
				}
				var filterCells = _rc.objExport.filterCells;
				if( filterCells != null ) {
					var fcs = filterCells.split( "," );
					for( var i = 0; i < fcs.length; i++ ) {
						var fc = document.getElementById( fcs[i] );
						var ds = eval( fc.getAttribute( "dataSet" ) );
						fc.setAttribute( "editConfig", ds.filter( fc.getAttribute( "filterExp" ) ) );
						fc.setAttribute( "value", "" );
						fc.innerText = "";
					}
				}
				try{ _cellValueChanged( _rc.objExport ); }catch(e){}
			}
        }
        else if( _rc.objExport.tagName == "INPUT" ) {
        	var dataValid = _rc.objExport.getAttribute( "dataValid" );
        	if( dataValid != null && dataValid.length > 0 ) {
        		if( ! eval( dataValid ) ) {
        			_rc.objExport.value = "";
        			return;
        		}
        	}
        	try {
				var form = _rc.objExport;
				while( form.tagName != "FORM" ) form = form.parentElement;
	        	eval( form.id + "_autoCalc()" );
        	}catch(e){}
    		try{ _rqBoxFilter( _rc.objExport.parentElement.childNodes[0] ); }catch(e2){}
    		if( valueChanged ) {
				try{ _cellValueChanged( _rc.objExport ); }catch(e){}
			}    			
        }
        _hiddenCalendar();
    }
}

function _changeYear( c ) {
	var obj = _runqianCalendar.iframe.document.getElementById( "yearInput" );
	if( c >= 96 && c <= 105 ){   //С����0-9
		c -= 96 - 48;
	}
	if( ( c >= 48 && c <= 57 ) ) {   //����
		if( _runqianCalendar.iframe.document.selection.type != "None" ) {
			obj.value = "";
			_runqianCalendar.iframe.document.selection.empty();
		}
		if( obj.value.length == 4 ) return;
		obj.value += String.fromCharCode(c);
	}else if( c == 8 ){   //Back
		if( _runqianCalendar.iframe.document.selection.type != "None" ) obj.value = "";
		else obj.value = obj.value.slice( 0, -1 );
	}else if( c == 46 ){   //Del
		if( _runqianCalendar.iframe.document.selection.type != "None" ) obj.value = "";
		else obj.value = obj.value.slice(1);
	}
	var y = obj.value;
	if( isNaN( y ) || obj.oldValue == y ) return;
	obj.oldValue = y;
	var value = 0;
	try { value = parseInt( y ); } catch( e ) {}
	if( value > 999 && value < 10000 ) {
		_runqianCalendar.thisYear = value;
		_writeCalendar();
	}
}

function _changeTime( c , _type){
  	var obj = _runqianCalendar.iframe.document.getElementById( _type );
		if( c >= 96 && c <= 105 ){   //С����0-9
			c -= 96 - 48;
		}
		if( ( c >= 48 && c <= 57 ) ) {   //����
			if( _runqianCalendar.iframe.document.selection.type != "None" ) {
				obj.value = "";
				_runqianCalendar.iframe.document.selection.empty();
			}
			if( obj.value.length == 2 ) return;
			obj.value += String.fromCharCode(c);
		}else if( c == 8 ){   //Back
			if( _runqianCalendar.iframe.document.selection.type != "None" ) obj.value = "";
			else obj.value = obj.value.slice( 0, -1 );
		}else if( c == 46 ){   //Del
			if( _runqianCalendar.iframe.document.selection.type != "None" ) obj.value = "";
			else obj.value = obj.value.slice(1);
		}
		var y = obj.value;
		if( isNaN( y ) || obj.oldValue == y ) return;
		obj.oldValue = y;
		var value = 0;
		try { value = parseInt( y ); } catch( e ) {}
		if (_type == 'hour'){
			if( value >= 0 && value <=23 ) {
				_runqianCalendar.thisHour = value;
				_writeCalendar();
			}
		}
		else if(_type == 'second') {
			if( value >= 0 && value <=59 ) {
				_runqianCalendar.thisSeconds = value;
				_writeCalendar();
			}
		}
		else if( _type == 'minute') {
			if( value >= 0 && value <=59 ) {
				_runqianCalendar.thisMinutes = value;
				_writeCalendar();
			}
		}
}

function trim(str) {
	str = str.replace(/^\s*(.*)/, "$1");
	str = str.replace(/(.*?)\s*$/, "$1");
	return str;
}


function _getDateWithFormat( fmt , y , M , d , H , m , s ){
		    if ( fmt .indexOf( 'yyyy' ) >= 0 ){
		    	fmt = fmt.replace( "yyyy" , y );
		    }
		    if ( fmt .indexOf( 'MM' ) >= 0 ){
		    	fmt = fmt.replace( "MM" , M );//��
		    }
		    if ( fmt .indexOf( 'dd' ) >= 0 ){
		    	fmt = fmt.replace( "dd" , d);//��
		    }
		    if ( fmt .indexOf( 'HH' ) >= 0 ){
		    	fmt = fmt.replace( "HH" , H );//Сʱ
		    }
		    if ( fmt .indexOf( 'mm' ) >= 0 ){
		    	fmt = fmt.replace( "mm" , m );//��
		    }
		    if ( fmt .indexOf( 'ss' ) >= 0 ){
		    	fmt = fmt.replace( "ss" , s );//��
		    }
		return fmt;
}
