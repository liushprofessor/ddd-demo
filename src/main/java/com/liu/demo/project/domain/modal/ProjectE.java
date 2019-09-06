package com.liu.demo.project.domain.modal;

import java.util.Date;

/**
 * @author Liush
 * @description 工程实体
 * @date 2019/9/6 10:49
 **/
public class ProjectE {

    //项目ID
    private String projectId;

    //项目名
    private String name;

    //项目开始日期
    private Date beginDate;


    /**
     *由于工程子项目属于工程，按照通用语言，工程子项目要由工程去穿件，这样保证了领域和业务模型的统一性
     */
    public ItemE createItem(String itemId,String name){
        return new ItemE(itemId,name);
    }


    public ProjectE(String projectId, String name, Date beginDate) {
        this.projectId = projectId;
        this.name = name;
        this.beginDate = beginDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public Date getBeginDate() {
        return beginDate;
    }
}
