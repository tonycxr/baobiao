var RQS_arrow = "▼";
var RQS_all = "全选";
var RQS_ok = "确定";

if( RQS_VAR == null ) {
	var RQS_VAR = new Object();
	RQS_VAR.SelectList = new Array();
	document.attachEvent( 'onmousedown', RQS_blur );
}

function RQS_blur() {
	var src = event.srcElement;
	for( var i = 0; i < RQS_VAR.SelectList.length; i++ ) {
		var box = RQS_VAR.SelectList[i];
		if( src != box.editBox && src != box.Table.cells[1] ) {
			box.acceptInput();
			if( box.bExpanded ) box.toggleOptions();
		}
	}
}

function RQSelectBox( ListMax ) {
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
	this.createTable();
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
			if( value == cell.value || value == cell.childNodes[0].innerText ) {
				found = true;
				this.editBox.realValue = cell.value;
				this.editBox.value = cell.childNodes[0].innerText;
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
				if( cell.childNodes[0].innerText == avalue ) {
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
			return cell.childNodes[0].innerText;
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
	this.Table.style.height = height + "px";
	this.editBox.style.height = ( height - this.buttonMargin * 2 - 1 ) + "px";
	this.editBox.style.lineHeight = ( height - 5 ) + "px";
}

function RQS_setWidth( width ) {
	this.width = width;
	this.Table.style.width = width + "px";
	this.editBox.style.width = ( width - 22 < 0 ? 0: width - 22 ) + "px";
	this.OptionsTable.style.width = "10px";
}

function RQS_setLeft( left ) {
	this.Table.style.left = left + "px";
}

function RQS_setTop( top ) {
	this.Table.style.top = top + "px";
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
			this.buttonTable = RQS_createTmpObj( this.OptionsDiv.document.body.childNodes[1], tblHtml );
			this.OptionsDiv.document.body.appendChild( this.buttonTable );
/*
			this.buttonTable = this.OptionsDiv.document.createElement( "<table border=0 bgcolor=LightBlue cellpadding=0 cellspacing=0 width=100% style='font-family:宋体;font-size:13px;'></table>" );
			this.OptionsDiv.document.body.appendChild( this.buttonTable );
			var buttonTr = this.buttonTable.insertRow(0);
			buttonTr.style.height = "22px";
			var cell1 = this.OptionsDiv.document.createElement( "<td align=left nowrap></td>" );
			var box = this.OptionsDiv.document.createElement( "<input type=checkbox onclick='if( this.checked ) parent.RQS_VAR.SelectList[" + this.ssID + "].selectAll(true); else parent.RQS_VAR.SelectList[" + this.ssID + "].selectAll( false );'>" );
			buttonTr.appendChild( cell1 );
			cell1.appendChild( box );
			var text1 = this.OptionsDiv.document.createElement( "<span></span>" );
			text1.innerText = RQS_all;
			text1.style.color = "blue";
			cell1.appendChild( text1 );
			var cell2 = this.OptionsDiv.document.createElement( "<td align=right nowrap style='padding-left:10px'></td>" );
			buttonTr.appendChild( cell2 );
			var text2 = this.OptionsDiv.document.createElement( "<div onclick='parent.RQS_VAR.SelectList[" + this.ssID + "].multiSelectOk()' style='cursor:hand;padding-top:3px'></div>" );
			text2.innerText = RQS_ok;
			text2.style.color = "blue";
			cell2.appendChild( text2 );*/
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
		OptionCheck.style.width = "16px";
		//OptionTr.appendChild( OptionCheck );
		OptionCheck.appendChild( RQS_createTmpObj( this.OptionsDiv.document.body.childNodes[1], "<input type=checkbox>" ) );
	}
	var OptionTd = OptionTr.insertCell();
	/*OptionTd.innerHTML =  "<td height=" + this.OptionHeight + " style='cursor:hand'"
		+ " onmouseover='parent.RQS_VAR.SelectList[" + this.ssID + "].turnOnOption(this.parentElement.rowIndex)'"
		//+ " onmouseout='parent.RQS_VAR.SelectList[" + this.ssID + "].turnOffOption(this.parentElement.rowIndex)'"
		+ " onmouseup='parent.RQS_VAR.SelectList[" + this.ssID + "].pressOption(this)'"
		+ " ondragstart='parent.RQS_cancelEvent(window.event)'"
		+ " onselectstart='return false;'"
		+ "><nobr></nobr></td>";*/
	OptionTd.style.height = this.OptionHeight + "px";
	OptionTd.style.cursor = "pointer";
	OptionTd.box = this;
	OptionTd.onmouseover = function(){ this.box.turnOnOption(this.parentElement.rowIndex);};
	OptionTd.onmouseup = function(){ this.box.pressOption(this);};
	OptionTd.ondragstart = function(event){ parent.RQS_cancelEvent(event);};
	OptionTd.onselectstart = function(){ return false; };
	OptionTd.innerHTML = "<nobr></nobr>";
	OptionTd.value = value;
	OptionTd.childNodes[0].innerText = innerText;
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
		this.OptionsDiv.show( 0, this.height, w - 16, h, this.Table );
		var w1 = this.OptionsTable.offsetWidth;
		var rows = 0;
		for( var i = 0; i < this.OptionsTable.rows.length; i++ ) {
			if( this.OptionsTable.rows[i].style.display != "none" ) rows++;
			if( i == this.selectedIndex ) this.turnOnOption( i );
			else this.turnOffOption( i );
		}
		if( rows > this.ListMax ) w1 += 16;
		if( this.isMulti ) {
			if( w1 < this.buttonTable.offsetWidth ) w1 = this.buttonTable.offsetWidth;
		}
		if( w1 > w ) w = w1;
		this.scrollDiv.style.width = w - 1 + "px";
		this.scrollDiv.style.height = h + "px";
		if( this.isMulti ) h += 22;
		this.OptionsDiv.show( 0, this.height, w, h + 2, this.Table );
		if( rows > this.ListMax ) this.OptionsTable.style.width = w - 16 + "px";
		else this.OptionsTable.style.width = w + "px";
		var selectedRow = this.OptionsTable.rows[ this.selectedIndex ]
		if( selectedRow != null ) selectedRow.scrollIntoView();
		this.arrow.style.border = "1px solid " + this.arrow.style.borderRightColor;
	}
	else {
		this.OptionsDiv.hide();
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
	var row = this.OptionsTable.rows[ index ];
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
	var row = this.OptionsTable.rows[ index ];
	if( row == null ) return;
	var cell = row.cells[0];
	if( this.isMulti ) cell = row.cells[1];
	cell.style.color = '';
	cell.style.backgroundColor = '';
}

function RQS_pressOption( cell ) {
	if( this.isMulti ) {
		var box = cell.parentElement.cells[0].childNodes[0];
		box.checked = !box.checked;
		return;
	}
	this.toggleOptions( false );
	this.editBox.realValue = cell.value;
	this.editBox.needAcceptInput = false;
	this.Table.value = cell.value;      //Table.value是为参数表单中动态过滤用的
	this.editBox.value = cell.childNodes[0].innerText;
	this.selectedIndex = cell.parentElement.rowIndex;
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
			disp += row.cells[1].childNodes[0].innerText;
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

function RQS_editBoxKeyDown() {
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
		try {
			var cell = _lookupNextCell( this.editingCell, flag );
			if( cell != null ) {
				this.toggleOptions( false );
				_bindingEditor( cell );
			}
		}catch(ex){}
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

function RQS_editBoxKeyUp() {
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
	if( !this.OptionsDiv.isOpen ) this.toggleOptions( true );
	if( this.editable ) {
		var c1 = RQS_getScreenCoord( document, this.Table );
		var c2 = RQS_getScreenCoord( this.OptionsDiv.document, this.OptionsDiv.document.body );
		if( c2[1] < c1[1]+this.Table.offsetHeight-10 && c2[0] < c1[0]+this.Table.offsetWidth-30 ) {
			this.toggleOptions( false );
		}
	}
	var value = this.editBox.value;
	if( value == this.editBox.prevValue ) return;
	this.editBox.needAcceptInput = true;
	var values = value.split( "," );
	for( var i = 0; i < this.OptionsTable.rows.length; i++ ) {
		var row = this.OptionsTable.rows[i];
		var cell = row.cells[0];
		if( this.isMulti ) cell = row.cells[1];
		var code = cell.value;
		var disp = cell.childNodes[0].innerText;
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

function RQS_createTable() {
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
		+ " onkeyup='RQS_VAR.SelectList[" + this.ssID + "].editBoxKeyUp()'"
		+ " onkeydown='RQS_VAR.SelectList[" + this.ssID + "].editBoxKeyDown()'"
		+ "></td>"
		+ " <td width=16 align=center style='width:16px;padding:0px;FONT-FAMILY: 宋体; FONT-SIZE: 9px; color:black;word-wrap:normal;z-index:99;background-color:" + this.buttonBkColor + ";" + btnBorder + "'"
		+ " onmousedown='RQS_VAR.SelectList[" + this.ssID + "].arrowMouseDown();' onselectstart='return false;'"
		+ " onmouseup='RQS_VAR.SelectList[" + this.ssID + "].editBox.focus();'"
		//+ " onmouseout='RQS_VAR.SelectList[" + this.ssID + "].arrowMouseOut();'"
		//+ " onmouseover='RQS_VAR.SelectList[" + this.ssID + "].arrowMouseOver();'"
		+ ">" + RQS_arrow + "</td>"
		+ " </tr>"
		+ "</table>";
	document.body.insertAdjacentHTML( "AfterBegin", html );
	this.Table = document.body.childNodes[0];
	this.Table.ddbox = this;
	if ( !isNaN( this.width ) )	this.Table.style.width = this.width + "px";
	this.Table.style.height = this.height + "px";
	this.editBox = this.Table.cells[0].childNodes[0];
	this.arrow = this.Table.cells[1];
	this.createOptionsDiv();
}

function RQS_createOptionsDiv () {
	this.OptionsDiv = window.createPopup();
	var oPopBody = this.OptionsDiv.document.body;
	//this.OptionsDiv.document.writeln( "<html><body style='border:solid 1px black;overflow:hidden;margin:0'><div width=100% style='overflow-x:hidden; overflow-y:auto'><table border=0 cellpadding=0 cellspacing=0 width=100% style='font-family:宋体;font-size:13px;'></table></div></body></html>" );
	oPopBody.innerHTML = "<div width=100% style='overflow-x:hidden; overflow-y:auto'><table border=0 cellpadding=0 cellspacing=0 width=100% style='font-family:宋体;font-size:13px;'></table></div><div style='display:none'></div>";
	oPopBody.style.border = "solid 1px black";
	oPopBody.style.overflowX = "hidden";
	oPopBody.style.overflowY = "hidden";
	oPopBody.style.margin = "0";
	/*oPopBody.onkeyup = aaa;
	this.scrollDiv = this.OptionsDiv.document.createElement( "<div width=100% style='overflow-x:hidden; overflow-y:auto'></div>" );
	oPopBody.appendChild( this.scrollDiv );
	this.OptionsTable = this.OptionsDiv.document.createElement( "<table border=0 cellpadding=0 cellspacing=0 width=100% style='font-family:宋体;font-size:13px;'></table>" );
	this.scrollDiv.appendChild( this.OptionsTable );*/
	this.scrollDiv = this.OptionsDiv.document.body.childNodes[0];
	this.OptionsTable = this.scrollDiv.childNodes[0];
}

function RQS_getScreenCoord( doc, obj ) {
	var x = obj.offsetLeft, y = obj.offsetTop;
	var o = obj.offsetParent;
	while( o != null ) {
		x += o.offsetLeft + o.clientLeft - o.scrollLeft;
		y += o.offsetTop + o.clientTop - o.scrollTop;
		o = o.offsetParent;
	}
	x += doc.parentWindow.screenLeft;
	y += doc.parentWindow.screenTop;
	var coord = new Array();
	coord[0] = x;
	coord[1] = y;
	return coord;
}

function RQS_createTmpObj( div, html ) {
	div.innerHTML = html;
	var obj = div.childNodes[0];
	return obj;
}