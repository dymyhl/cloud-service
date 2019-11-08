package com.puvar.cloudservice.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/10/31
 * @version : 1.0
 */
@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String taskCount;
    private String delay48Count;
}
