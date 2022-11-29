package com.example.clouddisk.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;

/**
 * @author R.Q.
 */
@Data
@Table(name="file")
@Entity
@TableName("file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type= IdType.AUTO)
    @Column(columnDefinition="bigint(20) comment '文件id'")
    private Long fileId;

    @Column(columnDefinition="varchar(500) comment '时间戳名称'")
    private String timeStampName;

    @Column(columnDefinition="varchar(500) comment '文件url'")
    private String fileUrl;

    @Column(columnDefinition="bigint(10) comment '文件大小'")
    private Long fileSize;
}
