package com.zhou.domain.vo;

import com.zhou.domain.entity.Role;
import com.zhou.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShowVO {
    List<Long> roleids;
    List<Role>roles;
    UserVo user;
}
