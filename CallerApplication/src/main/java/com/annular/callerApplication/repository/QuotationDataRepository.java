package com.annular.callerApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.annular.callerApplication.model.QuotationData;

@Repository
public interface QuotationDataRepository extends MongoRepository<QuotationData,String>{

}
