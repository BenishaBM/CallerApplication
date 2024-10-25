package com.annular.callerApplication.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationData {

	@Id
	private String QuotationDataId;
	
    @Field("Service Type")
    private String serviceType;

    @Field("Cost")
    private String cost;

    @Field("Weight")
    private String weight;

    @Field("Dimensions")
    private String dimensions;

    @Field("Estimated Delivery Time")
    private String estimatedDeliveryTime;

    @Field("Transit Time")
    private String transitTime;

    @Field("Validity")
    private String validity;
    
    @Field("mailDetailsId")
    private String mailDetailsId;

 // Add a summarized field if you want to include it in the response
    public String getSummarized() {
        return String.format("Service Type: %s. Cost: %s. Weight: %s. Transit Time: %s.", 
                             serviceType, cost, weight, transitTime);
    }


}
