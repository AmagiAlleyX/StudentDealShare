package com.student.dealshare.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应结果
 *
 * @param <T> 数据类型
 * @author student-deal-share
 * @since 1.0.0
 */
@Data
@Schema(description = "分页响应结果")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "数据列表")
    private List<T> list;

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "当前页码")
    private Integer page;

    @Schema(description = "每页数量")
    private Integer size;

    @Schema(description = "总页数")
    private Integer pages;

    /**
     * 构造函数
     *
     * @param list  数据列表
     * @param total 总记录数
     * @param page  当前页码
     * @param size  每页数量
     */
    public PageResult(List<T> list, Long total, Integer page, Integer size) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.size = size;
        this.pages = (int) Math.ceil((double) total / size);
    }

    /**
     * 空的分页结果
     *
     * @param page 当前页码
     * @param size 每页数量
     * @return 分页结果
     */
    public static <T> PageResult<T> empty(Integer page, Integer size) {
        return new PageResult<>(List.of(), 0L, page, size);
    }
}
