/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Data
@Entity
public class AssessmentUser {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JsonBackReference
    private Assessment assessment;
    @ManyToOne
    @JsonIgnore
    private UserDetails user;
    private boolean pass = false;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @PrePersist
    public void createDate() {
        this.createDate = new Date();
    }

}
