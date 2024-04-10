

package ru.netology;

import lombok.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.DataHelper.*;

public class TestIbankAccount {
    DashboardPage dashboardPage;
    CardInfo cardInfo;
    CardInfo secondCardInfo;
    int cardBalance;
    int secondCardBalance;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();
        dashboardPage = verificationPage.validVerify(verificationCode);
        cardInfo = getCardInfo();
        secondCardInfo = getSecondCardInfo();
        cardBalance = dashboardPage.getCardBalance(cardInfo);
        secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    }

    @Test
    void shouldTransferMoney() {
        var amount = generateValidAmount(cardBalance);
        var expectedCardBalance = cardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var trasferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), cardInfo);
        var actualCardBalance = dashboardPage.getCardBalance(cardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertAll(() -> assertEquals(expectedCardBalance, actualCardBalance),
                () -> assertEquals(expectedSecondCardBalance, actualSecondCardBalance));

    }


}
