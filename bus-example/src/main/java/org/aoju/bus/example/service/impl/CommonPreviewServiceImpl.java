package org.aoju.bus.example.service.impl;

import org.aoju.bus.base.service.impl.BaseServiceImpl;
import org.aoju.bus.core.toolkit.FileKit;
import org.aoju.bus.example.entity.CommonVersion;
import org.aoju.bus.example.mapper.CommonVersionMapper;
import org.aoju.bus.example.service.CommonPreviewService;
import org.aoju.bus.office.Provider;
import org.aoju.bus.office.Registry;
import org.aoju.bus.office.magic.family.DefaultFormatRegistry;
import org.aoju.bus.starter.office.OfficeProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

@Service
public class CommonPreviewServiceImpl extends BaseServiceImpl<CommonVersionMapper, CommonVersion>
        implements CommonPreviewService {

    @Autowired
    OfficeProviderService officeProviderService;

    @Override
    public void preview(InputStream inputStream, OutputStream outputStream, String type, String filename) {
        Provider provider = officeProviderService.require(Registry.LOCAL);
        provider.convert(inputStream)
                .as(DefaultFormatRegistry.getFormatByExtension(FileKit.getExtension(filename)))
                .to(outputStream)
                .as(DefaultFormatRegistry.getFormatByExtension(type))
                .execute();

    }

}
