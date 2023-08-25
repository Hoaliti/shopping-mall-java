package com.rex.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 整合Mybatis-Plus
 * 1) 导入依赖
 * 2) 配置
 * 1. 配置数据源
 * application.yml
 * 2.
 * <p>
 * 2) 逻辑删除
 * 1. 配置全局的逻辑删除规则(省略)
 * 2. 配置逻辑删除的组件bean(省略)
 * 3. 给bean加上逻辑删除的注解
 *
 *
 * 3) JRS303
 *  1. use javax.validation.constraints, 并定义自己的message提示
 *  2. 开启检验功能@Valid
 *      效果：检验错误会有默认的返回
 *     跟着BindResult就能看到检验结果
 *
 *  3. 分组校验
 *      @NotNull(message = "Edit Brand must have brand id",groups = {UpdateGroup.class})
 *      Give Validation Annotation groups in which situation
 *      2. @Validated(AddGroup.class);
 *      3. 默认没有指定分组的检验注解@Notblank,在分组校验中无效
 *
 *  4. 自定义校验
 *      1). 编写一个自定义的检验注解
 *      2). 编写一个自定义的校验器
 *      3） 关联注解和校验器
 *
 *      @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
 *      @Retention(RetentionPolicy.RUNTIME)
 *      @Documented
 *      @Constraint(
 *         validatedBy = {ListValueConstraintValidator.class} // 可以指定多个校验器
 *      )
 * 4) 统一的异常处理
 * @Controller Advice
 *
 *
 */

@EnableFeignClients(basePackages = "com.rex.mall.product.feign")
@MapperScan("com.rex.mall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class MallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallProductApplication.class, args);
    }

}
