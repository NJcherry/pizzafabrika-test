package tests;

import base.PageManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class AddToCartTest extends PageManager {
    private String pizzaName = "Мясное плато";
    private String toppingName = "Шампиньоны 20г";
    private String addToCartButtonText = "В корзину";
    private String largeSizeText = "Большая";
    private String mediumSizeText = "Средняя";
    private String cartItemCountText = "В корзине 1 шт.";

    @Test
    @DisplayName("Добавление товара в корзину")
    void addPizzaToCartTest() {
        step("Открываем главную страницу", () -> {
            base.openMainVologda();
        });

        step("Открываем раздел \"Пицца\"", () -> {
            base.goToPizzaSection();
        });

        step("Выбираем нужный товар", () -> {
            base.scrollAndSelectItem(pizzaName);
        });

        step("Выбираем большой размер пиццы", () -> {
            base.selectLargePizza(largeSizeText);
        });

        int largeSizePrice = base.getPizzaPrice();

        step("Выбираем средний размер пиццы", () -> {
            base.selectMediumPizza(mediumSizeText);
        });

        int mediumSizePrice = base.getPizzaPrice();

        Assertions.assertTrue(largeSizePrice > mediumSizePrice, "Цена на большую пиццу \"" + largeSizePrice + "\" должна быть больше, чем цена на среднюю \"" + mediumSizePrice + "\"");

        step("Выбираем доп. ингредиент", () -> {
            base.selectTopping(toppingName);
        });

        int ingredientPrice = base.getToppingPrice();

        int finalPrice = base.getPizzaPrice();

        Assertions.assertEquals(finalPrice, mediumSizePrice + ingredientPrice, "Итоговая сумма должна увеличиться на цену топпинга");

        step("Добавляем товар в корзину", () -> {
            base.addToCart(addToCartButtonText);
        });

        step("Проверяем количество добавленных товаров", () -> {
            base.countCartItem(cartItemCountText);
        });

        actions().sendKeys(Keys.ESCAPE).perform();

       step("Проверяем наличие товара в корзине", () -> {
           base.checkCart(pizzaName, mediumSizeText, toppingName, finalPrice);
       });
    }
}
