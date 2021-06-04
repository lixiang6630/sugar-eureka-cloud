package com.common.base.util;

import com.common.base.exception.GunsException;
import com.common.base.exception.GunsExceptionEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 渲染工具类
 */
public class RenderUtil {

    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, String jsonStr) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(jsonStr);
        } catch (IOException e) {
            throw new GunsException(GunsExceptionEnum.WRITE_ERROR);
        }
    }
}
