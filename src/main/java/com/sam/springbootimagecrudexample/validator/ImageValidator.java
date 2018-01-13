package com.sam.springbootimagecrudexample.validator;

import com.sam.springbootimagecrudexample.domain.ImageFileModel;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

public class ImageValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ImageFileModel.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {

        ImageFileModel imageFileModel = (ImageFileModel) o;
        MultipartFile imageFile = imageFileModel.getFile();

        if(imageFile.isEmpty()){
            errors.rejectValue("file","file.empty");
            return;
        }

        if(!(imageFile.getContentType().equals("image/jpeg") ||
                imageFile.getContentType().equals("image/png") ||
                imageFile.getContentType().equals("image/gif")
                ))
        {
            errors.rejectValue("file","file.invalid.type");
        }

    }
}
