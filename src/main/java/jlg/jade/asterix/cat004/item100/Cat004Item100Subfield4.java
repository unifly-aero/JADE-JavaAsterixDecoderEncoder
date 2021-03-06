/**
 * Created by Dan Geabunea on 6/28/2016.
 */

package jlg.jade.asterix.cat004.item100;


/**
 * Cat004 Item100 Subfield 4 - Runway/Taxiway Designator 2
 * Designator of Runway/Taxiway 2 Involved in a RIMCA
 *
 * @implSpec The runway designator is always left adjusted. If needed, the
 * remaining characters are filled with space characters.
 * The runway is encoded as follows: Location indicator, runway
 * direction, left or right.
 * Example: EGLL09L means London Heathrow (EGLL), Runway 09
 * (direction 090 degrees) left runway
 */
public class Cat004Item100Subfield4 extends Cat004Item100RimcaDesignatorData {
    @Override
    protected String setDisplayName() {
        return "Cat004Item100Subfield4 - Runway/Taxiway Designator 2";
    }
}
