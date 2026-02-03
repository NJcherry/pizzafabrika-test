package ui_pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.ScrollIntoViewOptions.Block.center;
import static com.codeborne.selenide.ScrollIntoViewOptions.instant;
import static com.codeborne.selenide.Selenide.*;

public class BasePage {
    public String addToCartButtonText = "В корзину";
    public String toppingName = "Шампиньоны 20г";

    @Step("https://pizzafabrika.ru")
    public BasePage openMain() {
        Configuration.baseUrl = "https://pizzafabrika.ru";
        Selenide.open("/");
        return this;
    }

    @Step("https://pizzafabrika.ru/vologda")
    public BasePage openMainVologda() {
        Configuration.baseUrl = "https://pizzafabrika.ru";
        Selenide.open("/vologda");
        return this;
    }

    private final static SelenideElement PIZZA_SECTION = Selenide.$("[data-testid='pizza']");


    @Step("Ищем раздел меню \"Пицца\"")
    public BasePage goToPizzaSection() {
        PIZZA_SECTION.click();
        return this;
    }

    @Step("Прокручиваем список и выбираем нужный товар \"{pizzaName}\"")
    public BasePage scrollAndSelectItem(String pizzaName) {
                Selenide.$$("#pizza article[role='button']")
                .findBy(Condition.text(pizzaName))
                .scrollIntoView((ScrollIntoViewOptions.instant().block(Block.center)))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Кликаем на кнопку размера пиццы \"{largeSizeText}\"")
    public BasePage selectLargePizza(String largeSizeText) {
        Selenide.$$("[class*='switcher_tab-label']")
                .findBy(Condition.text(largeSizeText))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Получаем цену пиццы")
    public int getPizzaPrice() {
        String pizzaPrice = Selenide.$$("button").findBy(Condition.text(addToCartButtonText)).$("[class*='currency_nowrap'] span").shouldNotBe(Condition.animated).getText();
        return Integer.parseInt(pizzaPrice);
    }

    @Step("Кликаем на кнопку размера пиццы \"{mediumSizeText}\"")
    public BasePage selectMediumPizza(String mediumSizeText) {
        Selenide.$$("[class*='switcher_tab-label']")
                .findBy(Condition.text(mediumSizeText))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Выбираем топпинг")
    public BasePage selectTopping(String toppingName) {
        Selenide.$$("li[role='menuitem']")
                .findBy(Condition.text(toppingName))
                .scrollIntoView((ScrollIntoViewOptions.instant().block(Block.center)))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Получаем цену топпинга")
    public int getToppingPrice() {
        String toppingPrice = Selenide.$$("li[role='menuitem']").findBy(Condition.text(toppingName)).$("span [class*='currency_nowrap']").getOwnText().trim();
        return Integer.parseInt(toppingPrice);
    }

    @Step("Кликаем на кнопку \"{addToCartButtonText}\"")
    public BasePage addToCart(String addToCartButtonText) {
        Selenide.$$("[class*='editor-buttons_editor']")
                .findBy(Condition.text(addToCartButtonText))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Проверяем количество выбранных товаров")
    public BasePage countCartItem(String cartItemCountText) {
        Selenide.$$("[class*='buy-button_small']")
                .findBy(Condition.text(cartItemCountText))
                .shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверяем наличие товаров в корзине")
    public BasePage checkCart(String pizzaName, String mediumSizeText, String toppingName, int finalPrice) {
        Selenide.$("[class*='entry-card_info']").shouldHave(Condition.text(pizzaName + " " + mediumSizeText), Condition.text(toppingName), Condition.text(String.valueOf(finalPrice)));
        return this;
    }
}
