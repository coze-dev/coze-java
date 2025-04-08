package com.coze.openapi.service.service.commerce;

import com.coze.openapi.api.*;
import com.coze.openapi.service.service.commerce.benefits.BenefitService;

public class CommerceService {
  private final BenefitService benefitAPI;

  public CommerceService(
      CommerceBenefitLimitationAPI limitationAPI, CommerceBenefitBillAPI billAPI) {
    this.benefitAPI = new BenefitService(limitationAPI, billAPI);
  }

  public BenefitService benefits() {
    return this.benefitAPI;
  }
}
