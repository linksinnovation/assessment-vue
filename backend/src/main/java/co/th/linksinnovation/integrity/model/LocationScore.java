/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.model;

import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Entity
@Data
public class LocationScore {
    @Id
    @GeneratedValue
    private Integer id;
    
    private String organizeName;
    private BigInteger organizeTotal = BigInteger.ZERO;
    private BigInteger organizePass = BigInteger.ZERO;
    private BigInteger organizeDone = BigInteger.ZERO;
    
    private String businessName;
    private BigInteger businessTotal = BigInteger.ZERO;
    private BigInteger businessPass = BigInteger.ZERO;
    private BigInteger businessDone = BigInteger.ZERO;
    
    private String groupName;
    private BigInteger groupTotal = BigInteger.ZERO;
    private BigInteger groupPass = BigInteger.ZERO;
    private BigInteger groupDone = BigInteger.ZERO;
    
    private String fieldName;
    private BigInteger fieldTotal = BigInteger.ZERO;
    private BigInteger fieldPass = BigInteger.ZERO;
    private BigInteger fieldDone = BigInteger.ZERO;
            
    private String areaName;
    private BigInteger areaTotal = BigInteger.ZERO;
    private BigInteger areaPass = BigInteger.ZERO;
    private BigInteger areaDone = BigInteger.ZERO;
    
    private String partyName;
    private BigInteger partyTotal = BigInteger.ZERO;
    private BigInteger partyPass = BigInteger.ZERO;
    private BigInteger partyDone = BigInteger.ZERO;
    
    @ManyToOne
    private Assessment assessment;
    
    public LocationScore incrementDone(){
        this.organizeDone = this.organizeDone.add(BigInteger.ONE);
        this.businessDone = this.businessDone.add(BigInteger.ONE);
        this.groupDone = this.groupDone.add(BigInteger.ONE);
        this.fieldDone = this.fieldDone.add(BigInteger.ONE);
        this.areaDone = this.areaDone.add(BigInteger.ONE);
        this.partyDone = this.partyDone.add(BigInteger.ONE);
        return this;
    }
    
    public LocationScore incrementPass(){
        this.organizePass = this.organizePass.add(BigInteger.ONE);
        this.businessPass = this.businessPass.add(BigInteger.ONE);
        this.groupPass = this.groupPass.add(BigInteger.ONE);
        this.fieldPass = this.fieldPass.add(BigInteger.ONE);
        this.areaPass = this.areaPass.add(BigInteger.ONE);
        this.partyPass = this.partyPass.add(BigInteger.ONE);
        return this;
    }
}
