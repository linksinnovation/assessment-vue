/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.model;

import co.th.linksinnovation.integrity.model.enumuration.ContentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import lombok.Data;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Entity
@Data
public class Course {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ManyToOne
    @JsonBackReference
    private Section section;
    private String content;
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    @Column(nullable = false)
    private Long duration = 0L;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateDate;
    private String uuid;
}
