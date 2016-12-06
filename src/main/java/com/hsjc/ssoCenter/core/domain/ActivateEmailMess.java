package com.hsjc.ssoCenter.core.domain;

import java.io.Serializable;

/**
 * @author : zga
 * @date : 2015-12-03
 *
 * Email激活实体
 */
public class ActivateEmailMess implements Serializable {
    private String email;

    private String ticket;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
