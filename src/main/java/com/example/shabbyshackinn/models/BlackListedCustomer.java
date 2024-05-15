package com.example.shabbyshackinn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlackListedCustomer {

    public long id;
    public String email;
    public String name;
    public String group;
    public Date created;
    public boolean ok;

    public BlackListedCustomer(long id, String email, String name, boolean ok) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.ok = ok;
    }
}
