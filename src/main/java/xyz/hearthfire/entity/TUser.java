package xyz.hearthfire.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by fz on 2015/10/26.
 */
@Entity
@Table(name = "t_user")
public class TUser {
    private int id;
    private String userName;
    private Timestamp createTime;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "create_time", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TUser tUser = (TUser) o;

        if (id != tUser.id) return false;
        if (userName != null ? !userName.equals(tUser.userName) : tUser.userName != null) return false;
        if (createTime != null ? !createTime.equals(tUser.createTime) : tUser.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
