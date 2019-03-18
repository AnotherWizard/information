package cn.edu.csu.information.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class NewsForm {

    @NotEmpty(message = "标题必填")
    private String title;

    @NotEmpty(message = "摘要必填")
    private String digest;

    @NotNull(message = "分类必选")
    private Integer categoryId;

    @NotNull(message = "未传图像")
    private MultipartFile indexImage;

    @NotEmpty(message = "内容不能为空")
    private String content;



}
