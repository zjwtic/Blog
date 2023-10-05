package com.zhou.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 35238
 * @date 2023/7/22 0022 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVO {

    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;

}
