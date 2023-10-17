package com.zhou.controller;

import com.alibaba.excel.EasyExcel;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Category;
import com.zhou.domain.vo.ExcelCategoryVo;
import com.zhou.enums.AppHttpCodeEnum;
import com.zhou.service.CategoryService;
import com.zhou.domain.vo.CategoryVo;
import com.zhou.utils.BeanCopyUtils;
import com.zhou.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 35238
 * @date 2023/8/7 0007 14:28
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    //---------------------------写博文-查询文章分类的接口--------------------------------

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    //---------------------------把分类数据写入到Excel并导出-----------------------------
@PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    //注意返回值类型是void
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头，下载下来的Excel文件叫'分类.xlsx'
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> xxcategory = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(xxcategory, ExcelCategoryVo.class);
            //把数据写入到Excel中，也就是把ExcelCategoryVo实体类的字段作为Excel表格的列头
            //sheet方法里面的字符串是Excel表格左下角工作簿的名字
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("文章分类")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常,就返回失败的json数据给前端。AppHttpCodeEnum和ResponseResult是我们在huanf-framework工程写的类
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            //WebUtils是我们在huanf-framework工程写的类，里面的renderString方法是将json字符串写入到请求体，然后返回给前端
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}