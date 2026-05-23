package com.hongyun.entity;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Target {
    private Integer id;
    private Integer userId;

    @NotBlank(message = "目标名称不能为空")
    @Size(max = 30, message = "目标名称长度不能超过30个字符")
    private String name;

    @NotBlank(message = "目标描述不能为空")
    @Size(max = 30, message = "目标描述长度不能超过30个字符")
    private String description;

    private String createTime;

    @NotNull(message = "提醒天数不能为空")
    private Integer day;
}