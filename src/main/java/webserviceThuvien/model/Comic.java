package webserviceThuvien.model;

import java.util.Date;

public class Comic {
    String id;
    String name;
    String description;
    String category;

    String tags;
    int followNo;
    int viewNo;
    Date lastUpdateTime;
    Date createDate;
    String image;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public void setFollowNo(int followNo) {
        this.followNo = followNo;
    }

    public void setViewNo(int viewNo) {
        this.viewNo = viewNo;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getTags() {
        return tags;
    }

    public int getFollowNo() {
        return followNo;
    }

    public int getViewNo() {
        return viewNo;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getImage() {
        return image;
    }
}
