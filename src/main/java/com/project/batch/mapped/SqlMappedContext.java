package com.project.batch.mapped;

public interface SqlMappedContext {

    String INSERT_ALLOCATION =
                     """
                     INSERT INTO allocation
                         (
                             order_code,
                             vehicle,
                             tonnage,
                             loading_area,
                             unloading_area,
                             shipping_cost,
                             commission,
                             payment_type,
                             shipper_name,
                             shipper_phone,
                             information,
                             registration_date
                         )
                         VALUES
                         (
                             :화물번호,
                             :차량종류,
                             :톤수,
                             :상차지,
                             :하차지,
                             :운송료,
                             :수수료,
                             :지불방식,
                             :화주명,
                             :화주전화,
                             :화물정보,
                             :등록일자
                         )
                     """;

}
