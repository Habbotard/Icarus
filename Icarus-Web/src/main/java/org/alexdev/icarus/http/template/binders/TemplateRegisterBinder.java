package org.alexdev.icarus.http.template.binders;

import org.alexdev.icarus.http.util.config.Configuration;

public class TemplateRegisterBinder {

    private String publicRecaptchaKey;
    private String privateRecaptchaKey;

    public TemplateRegisterBinder() {
        this.publicRecaptchaKey = Configuration.PUBLIC_RECAPTCHA_KEY;
        this.privateRecaptchaKey = Configuration.PRIVATE_RECAPTCHA_KEY;
    }
}
