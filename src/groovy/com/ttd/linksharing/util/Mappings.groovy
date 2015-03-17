package com.ttd.linksharing.util

import com.ttd.linksharing.enums.Visibility
import org.imgscalr.Scalr
import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class Mappings {

    static List getScopes(Boolean includePrivates) {
        includePrivates ? Visibility.values() : [Visibility.PUBLIC]
    }

    static Long parseStringOrLong(def attr, Long defaultValue = 0) {
        if (! attr) {
            return defaultValue
        }

        if (attr.class == String.class) {
            return Long.parseLong(attr)
        } else {
            return attr
        }
    }

    static Map getIdToPropertyMapping(def properties) {
        Map result = [:]
        properties.each { row ->
            result[row[0]] = row[1]
        }
        result
    }

    static void setScaledImage(MultipartFile file, def co) {
        if(! file?.size) {
            return
        }

        co.avatarType = file.contentType

        BufferedImage image = ImageIO.read(file.getInputStream())

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write( Scalr.resize(image, 150), "jpg", outputStream );
        outputStream.flush();

        co.photo = outputStream.toByteArray()
        co.validate()
    }
}
