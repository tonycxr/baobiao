var autoCalcOnlyOnSubmit = false;
var checkDataTypeOnSubmit = false;
// BEGIN
var __AA = "��";
var __BB = "����";
var __DD = "��Ԫ��";
var __EE = "��������ֵ��";
var __CC = "��������ֵ��";
var __FF = "������Ч�����ڣ�";
var __GG = "����ȫ����������ɵ��ַ�����";
var __HH = "������ȷ��Email��ַ��";
var __II = "���ݲ�������Ч��Ҫ��";
var __JJ = "�ؼ��ֶ�ȡֵ��Ԫ��";
var __KK = "�����ڣ������������";
var __LL = "�ؼ��ֶε�Ԫ��";
var __MM = "δ����ֵ��";
var __NN = "�ֶ�ȡֵ��Ԫ��";
var __OO = "�����ڣ������������";
var __PP = "˫�����ػ������ļ�";
var __QQ = "switchCase��������̫�٣�����Ӧ��4����������ȷ��ʽΪ��\n\nswitchCase( ��������, ֵ1, ����ֵ1, [ֵ2, ����ֵ2, ......]ȱʡ����ֵ )";
var __RR = "switchCase����������������ӦΪ˫������ȷ��ʽΪ��\n\nswitchCase( ��������, ֵ1, ����ֵ1, [ֵ2, ����ֵ2, ......]ȱʡ����ֵ )";
var __SS = "�����ļ�·��";
var __TT = "�޷������ļ�";
var __UU = "���ڿ�ѡ���У���������";


// END

function _initInput( table ) {
	table.currEditor = null;
	document.body.onkeydown = function(event){ if( event.keyCode == 8 && !( event.target.tagName == "INPUT" && ( event.target.type == "text" || event.target.type == "file" || event.target.type == "password" ) ) && event.target.tagName != "TEXTAREA" ) return false;};
        _initInputVar();//init
}

function _getReportName( table ) {
	var pos = table.id.indexOf( "_$_" );
	if( pos < 0 ) return table.id;
	else return table.id.substring( 0, pos );
}

function _clearValueFormat( cell, value ) {
	if( cell.getAttribute( "digits" ) != null ) {
		var i = 0;
		while( i < value.length ) {
			var ch = value.charAt( i );
			if( ch == "," || ch == "$" || ch == "%" || ch == "��" ) {
				value = value.substring( 0, i ) + value.substring( i + 1 );
				if( ch == "%" ) {
					value = value / 100.0;
					value = parseFloat( value.toFixed( parseInt( cell.getAttribute( "digits" ) ) ) ) + "";
				}
			}
			else i++;
		}
	}
	return value;
}

function _submitEditor2( table ) {
	var editor = table.currEditor;
	var valueChanged = true;
	if( editor != null && editor.editingCell != null ) {
		var disp = editor.value;
		var value = editor.value;
		var editStyle = editor.editingCell.getAttribute( "editStyle" );
		switch( editStyle ) {
			case "1":
				value = _clearValueFormat( editor.editingCell, value );
				var inputDataType = editor.editingCell.getAttribute( "inputDataType" );
				if( ! _checkDataType( inputDataType, value, "" ) ) {
					editor.focus();
					if( table.getAttribute( "selectText" ) ) editor.select();
					return false;
				}
				if( value == editor.oldValue ) {
					value = editor.editingCell.getAttribute( "value" );
					valueChanged = false;
				}
				editor.style.display = "none";
				break;
			case "2":
			case "3":
				value = editor.getValue();
				disp = editor.getDisp();
				valueChanged = value != editor.editingCell.getAttribute( "value" );
				editor.Table.style.display = "none";
				break;
			case "9":
				disp = "";
				for( var k = 0; k < value.length; k++ ) disp += "*";
				editor.style.display = "none";
				break;
			case "0":
				if( value == editor.oldValue ) {
					value = editor.editingCell.getAttribute( "value" );
					valueChanged = false;
				}
				editor.style.display = "none";
				break;
			default:
				editor.style.display = "none";
		}
		try{ if( all2Half ) {   //����ʾֵ�е�ȫ�ǿո��ɰ��
			var re = /��/g;
			disp = disp.replace( re, "  " );
		}}catch(ex){}
		if( valueChanged ) {
			editor.editingCell.textContent = disp;
			editor.editingCell.setAttribute( "value", value );
		}
		if( editStyle == "1" || editStyle == "2" || editStyle == "3" || editStyle == "0" || editStyle == "9" ) {
			var dataValid = editor.editingCell.getAttribute( "dataValid" );
			if( valueChanged && dataValid != null && dataValid.length > 0 ) {
				if( table.getAttribute( "isli" ) ) {
					li_currTbl = table;
					li_currCell = editor.editingCell;
				}
				if( !eval( dataValid ) ) {
					editor.oldValue = value;
					if( table.getAttribute( "isli" ) ) {
						var editingRow = editor.editingCell.parentNode;
						if( editingRow.getAttribute( "status" ) == "0" ) editingRow.setAttribute( "status", "1" );
						else if( editingRow.getAttribute( "status" ) == "2" ) editingRow.setAttribute( "status", "3" );
						_calcTbl( table, editor.editingCell );
					}
					if( editor.style ) {
						editor.style.display = "block";
						editor.focus();
						if( table.getAttribute( "selectText" ) ) editor.select();
					}
					else editor.Table.style.display = "block";
					return false;
				}
			}
			var cellName = editor.editingCell.id;
			cellName = cellName.substring( cellName.lastIndexOf( "_" ) + 1 );
			if( valueChanged && !autoCalcOnlyOnSubmit ) {
				if( !table.getAttribute( "isli" ) ) eval( _getReportName( table ) + "_autoCalc( '" + cellName + "' )" );
			}
			if( valueChanged ) {
				table.changed = true;
				var filterCells = editor.editingCell.filterCells;
				if( filterCells != null ) {
					if( table.getAttribute( "isli" ) ) {
						li_currTbl = table;
						li_currCell = editor.editingCell;
					}
					var fcs = filterCells.split( "," );
					for( var i = 0; i < fcs.length; i++ ) {
						var fc = document.getElementById( fcs[i] );
						var ds = eval( fc.getAttribute( "dataSet" ) );
						var ec = ds.filter( fc.getAttribute( "filterExp" ) );
						fc.setAttribute( "editConfig", ec );
						if( ec == "" ) {
							fc.setAttribute( "value", "" );
							fc.innerHTML = "";
						}
						else {
							var autoSelect = true;
							try{ autoSelect = autoSelectWhileFilter; }catch(ex){}
							var ecs1 = fc.getAttribute( "editConfig" ).split(";");
							if( ecs1.length == 1 && autoSelect ) {
								var item = ecs1[0].split(",");
								fc.setAttribute( "value", item[0] );
								fc.innerHTML = item[1];
							}
							else {
								fc.setAttribute( "value", "" );
								fc.innerHTML = "";
							}
						}
					}
				}
				if( table.getAttribute( "isli" ) ) {
					var editingRow = editor.editingCell.parentNode;
					if( editingRow.getAttribute( "status" ) == "0" ) editingRow.setAttribute( "status", "1" );
					else if( editingRow.getAttribute( "status" ) == "2" ) editingRow.setAttribute( "status", "3" );
					_calcTbl( table, editor.editingCell );
				}
				try{ _cellValueChanged( editor.editingCell ); }catch(e){}
			}
		}
		editor.editingCell = null;
		table.currEditor = null;
		//_formatData( table );
	}
	return true;
}

function _fireFilter( cell ) {
	var filterCells = cell.filterCells;
	if( filterCells != null ) {
		var fcs = filterCells.split( "," );
		for( var i = 0; i < fcs.length; i++ ) {
			var fc = document.getElementById( fcs[i] );
			var ds = eval( fc.getAttribute( "dataSet" ) );
			fc.setAttribute( "editConfig", ds.filter( fc.getAttribute( "filterExp" ) ) );
			fc.setAttribute( "value", "" );
			fc.innerHTML = "";
		}
	}
}

function _setEditingValue2( cell, disp ) {
	var value = _clearValueFormat( cell, disp );
	var cellName = cell.id;
	cellName = cellName.substring( cellName.lastIndexOf( "_" ) + 1 );
	if( cell.getAttribute( "editConfig" ) != null ) {
		var configs = cell.getAttribute( "editConfig" );
		if( configs.indexOf( "ref:" ) == 0 ) {
			configs = document.getElementById( configs.substring( 4 ) ).getAttribute( "editConfig" );
		}
		var values = configs.split( ";" );
		var found = false;
		for( var i = 0; i < values.length; i++ ) {
			var s = values[i].split( "," );
			if( disp == s[1] || disp == s[0] ) {
				value = s[0];
				disp = s[1];
				found = true;
				break;
			}
		}
		if( !found && value.length > 0 && cell.getAttribute( "ddNew" ) != "1" ) {
			cell.innerHTML = disp;
			alert( cellName + __DD + ": \"" + disp + "\"" + __UU );
			cell.innerHTML = "";
			cell.setAttribute( "value", "" );
			return false;
		}
	}
	if( cell.getAttribute( "editStyle" ) == "8" ) {
		var treeWin = document.getElementById( cell.getAttribute( "treeId" ) );
		var nodes = new Array();
		var doc = treeWin.treeFrame.document;
		for( var i = 2; ; i++ ) {
			var node = doc.getElementById( "id_" + i );
			if ( node == null ) break;
			nodes[nodes.length] = node;
		}
		for( var i = 0; i < nodes.length; i++ ) {
			var nodeCode = nodes[i].getAttribute( "code" );
			var nodeDisp = nodes[i].innerHTML;
			if( disp == nodeDisp || disp == nodeCode ) {
				value = nodeCode;
				disp = nodeDisp;
				break;
			}
		}
	}
	cell.innerHTML = disp;
	var inputDataType = cell.getAttribute( "inputDataType" );
	if( ! _checkDataType( inputDataType, value, cellName ) ) {
		cell.innerHTML = "";
		cell.setAttribute( "value", "" );
		return false;
	}
	return _setEditingValue( cell, value, disp );
}

function _setEditingValue( editingObj, value, disp ) {
	if( editingObj.tagName == "TD" ) {
		try{ value = _clearValueFormat( editingObj, value ); }catch(ex){}
		if( editingObj.childNodes[0] && editingObj.childNodes[0].tagName == "INPUT" ) {    //˵���ǿ�ע��༭���Ϊ�������ĵ�Ԫ������ֵ
			editingObj.childNodes[0].value = value;
		}
		else {
			var table = _lookupTable( editingObj );
			var valueChanged = false;
			if( value != editingObj.getAttribute( "value" ) ) valueChanged = true;
			if( disp != null ) editingObj.textContent = disp;
			editingObj.setAttribute( "value", value );
			var dataValid = editingObj.getAttribute( "dataValid" );
			if( dataValid != null && dataValid.length > 0 ) {
				if( table.getAttribute( "isli" ) ) {
					li_currTbl = table;
					li_currCell = editingObj;
				}
				if( ! eval( dataValid ) ) {
					editingObj.textContent = "";
					editingObj.setAttribute( "value", "" );
					return false;
				}
			}
			var cellName = editingObj.id;
			cellName = cellName.substring( cellName.lastIndexOf( "_" ) + 1 );
			if( valueChanged && !autoCalcOnlyOnSubmit ) {
				if( !table.getAttribute( "isli" ) ) eval( _getReportName( table ) + "_autoCalc( '" + cellName + "' )" );
			}
			if( valueChanged ) {
				table.changed = true;
				if( table.getAttribute( "isli" ) ) {
					var editingRow = editingObj.parentNode;
					if( editingRow.getAttribute( "status" ) == "0" ) editingRow.setAttribute( "status", "1" );
					else if( editingRow.getAttribute( "status" ) == "2" ) editingRow.setAttribute( "status", "3" );
					var oldCell = li_currCell;
					_calcTbl( table, editingObj );
					li_currCell = oldCell;
				}
				var filterCells = editingObj.filterCells;
				if( filterCells != null ) {
					var fcs = filterCells.split( "," );
					for( var i = 0; i < fcs.length; i++ ) {
						var fc = document.getElementById( fcs[i] );
						var ds = eval( fc.getAttribute( "dataSet" ) );
						var ec = ds.filter( fc.getAttribute( "filterExp" ) );
						fc.setAttribute( "editConfig", ec );
						if( ec == "" ) {
							fc.setAttribute( "value", "" );
							fc.innerHTML = "";
						}
						else {
							var autoSelect = true;
							try{ autoSelect = autoSelectWhileFilter; }catch(ex){}
							var ecs1 = fc.getAttribute( "editConfig" ).split(";");
							if( ecs1.length == 1 && autoSelect ) {
								var item = ecs1[0].split(",");
								fc.setAttribute( "value", item[0] );
								fc.innerHTML = item[1];
							}
							else {
								fc.setAttribute( "value", "" );
								fc.innerHTML = "";
							}
						}
					}
				}
				try{ _cellValueChanged( editingObj ); }catch(e){}
			}
		}
	}
	else if( editingObj.tagName == "INPUT" ) {
		editingObj.setAttribute( "value", disp );
		var pos = editingObj.id.lastIndexOf( "_text" );
		var hiddenInput = document.getElementById( editingObj.id.substring( 0, pos ) );
		hiddenInput.value = value;
		var form = hiddenInput;
		while( form.tagName != "FORM" ) form = form.parentNode;
		try{ eval( form.id + "_autoCalc()" ); }catch(e1){}
	}
	return true;
}

function _getEditingValue( editingObj ) {
	if( editingObj.tagName == "INPUT" ) {
		var pos = editingObj.id.lastIndexOf( "_text" );
		var hiddenInput = document.getElementById( editingObj.id.substring( 0, pos ) );
		return hiddenInput.value;
	}
	else {
		if( editingObj.childNodes[0] && editingObj.childNodes[0].tagName == "INPUT" ) {    //˵���ǿ�ע��༭���ȡ�������ĵ�Ԫ��ֵ
			return editingObj.childNodes[0].value;
		}
		return editingObj.getAttribute( "value" );
	}
}

function _getEditingDispValue( editingObj ) {
	if( editingObj.tagName == "INPUT" ) return editingObj.value;
	else return editingObj.textContent;
}

function _getEditStyleConfig( editTarget ) {
	return editTarget.getAttribute( "rescfg" );
}

function _getOtherCellValue( editTarget, cellName ) {
	var targetCell = editTarget;
	if( editTarget.tagName == "INPUT" ) targetCell = editTarget.parentNode;
	var pos = targetCell.id.lastIndexOf( "_" );
	cellName = targetCell.id.substring( 0, pos ) + "_" + cellName.toUpperCase();
	var cell = document.getElementById( cellName );
	if( cell.childNodes[0] && cell.childNodes[0].tagName == "INPUT" ) {
		return cell.childNodes[0].value;
	}
	else {
		if( cell.getAttribute( "value" ) ) return cell.getAttribute( "value" );
		return cell.textContent;
	}
}

function _submitEditor( table ) {
	if( !_submitEditor2( table ) ) return false;
	var name = _getReportName( table );
	var otherTable = document.getElementById( name + "_$_corner" );
	if( otherTable != null && otherTable != table ) {
		if( !_submitEditor2( otherTable ) ) return false;
	}
	otherTable = document.getElementById( name + "_$_top" );
	if( otherTable != null && otherTable != table ) {
		if( !_submitEditor2( otherTable ) ) return false;
	}
	otherTable = document.getElementById( name + "_$_left" );
	if( otherTable != null && otherTable != table ) {
		if( !_submitEditor2( otherTable ) ) return false;
	}
	otherTable = document.getElementById( name );
	if( otherTable != null && otherTable != table ) {
		if( !_submitEditor2( otherTable ) ) return false;
	}
	return true;
}

function _checkDataType( type, value, cellName ) {
	if( value == null || value.length == 0 ) return true;
	if( cellName.length > 0 ) cellName = __BB + cellName + __DD;
	else cellName = __AA;
	switch( type ) {
		case "1": return true;
		case "2":
		case "9":
			if( isNaN( value ) ) {
				alert( cellName + __EE );
				return false;
			}
			return true;
		case "3":
			var r = value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
   			if( r == null ) {
   				alert( cellName + __FF );
   				return false;
   			}
   			var d = new Date( r[1], r[3]-1, r[4] );
   			if( ! ( d.getFullYear() == r[1] && ( d.getMonth()+1 ) == r[3] && d.getDate() == r[4] ) ) {
				alert( cellName + __FF );
   				return false;
   			}
			return true;
		case "4":
			if( isNaN( value ) ) {
				alert( cellName + __GG );
				return false;
			}
			return true;
		case "5":
			var pos = value.indexOf( "@" );
			if( pos < 1 || pos == value.length - 1 ) {
				alert( cellName + __HH  );
				return false;
			}
			return true;
		case "6":
			if( isNaN( value ) || value.indexOf( "." ) >= 0 ) {
				alert( cellName + __CC );
				return false;
			}
			return true;
	}
	return true;
}

function _checkAllDataType( table ) {
	for( var r = 0; r < table.rows.length; r++ ) {
		var row = table.rows[r];
		for( var c = 0; c < row.cells.length; c++ ) {
			var cell = row.cells[c];
			//if( ( cell.writable || cell.editStyle == '6' || cell.editStyle == '8' || cell.editStyle == '11' || cell.editStyle == '12') ) {
			var inputDataType = cell.getAttribute( "inputDataType" );
			var cellName = cell.id.substring( cell.id.lastIndexOf( "_" ) + 1 );
			if( ! _checkDataType( inputDataType, cell.value, cellName ) ) return false;
		}
	}
	return true;
}

function _hideEditor( event ) {
	var cell = event.target;
	var table = _lookupTable( cell );
	_submitEditor( table );
	try {
		_hiddenCalendar();
	}catch( e ) {}
	try {
		tree_hide();
	}catch( e ) {}
	var hide = document.body.getAttribute( "_jsHideFuncs" );
	if( hide != null ) {   //�����Զ���༭�ؼ�
		for( var i = 0; i < hide.length; i++ ) {
			try { eval( hide[i] ); }catch( e ) {}
		}
	}
}

function _displayEditor( event ) {
	try {
		_hiddenCalendar();
	}catch( e ) {}
	try {
		tree_hide();
	}catch( e ) {}
	var cell = event.target;
	_bindingEditor( cell );
}

function _bindingEditor( cell ) {
	var table = _lookupTable( cell );
	if( ! _submitEditor( table ) ) return;
	var editor = _lookupEditor( table, cell );
	table.currEditor = editor;
	_setRowColBackColor( cell );
	_setEditorStyle( editor, cell );
}

function _setRowColBackColor( cell ) {
	var rowbkcolor = "#d1f2fe", colbkcolor = "";
	try { rowbkcolor = _editingRowBackColor; } catch(e){}
	try { colbkcolor = _editingColBackColor; } catch(e){}
	var table = _lookupTable( cell );
	_setTableEditingFlag( table, cell, rowbkcolor, colbkcolor );
	var name = _getReportName( table );
	var otherTable = document.getElementById( name + "_$_corner" );
	if( otherTable != null && otherTable != table ) _setTableEditingFlag( otherTable, null, rowbkcolor, colbkcolor );
	otherTable = document.getElementById( name + "_$_top" );
	if( otherTable != null && otherTable != table ) _setTableEditingFlag( otherTable, null, rowbkcolor, colbkcolor );
	otherTable = document.getElementById( name + "_$_left" );
	if( otherTable != null && otherTable != table ) _setTableEditingFlag( otherTable, null, rowbkcolor, colbkcolor );
	otherTable = document.getElementById( name );
	if( otherTable != null && otherTable != table ) _setTableEditingFlag( otherTable, null, rowbkcolor, colbkcolor );
}

function  _setTableEditingFlag( table, cell, rowbkcolor, colbkcolor ) {
	if( table.currCell != null ) {   //��ԭ����������еı���ɫ�ָ�
		var c = table.currCell;
		if( rowbkcolor != "" ) {
			var r = c.parentNode;
			for( var i = 0; i < r.cells.length; i++ ) {
				var tmpCell = r.cells[i];
				if( tmpCell.oldBkcolor != null ) {
					tmpCell.style.backgroundColor = tmpCell.oldBkcolor;
				}
			}
		}
		if( colbkcolor != "" ) {
			var s = c.id.substring( c.id.lastIndexOf( "_" ) + 1 );
			var rc = _toRowCol( s );
			var col = rc[1];
			for( var i = 0; i < table.rows.length; i++ ) {
				var tmpCell = null;
				try { tmpCell = document.getElementById( _getReportName( table ) + "_" + _toExcelNotation( i + rc[0] - r.rowIndex, col ) ); }catch(e){}
				if( tmpCell != null && tmpCell.oldBkcolor != null ) tmpCell.style.backgroundColor = tmpCell.oldBkcolor;
			}
		}
	}
	if( cell != null ) {    //�����¹���������еı���ɫ
		if( rowbkcolor != "" ) {
			var r = cell.parentNode;
			for( var i = 0; i < r.cells.length; i++ ) {
				var tmpCell = r.cells[i];
				if( tmpCell != cell ) {
					tmpCell.oldBkcolor = window.getComputedStyle(tmpCell,null).backgroundColor;
					tmpCell.style.backgroundColor = rowbkcolor;
				}
			}
		}
		if( colbkcolor != "" ) {
			var s = cell.id.substring( cell.id.lastIndexOf( "_" ) + 1 );
			var rc = _toRowCol( s );
			var col = rc[1];
			for( var i = 0; i < table.rows.length; i++ ) {
				var tmpCell = null;
				try { tmpCell = document.getElementById( _getReportName( table ) + "_" + _toExcelNotation( i + rc[0] - r.rowIndex, col ) ); }catch(e){}
				if( tmpCell != null && tmpCell != cell ) {
					tmpCell.oldBkcolor = window.getComputedStyle( tmpCell, null).backgroundColor;
					tmpCell.style.backgroundColor = colbkcolor;
				}
			}
		}
	}
	table.currCell = cell;
}

function _lookupTable( cell ) {
	var table = cell;
	while( table.tagName != "TABLE" ) {
		table = table.parentNode;
	}
	return table;
}

function _lookupEditor( table, cell ) {
	if( !cell.getAttribute( "writable" ) ) return null;
	var editStyle = cell.getAttribute( "editStyle" );
	if( editStyle == null ) editStyle = "1";
	var editor = null;
	switch( editStyle ) {
		case "0":
			editor = document.getElementById( table.id + "_textArea" );
			if( editor == null ) {
				var border = "";
				try {
					border += "border-left:" + _editorBorderLeft;
					border += ";border-right:" + _editorBorderRight;
					border += ";border-top:" + _editorBorderTop;
					border += ";border-bottom:" + _editorBorderBottom;
				}catch( e ) {
					border = "border: 1px solid red";
				}
				var mainName = table.id;
				var pos = mainName.indexOf( "_$_" );
				if( pos > 0 ) mainName = mainName.substring( 0, pos );
				var div_input = document.getElementById( mainName + "_reportDiv" );
				var tmpDiv = document.createElement( "div" );
				div_input.appendChild( tmpDiv );
				tmpDiv.innerHTML = "<textArea id=\"" + table.id + "_textArea\" style=\"position:absolute;" + border + ";display:none;zIndex:100\" onkeyup=\"_editorKeyPress( event )\" onkeydown=\"_editorKeyDown( event )\"></textArea>";
				editor = document.getElementById( table.id + "_textArea" );
			}
			editor.value = cell.getAttribute( "value" );
			editor.oldValue = cell.getAttribute( "value" );
			editor.style.display = "block";
			//if( table.getAttribute( "selectText" ) ) editor.select();
			break;
		case "2":
		case "3":
			try{ editor = eval( table.id + "_listBox" ); }catch(ee){}
			if( editor == null ) {
				var mainName = table.id;
				var pos = mainName.indexOf( "_$_" );
				if( pos > 0 ) mainName = mainName.substring( 0, pos );
				var div_input = document.getElementById( mainName + "_reportDiv" );
				eval( table.id + "_listBox = new RQSelectBox( div_input );" );
				editor = eval( table.id + "_listBox" );
			}
			var oldMulti = editor.isMulti;
			editor.setMultipleSelect( cell.getAttribute( "isMulti" ) == 1 );
			editor.setEditable( cell.getAttribute( "ddEdit" ) == 1 );
			editor.newValue = cell.getAttribute( "ddNew" ) == 1;
			if( cell.getAttribute( "editConfig" ) != null ) {
				var configs = cell.getAttribute( "editConfig" );
				if( configs.indexOf( "ref:" ) == 0 ) {
					if( cell.getAttribute( "dataSet" ) ) configs = "";
					else configs = document.getElementById( configs.substring( 4 ) ).getAttribute( "editConfig" );
				}
				if( configs == "" && cell.getAttribute( "dataSet" ) ) {
					if( table.getAttribute( "isli" ) ) {
						li_currTbl = table;
						li_currCell = cell;
					}
					var ds = eval( cell.getAttribute( "dataSet" ) );
					cell.setAttribute( "editConfig", ds.filter( cell.getAttribute( "filterExp" ) ) );
					configs = cell.getAttribute( "editConfig" );
				}
				if( editor.configs != configs || oldMulti != editor.isMulti ) {
					editor.configs = configs;
					editor.clearOptions();
					if( !editor.isMulti && cell.getAttribute( "canEmpty" ) == 1) editor.addOption( "", "" );
					var items = configs.split( ";" );
					editor.length = items.length;
					for( var i = 0; i < items.length; i++ ) {
						var item = items[i];
						var pos = item.indexOf( "," );
						var value = item.substring( 0, pos );
						var disp = item.substring( pos + 1 );
						editor.addOption( value, disp );
					}
				}
			}
			editor.setValue( cell.getAttribute( "value" ) );
			editor.Table.style.display = "block";
			break;
		case "9":
			editor = document.getElementById( table.id + "_pwdBox" );
			if( editor == null ) {
				var border = "";
				try {
					border += "border-left:" + _editorBorderLeft;
					border += ";border-right:" + _editorBorderRight;
					border += ";border-top:" + _editorBorderTop;
					border += ";border-bottom:" + _editorBorderBottom;
				}catch( e ) {
					border = "border: 1px solid red";
				}
				var mainName = table.id;
				var pos = mainName.indexOf( "_$_" );
				if( pos > 0 ) mainName = mainName.substring( 0, pos );
				var div_input = document.getElementById( mainName + "_reportDiv" );
				var tmpDiv = document.createElement( "div" );
				div_input.appendChild( tmpDiv );
				tmpDiv.innerHTML = "<input type=password id=\"" + table.id + "_pwdBox\" style=\"position:absolute;" + border + ";display:none;zIndex:100\" onkeyup=\"_editorKeyPress( event )\" onkeydown=\"_editorKeyDown( event )\">";
				editor = document.getElementById( table.id + "_pwdBox" );
			}
			editor.value = cell.getAttribute( "value" );
			editor.oldValue = cell.getAttribute( "value" );
			editor.style.display = "block";
			//if( table.getAttribute( "selectText" ) ) editor.select();
			break;
		default:
			editor = document.getElementById( table.id + "_editBox" );
			if( editor == null ) {
				var border = "";
				try {
					border += "border-left:" + _editorBorderLeft;
					border += ";border-right:" + _editorBorderRight;
					border += ";border-top:" + _editorBorderTop;
					border += ";border-bottom:" + _editorBorderBottom;
				}catch( e ) {
					border = "border: 1px solid red";
				}
				var mainName = table.id;
				var pos = mainName.indexOf( "_$_" );
				if( pos > 0 ) mainName = mainName.substring( 0, pos );
				var div_input = document.getElementById( mainName + "_reportDiv" );
				var tmpDiv = document.createElement( "div" );
				div_input.appendChild( tmpDiv );
				tmpDiv.innerHTML = "<input type=text id=\"" + table.id + "_editBox\" style=\"position:absolute;" + border + ";display:none;zIndex:100\" onkeyup=\"_editorKeyPress( event )\" onkeydown=\"_editorKeyDown( event )\" onpaste=\"return _paste(event)\">";
				editor = document.getElementById( table.id + "_editBox" );
			}
			var tc = cell.textContent;
			var re = String.fromCharCode( 160 );    //��Ӧ&nbsp;
			while( tc.indexOf( re ) >= 0 ) tc = tc.replace( re, " " );
			editor.value = tc;
			editor.oldValue = tc;
			editor.style.display = "block";
	}
	editor.editingCell = cell;
	return editor;
}

function _setEditorStyle( editor, cell ) {
	if( editor == null ) return;
	var x = cell.offsetLeft, y = cell.offsetTop;
	var obj = cell.offsetParent;
	var offsetP;
	if( editor.style ) offsetP = editor.offsetParent;
	else offsetP = editor.Table.offsetParent;
	while( obj != null && obj != offsetP ) {
		x += obj.offsetLeft + obj.clientLeft;
		y += obj.offsetTop + obj.clientTop;
		obj = obj.offsetParent;
	}
	var dx, dy;
	var isScroll = document.getElementById( cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) + "_contentdiv" ) != null;
	if( isScroll ) {
		var div = cell.parentNode;
		while( div.tagName != "DIV" || div.id == "div_" + cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) ) div = div.parentNode;
		x = x - div.scrollLeft;
		y = y - div.scrollTop;
		dx = div.offsetLeft;
		dy = div.offsetTop;
		obj = div.offsetParent;
		while( obj != null && obj != offsetP ) {
			dx += obj.offsetLeft;// + obj.clientLeft;
			dy += obj.offsetTop;// + obj.clientTop;
			obj = obj.offsetParent;
		}
	}
	var currentStyle = window.getComputedStyle(cell, null);
	switch( cell.getAttribute( "editStyle" ) ) {
		case "2":
		case "3":
			if( currentStyle.backgroundColor != "transparent" ) editor.setBackColor( currentStyle.backgroundColor );
			else editor.setBackColor( "white" );
			editor.setFontWeight( currentStyle.fontWeight );
			editor.setFontColor( currentStyle.color );
			editor.setFontFace( currentStyle.fontFamily );
			editor.setFontItalic( currentStyle.fontStyle );
			editor.setFontSize( currentStyle.fontSize );
			editor.setLeft( x - 2 );
			editor.setTop( y - 2 );
			editor.setWidth( cell.offsetWidth + 2 );
			editor.setHeight( cell.offsetHeight );
			editor.editBox.focus();
			if( !editor.editable ) editor.toggleOptions();
			break;
		default:
			editor.style.textAlign = currentStyle.textAlign;
			if( currentStyle.backgroundColor != "transparent" ) editor.style.backgroundColor = currentStyle.backgroundColor;
			else editor.style.backgroundColor = "white";
			editor.style.paddingLeft = currentStyle.paddingLeft;
			editor.style.paddingRight = currentStyle.paddingRight;
			editor.style.fontWeight = currentStyle.fontWeight;
			editor.style.color = currentStyle.color;
			editor.style.fontFamily = currentStyle.fontFamily;
			editor.style.fontStyle = currentStyle.fontStyle;
			editor.style.fontSize = currentStyle.fontSize;
			editor.style.textDecoration = currentStyle.textDecoration;
			editor.style.verticalAlign = currentStyle.verticalAlign;
			editor.style.left = x - 2;
			editor.style.top = y - 2;
			editor.style.width = cell.offsetWidth + 1;
			editor.style.height = cell.offsetHeight + 1;
			if( _lookupTable(cell).getAttribute( "selectText" ) ) editor.select();
			editor.focus();
	}
	/*if( isScroll ) {
		if( y < dy || y + cell.offsetHeight > dy + div.offsetHeight - 16 ) {
			//cell.scrollIntoView( false );
			//_reportScroll( cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) );
		}
		if( x < dx ) {
			div.scrollLeft -= dx - x + 1;
			_reportScroll( cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) );
		}
		if( x + cell.offsetWidth > dx + div.offsetWidth - 16 ) {
			div.scrollLeft += x + cell.offsetWidth - dx - div.offsetWidth + 17;
			_reportScroll( cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) );
		}
	}*/
	if( isScroll ) {
		if( y < dy ) div.scrollTop -= dy - y + 1;
		if( y + cell.offsetHeight > dy + div.offsetHeight - 16 ) div.scrollTop += y + cell.offsetHeight - dy - div.offsetHeight + 17
		if( x < dx ) div.scrollLeft -= dx - x + 1;
		if( x + cell.offsetWidth > dx + div.offsetWidth - 16 ) div.scrollLeft += x + cell.offsetWidth - dx - div.offsetWidth + 17;
		_reportScroll( cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) );
	}
}

function _editorKeyPress( event ) {
	var keyCode = event.keyCode;
	var flag = -1;
	var gotoNext = false;
	var editor = event.target;
	if( keyCode == 39 && event.ctrlKey ) {  //right
		flag = 3;
		gotoNext = true;
	}
	if( keyCode == 13 ) {
		flag = 3;
		gotoNext = true;
		if( editor.tagName == "TEXTAREA" && !event.ctrlKey ) gotoNext = false;
	}
	if( keyCode == 37 && event.ctrlKey ) {  //left
		flag = 1;
		gotoNext = true;
	}
	if( keyCode == 38 || keyCode == 40 ) {
		if( keyCode == 38 ) flag = 2;
		if( keyCode == 40 ) flag = 4;
		gotoNext = true;
		if( editor.tagName == "TEXTAREA" && !event.ctrlKey ) gotoNext = false;
	}
	if( gotoNext ) {
		var cell = _lookupNextCell( editor.editingCell, flag );
		if( cell != null ) _bindingEditor( cell );
	}
	if( keyCode == 9 ) return false;
	return true;
}

function _editorKeyDown( event ) {
	var keyCode = event.keyCode;
	var flag = -1;
	var editor = event.target;
	if( keyCode == 9 ) {    //tab
		flag = event.shiftKey ? 1 : 3;
		var cell = _lookupNextCell( editor.editingCell, flag );
		if( cell != null ) _bindingEditor( cell );
		if(event.preventDefault) event.preventDefault();             
		return false;         
	}
}

function _lookupNextCell( cell, flag ) {
	var table = _lookupTable( cell );
	var nextCell = null;
	var rows = table.rows.length;
	if( flag == 1 || flag == 3 ) {  //left || right
		var delta = 1;
		if( flag == 1 ) delta = -1;
		var row = cell.parentNode.rowIndex + 1, col = parseInt( cell.getAttribute( "colNo" ) );
		if( flag == 3 ) col = col + cell.colSpan - 1;
		var cols = table.getAttribute( "cols" );
		while( true ) {
			col += delta;
			if( col < 1 ) {
				col = cols;
				row = row - 1;
				if( row < 1 ) return null;
			}
			if( col > cols ) {
				col = 1;
				row = row + 1;
				if( row > rows ) return null;
			}
			var tmpCell = _getMergeCell( table, row, col );
			if( tmpCell == null ) return null;
			if( !tmpCell.getAttribute( "writable" ) || tmpCell.style.display == "none" || tmpCell.parentNode.style.display == "none" ) {
				if( flag == 3 ) col = col + tmpCell.colSpan - 1;
				if( flag == 1 ) col = col - tmpCell.colSpan + 1;
			}
			else {
				nextCell = tmpCell;
				break;
			}
		}
	}
	else {  //2-up  4-down
		var delta = 1;
		if( flag == 2 ) delta = -1;
		var row = cell.parentNode.rowIndex + 1, col = parseInt( cell.getAttribute( "colNo" ) );
		if( flag == 4 ) row = row + cell.rowSpan - 1;
		while( true ) {
			row += delta;
			if( row < 1 || row > rows ) return null;
			var tmpCell = _getMergeCell( table, row, col );
			if( tmpCell == null ) return null;
			if( ! tmpCell.getAttribute( "writable" ) || tmpCell.style.display == "none" || tmpCell.parentNode.style.display == "none" ) {
				if( flag == 4 ) row = row + tmpCell.rowSpan - 1;
				if( flag == 2 ) row = row - tmpCell.rowSpan + 1;
			}
			else {
				nextCell = tmpCell;
				break;
			}
		}
	}
	return nextCell;
}

function _getMergeCell( table, row, col ) {  //���ذ���(row,col)�ĵ�Ԫ���ϲ���
	for( var i = row - 1; i >= 0; i-- ) {
		var r = table.rows[i];
		for( var j = 0; j < r.cells.length; j++ ) {
			var c = r.cells[j];
			var row1 = i + 1, col1 = parseInt( c.getAttribute( "colNo" ) );
			if( row1 <= row && row1 + c.rowSpan > row && col1 <= col && col1 + c.colSpan > col ) return c;
		}
	}
	return null;
}

function _toExcelNotation( row, col ) {
	var s = "";
	do {
		col--;
		var c = col % 26 + "A".charCodeAt(0);
		s = String.fromCharCode( c ) + s;
		col = Math.floor( col / 26 );
	} while ( col > 0 );
	return s + row;
}

function _toRowCol( s ) {
	var len = s.length;
	var row = 0, col = 0;
	var i = 0;
	while( i < len ) {
		var ch = s.charAt(i);
		if ( ch >= 'A' && ch <= 'Z' )
			col = col * 26 + ch.charCodeAt(0) - 'A'.charCodeAt(0) + 1;
		else break;
		i++;
	}
	row = parseInt( s.substring( i ) );
	var rc = new Array(2);
	rc[0] = row;
	rc[1] = col;
	return rc;
}

function StringBuffer(){
	this.data = [];
	this.append = function(s){this.data.push(s);return this;}
	this.toString = function(){return this.data.join("");}
	this.length = function(){ return this.data.length; }
}

function _submitTable( table, resultInfoPage ) {
	if( ! _submitEditor( table ) ) return false;
	if( checkDataTypeOnSubmit ) {
		if( !_checkAllDataType( table ) ) return false;
	}
	if( eval( table.id + "_validOnSubmit" ) ) {
		if( ! eval( table.id + "_checkValid()" ) ) {  //������Ч�Լ��
			try {
				if( batImport ) {
					parent.getResultInfo( "error", __II );
				}
			}catch(ex){}
			return false;
		}
		try {
			if( ! eval( table.id + "_userDefineValidScript()" ) ) return false;
		}catch( exception ) {}
	}	
	if( autoCalcOnlyOnSubmit ) eval( table.id + "_autoCalc( '' )" );
	var form = document.getElementById( table.id + "_submitForm" );
	var data = new StringBuffer();
	var otherTable = document.getElementById( table.id + "_$_corner" );
	if( otherTable != null ) {
		_getTableData( otherTable, data );
	}
	otherTable = document.getElementById( table.id + "_$_top" );
	if( otherTable != null ) {
		_getTableData( otherTable, data );
	}
	otherTable = document.getElementById( table.id + "_$_left" );
	if( otherTable != null ) {
		_getTableData( otherTable, data );
	}
	if( table != null ) {
		_getTableData( table, data );
	}
	form.data.value = data.toString();
	try {
		if( batImport ) {
			form.pageUrl.value = resultInfoPage;
			form.backAndRefresh.value = "batImport";
		}
	}catch( e ) {}
	form.submit();
	return true;
}

function _getTableData( table, data ) {
	for( var row = 0; row < table.rows.length; row++ ) {
		var currRow = table.rows[ row ];
		for( var col = 0; col < currRow.cells.length; col++ ) {
			var currCell = currRow.cells[ col ];
			var name = currCell.id;
			name = name.substring( name.lastIndexOf( "_" ) + 1 );
			var value = currCell.getAttribute( "value" );
			if( value == null ) value = "";
			var needSubmit = false;
			switch( table.getAttribute( "submitCells" ) ) {
				case "0": if( currCell.getAttribute( "updatable" ) ) needSubmit = true; break;
				case "1": if( currCell.getAttribute( "updatable" ) || currCell.getAttribute( "modifiable" ) ) needSubmit = true; break;
				case "2": needSubmit = true; break;
			}
			if( needSubmit ) {
				if( data.length() > 0 ) data.append( ";" );
				data.append( name ).append( "=" ).append( _addEscape( value ) );
			}
		}
	}
}

function _submitReport( table ) {
	if( ! _submitEditor( table ) ) return false;
	if( autoCalcOnlyOnSubmit ) eval( table.id + "_autoCalc( '' )" );
	var form = document.getElementById( table.id + "_submitForm" );
	var data = new StringBuffer();
	var otherTable = document.getElementById( table.id + "_$_corner" );
	if( otherTable != null ) {
		_getTableData( otherTable, data );
	}
	otherTable = document.getElementById( table.id + "_$_top" );
	if( otherTable != null ) {
		_getTableData( otherTable, data );
	}
	otherTable = document.getElementById( table.id + "_$_left" );
	if( otherTable != null ) {
		_getTableData( otherTable, data );
	}
	if( table != null ) {
		_getTableData( table, data );
	}
	form.data.value = data.toString();
	form.submit();
	return true;
}

function _addEscape( src ) {
	try {
		var re = /\\/g;
		src = src.replace( re, "\\\\" );
		re = /\"/g;
		src = src.replace( re, "\\\"" );
		return "\"" + src + "\"";
	}catch( exception ) {
		return "\"" + src + "\"";
	}
}

function _parseValue( cell ) {
	var value = parseFloat( cell.getAttribute( "value" ) );
	if( cell.getAttribute( "digits" ) != null ) {
		if( ! isNaN( value ) ) value = parseFloat( value.toFixed( parseInt( cell.getAttribute( "digits" ) ) ) );
	}
	return isNaN( value ) ? 0 : value;
}

function _formatData( table ) {
	if( autoCalcOnlyOnSubmit ) return;
	for( var row = 0; row < table.rows.length; row++ ) {
		var currRow = table.rows[ row ];
		for( var col = 0; col < currRow.cells.length; col++ ) {
			var currCell = currRow.cells[ col ];
			if( currCell.getAttribute( "digits" ) != null ) {
				var value = parseFloat( currCell.getAttribute( "value" ) );
				if( ! isNaN( value ) ) {
					value = value.toFixed( parseInt( currCell.getAttribute( "digits" ) ) );
					currCell.setAttribute( "value", value + "" );
					//currCell.textContent = value;
				}
			}
		}
	}
}

function _formatCalcValue( cell ) {
	if( cell.getAttribute( "bkj" ) == "true" ) return;
	if( cell.getAttribute( "format" ) != null ) {
		var xmlhttp = new XMLHttpRequest();
		var table = _lookupTable( cell );
		xmlhttp.open( "POST", table.getAttribute( "ajaxUrl" ) + "?action=27&value=" + cell.getAttribute( "value" ) + "&format=" + cell.getAttribute( "format" ), false );
		xmlhttp.send( " " );
		try { 
	   		cell.textContent = xmlhttp.responseText;
		} catch( exception ) {}
	}
	else if( cell.getAttribute( "digits" ) != null ) {
		var value = parseFloat( cell.getAttribute( "value" ) );
		if( ! isNaN( value ) ) {
			value = value.toFixed( parseInt( cell.getAttribute( "digits" ) ) );
			cell.textContent = value + "";
		}
	}
	else {
		cell.textContent = cell.getAttribute( "value" );
	}
}

function _uploadFile( currCell, url, table, ds, tblName, keyCols, fields, autoIncrements, ext, rights, fileNameCol ) {
	if( !_submitEditor( table ) ) return;
	try{ if( !eval( _getReportName( table ) + "_checkValid()" ) ) return; }catch(e){}
	var data = "";
	var keyCells = currCell.getAttribute( "upKeyCells" );
	var origins = currCell.getAttribute( "upOrigins" );
	var valueCells = currCell.getAttribute( "upValueCells" );
	var cellName = currCell.id.substring( currCell.id.lastIndexOf( "_" ) + 1 );
	var keys = keyCells.split( "," );
	for( var i = 0; i < keys.length; i++ ) {
		var key = keys[i];
		var cell = null;
		try { cell = document.getElementById( _getReportName( table ) + "_" + key ); }
		catch( e ) {
			alert( __JJ + key + __KK );
			return;
		}
		if( cell.getAttribute( "value" ).length == 0 ) {
			alert( __LL + key + __MM );
			return;
		}
	}
	var cols = valueCells.split( "," );
	for( var i = 0; i < cols.length; i++ ) {
		var col = cols[i].toUpperCase();
		var cell = null;
		try { cell = document.getElementById( _getReportName( table ) + "_" + col ); }
		catch( e ) {
			alert( __NN + col + __OO );
			return;
		}
		if( data.length > 0 ) data += ";";
		data += col + "=" + _addEscape( cell.getAttribute( "value" ) );
	}
	url += "&ds=" + _urlEncode( ds ) + "&data=" + _urlEncode( data ) + "&originData=" + _urlEncode( origins ) + "&ext=" + _urlEncode( ext )
			+ "&tbl=" + _urlEncode( tblName ) + "&keys=" + _urlEncode( keyCols ) + "&cols=" + _urlEncode( fields )
			+ "&cells=" + _urlEncode( valueCells ) + "&autoIncrements=" + autoIncrements + "&currCell=" + cellName + "&keyCells=" + keyCells + "&right=" + rights + "&fileNameCol=" 
			+ fileNameCol + "&reportId=" + _urlEncode( table.id );
	rq_showPopWin( url, 400, 180, null );
}

function _uploadPrompt( display, txt, event ) {
	var promptBox = document.getElementById( "___promptBox" );
	if( promptBox == null ) {
		var tmpDiv = document.createElement( "div" );
		document.body.appendChild( tmpDiv );
		tmpDiv.innerHTML = "<table id=___promptBox style=\"display:none; BACKGROUND-COLOR: mistyrose; BORDER: blue 1px solid; FONT-FAMILY: ����; FONT-SIZE: 13px; POSITION: absolute;\">" +
											"<tr><td align=middle>" + txt + "</td></tr></table>";
		var promptBox = document.getElementById( "___promptBox" );
	}
	else promptBox.rows[0].cells[0].innerHTML = txt;
	var cell = event.target;
	var x = cell.offsetLeft, y = cell.offsetTop;
	var obj = cell.offsetParent;
	while( obj.tagName != 'BODY' ) {
		x += obj.offsetLeft;
		y += obj.offsetTop;
		obj = obj.offsetParent;
	}
	promptBox.style.left = x + _getOffsetX( cell, event ) + 10;
	promptBox.style.top = y + _getOffsetY( cell, event ) + 5;
	promptBox.style.display = display;
}

function _getOffsetX( obj, evt ) {   
	if( evt.offsetX ) return evt.offsetX;   
	var o = obj;
	var x = 0;   
	while( o.offsetParent ) {
		x += o.offsetLeft;   
		o = o.offsetParent;   
	}   
	var left = 0;   /*
	while( obj.parentNode ) {
		left += obj.scrollLeft;   
		obj = obj.parentNode;   
	}   */
	return evt.pageX + left - x;   
} 

function _getOffsetY( obj, evt ) {   
	if( evt.offsetY ) return evt.offsetY;   
	var o = obj;
	var y = 0;   
	while( o.offsetParent ) {
		y += o.offsetTop;   
		o = o.offsetParent;   
	}   
	var top = 0;   /*
	while( obj.parentNode ) {
		top += obj.scrollTop;   
		obj = obj.parentNode;   
	}   */
	return evt.pageY + top - y;   
} 
function _urlEncode( str )
{
	var dst = "";
	for ( var i = 0; i < str.length; i++ )
	{
		switch ( str.charAt( i ) )
		{
			case ' ':
				dst += "+";
				break;
			case '!':
				dst += "%21";
				break;
			case '\"':
				dst += "%22";
				break;
			case '#':
				dst += "%23";
				break;
			case '$':
				dst += "%24";
				break;
			case '%':
				dst += "%25";
				break;
			case '&':
				dst += "%26";
				break;
			case '\'':
				dst += "%27";
				break;
			case '(':
				dst += "%28";
				break;
			case ')':
				dst += "%29";
				break;
			case '+':
				dst += "%2B";
				break;
			case ',':
				dst += "%2C";
				break;
			case '/':
				dst += "%2F";
				break;
			case ':':
				dst += "%3A";
				break;
			case ';':
				dst += "%3B";
				break;
			case '<':
				dst += "%3C";
				break;
			case '=':
				dst += "%3D";
				break;
			case '>':
				dst += "%3E";
				break;
			case '?':
				dst += "%3F";
				break;
			case '@':
				dst += "%40";
				break;
			case '[':
				dst += "%5B";
				break;
			case '\\':
				dst += "%5C";
				break;
			case ']':
				dst += "%5D";
				break;
			case '^':
				dst += "%5E";
				break;
			case '`':
				dst += "%60";
				break;
			case '{':
				dst += "%7B";
				break;
			case '|':
				dst += "%7C";
				break;
			case '}':
				dst += "%7D";
				break;
			case '~':
				dst += "%7E";
				break;
			default:
				dst += str.charAt( i );
				break;
		}
	}
	return dst;
}

function switchCase() {
	var len = arguments.length;
	if( len < 4 ) {
		alert( __QQ );
		return;
	}
	if( arguments[0] == arguments[1] ) return arguments[2];
	if( len > 4 ) {
		if( len % 2 == 1 ) {
			alert( __RR );
			return;
		}
		var i = 3;
		while( true ) {
			if( arguments[i] != null && arguments[i+1] != null ) {
				if( arguments[0] == arguments[i] ) return arguments[i+1];
			}
			else break;
			i += 2;
		}

	}
	return arguments[ len - 1 ];
}

function _getScriptFunctions() {
	var html = "<script language=javascript>\n";
	if( autoCalcOnlyOnSubmit ) html += "\tvar autoCalcOnlyOnSubmit = true;\n";
	else html += "\tvar autoCalcOnlyOnSubmit = false;\n";
	if( checkDataTypeOnSubmit ) html += "\tvar checkDataTypeOnSubmit = true;\n";
	else html += "\tvar checkDataTypeOnSubmit = false;\n";
        //add var
        html += "\tvar __AA ;\n";
        html += "\tvar __BB ;\n";
        html += "\tvar __CC ;\n";
        html += "\tvar __DD ;\n";
        html += "\tvar __EE ;\n";
        html += "\tvar __FF ;\n";
        html += "\tvar __GG ;\n";
        html += "\tvar __HH ;\n";
        html += "\tvar __II ;\n";
        html += "\tvar __JJ ;\n";
        html += "\tvar __KK ;\n";
        html += "\tvar __LL ;\n";
        html += "\tvar __MM ;\n";
        html += "\tvar __NN ;\n";
        html += "\tvar __OO ;\n";
        html += "\tvar __PP ;\n";
        html += "\tvar __QQ ;\n";
        html += "\tvar __RR ;\n";
        html += "\tvar __SS ;\n";
        html += "\tvar __TT ;\n";
        html += _initInputVar.toString() + "\n";//�滻����

	html += _initInput.toString() + "\n";
	html += _getReportName.toString() + "\n";
	html += _clearValueFormat.toString() + "\n";
	html += _submitEditor.toString() + "\n";
	html += _submitEditor2.toString() + "\n";
	html += _setEditingValue.toString() + "\n";
	html += _checkDataType.toString() + "\n";
	html += _checkAllDataType.toString() + "\n";
	html += _hideEditor.toString() + "\n";
	html += _displayEditor.toString() + "\n";
	html += _bindingEditor.toString() + "\n";
	html += _setRowColBackColor.toString() + "\n";
	html += _setTableEditingFlag.toString() + "\n";
	html += _lookupTable.toString() + "\n";
	html += _lookupEditor.toString() + "\n";
	html += _setEditorStyle.toString() + "\n";
	html += _editorKeyPress.toString() + "\n";
	html += _editorKeyDown.toString() + "\n";
	html += _lookupNextCell.toString() + "\n";
	html += _getMergeCell.toString() + "\n";
	html += _toExcelNotation.toString() + "\n";
	html += _toRowCol.toString() + "\n";
	html += StringBuffer.toString() + "\n";
	html += _submitTable.toString() + "\n";
	html += _getTableData.toString() + "\n";
	html += _submitReport.toString() + "\n";
	html += _addEscape.toString() + "\n";
	html += _parseValue.toString() + "\n";
	html += _formatData.toString() + "\n";
	html += _formatCalcValue.toString() + "\n";
	html += _uploadFile.toString() + "\n";
	html += _uploadPrompt.toString() + "\n";
	html += _getOffsetX.toString() + "\n";
	html += _getOffsetY.toString() + "\n";
	html += _urlEncode.toString() + "\n";
	html += switchCase.toString() + "\n";
	html += _getScriptFunctions.toString() + "\n";
	html += _saveToLocal.toString() + "\n";
	html += _tabClicked.toString() + "\n";
	html += _submitSheets.toString() + "\n";
	html += _saveSheetsToLocal.toString() + "\n";
	html += _toDate.toString() + "\n";
	html += _paste.toString() + "\n";
	html += _getClipboard.toString() + "\n";
	try {
		RQSelectBox.toString();
        html += "\tvar RQS_arrow ;\n";
        html += "\tvar RQS_all ;\n";
        html += "\tvar RQS_ok ;\n";
        html += _initSelectJsVar.toString() + "\n";//�滻����
		html += "\t\tif( RQS_VAR == null ) {\n";
		html += "\t\t\tvar RQS_VAR = new Object();\n";
		html += "\t\t\tRQS_VAR.SelectList = new Array();\n";
		html += "\t\t\tdocument.attachEvent( \"onmousedown\", RQS_blur );\n";
		html += "\t\t}\n";
		html += RQS_blur.toString() + "\n";
		html += RQSelectBox.toString() + "\n";
		html += RQS_getValue.toString() + "\n";
		html += RQS_acceptInput.toString() + "\n";
		html += RQS_setValue.toString() + "\n";
		html += RQS_lookupDisp.toString() + "\n";
		html += RQS_setChecked.toString() + "\n";
		html += RQS_getDisp.toString() + "\n";
		html += RQS_setHeight.toString() + "\n";
		html += RQS_setWidth.toString() + "\n";
		html += RQS_setLeft.toString() + "\n";
		html += RQS_setTop.toString() + "\n";
		html += RQS_setFontFace.toString() + "\n";
		html += RQS_setFontColor.toString() + "\n";
		html += RQS_setFontSize.toString() + "\n";
		html += RQS_setFontWeight.toString() + "\n";
		html += RQS_setFontItalic.toString() + "\n";
		html += RQS_setBackColor.toString() + "\n";
		html += RQS_setMultipleSelect.toString() + "\n";
		html += RQS_setEditable.toString() + "\n";
		html += RQS_addOption.toString() + "\n";
		html += RQS_clearOptions.toString() + "\n";
		html += RQS_cancelEvent.toString() + "\n";
		html += RQS_toggleOptions.toString() + "\n";
		html += RQS_handleOverTitle.toString() + "\n";
		html += RQS_handleOutTitle.toString() + "\n";
		html += RQS_turnOnOption.toString() + "\n";
		html += RQS_turnOffOption.toString() + "\n";
		html += RQS_pressOption.toString() + "\n";
		html += RQS_editBoxMouseDown.toString() + "\n";
		html += RQS_arrowMouseDown.toString() + "\n";
		html += RQS_selectAll.toString() + "\n";
		html += RQS_multiSelectOk.toString() + "\n";
		html += RQS_editBoxKeyDown.toString() + "\n";
		html += RQS_editBoxKeyUp.toString() + "\n";
		html += RQS_createTable.toString() + "\n";
		html += RQS_createOptionsDiv.toString() + "\n";
		html += RQS_createTmpObj.toString() + "\n";
	} catch( e ) {}
	try {
                //ADD VAR
                html += "\tvar __time ;\n";
                html += "\tvar __year ;\n";
                html += "\tvar __month ;\n";
                html += "\tvar __date ;\n";
                html += "\tvar __hour ;\n";
                html += "\tvar __minute ;\n";
                html += "\tvar __second ;\n";
                html += "\tvar __prevYear ;\n";
                html += "\tvar __nextYear ;\n";
                html += "\tvar __prevMonth ;\n";
                html += "\tvar __nextMonth ;\n";
                html += "\tvar __prevHour ;\n";
                html += "\tvar __nextHour ;\n";
                html += "\tvar __prevMinutes ;\n";
                html += "\tvar __nextMinutes ;\n";
                html += "\tvar __prevSeconds ;\n";
                html += "\tvar __nextSeconds ;\n";
                html += "\tvar __funMonthSelect ;\n";
                html += "\tvar __Sunday ;\n";
                html += "\tvar __Monday ;\n";
                html += "\tvar __Tuesday ;\n";
                html += "\tvar __Wednesday ;\n";
                html += "\tvar __Thursday ;\n";
                html += "\tvar __Friday ;\n";
                html += "\tvar __Saturday ;\n";
                html += "\tvar __clear ;\n";
                html += "\tvar __close ;\n";
                html += "\tvar __fontFamily ;\n";

                html += _initCalJsVar.toString() + "\n";//�滻����
		html += _createRunqianCalendar.toString() + "\n";
		html += _createIframeSyntax.toString() + "\n";
		html += _showCalendar.toString() + "\n";
		html += _funMonthSelect.toString() + "\n";
		html += _prevMonth.toString() + "\n";
		html += _nextMonth.toString() + "\n";
		html += _prevYear.toString() + "\n";
		html += _nextYear.toString() + "\n";
		html += _hiddenSelect.toString() + "\n";
		html += _hiddenCalendar.toString() + "\n";
		html += _appendZero.toString() + "\n";
		html += _trimString.toString() + "\n";
		html += _dayMouseOver.toString() + "\n";
		html += _dayMouseOut.toString() + "\n";
		html += _writeCalendar.toString() + "\n";
		html += _returnDate.toString() + "\n";
		html += _changeYear.toString() + "\n";
        html += _getDateWithFormat() + "\n";
	} catch( e ) {}
	try {
		html += tree_iconClick.toString() + "\n";
		html += tree_mouseOver.toString() + "\n";
		html += tree_mouseOut.toString() + "\n";
		html += tree_select.toString() + "\n";
		html += tree_show.toString() + "\n";
		html += tree_hide.toString() + "\n";
	} catch( e ) {}
	try {
		html += _reportScroll.toString() + "\n";
		html += _tableScrolling.toString() + "\n";
		html += _resizeScroll.toString() + "\n";
		html += _lookupDiv.toString() + "\n";
	} catch( e ) {}
	try {
		html += _rqDataSet.toString() + "\n";
		html += _setRQColNames.toString() + "\n";
		html += _setRQColTypes.toString() + "\n";
		html += _appendRQData.toString() + "\n";
		html += _filterRQDS.toString() + "\n";
	} catch( e ) {}
	html += "</scr" + "ipt>\n";
	return html;
}

function _saveToLocal( table, name ) {
	if( ! _submitEditor( table ) ) return;
	var html = new StringBuffer();
	html.append( "<html>\n<body>\n" );
	html.append( "<script language=javascript>" );
	if( eval( table.id + "_validOnSubmit" ) ) {
		html.append( "var " ).append( table.id ).append( "_validOnSubmit = true;\n" );
	}
	else html.append( "var " ).append( table.id ).append( "_validOnSubmit = false;\n" );
	html.append( "</scr" ).append( "ipt>\n" );
	html.append( _getScriptFunctions() );
	/*try {
		while( my___select.length ) {
			my___select[0].outerHTML = "";
		}
		my___select.outerHTML = "";
	}catch( e ) {}
	try {
		while( _runqianCalendar.length ) {
			_runqianCalendar[0].outerHTML = "";
		}
		_runqianCalendar.outerHTML = "";
	}catch( e ) {}*/
	var div = document.getElementById( "div_" + table.id );
	if( div != null ) html.append( div.outerHTML + "\n" );
	else html.append( document.body.innerHTML + "\n" );
	html.append( "<SCRIPT language=javascript>\n" );
	html.append( "\tvar batImport = document.location.href.indexOf( \"?batImport\" ) > 0;\n" );
	html.append( "\tvar resultInfoPage = parent.location.href.substring( 0, parent.location.href.lastIndexOf( \"/\" ) ) + \"/getResultInfo.html\";\n" );
	html.append( "\tif( batImport ) _submitTable( " + table.id + ", resultInfoPage );\n" );
	html.append( "</SCR" + "IPT>\n" );
	html.append( "</body>\n</html>" );
	try {
		var fso = new ActiveXObject( "Scripting.FileSystemObject" );
		//if( ! eval( table.id + "_checkValid()" ) ) return;  //������Ч�Լ��
		var fileDialog;
		try {
			fileDialog = eval( "_fileDialog" );
		}catch( exception ) {
			document.body.insertAdjacentHTML( "AfterBegin", "<object id=\"_fileDialog\" width=\"0px\" height=\"0px\" classid=\"clsid:F9043C85-F6F2-101A-A3C9-08002B2F49FB\" codebase=\"http://activex.microsoft.com/controls/vb5/comdlg32.cab\"></object>" );
			fileDialog = eval( "_fileDialog" );
		}
	  	fileDialog.Filter = "HTML Files (*.html) | *.html";
		fileDialog.filename = name;
		try {
			fileDialog.ShowSave();
		}catch( e ) {
			fileDialog.filename = window.prompt( __SS, name );
		}
		try {
			var f = fso.CreateTextFile( fileDialog.filename, true );
			f.writeLine( html );
			f.Close();
		}catch( e ) { alert( __TT + fileDialog.filename ); }
	}catch( exception ) {
		var form = document.getElementById( table.id + "_saveToLocalForm" );
		form.fileContent.value = html.toString();
		form.saveAsName.value = name;
		form.submit();
	}
}

function _tabClicked( tab, sheetName ) {
	var tabs = document.getElementById( "tabs_" + sheetName );
	var currTab = document.getElementById( tabs.getAttribute( "currTab" ) );
	if( ! _submitEditor( document.getElementById( currTab.getAttribute( "report" ) ) ) ) return;
	currTab.style.backgroundColor = tabs.getAttribute( "bkColor" );
	currTab.style.color = tabs.getAttribute( "fontColor" );
	tab.style.backgroundColor = tabs.getAttribute( "bkHLColor" );
	tab.style.color = tabs.getAttribute( "fontHLColor" );
	document.getElementById( "div_" + currTab.getAttribute( "report" ) ).style.display = "none";
	document.getElementById( "div_" + tab.getAttribute( "report" ) ).style.display = "block";
	tabs.setAttribute( "currTab", tab.id );
}

function _submitSheets( sheets, resultInfoPage ) {
	var tabs = document.getElementById( "tabs_" + sheets.id );
	if( ! _submitEditor( document.getElementById( document.getElementById( tabs.getAttribute( "currTab" ) ).getAttribute( "report" ) ) ) ) return;
	var tables = sheets.getAttribute( "tables" ).split( "," );
	for( var i = 0; i < tables.length; i++ ) {
		if( ! eval( tables[i] + "_checkValid()" ) ) {  //����������Ч�Լ��
			try {
				if( batImport ) {
					parent.getResultInfo( "error", __II );
				}
			}catch(ex){}
			return;
		}
	}
	try {
		if( ! eval( sheets.id + "_sheetsValid()" ) ) {   //���������Ч�Լ��
			try {
				if( batImport ) {
					parent.getResultInfo( "error", __II );
				}
			}catch(ex){}
			return;
		}
	}catch( exception ) {}
	try {    //�û��Զ�����Ч�Լ��
		if( ! eval( sheets.id + "_userDefineValidScript()" ) ) return;
	}catch( exception ) {}

	var form = document.getElementById( sheets.id + "_submitForm" );
	for( var i = 0; i < tables.length; i++ ) {
		var table = document.getElementById( tables[i] );
		var data = "", originData = "";
		for( var row = 0; row < table.rows.length; row++ ) {
			var currRow = table.rows[ row ];
			for( var col = 0; col < currRow.cells.length; col++ ) {
				var currCell = currRow.cells[ col ];
				var name = currCell.id;
				name = name.substring( name.lastIndexOf( "_" ) + 1 );
				var value = currCell.getAttribute( "value" );
				if( value == null ) value = "";
				if( currCell.getAttribute( "keyCell" ) ) {
					if( data.length > 0 ) data += ";";
					data += name + "=" + _addEscape( value );
					var origin = currCell.getAttribute( "originValue" );
					if( origin == null ) origin = "";
					if( originData.length > 0 ) originData += ";";
					originData += name + "=" + _addEscape( origin );
				}
				else {
					var needSubmit = false;
					switch( table.getAttribute( "submitCells" ) ) {
						case "0": if( currCell.getAttribute( "updatable" ) ) needSubmit = true; break;
						case "1": if( currCell.getAttribute( "updatable" ) || currCell.getAttribute( "modifiable" ) ) needSubmit = true; break;
						case "2": needSubmit = true; break;
					}
					if( needSubmit ) {
						if( data.length > 0 ) data += ";";
						data += name + "=" + _addEscape( value );
					}
				}
			}
		}
		form.elements[ tables[i] + "_data" ].value = data;
		form.elements[ tables[i] + "_originData" ].value = originData;
	}
	try {
		if( batImport ) {
			form.pageUrl.value = resultInfoPage;
			form.backAndRefresh.value = "batImport";
		}
	}catch( e ) {}
	form.submit();
}

function _saveSheetsToLocal( sheets, name ) {
	var tabs = document.getElementById( "tabs_" + sheets.id );
	if( ! _submitEditor( document.getElementById( document.getElementById( tabs.getAttribute( "currTab" ) ).getAttribute( "report" ) ) ) ) return;
	var html = "<html>\n<body>\n";
	html += _getScriptFunctions();
	/*try {
		while( my___select.length ) {
			my___select[0].outerHTML = "";
		}
		my___select.outerHTML = "";
	}catch( exception ) {}*/
	html += sheets.outerHTML + "\n";
	html += "<SCRIPT language=javascript>\n";
	html += "\tvar batImport = document.location.href.indexOf( \"?batImport\" ) > 0;\n";
	html += "\tvar resultInfoPage = parent.location.href.substring( 0, parent.location.href.lastIndexOf( \"/\" ) ) + \"/getResultInfo.html\";\n";
	html += "\tif( batImport ) _submitSheets( " + sheets.id + ", resultInfoPage );\n";
	html += "</SCR" + "IPT>\n";
	html += "</body>\n</html>";
	try {
		var fso = new ActiveXObject( "Scripting.FileSystemObject" );
		var fileDialog;
		try {
			fileDialog = eval( "_fileDialog" );
		}catch( exception ) {
			document.body.insertAdjacentHTML( "AfterBegin", "<object id=\"_fileDialog\" width=\"0px\" height=\"0px\" classid=\"clsid:F9043C85-F6F2-101A-A3C9-08002B2F49FB\" codebase=\"http://activex.microsoft.com/controls/vb5/comdlg32.cab\"></object>" );
			fileDialog = eval( "_fileDialog" );
		}
	  	fileDialog.Filter = "HTML Files (*.html)|*.html";
		fileDialog.filename = name;
		try {
			fileDialog.ShowSave();
		}catch( e ) {
			fileDialog.filename = window.prompt( __SS, name );
		}
		try {
			var f = fso.CreateTextFile( fileDialog.filename, true );
			f.writeLine( html );
			f.Close();
		}catch( e ) { alert( __TT + fileDialog.filename ); }
	}catch( exception ) {
		var form = document.getElementById( sheets.id + "_saveToLocalForm" );
		form.fileContent.value = html;
		form.saveAsName.value = name;
		form.submit();
	}
}

function _toDate( s ) {
	var re = /\./g;
	s = s.replace( re, "/" );
	re = /-/g;
	s = s.replace( re, "/" );
	var ymr = s.split( "/" );
	return new Date( ymr[1] + "-" + ymr[2] + "-" + ymr[0] );
}

function _paste( event ) {
	var s = _getClipboard();
	if( s == null ) return true;
	while( s.length > 0 ) {
		var c = s.charAt( s.length - 1 );
		if( c == '\n' || c == '\r' || c == '\t' ) s = s.substring( 0, s.length - 1 );
		else break;
	}
	var pos = s.indexOf( "\n" );
	if( pos < 0 ) {
		pos = s.indexOf( "\t" );
		if( pos < 0 ) return true;
	}
	var re = /\r/g;
	s = s.replace( re, "" );
	var editor = event.target;
	var currCell = editor.editingCell;
	var table = _lookupTable( currCell );
	var cid = currCell.id.substring( currCell.id.lastIndexOf( "_" ) + 1 );
	var rc = _toRowCol( cid );
	var row = rc[0], col = rc[1];
	var rows = s.split( "\n" );
	var oldCurrCell = table.currCell;
	if( table.getAttribute( "isli" ) ) {
		var oldLi_currCell = li_currCell;
		var index = currCell.parentNode.rowIndex;
		for( var i = 0; i < rows.length; i++ ) {
			var rowIndex = index + i;
			var rowObj = table.rows[rowIndex];
			if( currCell.parentNode.isDetail && ( rowObj == null || !rowObj.isDetail ) ) {
				_appendRow( table );
				try{ _submitEditor( table ); }catch(ex){}
				rowObj = table.rows[rowIndex];
			}
			if( rowObj == null ) continue;
			var rowid = rowObj.id;
			for( var k = rowid.length - 1; k >= 0; k-- ) {
				var ss = rowid.substring( k );
				if( isNaN( ss ) ) {
					pos = k + 1;
					break;
				}
			}
			row = parseInt( rowid.substring( pos ) );
			var cols = rows[i].split( "\t" );
			for( var j = 0; j < cols.length; j++ ) {
				var cell = document.getElementById( table.id + "_" + _toExcelNotation( row, col + j ) );
				if( cell == null || !cell.writable ) continue;
				table.currCell = cell;
				li_currCell = cell;
				if( !_setEditingValue2( cell, cols[j] ) ) {
					_bindingEditor( cell );
					return false;
				}
				if( cell == editor.editingCell ) editor.value = cols[j];
			}
		}
		li_currCell = oldLi_currCell;
	}
	else {
		for( var i = 0; i < rows.length; i++ ) {
			var cols = rows[i].split( "\t" );
			for( var j = 0; j < cols.length; j++ ) {
				var cell = document.getElementById( table.id + "_" + _toExcelNotation( row + i, col + j ) );
				if( cell == null || !cell.writable ) continue;
				table.currCell = cell;
				if( !_setEditingValue2( cell, cols[j] ) ) {
					_bindingEditor( cell );
					return false;
				}
				if( i==0 && j == 0 ) editor.value = cols[j];
			}
		}
	}
	table.currCell = oldCurrCell;
	_bindingEditor( oldCurrCell );
	return false;
}

function _getClipboard() {
   if (window.clipboardData) {
      return(window.clipboardData.getData('Text'));
   }
   else if (window.netscape) {
      netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
      var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
      if (!clip) return;
      var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
      if (!trans) return;
      trans.addDataFlavor('text/unicode');
      clip.getData(trans,clip.kGlobalClipboard);
      var str = new Object();
      var len = new Object();
      try {
         trans.getTransferData('text/unicode',str,len);
      }
      catch(error) {
         return null;
      }
      if (str) {
         if (Components.interfaces.nsISupportsWString) str=str.value.QueryInterface(Components.interfaces.nsISupportsWString);
         else if (Components.interfaces.nsISupportsString) str=str.value.QueryInterface(Components.interfaces.nsISupportsString);
         else str = null;
      }
      if (str) {
         return(str.data.substring(0,len.value / 2));
      }
   }
   return null;
} 
