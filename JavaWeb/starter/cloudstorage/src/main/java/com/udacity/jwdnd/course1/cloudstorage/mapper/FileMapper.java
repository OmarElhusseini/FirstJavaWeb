package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    File getFile(String fileName, Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{file})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadNewFIle(File fIle);

    @Delete("DELETE FROM FILES WHERE filename=#{fileName}")
    int deleteFileByFileName(String fileName);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllFiles(Integer userId);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    public int deleteFileByFileId(String fileid);
}
