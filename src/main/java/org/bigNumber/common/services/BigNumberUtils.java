package org.bigNumber.common.services;

import org.bigNumber.common.interfaces.StaticMethods;

/**
 * @author aloks
 * Date: 21/11/13
 * Time: 3:35 PM
 * @since 2.0.0
 */
public final class BigNumberUtils implements StaticMethods {
    // TODO Keep two interfaces StaticMethods and NonStaticMethods
    // TODO #33 : BigNumberUtil will implement StaticMethods while BigNumberParent will implement NonStaticMethods which extends Serializable and Comparable<BigNumber>
    // TODO Make necessary changes in Tests
    // TODO Set default mathContext in BigNUmber constructor

    // Singleton
    private BigNumberUtils(){}

}
