package com.annular.callerApplication.webModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationDataWebModel {

    private String serviceType;
    private String cost;
    private String weight;
    private String dimensions;
    private String estimatedDeliveryTime;
    private String transitTime;
    private String validity;
    private String summarized;

}