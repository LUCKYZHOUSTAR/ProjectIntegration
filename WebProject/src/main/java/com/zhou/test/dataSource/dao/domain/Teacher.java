/**
 * 
 */
package com.zhou.test.dataSource.dao.domain;

import java.util.Date;

/**
 * @author LUCKY
 *
 */
public class Teacher {

    private int    tNo;
    private String tName;
    private String tSex;
    private Date   tBirthDay;
    private String tSalary;
    private String tHairDate;
    private int    depNo;

    /**
     * @return the tNo
     */
    public int gettNo() {
        return tNo;
    }

    /**
     * @param tNo the tNo to set
     */
    public void settNo(int tNo) {
        this.tNo = tNo;
    }

    /**
     * @return the tName
     */
    public String gettName() {
        return tName;
    }

    /**
     * @param tName the tName to set
     */
    public void settName(String tName) {
        this.tName = tName;
    }

    /**
     * @return the tSex
     */
    public String gettSex() {
        return tSex;
    }

    /**
     * @param tSex the tSex to set
     */
    public void settSex(String tSex) {
        this.tSex = tSex;
    }

    /**
     * @return the tBirthDay
     */
    public Date gettBirthDay() {
        return tBirthDay;
    }

    /**
     * @param tBirthDay the tBirthDay to set
     */
    public void settBirthDay(Date tBirthDay) {
        this.tBirthDay = tBirthDay;
    }

    /**
     * @return the tSalary
     */
    public String gettSalary() {
        return tSalary;
    }

    /**
     * @param tSalary the tSalary to set
     */
    public void settSalary(String tSalary) {
        this.tSalary = tSalary;
    }

    /**
     * @return the tHairDate
     */
    public String gettHairDate() {
        return tHairDate;
    }

    /**
     * @param tHairDate the tHairDate to set
     */
    public void settHairDate(String tHairDate) {
        this.tHairDate = tHairDate;
    }

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

}
