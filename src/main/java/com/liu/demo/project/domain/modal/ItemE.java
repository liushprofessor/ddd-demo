package com.liu.demo.project.domain.modal;

/**
 * @author Liush
 * @description 项目实体
 * @date 2019/9/6 10:49
 **/
public class ItemE {

    //工程id
    private String projectId;

    //项目实体
    private String itemId;

    //项目名
    private String name;

    public ItemE(String itemId, String name) {
        this.itemId = itemId;
        this.name = name;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getProjectId() {
        return projectId;
    }
}
