package com.liu.demo.project.domain.modal;

import java.util.Date;
import java.util.List;

/**
 * @author Liush
 * @description 项目旧模型
 * @date 2019/9/6 10:59
 **/
public class ProjectEOld {

    //项目ID
    private String projectId;

    //项目名
    private String name;

    //项目开始日期
    private Date beginDate;

    //子项目
    private List<ItemE> items;


    public ProjectEOld(String projectId, String name, Date beginDate, List<ItemE> items) {
        this.projectId = projectId;
        this.name = name;
        this.beginDate = beginDate;
        this.items = items;
    }

    //创建子项目
    public void createItem(ItemE itemE){

        items.add(itemE);

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

    public List<ItemE> getItems() {
        return items;
    }
}
