function _rqDataSet( valueCol, dispCol ) {
	this.valueCol = valueCol;
	this.dispCol = dispCol;
	this.valueColNum = 0;
	this.dispColNum = 0;
	this.rows = new Array();

	this.setColNames = _setRQColNames;
	this.setColTypes = _setRQColTypes;
	this.appendData = _appendRQData;
	this.filter = _filterRQDS;
}

function _setRQColNames( names ) {
	this.colNames = names.split( "," );
	for( var i = 0; i < this.colNames.length; i++ ) {
		if( this.colNames[i] == this.valueCol ) this.valueColNum = i;
		if( this.colNames[i] == this.dispCol ) this.dispColNum = i;
	}
}

function _setRQColTypes( types ) {
	this.colTypes = types.split( "," );
}

function _appendRQData( datas ) {
	var cells = datas.split( "\t" );
	this.rows[ this.rows.length ] = cells;
}

function _filterRQDS( exp ) {
	var s = "";
	var list = new Array();
	for( var i = 0; i < this.colNames.length; i++ ) {
		try{ eval( "var " + this.colNames[i] + ";" ); }catch(ex){}
	}
	for( var i = 0; i < this.rows.length; i++ ) {
		var cells = this.rows[i];
		for( var j = 0; j < cells.length; j++ ) {
			if( this.colTypes[j] == "1" ) {
				try{ eval( this.colNames[j] + " = \"" + cells[j] + "\";" ); }catch(ex){}
			}
			else {
				var colValue = cells[j];
				if( colValue == null || colValue.length == 0 ) colValue = "0";
				try{ eval( this.colNames[j] + " = " + colValue + ";" ); }catch(ex){}
			}
		}
		if( eval( exp ) ) {
			var s1 = cells[ this.valueColNum ] + "," + cells[ this.dispColNum ];
			var found = false;
			for( var k = 0; k < list.length; k++ ) {
				if( list[k] == s1 ) {
					found = true;
					break;
				}
			}
			if( !found ) {
				list.push( s1 );
				if( s.length > 0 ) s += ";";
				s += s1;
			}
		}
	}
	return s;
}

