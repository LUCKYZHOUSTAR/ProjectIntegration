/**
 * 
 */
package com.zhou.test.dataSource.dao.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author LUCKY
 *
 */
public class Depart {

    private int    depNo;
    private String depName;
    private String depMan;

    /**
     * @return the depNo
     */
    public int getDepNo() {
        return depNo;
    }

    /**
     * @param depNo the depNo to set
     */
    public void setDepNo(int depNo) {
        this.depNo = depNo;
    }

    /**
     * @return the depName
     */
    public String getDepName() {
        return depName;
    }

    /**
     * @param depName the depName to set
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

    /**
     * @return the depMan
     */
    public String getDepMan() {
        return depMan;
    }

    /**
     * @param depMan the depMan to set
     */
    public void setDepMan(String depMan) {
        this.depMan = depMan;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
