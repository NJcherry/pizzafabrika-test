package page;

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
        open("/");
        return this;
    }

    @Step("https://pizzafabrika.ru/vologda")
    public BasePage openMainVologda() {
        Configuration.baseUrl = "https://pizzafabrika.ru";
        open("/vologda");
        return this;
    }

    private final static SelenideElement PIZZA_SECTION = $("[data-testid='pizza']");


    @Step("Ищем раздел меню \"Пицца\"")
    public BasePage goToPizzaSection() {
        PIZZA_SECTION.click();
        return this;
    }

    @Step("Прокручиваем список и выбираем нужный товар \"{pizzaName}\"")
    public BasePage scrollAndSelectItem(String pizzaName) {
                $$("#pizza article[role='button']")
                .findBy(text(pizzaName))
                .scrollIntoView((instant().block(center)))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Кликаем на кнопку размера пиццы \"{largeSizeText}\"")
    public BasePage selectLargePizza(String largeSizeText) {
        $$("[class*='switcher_tab-label']")
                .findBy(text(largeSizeText))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Получаем цену пиццы")
    public int getPizzaPrice() {
        String pizzaPrice = $$("button").findBy(text(addToCartButtonText)).$("[class*='currency_nowrap'] span").shouldNotBe(animated).getText();
        return Integer.parseInt(pizzaPrice);
    }

    @Step("Кликаем на кнопку размера пиццы \"{mediumSizeText}\"")
    public BasePage selectMediumPizza(String mediumSizeText) {
        $$("[class*='switcher_tab-label']")
                .findBy(text(mediumSizeText))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Выбираем топпинг")
    public BasePage selectTopping(String toppingName) {
        $$("li[role='menuitem']")
                .findBy(text(toppingName))
                .scrollIntoView((instant().block(center)))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Получаем цену топпинга")
    public int getToppingPrice() {
        String toppingPrice = $$("li[role='menuitem']").findBy(text(toppingName)).$("span [class*='currency_nowrap']").getOwnText().trim();
        return Integer.parseInt(toppingPrice);
    }

    @Step("Кликаем на кнопку \"{addToCartButtonText}\"")
    public BasePage addToCart(String addToCartButtonText) {
        $$("[class*='editor-buttons_editor']")
                .findBy(text(addToCartButtonText))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Проверяем количество выбранных товаров")
    public BasePage countCartItem(String cartItemCountText) {
        $$("[class*='buy-button_small']")
                .findBy(text(cartItemCountText))
                .shouldBe(visible);
        return this;
    }

    @Step("Проверяем наличие товаров в корзине")
    public BasePage checkCart(String pizzaName, String mediumSizeText, String toppingName, int finalPrice) {
        $("[class*='entry-card_info']").shouldHave(text(pizzaName + " " + mediumSizeText), text(toppingName), text(String.valueOf(finalPrice)));
        return this;
    }
}
