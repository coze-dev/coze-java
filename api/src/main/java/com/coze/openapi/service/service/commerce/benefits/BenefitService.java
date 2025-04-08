package com.coze.openapi.service.service.commerce.benefits;

import com.coze.openapi.api.CommerceBenefitBillAPI;
import com.coze.openapi.api.CommerceBenefitLimitationAPI;

public class BenefitService {
  private final LimitationService limitationAPI;

  private final BillService billAPI;

  public BenefitService(
      CommerceBenefitLimitationAPI limitationAPI, CommerceBenefitBillAPI billAPI) {
    this.limitationAPI = new LimitationService(limitationAPI);
    this.billAPI = new BillService(billAPI);
  }

  public LimitationService limitations() {
    return this.limitationAPI;
  }

  public BillService bills() {
    return this.billAPI;
  }
}
