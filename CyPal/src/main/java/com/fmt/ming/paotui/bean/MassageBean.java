/**
 *
 */
package com.fmt.ming.paotui.bean;

import java.io.Serializable;

/**
*
*  @Author luoming
*  @Date 2018/11/16 1:52 PM
*
*/
public class MassageBean implements Serializable {


    /**
     * uuid : 20181116213041sevU8b24562100
     * mtype : 0
     * to : 1
     * title : 撒旦发射点发生
     * content : dfgsdfgsdfgsdfgsdfgdsgdfg1111
     * sended_at : 2018-11-16 13:30:29
     * created_at : 1970-01-01 16:00:00
     * updated_at : 1970-01-01 16:00:00
     * deleted_at : null
     * isread : false
     */
//    uuid 消息编号
//    mtype 消息类型{0:骑手消息,1:用户消息}
//    to 接收者
//    title 标题
//    content 内容
//    sended_at 发送时间
//    created_at 创建时间
//    updated_at 更新时间
//    isread 是否已读

    private String uuid;
    private int mtype;
    private int to;
    private String title;
    private String content;
    private String sended_at;
    private String created_at;
    private String updated_at;
    private Object deleted_at;
    private boolean isread;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMtype() {
        return mtype;
    }

    public void setMtype(int mtype) {
        this.mtype = mtype;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSended_at() {
        return sended_at;
    }

    public void setSended_at(String sended_at) {
        this.sended_at = sended_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Object getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Object deleted_at) {
        this.deleted_at = deleted_at;
    }

    public boolean isIsread() {
        return isread;
    }

    public void setIsread(boolean isread) {
        this.isread = isread;
    }
}