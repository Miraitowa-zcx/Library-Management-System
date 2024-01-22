package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BookPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Book;
import com.example.springboot.service.IBookService;
import com.example.springboot.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * 书籍控制器
 *
 * @author 20383
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    private final IBookService bookService;

    /**
     * 构造函数，注入书籍服务
     *
     * @param bookService 书籍服务
     */
    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    private static final String BASE_FILE_PATH = System.getProperty("user.dir") + "/files/";

    /**
     * 文件上传
     *
     * @param file 要上传的文件
     * @return 上传结果
     */
    @PostMapping("/file/upload")
    public Result uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            return Result.error("文件名不能为空");
        }
        long flag = System.currentTimeMillis();
        String filePath = BASE_FILE_PATH + flag + "_" + originalFilename;
        try {
            FileUtil.mkParentDirs(filePath);
            file.transferTo(FileUtil.file(filePath));
            Admin currentAdmin = TokenUtils.getCurrentAdmin();
            if (currentAdmin != null) {
                String token = TokenUtils.genToken(currentAdmin.getId().toString(), currentAdmin.getPassword(), 15);
                String url = "http://localhost:9090/api/book/file/download/" + flag + "?token=" + token;
                String[] suffix = {"png", "jpg", "jpeg", "pdf"};
                int nums = 0;
                for (String s : suffix) {
                    if (originalFilename.endsWith(s)) {
                        nums++;
                        break;
                    }
                }
                if (nums == 0) {
                    return Result.error("文件格式不支持");
                } else {
                    url += "&play=1";
                    return Result.success(url);
                }
            } else {
                return Result.error("出错了！！");
            }
        } catch (Exception e) {
            log.error("文件上传失败", e);
        }
        return Result.error("文件上传失败");
    }

    /**
     * 文件下载
     *
     * @param flag     文件标识
     * @param play     是否播放
     * @param response 响应对象
     */
    @GetMapping("/file/download/{flag}")
    public void downloadFile(@PathVariable String flag, @RequestParam(required = false) String play, HttpServletResponse response) {
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(BASE_FILE_PATH);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                String realName = fileName.substring(fileName.indexOf("_") + 1);
                if ("1".equals(play)) {
                    response.addHeader("Content-Disposition", "inline);filename=" + URLEncoder.encode(realName, "UTF-8"));
                } else {
                    response.addHeader("Content-Disposition", "attachment);filename=" + URLEncoder.encode(realName, "UTF-8"));
                }
                byte[] bytes = FileUtil.readBytes(FileUtil.file(BASE_FILE_PATH + fileName));
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }

    /**
     * 添加书籍
     *
     * @param book 书籍对象
     * @return 添加结果
     */
    @PostMapping("/save")
    public Result save(@RequestBody Book book) {
        bookService.save(book);
        return Result.success();
    }

    /**
     * 更新书籍信息
     *
     * @param book 书籍对象
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        bookService.update(book);
        return Result.success();
    }

    /**
     * 根据ID删除书籍
     *
     * @param id 书籍ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        bookService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据ID查询书籍
     *
     * @param id 书籍ID
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        return Result.success(book);
    }

    /**
     * 全部查询
     *
     * @return 查询结果
     */
    @GetMapping("/list")
    public Result list() {
        List<Book> list = bookService.list();
        return Result.success(list);
    }

    /**
     * 模糊分页查询
     *
     * @param bookPageRequest 分页请求对象
     * @return 查询结果
     */
    @GetMapping("/page")
    public Result page(BookPageRequest bookPageRequest) {
        return Result.success(bookService.page(bookPageRequest));
    }
}
