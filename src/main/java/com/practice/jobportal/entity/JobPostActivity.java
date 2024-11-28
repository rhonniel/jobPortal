package com.practice.jobportal.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class JobPostActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobPostId;

    @ManyToOne
    @JoinColumn(name = "postedById", referencedColumnName = "userId")
    private Users usersId;

    @ManyToOne( cascade =  CascadeType.ALL)
    @JoinColumn(name ="jobLocationId", referencedColumnName = "Id")
    private JobLocation jobLocationId;

    @ManyToOne( cascade =  CascadeType.ALL)
    @JoinColumn(name ="jobCompanyId", referencedColumnName = "Id")
    private JobCompany jobCompanyId;

    @Transient
    private Boolean isSaved;

    @Length(max = 10000)
    private String jobType;
    private String salary;
    private String remote;

    private String descriptionOfJob;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date postedDate;

    private String JobTitle;


    public JobPostActivity() {
    }

    public JobPostActivity(Integer jobPostId, Users usersId, JobLocation jobLocationId, JobCompany jobCompanyId, Boolean isSaved, String jobType, String salary, String remote, Date postedDate, String jobTitle, String descriptionOfJob) {
        this.jobPostId = jobPostId;
        this.usersId = usersId;
        this.jobLocationId = jobLocationId;
        this.jobCompanyId = jobCompanyId;
        this.isSaved = isSaved;
        this.jobType = jobType;
        this.salary = salary;
        this.remote = remote;
        this.descriptionOfJob= descriptionOfJob;
        this.postedDate = postedDate;
        JobTitle = jobTitle;
    }

    public String getDescriptionOfJob() {
        return descriptionOfJob;
    }

    public void setDescriptionOfJob(String descriptionOfJob) {
        this.descriptionOfJob = descriptionOfJob;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }

    public Users getUsersId() {
        return usersId;
    }

    public void setUsersId(Users usersId) {
        this.usersId = usersId;
    }

    public JobLocation getJobLocationId() {
        return jobLocationId;
    }

    public void setJobLocationId(JobLocation jobLocationId) {
        this.jobLocationId = jobLocationId;
    }

    public JobCompany getJobCompanyId() {
        return jobCompanyId;
    }

    public void setJobCompanyId(JobCompany jobCompanyId) {
        this.jobCompanyId = jobCompanyId;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "JobPostActivity{" +
                "jobPostId=" + jobPostId +
                ", usersId=" + usersId +
                ", jobLocationId=" + jobLocationId +
                ", jobCompanyId=" + jobCompanyId +
                ", isSaved=" + isSaved +
                ", jobType='" + jobType + '\'' +
                ", descriptionOfJob='" + descriptionOfJob + '\''+
                ", salary='" + salary + '\'' +
                ", remote='" + remote + '\'' +
                ", postedDate=" + postedDate +
                ", JobTitle='" + JobTitle + '\'' +
                '}';
    }
}
