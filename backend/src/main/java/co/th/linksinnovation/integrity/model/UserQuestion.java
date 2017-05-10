/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UserQuestion {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JsonIgnore
    private UserDetails user;
    @ManyToOne
    private Question question;
    private Integer selected;
    private boolean checked;
}
