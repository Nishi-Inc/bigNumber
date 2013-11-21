/*
 * ========================= DECLARATION ===============================
 * NOTE: THIS IS NOT TO BE REMOVED IN ANY MODIFICATION & DISTRIBUTION
 * OF THIS CLASS.
 * 
 * Author: Nishi Inc.
 * Conceptualized by: Alok Shukla
 * Made available for free by: Nishi Inc. 
 * =====================================================================
 */

package org.bigNumber.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import org.bigNumber.common.interfaces.NonStaticMethods;
import org.bigNumber.common.services.GlobalConstants;
import org.bigNumber.common.interfaces.MathematicalMethods;
import org.bigNumber.common.interfaces.UtilityMethods;

/**
 * Parent class for BigNumber
 * @author Nishi Inc.
 * @since v1.1.0
 */
public abstract class BigNumberParent implements NonStaticMethods {

	protected static final long serialVersionUID = 1L;

    protected BigDecimal bigDecimal;
    protected BigNumberMathContext defaultBigNumberMathContext;

    protected BigDecimal getBigDecimal() {
        if(bigDecimal == null) {
            this.setBigDecimal(new BigDecimal(0));
        }
        return bigDecimal;
    }

    public BigNumberMathContext getDefaultMathContext() {
        return defaultBigNumberMathContext;
    }

    public void setDefaultMathContext(BigNumberMathContext defaultMathContext) {
        this.defaultBigNumberMathContext = defaultMathContext;
    }

    protected void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
        this.syncFromDecimal();
    }

    protected abstract void syncFromDecimal();

}
