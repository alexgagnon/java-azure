package org.example.beans;

import org.example.config.Constants;

import goc.webtemplate.component.spring.DefaultTemplateCoreBean;

public class AbstractDefaultBean extends DefaultTemplateCoreBean {
  @Override
  public String getRenderTop() {
    String url = super.getRenderTop();
    return url + "&" + Constants.LOCALE_INTERCEPTOR_PARAM + "="
        + this.getAlternateLanguage(this.getTwoLetterCultureLanguage());
  }

  private String getAlternateLanguage(String language) {
    return language.equals(goc.webtemplate.Constants.ENGLISH_ACCRONYM) ? goc.webtemplate.Constants.FRENCH_ACCRONYM
        : goc.webtemplate.Constants.ENGLISH_ACCRONYM;
  }
}
