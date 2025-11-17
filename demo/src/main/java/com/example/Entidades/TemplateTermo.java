package com.example.Entidades;
import java.util.Date;
public class TemplateTermo {
    private Date updateDateTime;
    private Date dataInicio;
    private Date createDateTime;
    private TemplateType templateType;

    
    public TemplateTermo(Date updateDateTime, Date dataInicio, Date createDateTime, TemplateType templateType) {
        this.updateDateTime = updateDateTime;
        this.dataInicio = dataInicio;
        this.createDateTime = createDateTime;
        this.templateType = templateType;
    }
    
    public Date getUpdateDateTime() {
        return updateDateTime;
    }
    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
    public Date getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    public Date getCreateDateTime() {
        return createDateTime;
    }
    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
    public TemplateType getTemplateType() {
        return templateType;
    }
    public void setTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }

    

}
