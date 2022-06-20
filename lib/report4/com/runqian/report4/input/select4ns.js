var RQS_arrow = "▼";
var RQS_all = "全选";
var RQS_ok = "确定";

if( RQS_VAR == null ) {
	var RQS_VAR = new Object();
	RQS_VAR.DivDummy = document.createElement("DIV");
	RQS_VAR.SelectList = new Array();
	document.addEventListener( 'mousedown', RQS_blur, false );
}

function RQS_blur( event ) {
	var src = event.target;
	for( var i = 0; i < RQS_VAR.SelectList.length; i++ ) {
		var box = RQS_VAR.SelectList[i];
		if( src != box.editBox && src != box.Table.rows[0].cells[1] ) {
			box.acceptInput();
			if( box.bExpanded ) box.toggleOptions();
		}
	}
}

function RQSelectBox( parentObj, ListMax ) {
	this.ssID = RQS_VAR.SelectList.length;
	this.CR_Border = '#ff0000';
	this.width = 60;
	this.height = 22;
	this.OptionHeight = 20;
	this.ListMax = ( ! isNaN( parseInt( ListMax ) ) ) ? Math.abs( ListMax ) : 10;
	this.Table;
	this.editBox;
	this.OptionsDiv;
	this.OptionsTable;
	this.scrollDiv;
	this.buttonTable = null;
	this.bExpanded = false;
	this.isMulti = false;
	this.editable = false;
	this.newValue = false;
	this.onchange = null;
	this.currIndex = -1;
	this.selectedIndex = -1;
	this.buttonBkColor = "";
	this.buttonLightBorder = "";
	this.buttonDarkBorder = "";
	this.buttonMargin = 0;
	
	// private method
	this.toggleOptions = RQS_toggleOptions;
	this.handleOverTitle = RQS_handleOverTitle;
	this.handleOutTitle = RQS_handleOutTitle;
	this.createTable = RQS_createTable;
	this.createOptionsDiv = RQS_createOptionsDiv;
	this.pressOption = RQS_pressOption;
	this.editBoxMouseDown = RQS_editBoxMouseDown;
	this.editBoxKeyUp = RQS_editBoxKeyUp;
	this.editBoxKeyDown = RQS_editBoxKeyDown;
	this.arrowMouseDown = RQS_arrowMouseDown;
	this.selectAll = RQS_selectAll;
	this.multiSelectOk = RQS_multiSelectOk;
	this.acceptInput = RQS_acceptInput;
	this.lookupDisp = RQS_lookupDisp;
	this.turnOnOption = RQS_turnOnOption;
	this.turnOffOption = RQS_turnOffOption;
	
	// public method
	this.addOption = RQS_addOption;
	this.clearOptions = RQS_clearOptions;
	this.setHeight = RQS_setHeight;
	this.setWidth = RQS_setWidth;
	this.setLeft = RQS_setLeft;
	this.setTop = RQS_setTop;
	this.setFontFace = RQS_setFontFace;
	this.setFontColor = RQS_setFontColor;
	this.setFontSize = RQS_setFontSize;
	this.setFontWeight = RQS_setFontWeight;
	this.setFontItalic = RQS_setFontItalic;
	this.setBackColor = RQS_setBackColor;
	this.getValue = RQS_getValue;
	this.setValue = RQS_setValue;
	this.getDisp = RQS_getDisp;
	this.setMultipleSelect = RQS_setMultipleSelect;
	this.setEditable = RQS_setEditable;
	
	// initiate
	_initSelectJsVar();
	if( parentObj == null ) parentObj = document.body;
	this.createTable( parentObj );
	RQS_VAR.SelectList[ this.ssID ] = this;
}

function RQS_getValue() {
	return this.editBox.realValue;
}

function RQS_acceptInput() {
	if( !this.editable || !this.editBox.needAcceptInput ) return;
	for( var i = 0; i < this.OptionsTable.rows.length; i++ ) {
		var row = this.OptionsTable.rows[i];
		row.style.display = "";
	}
	if( !this.isMulti ) {
		var value = this.editBox.value;
		var found = false;
		for( var i = 0; i < this.OptionsTable.rows.length; i++ ) {
			var row = this.OptionsTable.rows[i];
			var cell = row.cells[0];
			if( value == cell.value || value == cell.childNodes[0].textContent ) {
				found = true;
				this.editBox.realValue = cell.value;
				this.editBox.value = cell.childNodes[0].textContent;
				this.Table.value = cell.value;
				this.selectedIndex = i;
				if( this.onchange != null ) eval( this.onchange );
				if( this.Table.onchange1 != null ) eval( this.Table.onchange1 );
				break;
			}
		}
		if( !found ) {
			if( !this.newValue ) {
				this.editBox.value = this.lookupDisp( this.editBox.realValue );
			}
			else {
				this.editBox.realValue = value;
				this.Table.value = value;
				if( this.onchange != null ) eval( this.onchange );
				if( this.Table.onchange1 != null ) eval( this.Table.onchange1 );
			}
		}
	}
	else {
		var value = this.editBox.value;
		var s = value.split( "," );
		var values = "";
		for( var i = 0; i < s.length; i++ ) {
			if( i > 0 ) values += ",";
			var avalue = s[i];
			for( var j = 0; j < this.OptionsTable.rows.length; j++ ) {
				var row = this.OptionsTable.rows[j];
				var cell = row.cells[1];
				if( cell.childNodes[0].textContent == avalue ) {
					avalue = cell.value;
					break;
				}
			}
			values += avalue;
		}
		this.setValue( values );
	}
	this.editBox.needAcceptInput = false;
}

function RQS_setValue( value ) {
	this.editBox.realValue = value;
	if( this.isMulti ) {
		var s = value.split( "," );
		if( s.length > 1 ) {
			var disp = "";
			for( var i = 0; i < s.length; i++ ) {
				if( i > 0 ) disp += ",";
				disp += this.lookupDisp( s[i] );
			}
			this.editBox.value = disp;
		}
		else this.editBox.value = this.lookupDisp( value );
		RQS_setChecked( this.OptionsTable, value );
	}
	else {
		this.editBox.value = this.lookupDisp( value );
	}
	this.Table.value = value;
	if( this.onchange != null ) eval( this.onchange );
	if( this.Table.onchange1 != null ) eval( this.Table.onchange1 );  //参数表单中动态过滤
}

function RQS_lookupDisp( value ) {
	for( var i = 0; i < this.OptionsTable.rows.length; i++ ) {
		var row = this.OptionsTable.rows[i];
		var cell = row.cells[0];
		if( this.isMulti ) cell = row.cells[1];
		if( cell.value == value ) {
			this.selectedIndex = i;
			return cell.childNodes[0].textContent;
		}
	}
	return value;
}

function RQS_setChecked( optionsTable, values ) {
	values = "," + values + ",";
	for( var i = 0; i < optionsTable.rows.length; i++ ) {
		var row = optionsTable.rows[i];
		var cell = row.cells[0];
		if( values.indexOf( "," + row.cells[1].value + "," ) >= 0 ) cell.childNodes[0].checked = true;
		else cell.childNodes[0].checked = false;
	}
}

function RQS_getDisp() {
	return this.editBox.value;
}

function RQS_setHeight( height ) {
	this.height = height;
	this.Table.style.height = height;
	this.editBox.style.height = height - this.buttonMargin * 2 - 1;
	this.editBox.style.lineHeight = ( height - 5 ) + "px";
}

function RQS_setWidth( width ) {
	this.width = width;
	this.Table.style.width = width;
	this.editBox.style.width = width - 17 < 0 ? 0: width - 17;
	this.OptionsTable.style.width = 10;
}

function RQS_setLeft( left ) {
	this.Table.style.left = left;
}

function RQS_setTop( top ) {
	this.Table.style.top = top;
}

function RQS_setFontFace( face ) {
	this.editBox.style.fontFamily = face;
	//this.OptionsTable.style.fontFamily = face;
}

function RQS_setFontColor( color ) {
	this.editBox.style.color = color;
	//this.OptionsTable.style.color = color;
}

function RQS_setFontSize( size ) {
	this.editBox.style.fontSize = size;
	//this.OptionsTable.style.fontSize = size;
}

function RQS_setFontWeight( weight ) {
	this.editBox.style.fontWeight = weight;
}

function RQS_setFontItalic( italic ) {
	this.editBox.style.fontStyle = italic;
}

function RQS_setBackColor( color ) {
	this.editBox.style.backgroundColor = color;
	//this.OptionsTable.style.backgroundColor = color;
}

function RQS_setMultipleSelect( b ) {
	this.isMulti = b;
	if( b ) {
		if( this.buttonTable != null ) this.buttonTable.style.display = "";
		else {
			var tblHtml = "<table border=0 bgcolor=LightBlue cellpadding=0 cellspacing=0 width=100% style='font-family:宋体;font-size:13px;'>" 
			+ "<tr height=22><td align=left nowrap>"
			+ "<input type=checkbox onclick='if( this.checked ) parent.RQS_VAR.SelectList[" + this.ssID + "].selectAll(true); else parent.RQS_VAR.SelectList[" + this.ssID + "].selectAll( false );'>"
			+ "<span style='color:blue'>" + RQS_all + "</span></td><td align=right nowrap style='padding-left:10px'>" 
			+ "<div onclick='parent.RQS_VAR.SelectList[" + this.ssID + "].multiSelectOk()' style='color:blue;cursor:pointer;padding-top:3px'>" + RQS_ok 
			+ "</div></td></tr></table>";
			this.buttonTable = RQS_createTmpObj( this.OptionsDiv.tmpDiv, tblHtml );
			this.OptionsDiv.document.body.appendChild( this.buttonTable );
		}
	}
	else {
		if( this.buttonTable != null ) this.buttonTable.style.display = "none";
	}
}

function RQS_setEditable( b ) {
	this.editable = b;
	this.editBox.readOnly = !b;
}

function RQS_addOption( value, innerText ) {
	var OptionTr = this.OptionsTable.insertRow( -1 );
	if( this.isMulti ) {
		var OptionCheck = OptionTr.insertCell( -1 ); //this.OptionsDiv.document.createElement( "<td>" );
		OptionCheck.style.width = 16;
		//OptionTr.appendChild( OptionCheck );
		OptionCheck.appendChild( RQS_createTmpObj( this.OptionsDiv.tmpDiv, "<input type=checkbox>" ) );
	}
	OptionTr.innerHTML += "<td height=" + this.OptionHeight + " style='cursor:pointer'"
		+ " onmouseover='parent.RQS_VAR.SelectList[" + this.ssID + "].turnOnOption(this.parentNode.rowIndex)'"
		//+ " onmouseout='parent.RQS_VAR.SelectList[" + this.ssID + "].turnOffOption(this.parentNode.rowIndex)'"
		+ " onmouseup='parent.RQS_VAR.SelectList[" + this.ssID + "].pressOption(this)'"
		+ " ondragstart='parent.RQS_cancelEvent(window.event)'"
		+ " onselectstart='return false;'"
		+ "><nobr></nobr></td>";
	var OptionTd = OptionTr.cells[ OptionTr.cells.length - 1 ];
	OptionTd.value = value;
	OptionTd.childNodes[0].textContent = innerText;
	//OptionTr.appendChild( OptionTd );
}

function RQS_clearOptions() {
	for( var i = this.OptionsTable.rows.length - 1; i >= 0; i-- ) {
		this.OptionsTable.deleteRow( i );
	}
}

function RQS_cancelEvent( event ) {
	event.cancelBubble = true;
	event.returnValue = false;
}

function RQS_toggleOptions( bExpanded ) {
	this.bExpanded = ( 'undefined' != typeof( bExpanded ) ) ? bExpanded: ( !this.bExpanded );
	if( this.bExpanded ) {
		var w = this.width;
		var h = Math.min( this.OptionsTable.rows.length, this.ListMax ) * this.OptionHeight + 1;
		this.OptionsDiv.selfObj.style.width = w - 16;
		this.OptionsDiv.selfObj.style.display = "";
		var w1 = this.OptionsTable.offsetWidth;
		var rows = 0;
		for( var i = 0; i < this.OptionsTable.rows.length; i++ ) {
			if( this.OptionsTable.rows[i].style.display != "none" ) rows++;
			if( i == this.selectedIndex ) this.turnOnOption( i );
			else this.turnOffOption( i );
		}
		if( rows > this.ListMax ) w1 += 17;
		if( this.isMulti ) {
			if( w1 < this.buttonTable.offsetWidth ) w1 = this.buttonTable.offsetWidth;
		}
		if( w1 > w ) w = w1;
		this.scrollDiv.style.width = w - 2;
		this.scrollDiv.style.height = h;
		if( this.isMulti ) h += 22;
		if( this.Table.style.position == "relative" ) {
			var x = this.Table.offsetLeft, y = this.Table.offsetTop;
			var obj = this.Table.offsetParent;
			var offsetP = this.OptionsDiv.selfObj.offsetParent;
			while( obj != null && obj != offsetP ) {
				x += obj.offsetLeft;// + obj.clientLeft;
				y += obj.offsetTop;// + obj.clientTop;
				obj = obj.offsetParent;
			}
			this.OptionsDiv.selfObj.style.left = x;
			this.OptionsDiv.selfObj.style.top = y + this.Table.offsetHeight;
		}
		else {
			this.OptionsDiv.selfObj.style.left = this.Table.style.left;
			this.OptionsDiv.selfObj.style.top = parseInt( this.Table.style.top ) + this.Table.offsetHeight;
		}
		this.OptionsDiv.selfObj.style.width = w;
		this.OptionsDiv.selfObj.style.height = h + 2;
		if( rows > this.ListMax ) this.OptionsTable.style.width = w - 17;
		else this.OptionsTable.style.width = w;
		var selectedRow = null;
		try{ selectedRow = this.OptionsTable.rows[ this.selectedIndex ]; }catch( e2 ){}
		//if( selectedRow != null ) selectedRow.scrollIntoView();
		this.arrow.style.border = "1px solid " + this.arrow.style.borderRightColor;
	}
	else {
		this.OptionsDiv.selfObj.style.display = "none";
		this.arrow.style.borderLeft = this.buttonLightBorder;
		this.arrow.style.borderRight = this.buttonDarkBorder;
		this.arrow.style.borderTop = this.buttonLightBorder;
		this.arrow.style.borderBottom = this.buttonDarkBorder;
	}
}

function RQS_handleOverTitle() {
}

function RQS_handleOutTitle() {
}

function RQS_turnOnOption( index ) {
	if( index != this.currIndex ) this.turnOffOption( this.currIndex );
	var row = null;
	try{ row = this.OptionsTable.rows[ index ]; }catch(e1){}
	if( row == null ) return;
	var cell = row.cells[0];
	if( this.isMulti ) cell = row.cells[1];
	cell.style.color = "white";
	try {
		cell.style.color = _ddboxSelectedItemColor;
	}catch(ex){}
	cell.style.backgroundColor = "darkblue";
	try {
		cell.style.backgroundColor = _ddboxSelectedItemBackColor;
	}catch(ex){}
	this.currIndex = index;
}

function RQS_turnOffOption( index ) {
	var row = null;
	try{ row = this.OptionsTable.rows[ index ]; }catch(e1){}
	if( row == null ) return;
	var cell = row.cells[0];
	if( this.isMulti ) cell = row.cells[1];
	cell.style.color = '';
	cell.style.backgroundColor = '';
}

function RQS_pressOption( cell ) {
	if( this.isMulti ) {
		var box = cell.parentNode.cells[0].childNodes[0];
		box.checked = !box.checked;
		return;
	}
	this.toggleOptions( false );
	this.editBox.realValue = cell.value;
	this.editBox.needAcceptInput = false;
	this.Table.value = cell.value;      //Table.value是为参数表单中动态过滤用的
	this.editBox.value = cell.childNodes[0].textContent;
	this.selectedIndex = cell.parentNode.rowIndex;
	try{ 
		var s = this.editingCell.getAttribute( "onSelectChange" );
		var pos = s.indexOf( "(" );
		if( pos > 0 ) s = s.substring( 0, pos );
		eval( s + "('" + cell.value + "')" );
	}catch(ex){}
	if( this.onchange != null ) eval( this.onchange );
	if( this.Table.onchange1 != null ) eval( this.Table.onchange1 );  //参数表单中动态过滤
}

function RQS_editBoxMouseDown() {
	if( this.editable ) return;
	this.toggleOptions( !this.bExpanded );
}

function RQS_arrowMouseDown() {
	this.toggleOptions( !this.bExpanded );
}

function RQS_selectAll( b ) {
	for( var i = 0; i < this.OptionsTable.rows.length; i++ ) {
		if( i == 0 && b ) {
			var cell = this.OptionsTable.rows[0].cells[1];
			if( cell.value == "" ) this.OptionsTable.rows[0].cells[0].childNodes[0].checked = false;
		}
		this.OptionsTable.rows[i].cells[0].childNodes[0].checked = b;
	}
}

function RQS_multiSelectOk() {
	var value = "", disp = "";
	for( var i = 0; i < this.OptionsTable.rows.length; i++ ) {
		var row = this.OptionsTable.rows[i];
		if( row.style.display == "none" ) continue;
		if( row.cells[0].childNodes[0].checked ) {
			if( value.length > 0 ) { value += ","; disp += ","; }
			value += row.cells[1].value;
			disp += row.cells[1].childNodes[0].textContent;
			this.selectedIndex = i;
		}
	}
	this.editBox.value = disp;
	this.editBox.realValue = value;
	this.Table.value = value;
	if( this.onchange != null ) eval( this.onchange );
	if( this.Table.onchange1 != null ) eval( this.Table.onchange1 );
	this.toggleOptions( false );
}

function RQS_editBoxKeyDown( event ) {
	var keyCode = event.keyCode;
	if( keyCode == 38 ) {
		if( !this.bExpanded ) this.toggleOptions( true );
		for( var i = this.currIndex - 1; i >= 0; i-- ) {
			var row = this.OptionsTable.rows[i];
			if( row == null ) return;
			if( row.style.display != "none" ) {
				this.turnOnOption( i );
				row.scrollIntoView();
				return;
			}
		}
		return;
	}
	else if( keyCode == 40 ) {
		if( !this.bExpanded ) this.toggleOptions( true );
		for( var i = this.currIndex + 1; i < this.OptionsTable.rows.length; i++ ) {
			var row = this.OptionsTable.rows[i];
			if( row == null ) return;
			if( row.style.display != "none" ) {
				this.turnOnOption( i );
				row.scrollIntoView();
				return;
			}
		}
		return;
	}
	if( keyCode == 9 ) {    //tab
		flag = event.shiftKey ? 1 : 3;
		try{
			var cell = _lookupNextCell( this.editingCell, flag );
			if( cell != null ) {
				this.toggleOptions( false );
				_bindingEditor( cell );
			}
		}catch(ex){}
		if(event.preventDefault) event.preventDefault();             
		event.cancelBubble = true;
		event.returnValue = false;
		return false;
	}
	if( !this.editable ) {
		event.cancelBubble = true;
		event.returnValue = false;
		return false;
	}
	this.editBox.prevValue = this.editBox.value;
}

function RQS_editBoxKeyUp( event ) {
	var keyCode = event.keyCode;
	if( keyCode == 9 ) return false;  //tab
	if( keyCode == 38 || keyCode == 40 || keyCode == 37 || keyCode == 39 ) {
		if( event.ctrlKey ) {
			var flag = -1;
			if( keyCode == 37 ) flag = 1;
			if( keyCode == 38 ) flag = 2;
			if( keyCode == 39 ) flag = 3;
			if( keyCode == 40 ) flag = 4;
			try{
				var cell = _lookupNextCell( this.editingCell, flag );
				if( cell != null ) {
					this.toggleOptions( false );
					_bindingEditor( cell );
				}
			}catch(ex){}
		}
		return;
	}
	if( keyCode == 13 ) {
		if( this.currIndex >= 0 ) {
			var row = this.OptionsTable.rows[ this.currIndex ];
			var cell = row.cells[0];
			if( this.isMulti ) cell = row.cells[1];
			this.pressOption( cell );
		}
		else this.acceptInput();
		try{
			var cell = _lookupNextCell( this.editingCell, 3 );
			if( cell != null ) {
				this.toggleOptions( false );
				_bindingEditor( cell );
			}
		}catch(ex){}
		return;
	}
	if( !this.editable ) {
		event.cancelBubble = true;
		event.returnValue = false;
		return false;
	}
	if( this.OptionsDiv.selfObj.style.display == "none" ) this.toggleOptions( true );
	var value = this.editBox.value;
	if( value == this.editBox.prevValue ) return;
	this.editBox.needAcceptInput = true;
	var values = value.split( "," );
	for( var i = 0; i < this.OptionsTable.rows.length; i++ ) {
		var row = this.OptionsTable.rows[i];
		var cell = row.cells[0];
		if( this.isMulti ) cell = row.cells[1];
		var code = cell.value;
		var disp = cell.childNodes[0].textContent;
		var found = false, checked = false;
		if( value == "" ) found = true;
		else {
			for( var k = 0; k < values.length; k++ ) {
				if( disp.toUpperCase().indexOf( values[k].toUpperCase() ) >= 0 && values[k] != "" ) {
					found = true;
					break;
				}
			}
			if( this.isMulti ) {
				for( var k = 0; k < values.length; k++ ) {
					if( disp.toUpperCase() == values[k].toUpperCase() || code.toUpperCase() == values[k].toUpperCase() ) {
						checked = true;
						break;
					}
				}
			}
		}
		if( found )	row.style.display = "";
		else {
			row.style.display = "none";
			if( i == this.currIndex ) this.currIndex = -1;
		}
		if( this.isMulti ) {
			row.cells[0].childNodes[0].checked = checked;
		}
	}
}

function RQS_createTable( parentObj ) {
	var border = "";
	try {
		border += "border-left:" + _ddboxBorderLeft;
		border += ";border-right:" + _ddboxBorderRight;
		border += ";border-top:" + _ddboxBorderTop;
		border += ";border-bottom:" + _ddboxBorderBottom;
	}catch( e1 ) {
		border = "";
		try {
			border += "border-left:" + _editorBorderLeft;
			border += ";border-right:" + _editorBorderRight;
			border += ";border-top:" + _editorBorderTop;
			border += ";border-bottom:" + _editorBorderBottom;
		}catch( e ) {
			border = "border: 1px solid " + this.CR_Border;
		}
	}
	try {
		this.buttonBkColor = _ddboxBackColor;
	}catch( e ) {
		this.buttonBkColor = "lightgrey";
	}
	try {
		this.buttonMargin = _ddboxButtonMargin;
	}catch( e ) {
		this.buttonMargin = 0;
	}
	try {
		this.buttonLightBorder = _ddboxLightBorder;
		this.buttonDarkBorder = _ddboxDarkBorder;
	}catch( e ) {
		this.buttonLightBorder = "thin solid #f9f9f9";
		this.buttonDarkBorder = "thin solid #555555";
	}
	var btnBorder = "border-left:" + this.buttonLightBorder
					+ ";border-right:" + this.buttonDarkBorder
					+ ";border-top:" + this.buttonLightBorder
					+ ";border-bottom:" + this.buttonDarkBorder;
	var html = "<table id=my___select border=0 cellpadding=0 cellspacing=" + this.buttonMargin + " style='position:absolute;cursor:default;display:none;z-index:5;"
		+ "table-layout:fixed; " + border + ";font-family:宋体;font-size:13px;'"
		+ " onmouseover='RQS_VAR.SelectList[" + this.ssID + "].handleOverTitle()'"
		+ " onmouseout='RQS_VAR.SelectList["+this.ssID+"].handleOutTitle()'"
		+ " bgcolor=white>"
		+ " <tr>"
		+ "  <td style='boder-right:#555555 0.07mm solid'><input type=text readonly style='border:none'"
		+ " onmousedown='RQS_VAR.SelectList[" + this.ssID + "].editBoxMouseDown()'"
		+ " onkeyup='RQS_VAR.SelectList[" + this.ssID + "].editBoxKeyUp(event)'"
		+ " onkeydown='RQS_VAR.SelectList[" + this.ssID + "].editBoxKeyDown(event)'"
		+ "></td>"
		+ " <td width=17 align=center style='FONT-FAMILY: 宋体; FONT-SIZE: 9px; color:black;word-wrap:normal;background-color:" + this.buttonBkColor + ";" + btnBorder + "'"
		+ " onmousedown='RQS_VAR.SelectList[" + this.ssID + "].arrowMouseDown();' onselectstart='return false;'"
		+ " onmouseup='RQS_VAR.SelectList[" + this.ssID + "].editBox.focus();'"
		//+ " onmouseout='RQS_VAR.SelectList[" + this.ssID + "].arrowMouseOut();'"
		//+ " onmouseover='RQS_VAR.SelectList[" + this.ssID + "].arrowMouseOver();'"
		+ ">" + RQS_arrow + "</td>"
		+ " </tr>"
		+ "</table>";
	RQS_VAR.DivDummy.innerHTML = html;
	this.Table = RQS_VAR.DivDummy.childNodes[0];
	parentObj.appendChild( this.Table );
	this.Table.ddbox = this;
	if ( !isNaN( this.width ) )	this.Table.style.width = this.width;
	this.Table.style.height = this.height;
	this.editBox = this.Table.rows[0].cells[0].childNodes[0];
	this.arrow = this.Table.rows[0].cells[1];
	this.createOptionsDiv( parentObj );
}

function RQS_createOptionsDiv ( parentObj ) {
	RQS_VAR.DivDummy.innerHTML = "<iframe name=odiv_" + this.ssID + " id=odiv_" + this.ssID + " scrolling=no frameborder=0 width=100 height=100 style='position:absolute;display:none;z-index:9999'></iframe>";
	parentObj.appendChild( RQS_VAR.DivDummy.childNodes[0] );
	this.OptionsDiv = window.frames[ "odiv_" + this.ssID ];
	this.OptionsDiv.selfObj = document.getElementById( "odiv_" + this.ssID );
	this.OptionsDiv.document.writeln( "<html><body bgcolor=white style='border:solid 1px black;overflow:hidden;margin:0'><div width=100% style='overflow-x:hidden; overflow-y:auto'><table border=0 cellpadding=0 cellspacing=0 width=100% style='font-family:宋体;font-size:13px;'></table></div><div></div></body></html>" );
	this.OptionsDiv.document.close();
	this.scrollDiv = this.OptionsDiv.document.body.childNodes[0];
	this.OptionsTable = this.scrollDiv.childNodes[0];
	this.OptionsDiv.tmpDiv = this.OptionsDiv.document.body.childNodes[1];
}

function RQS_createTmpObj( div, html ) {
	div.innerHTML = html;
	var obj = div.childNodes[0];
	return obj;
}