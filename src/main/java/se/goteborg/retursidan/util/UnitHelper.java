package se.goteborg.retursidan.util;

import se.goteborg.retursidan.model.entity.Unit;

/**
 * Help class for returning current date. Compensates 1 hour time diff from server.
 * 
 * @author BJOTOR1216
 *
 */

public class UnitHelper {
	
	public static Unit getEmptyUnit() {
		Unit emptyUnit = new Unit();
		emptyUnit.setId(-1);
		emptyUnit.setAdministrationId("0");
		emptyUnit.setName("Välj förvaltning");
		return emptyUnit;
	} 
}
