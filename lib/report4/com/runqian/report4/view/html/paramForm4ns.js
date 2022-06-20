var __DDD = "����������ֵ��";
var __EEE = "��������Ч�����ڣ�";
var __FFF = "������ȫ����������ɵ��ַ�����";
var __GGG = "��������ȷ��Email��ַ��";
var __HHH = "����������ֵ��";
var __III = "��ѡ...";


_initParamFormVar();
function _checkDataType4Form( type, value ) {
	if( value.length == 0 ) return true;
	switch( type ) {
		case "1": return true;
		case "2":
			if( isNaN( value ) ) {
				alert( __DDD );
				return false;
			}
			return true;
		case "3":
			var r = value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
   			if( r == null ) {
				alert( __EEE );
   				return false;
   			}
   			var d = new Date( r[1], r[3]-1, r[4] );
   			if( ! ( d.getFullYear() == r[1] && ( d.getMonth()+1 ) == r[3] && d.getDate() == r[4] ) ) {
				alert( __EEE );
   				return false;
   			}
			return true;
		case "4":
			if( isNaN( value ) ) {
				alert( __FFF );
				return false;
			}
			return true;
		case "5":
			var pos = value.indexOf( "@" );
			if( pos < 1 || pos == value.length - 1 ) {
				alert( __GGG );
				return false;
			}
			return true;
		case "6":
			if( isNaN( value ) || value.indexOf( "." ) >= 0 ) {
				alert( __HHH );
				return false;
			}
			return true;
	}
	return true;
}

function _submit( form ) {
	_processMultiSelect( form, form );
	if( ! eval( form.name + "_checkValid()" ) ) return;
	var o = new Array();
	for( var i = 0; i < form.elements.length; i++ ) {
		var obj = form.elements[i];
		if( obj.getAttribute( "emptyIsNull" ) && obj.value == "" ) {
			if( obj.tagName != "SELECT" ) {
				obj.value = "_null_";
				o[o.length] = obj;
			}
			else {
				obj.options[0].value = "_null_";
				o[o.length] = obj.options[0];
			}
		}
	}
	form.submit();
	for( var i = 0; i < o.length; i++ ) o[i].value = "";
}

function _processMultiSelect( selectObj, form ) {
	var len = selectObj.childNodes.length;
	if( len == 0 ) return;
	for( var i = 0; i < len; i++ ) {
		var obj = selectObj.childNodes[i];
		if( obj.tagName == "SELECT" ) {
			var name = obj.id;
			if( name.indexOf( "multi_" ) == 0 ) {
				name = name.substring( 6 );
				var s = _getSelectedValues( obj );
				form.elements[ name ].value = s;
			}
		}
		else _processMultiSelect( obj, form );
	}
}

function _getSelectedValues( selectObj ) {
	if( selectObj.tagName != "SELECT" ) return "";
	var s = "";
	for( var i = 0; i < selectObj.length; i++ ) {
		var obj = selectObj.item( i );
		if( obj.selected ) s += "," + obj.value;
	}
	if( s.length > 0 ) s = s.substring( 1 );
	return s;
}

function _hideDropDown() {
	try {
		_hiddenCalendar();
	}catch( e ) {}
	try {
		tree_hide();
	}catch( e ) {}
	if( document.body._jsHideFuncs != null ) {   //�����Զ���༭�ؼ�
		for( var i = 0; i < document.body._jsHideFuncs.length; i++ ) {
			try { eval( document.body._jsHideFuncs[i] ); }catch( e ) {}
		}
	}
}

function _onFilter() {
	var obj;
	if( document.all ) obj = event.srcElement;
	else obj = arguments[0].target;
	_rqBoxFilter( obj );
}

function _rqBoxFilter( obj ) {
	var filterCells = obj.filterCells;
	if( filterCells != null ) {
		var fcs = filterCells.split( "," );
		for( var i = 0; i < fcs.length; i++ ) {
			var fc = document.getElementById( fcs[i] ).childNodes[0];
			var ds = eval( fc.getAttribute( "dataSet" ) );
			var editConfig = ds.filter( fc.getAttribute( "filterExp" ) );
			if( fc.tagName == "SELECT" ) {
				var oldValue = fc.getAttribute( "oldValue" );
				fc.options.length = 0;
				try{ __III = _ddboxEmptyLabel; }catch(ex){}
				if( fc.getAttribute( "canEmpty" ) == 1 ) fc.options.add( new Option( __III, "", false, false ), 0 );
				var configs = editConfig.split( ";" );
				var values = new Array();
				for( var j = 0; j < configs.length; j++ ) {
					var s = configs[j].split( "," );
					if( s[0] == "" ) continue;
					var idx = fc.options.length;
					var NewOption = new Option( s[1], s[0], false, false );
					fc.options.add( NewOption, idx );
					values[ values.length ] = s[0];
				}
				var autoSelect = true;
				try{ autoSelect = autoSelectWhileFilter; }catch(ex){}
				if( _valueExist( values, oldValue ) ) {
					if( autoSelect ) fc.value = oldValue;
					else fc.value = "";
				}
				else {
					if( configs.length == 1 && autoSelect ) {
						fc.value = configs[0].split(",")[0];
					}
					else fc.value = "";
				}
			}
			else {
				var box = fc.ddbox;
				var oldValue = box.getValue();
				box.clearOptions();
				try{ __III = _ddboxEmptyLabel; }catch(ex){}
				if( !box.isMulti && box.Table.canEmpty == 1 ) box.addOption( "", __III );
				var configs = editConfig.split( ";" );
				var values = new Array();
				for( var j = 0; j < configs.length; j++ ) {
					var s = configs[j].split( "," );
					if( s[0] == "" ) continue;
					box.addOption( s[0], s[1] );
					values[ values.length ] = s[0];
				}
				var autoSelect = true;
				try{ autoSelect = autoSelectWhileFilter; }catch(ex){}
				if( _valueExist( values, oldValue ) ) {
					if( autoSelect ) box.setValue( oldValue );
					else box.setValue( "" );
				}
				else {
					if( configs.length == 1 && autoSelect ) {
						box.setValue( configs[0].split(",")[0] );
					}
					else box.setValue( "" );
				}
				if( box.buttonTable != null ) {
					box.buttonTable.rows[0].cells[0].childNodes[0].checked = false;
				}
				try{ eval( box.onchange ); }catch( e ) {}
			}
		}
	}
}

function _valueExist( values, oldValue ) {
	var ov = oldValue.split( "," );
	for( var i = 0; i < ov.length; i++ ) {
		var found = false;
		for( var j = 0; j < values.length; j++ ) {
			if( values[j] == ov[i] ) {
				found = true;
				break;
			}
		}
		if( !found ) return false;
	}
	return true;
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
					_calcTbl( table, editingObj );
				}
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
		try{ eval( form.name + "_autoCalc()" ); }catch(e1){}
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
