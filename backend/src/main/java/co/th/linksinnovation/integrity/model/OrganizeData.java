/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.model;

import com.univocity.parsers.annotations.Parsed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Entity
@Data
public class OrganizeData {
    @Id
    @GeneratedValue
    private Integer id;
    @Parsed(index = 8)
    private String organizeId;
    @Parsed(index = 9)
    private String organizeName;
    @Parsed(index = 11)
    private String businessId;
    @Parsed(index = 12)
    private String businessName;
    @Parsed(index = 14)
    private String groupId;
    @Parsed(index = 15)
    private String groupName;
    @Parsed(index = 17)
    private String fieldId;
    @Parsed(index = 18)
    private String fieldName;
    @Parsed(index = 20)
    private String areaId;
    @Parsed(index = 21)
    private String areaName;
    @Parsed(index = 23)
    private String partyId;
    @Parsed(index = 24)
    private String partyName;
    @Parsed(index = 54)
    private String location;
    @Parsed(index = 60)
    private String username;
    @Parsed(index = 0)
    private String employeeId;
}
