/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Entity
@Data
public class Assessment {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    @Column(length = 4000)
    private String detail;
    @Column(length = 4000)
    private String objective;
    @JsonFormat(pattern="dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @JsonFormat(pattern="dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    private String cover = "default.jpg";
    @Min(0)
    private Integer repeatTime;
    @Min(0)
    @Max(100)
    private Integer passScore;
    @OneToMany(mappedBy = "assessment",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    @OrderBy("id ASC")
    private List<Question> questions;
    @OneToMany(mappedBy = "assessment",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    @OrderBy("id ASC")
    private List<Section> sections;
    private String organizeFile;
    @OneToMany(mappedBy = "assessment",cascade = CascadeType.ALL)
    @JsonIgnore
    @OrderBy("id ASC")
    private List<OrganizeData> organizeDatas;
    @OneToMany(mappedBy = "assessment",cascade = CascadeType.ALL)
    @OrderBy("id DESC")
    @JsonManagedReference
    private List<AssessmentUser> assessmentUsers;
    
    public void addAssessmentUser(AssessmentUser assessmentUser){
        if(this.assessmentUsers.isEmpty()){
            this.assessmentUsers = new ArrayList<>();
        }
        this.assessmentUsers.add(assessmentUser);
    }
}
