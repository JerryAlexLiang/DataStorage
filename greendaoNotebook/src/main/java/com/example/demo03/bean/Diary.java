package com.example.demo03.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 创建日期：2018/1/23 on 下午5:56
 * 描述:
 * 作者:yangliang
 */

@Entity
public class Diary {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String title;
    private String content;

    @Generated(hash = 1387660138)
    public Diary(Long id, @NotNull String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Generated(hash = 112123061)
    public Diary() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
