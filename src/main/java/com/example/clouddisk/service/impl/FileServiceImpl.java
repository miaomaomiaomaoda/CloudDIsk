package com.example.clouddisk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.clouddisk.mapper.FileMapper;
import com.example.clouddisk.model.File;
import com.example.clouddisk.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author R.Q.
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,File> implements FileService {
}
