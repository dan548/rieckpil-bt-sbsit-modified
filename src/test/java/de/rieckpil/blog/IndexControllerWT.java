package de.rieckpil.blog;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.core.env.Environment;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.Testcontainers;

import static com.codeborne.selenide.Selenide.*;

@ExtendWith({ScreenshotOnFailureExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerWT {

  @LocalServerPort
  private int port;

  static BrowserWebDriverContainer<?> webDriverContainer =
    new BrowserWebDriverContainer<>()
      .withCapabilities(new FirefoxOptions());

  @RegisterExtension
  public static ScreenShooterExtension screenShooterExtension =
    new ScreenShooterExtension().to("target/selenide");

  @BeforeAll
  static void beforeAll(@Autowired Environment environment) {
    Testcontainers.exposeHostPorts(environment.getProperty("local.server.port", Integer.class));
    webDriverContainer.start();
  }

  @Test
  void shouldDisplayMessage() {
    Configuration.timeout = 2000;
    Configuration.baseUrl = String.format("http://host.testcontainers.internal:%d/", port);

    RemoteWebDriver remoteWebDriver = webDriverContainer.getWebDriver();
    WebDriverRunner.setWebDriver(remoteWebDriver);

    open("/sampleInputs");

    screenshot("emptyInputs");

    $(By.className("form__date")).setValue("2021-08-18");
    $(By.className("form__date-time")).setValue("2021-08-18T12:24");

    Configuration.fastSetValue = true;
    $(By.className("form__color")).setValue("#5f2ac0");
    Configuration.fastSetValue = false;
    $(By.className("form__string")).setValue("ass we can");
    $(By.className("form__double")).setValue("14.88");
    $(By.className("form__int")).setValue("1337");

    screenshot("beforeSubmit");

    $(By.cssSelector("button")).click();

    screenshot("afterSubmit");

    $(By.id("date")).shouldBe(Condition.ownText("Wed Aug 18 00:00:00 OMST 2021"));
    $(By.id("number")).shouldBe(Condition.ownText("1337"));
    $(By.id("color")).shouldBe(Condition.ownText("#5f2ac0"));
    $(By.id("text")).shouldBe(Condition.ownText("ass we can"));
    $(By.id("double")).shouldBe(Condition.ownText("14.88"));
    $(By.id("date-time")).shouldBe(Condition.ownText("Wed Aug 18 12:24:00 OMST 2021"));

    $(By.tagName("a")).shouldBe(Condition.ownText("Fill the form again"));
  }
}
