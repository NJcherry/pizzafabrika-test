import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.ScrollIntoViewOptions.Block.center;
import static com.codeborne.selenide.ScrollIntoViewOptions.instant;
import static com.codeborne.selenide.Selenide.*;

public class AddToCartTest {
    private String itemPizza = "Мясное плато";

    @Test
    void cartTest() {
        open("https://pizzafabrika.ru/vologda");

        $("[data-testid='pizza']").click();

        $$("#pizza article[role='button']")
                .findBy(text(itemPizza))
                .scrollIntoView((instant().block(center)))
                .shouldBe(visible).click();

        int bigPrice = Integer.parseInt($$("button").findBy(Condition.text("В корзину")).$("[class*='currency_nowrap'] span").getText());
        System.out.println(bigPrice);

        $$("[class*='switcher_tab-label']")
                .findBy(Condition.text("Средняя"))
                .shouldBe(visible).click();

        int mediumPrice = Integer.parseInt($$("button").findBy(Condition.text("В корзину")).$("[class*='currency_nowrap'] span").getText());
        System.out.println(mediumPrice);

        Assertions.assertTrue(bigPrice > mediumPrice, "Цена на большую пиццу \"" + bigPrice + "\" должна быть больше, чем цена на среднюю \"" + mediumPrice + "\"");

        $$("li[role='menuitem']")
                .findBy(text("Шампиньоны 20г"))
                .scrollIntoView((instant().block(center)))
                .shouldBe(visible).click();

        int ingredientPrice = Integer.parseInt($$("li[role='menuitem']").findBy(text("Шампиньоны 20г")).$("span [class*='currency_nowrap']").getOwnText().trim());
        System.out.println(ingredientPrice);

        int finalPrice = Integer.parseInt($$("button").findBy(Condition.text("В корзину")).$("[class*='currency_nowrap'] span").getText());
        System.out.println(finalPrice);

        Assertions.assertEquals(finalPrice, mediumPrice + ingredientPrice, "Итоговая сумма должна увеличиться на цену топпинга");

        $$("[class*='editor-buttons_editor']")
                .findBy(Condition.text("В корзину"))
                .shouldBe(visible).click();

        $$("[class*='buy-button_small']")
                .findBy(Condition.text("В корзине 1 шт."))
                .shouldBe(visible);

        actions().sendKeys(Keys.ESCAPE).perform();

        $("[class*='entry-card_info']").shouldHave(text("Мясное плато средняя"), text("Шампиньоны 20г"), text(String.valueOf(finalPrice)));


    }
}
