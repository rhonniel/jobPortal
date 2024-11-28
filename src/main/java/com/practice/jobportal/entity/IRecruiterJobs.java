package com.practice.jobportal.entity;

//TODO OJO: la interfaz pued que se use porque asi puedes crear diferentes dto para la misma consulta y crear metodos nuevos
public interface IRecruiterJobs {

    Long getTotalCandidates();

    int getJob_post_id();
    int getLocationId();
    String getJob_title();

    String getCity();
    String getState();
    String getCountry();
    int getCompanyId();
    String getName();

}
