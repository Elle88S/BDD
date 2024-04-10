package ru.netology;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

        private SelenideElement loginField = $("[data-test-id=login] input");
        private SelenideElement passField = $("[data-test-id=password] input");
        private SelenideElement enterButton = $("button.button");

        public VerificationPage validLogin(DataHelper.AuthInfo info) {
            loginField.setValue(info.getLogin());
            passField.setValue(info.getLogin());
            enterButton.click();
            return new VerificationPage();
        }
    }



